package uk.co.birchlabs;

import catRecurserPkg.*;
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

    private static Integer PERCENT_TO_DECIMAL = 100;

    public Test6Model test6(Integer partition, Float percentLimit, String input) {

        String nerima = null, nihon = null, eva = null;
        try {
            nerima = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/nerima.txt")));
            nihon = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/nihon.txt")));
            eva = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/evangelion.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vocablist unsortedVocablist = new Vocablist(
//                "よしのり"
//                input
//                nerima +
//                nihon +
//               eva
//                "東京23区の中では最も新しく誕生した区で。"
                "東京23区の中では最も新しく誕生した区で、板橋区の一部だった旧北豊島郡練馬町・上練馬村・中新井村・石神井村・大泉村の区域が1947年8月1日に分離して発足した。当時、広大な板橋区の区役所までの経路が遠く、著しく不便であったことが分離の要因とされている。板橋区は練馬、石神井に行政派出所（後に、支所）を設けたが、行政サービスは極めて限られていたため、西武池袋線沿線を中心として生活する住民の要望にこたえる形で分離された[1]。\n" +
                "練馬区は緑の多い閑静な住宅街であり、最低居住面積水準未満の世帯率は東京23区で最も低い。練馬区民の男性の平均寿命は81.2歳で全国で第5位、東京23区で第1位である[2]。また刑法犯認知件数は、人口が60万人以上のほぼ同規模の特別区[3]の中で大田区に次いで少ない[4]。人口は約70万人で、23区中世田谷区に次いで多い。近年は副都心線や大江戸線などの開通に伴って、マンション建設ラッシュに沸いている。"
                ,
                Vocablist.Filtering.MANDATORY
        );
        // TODO: enable the return of a List<Sentence> in response to an input ForwardingToken's hash
        SetMultimap<ForwardingToken, Sentence> exampleSentences = unsortedVocablist.getTokensToSentences();
        List<VocabListRow> sortedByFreq = unsortedVocablist.getSortedByFreq();

        List<VocabListRowCumu> cumulative = buildVocabListCumu(percentLimit, unsortedVocablist, sortedByFreq);

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
                                        exampleSentences,
                                        wordEntries,
                                        entriesByMecabPOSHiragana,
                                        entriesByMecabPOSKatakana
                                )
                )
                .collect(Collectors.toList());

        // Partition the list only if partition size is non-zero.
        if(partition.equals(0)) return new Test6Model(list);
        else return new Test6Model(Lists.partition(list, partition).get(0));
    }


    private List<VocabListRowCumu> buildVocabListCumu(Float limit, Vocablist vocablist, List<VocabListRow> sortedByFreq) {
        List<VocabListRowCumu> cumulative = new ArrayList<>();

        final int s = vocablist.getTokenCount().size();
        float runningPercent = 0;
        for (int i = 0; i < sortedByFreq.size(); i++) {
            VocabListRow vocabListRow = sortedByFreq.get(i);
            float myPercent = (float)vocabListRow.getCount() / (float)s;
            runningPercent += myPercent;
            if(runningPercent > (limit/PERCENT_TO_DECIMAL)) return cumulative; // bail out early if limit has been reached.
            boolean fundamental = Vocablist.filterOut(vocabListRow.getToken(), Vocablist.Filtering.FUNDAMENTAL);
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
