package uk.co.birchlabs;

import catRecurserPkg.Filter;
import catRecurserPkg.VocabListRow;
import catRecurserPkg.Vocablist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamiebirch on 23/06/2016.
 */
@Service
public class JMDictPronunciationService {
    @Autowired
    JMDictPronunciationRepository2 jmDictPronunciationRepository2;

    @Autowired
    JMDictPronunciationRepository jmDictPronunciationRepository;

    public Test6Model test6(
            String input
    ) {
        Vocablist vocablist = new Vocablist(input, Vocablist.Filtering.MANDATORY);
        List<VocabListRow> sortedByFreq = vocablist.getSortedByFreq();

        List<VocabListRowCumulative> cumulative = new ArrayList<>();
        List<String> baseForms = new ArrayList<>();
        List<String> readings = new ArrayList<>();

        final int s = vocablist.getTokenCount().size();
        float runningPercent = 0;
        for (int i = 0; i < sortedByFreq.size(); i++) {
            VocabListRow vocabListRow = sortedByFreq.get(i);
            baseForms.add(vocabListRow.getToken().getBaseForm());
            baseForms.add(vocabListRow.getToken().getReading());
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
        // size == 0 for some reason
        jmDictPronunciationRepository2.getSome(readings);

        return new Test6Model(cumulative);
    }
}
