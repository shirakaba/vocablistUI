package uk.co.birchlabs;

import catRecurserPkg.*;
import com.google.common.base.Strings;
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

    public Test6Model test6(String input) {

        String nerima = null, nihon = null, eva = null;
        try {
            nerima = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/nerima.txt")));
            nihon = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/nihon.txt")));
            eva = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/evangelion.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vocablist vocablist = new Vocablist(
//                "天皇皇后がタイ、マレーシア、インドネシア、シンガポール、フィリピンを訪問している。"
//                input,
//                nerima +
//                nihon +
               eva
                ,
                Vocablist.Filtering.MANDATORY
        );
        List<VocabListRow> sortedByFreq = vocablist.getSortedByFreq();

        List<VocabListRowCumulative> cumulative = new ArrayList<>();

        final int s = vocablist.getTokenCount().size();
        float runningPercent = 0;
        for (int i = 0; i < sortedByFreq.size(); i++) {
            VocabListRow vocabListRow = sortedByFreq.get(i);
            float myPercent = (float)vocabListRow.getCount() / (float)s;
            runningPercent += myPercent;
            boolean fundamental = Vocablist.filterOut(vocabListRow.getToken(), Vocablist.Filtering.FUNDAMENTAL);
            boolean n5 = Filter.N5_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean n4 = Filter.N4_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean n3 = Filter.N3_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean n2 = Filter.N2_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean n1 = Filter2.N1_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            cumulative.add(new VocabListRowCumulative(vocabListRow,
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
                                        row,
                                        wordEntries,
                                        entriesByMecabPOSHiragana,
                                        entriesByMecabPOSKatakana
                                )
                )
                .collect(Collectors.toList());

        return new Test6Model(list);
    }
}
