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

        Set<ForwardingToken> tokensToSearch = new HashSet<>();
        sortedByFreq.forEach(vocablistRow -> tokensToSearch.add(vocablistRow.getToken()));

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

        return new Test6Model(list);
    }
}
