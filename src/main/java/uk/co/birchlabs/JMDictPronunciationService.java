package uk.co.birchlabs;

import catRecurserPkg.Filter;
import catRecurserPkg.ForwardingToken;
import catRecurserPkg.VocabListRow;
import catRecurserPkg.Vocablist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
            boolean JLPT4 = Filter.JLPT4_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean JLPT3 = Filter.JLPT3_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean JLPT2 = Filter.JLPT2_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            boolean JLPT1 = Filter.JLPT1_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
            cumulative.add(new VocabListRowCumulative(vocabListRow,
                    myPercent,
                    runningPercent,
                    fundamental,
                    JLPT4,
                    JLPT3,
                    JLPT2,
                    JLPT1
            ));
        }
        // size == 631. ids all correspond to a unique word (in a prior, non-provided table), but one id may appear twice
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

        // TODO: pass a list of tokens to both getSome() methods instead of a list of processed Tokens so that they can be subtracted from.
        Iterable<JMDictWord> idWordPairs = jmDictWordRepository2.getSome(tokensToSearch);
        // Identify the set of all baseForms successfully found in idWordPairs, then subtract their corresponding Tokens
        // from search-set for the next search on pronunciations.
        Iterable<JMDictPronunciation> idReadingPairs = jmDictPronunciationRepository2.getSome(tokensToSearch);
        // Now we have ids for all possible baseForms and all possible Readings, so we must map them back to the sorted
        // vocabListRows. Must map the baseForms first and declare them to be "dealt with" before mapping the readings
        // (which could equally
        // apply to many of the baseForms and thus wreak havoc).


        return new Test6Model(cumulative);
    }
}
