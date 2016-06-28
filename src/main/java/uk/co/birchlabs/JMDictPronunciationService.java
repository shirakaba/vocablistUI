package uk.co.birchlabs;

import catRecurserPkg.*;
import com.google.common.collect.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static uk.co.birchlabs.JMDictPronunciationRepository2.Mode.*;

/**
 * Created by jamiebirch on 23/06/2016.
 */
@Service
public class JMDictPronunciationService {
    @Autowired
    JMDictPronunciationRepository2 jmDictPronunciationRepository2;

    @Autowired
    JMDictWordRepository2 jmDictWordRepository2;

//    @Autowired
//    JMDictPronunciationRepository jmDictPronunciationRepository;

    public Test6Model test6(String input) {
        Vocablist vocablist = new Vocablist(input, Vocablist.Filtering.MANDATORY);
        List<VocabListRow> sortedByFreq = vocablist.getSortedByFreq();

        List<VocabListRowCumulative> cumulative = new ArrayList<>();

        final int s = vocablist.getTokenCount().size();
        float runningPercent = 0;
        for (int i = 0; i < sortedByFreq.size(); i++) {
            VocabListRow vocabListRow = sortedByFreq.get(i);
//            baseForms.add(vocabListRow.getToken().getBaseForm());
//            readings.add(Utils.convertKana(vocabListRow.getToken().getReading()));
            float myPercent = (float)vocabListRow.getCount() / (float)s;
            runningPercent += myPercent;
            // Evaluates to true if FUNDAMENTAL filtering level excludes the Token.
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
        // size == 631 if you don't subtract the Tokens for which baseForms are identifiable. ids all correspond to a unique word (in a prior, non-provided table), but one id may appear twice
        // due to having two valid readings. Ordering is purely by word id, so unrelated words written as 'ikou' aren't
        // necessarily in perfect sequence.         size == 115. Note that sortedByFreq has size 159.
        // [[0] {id=1001420, data="へ"} ...          [0] {id=1151260, data="悪い"},
        // [18] {id=1155870, data="いこう"},          [1] {id=1155110, data="以降"},
        // [19] {id=1156280, data ="いこう"},         [2] {id=1158390, data="移転"}...
        // [20] {id=1156990, data ="じょう"}...       [9] {id=1215230, data="間"},
        // [23] {id=1158270, data ="いこう"} ...].   [10] {id=1215240, data="間"},
        // Joining to JMDictWord might be hairy because one id can link to variant kanji as well as variant readings, so
        // we'd get a lot duplication within rows. So get JMDictWord separately.

        Set<ForwardingToken> tokensToSearch = new HashSet<>();
        sortedByFreq.forEach(vocablistRow -> tokensToSearch.add(vocablistRow.getToken()));
//        List<String> readings = new ArrayList<>();

        // Searches jmdict_word for tokens by their baseForms
        // (for list entries likely to have at least one kanji such as 作る、日、又)
        List<JMDictWord> idWordPairs = Lists.newArrayList(jmDictWordRepository2.getSome(tokensToSearch));
        Set<String> wordsFound = new HashSet<>();
        idWordPairs.forEach(word -> wordsFound.add(word.getIdDataKey().getData()));
        tokensToSearch.removeIf(token -> wordsFound.contains(token.getBaseForm())); // tokensToSearch goes from 159 -> 66 here.

        // Searches jmdict_pronunciation for any still-unfound tokens by their readings converted into hiragana
        // (for list entries likely rendered without any kanji such as する、ある、いる、として).
        List<JMDictPronunciation> idReadingPairs = Lists.newArrayList(jmDictPronunciationRepository2.getSome(tokensToSearch, READINGS_IN_HIRAGANA));
        idReadingPairs.forEach(reading -> wordsFound.add(Utils.convertKana(reading.getIdDataKey().getData())));
        tokensToSearch.removeIf(token -> wordsFound.contains(token.getReading())); // tokensToSearch goes from 66 -> 17 here.

        // Searches jmdict_pronunciation for the remaining tokens by their katakana readings
        // (for list entries likely to be loan words such as キャンパス)
        List<JMDictPronunciation> idReadingPairs2 = Lists.newArrayList(jmDictPronunciationRepository2.getSome(tokensToSearch, READINGS_IN_KATAKANA));
//        Lists.newArrayList(Iterables.concat(idReadingPairs, idReadingPairs2));
        Iterables
                .concat(idReadingPairs, idReadingPairs2)
                .forEach(reading -> wordsFound.add(reading.getIdDataKey().getData()));
        tokensToSearch.removeIf(token -> wordsFound.contains(token.getReading()));  // tokensToSearch goes from 17 -> 11 here.
        // Remaining Tokens are generally proper nouns or auxiliary verb stems.

        // Now we have ids for all possible baseForms and all possible Readings, so we must map them back to the sorted
        // vocabListRows. Must map the baseForms first and declare them to be "dealt with" before mapping the readings
        // (which could equally apply to many of the baseForms and thus wreak havoc).
        // TODO: query to retrieve definitions for all ids listed in idWordPairs and idReadingsPairs1/2. This will first involve mapping entries to searchable ids as follows [may involve stream.filter()]:
        // TODO: map each entry in the cumulative list to a set of ids for cases where their baseForm matches a member of idWordPairs.getData()...
        // TODO: ... then, for yet-unassigned entries, map them each to a set of ids for cases where their baseForm (hiragana) matches a member of idReadingPairs.getData() (hiragana)...
        // TODO: ... finally, for yet-unassigned entries, map them each to a set of ids for cases where their reading (katakana) matches a member of idReadingPairs2.getData() (katakana)...
        // TODO: also assess the efficiency of doing idReadingPairs as just one query's operation (searching EVERY reading simultaneously in both hiragana and katakana)


//        Multimap<VocabListRowCumulative, Integer> listmap = new
//        idWordPairs               [based on getBaseForm()]
//        idReadingPairs (hiragana) [based on getReading()]
//        idReadingPairs2 (katakana) [based on getReading()]
        List<VocabListRowCumulativeMapped> list = cumulative
                .stream()
                .map(
                        row ->
                                new VocabListRowCumulativeMapped(
                                        row,
                                        idWordPairs,
                                        idReadingPairs,
                                        idReadingPairs2
                                )
                )
                .collect(Collectors.toList());

//        Iterables.filter(cumulative, row -> row.getVocabListRow().getToken().getBaseForm())
//        cumulative.stream().filter(row -> row.getVocabListRow().getToken().getBaseForm().equals())
//        for (VocabListRowCumulative row : cumulative) {
//            if(row.getVocabListRow().getToken().getBaseForm())
//        }

//        idWordPairs.forEach(pair -> pair.getData());
//        cumulative.forEach(row -> row.getVocabListRow().getToken().getBaseForm());

        // [14] {id=1153670, data=やすい}, // [15] {id=1156990, data=やすい}... // [33] {id=1296400, data=ある}
        return new Test6Model(list);
    }
}
