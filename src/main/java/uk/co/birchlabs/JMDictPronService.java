package uk.co.birchlabs;

import catRecurserPkg.*;
import catRecurserPkg.Vocablist.Filtering;
import com.google.common.collect.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jamiebirch on 23/06/2016.
 */
@Service
public class JMDictPronService {
    @Autowired
    JMDictEntryRepo2 jmDictEntryRepo2;

    @Autowired
    EntriesByMecabPOSService entriesByMecabPOSService;

    @Autowired
    JMDictPronRepo2 jmDictPronRepo2;

    @Autowired
    JMDictWordRepo2 jmDictWordRepo2;

//    @Autowired
//    JMDictPronRepo jmDictPronunciationRepository;

    public static Integer PERCENT_TO_DECIMAL = 100;

    public Test6Model test6(boolean makeQuiz, Integer maxArticles, String filtering, Integer egs, Float minYield, Integer partition, Float percentLimit, String input) {

        if(makeQuiz){
            maxArticles = 1;
            filtering = "n3"; // only n2 and above accepted (being the topic-specific vocab starting level: http://www.jlpt.jp/e/about/levelsummary.html)
//            egs = 0;
            minYield = new Float(0.0);
            partition = 0;
            percentLimit = new Float(100.0);
            input = "test";
        }

        Filtering filteringEnum = determineFiltering(filtering);
        Vocablist unsortedVocablist;

        if(input.equals("test")){
            String nerima = null, nihon = null, eva = null;
            try {
                nerima = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/nerima.txt")));
                nihon = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/nihon.txt")));
                eva = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/evangelion.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            unsortedVocablist = new Vocablist(eva, filteringEnum);
        }
        else {
            HashSet<String> pagesReturned;
            try {
                pagesReturned = ExpandWikipediaCategories.retrieveArticlesFromCategoryRecursively(
                        input,
                        ExpandWikipediaCategories.SupportedLocales.JAPANESE,
                        maxArticles,
                        false
                );
                unsortedVocablist = Vocablist.addTokensFromListOfWikipediaArticles(pagesReturned, filteringEnum);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                throw new IllegalStateException("Vocab list was unable to be built from category specified.");
            }
        }

        SetMultimap<ForwardingToken, Sentence> exampleSentences = unsortedVocablist.getTokensToSentences();

        List<VocabListRow> sortedByFreq = unsortedVocablist.getSortedByFreq();

        List<VocabListRowCumu> cumulative = buildVocabListCumu(minYield, percentLimit, unsortedVocablist, sortedByFreq);

        Set<ForwardingToken> tokensToSearch = new HashSet<>();
        Set<ForwardingToken> tokensToSearchInProperNouns = new HashSet<>();
        sortedByFreq.forEach(vocablistRow -> {
            ForwardingToken token = vocablistRow.getToken();
            if(TokensByMecabPOS.determinePOS(token).equals(JMDictPronRepo2.POS.properNouns)) tokensToSearchInProperNouns.add(token);
            else tokensToSearch.add(token);
        });

        List<JMDictEntry> wordEntries = Lists.newArrayList(jmDictEntryRepo2.getEntries(tokensToSearch, true));
        List<JMDictEntry> wordEntriesFromProperNouns = Lists.newArrayList(jmDictEntryRepo2.getEntries(tokensToSearchInProperNouns, false));

        HashSet<String> baseFormsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(
                Lists.newArrayList(Iterables.concat(wordEntries, wordEntriesFromProperNouns)),
                JMDictEntryRepo2.CollectionMode.word
        );
        wordEntries.addAll(wordEntriesFromProperNouns);
        tokensToSearch.addAll(tokensToSearchInProperNouns);
        tokensToSearch.removeIf(token -> baseFormsFound.contains(token.getBaseForm()));

        TokensByMecabPOS tokensByMecabPOS = new TokensByMecabPOS(tokensToSearch);


        EntriesByMecabPOS entriesByMecabPOSHiragana = entriesByMecabPOSService.construct(tokensByMecabPOS, JMDictPronRepo2.Mode.READINGS_IN_HIRAGANA);

        // List of all JMDictEntrys with a valid hiragana reading
        PronsFoundByMecabPOS pronsFoundByMecabPOSHiragana = new PronsFoundByMecabPOS(entriesByMecabPOSHiragana);

        TokensByMecabPOS.updateTokensRemainingToBeSearched(tokensByMecabPOS, pronsFoundByMecabPOSHiragana, tokensToSearch, JMDictPronRepo2.Mode.READINGS_IN_HIRAGANA);

        EntriesByMecabPOS entriesByMecabPOSKatakana = entriesByMecabPOSService.construct(tokensByMecabPOS, JMDictPronRepo2.Mode.READINGS_IN_KATAKANA);

        PronsFoundByMecabPOS pronsFoundByMecabPOSKatakana = new PronsFoundByMecabPOS(entriesByMecabPOSKatakana);

        TokensByMecabPOS.updateTokensRemainingToBeSearched(tokensByMecabPOS, pronsFoundByMecabPOSKatakana, tokensToSearch, JMDictPronRepo2.Mode.READINGS_IN_KATAKANA);

        List<VocabListRowCumulativeMapped> list = cumulative
                .stream()
                .map(
                        row ->
                                new VocabListRowCumulativeMapped(
                                        egs,
                                        row,
                                        exampleSentences,
                                        wordEntries,
                                        entriesByMecabPOSHiragana,
                                        entriesByMecabPOSKatakana
                                )
                )
                .collect(Collectors.toList());

        if(partition.equals(0)) return new Test6Model(list, makeQuiz);
        else return new Test6Model(Lists.partition(list, partition).get(0), makeQuiz);
    }


    private Filtering determineFiltering(String filtering) {
        Filtering filteringEnum;
        switch (filtering){
            case "mandatory":
                filteringEnum = Filtering.MANDATORY;
                break;
            case "fundamental":
                filteringEnum = Filtering.FUNDAMENTAL;
                break;
            case "n5":
                filteringEnum = Filtering.n5;
                break;
            case "n4":
                filteringEnum = Filtering.n4;
                break;
            case "n3":
                filteringEnum = Filtering.n3;
                break;
            case "n2":
            filteringEnum = Filtering.n2;
                break;
            case "n1":
                filteringEnum = Filtering.n1;
                break;
            default:
                filteringEnum = Filtering.MANDATORY;
        }
        return filteringEnum;
    }


    private List<VocabListRowCumu> buildVocabListCumu(Float minimumYield, Float limit, Vocablist vocablist, List<VocabListRow> sortedByFreq) {
        List<VocabListRowCumu> cumulative = new ArrayList<>();

        final int s = vocablist.getTokenCount().size();
        float runningPercent = 0;
        for (int i = 0; i < sortedByFreq.size(); i++) {
            VocabListRow vocabListRow = sortedByFreq.get(i);
            float myPercent = (float)vocabListRow.getCount() / (float)s;
            runningPercent += myPercent;
            if(myPercent < (minimumYield/PERCENT_TO_DECIMAL)) return cumulative; // bail out early if token's yield too low.
            if(runningPercent > (limit/PERCENT_TO_DECIMAL)) return cumulative; // bail out early if cumulative limit is reached.
            boolean fundamental = Vocablist.filterOut(vocabListRow.getToken(), Filtering.FUNDAMENTAL);
            boolean n5 = Filter.N5_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean n4 = Filter.N4_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean n3 = Filter.N3_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean n2 = Filter.N2_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean n1 = Filter2.N1_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            cumulative.add(new VocabListRowCumu(vocabListRow,
                    myPercent,
                    runningPercent,
                    fundamental,
                    n5,
                    n4,
                    n3,
                    n2,
                    n1
            ));
        }
        return cumulative;
    }
}
