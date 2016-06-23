package uk.co.birchlabs;

import catRecurserPkg.Filter;
import catRecurserPkg.VocabListRow;
import catRecurserPkg.Vocablist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamiebirch on 21/06/2016.
 */
@RestController
public class MyRestController {

    @Autowired
    JMDictPronunciationRepository JMDictPronunciationRepository;

    @RequestMapping("/thing")
    public JMDictPronunciation thing(
            @RequestParam Integer id
    ) {
        return JMDictPronunciationRepository.findOne(id);
    }

//    @RequestMapping("/test5")
//    public List<VocabListRowCumulative> test5(
//            // eg. http://localhost:8080/test4?input="この俺が俺です。"
//            @RequestParam(name="input", defaultValue = "するためにしない。する為に行く。何のためにした？自分の為。する為。") String input
//    ) {
//        Vocablist vocablist = new Vocablist(input, Vocablist.Filtering.MANDATORY);
//        List<VocabListRow> sortedByFreq = vocablist.getSortedByFreq();
//
//        List<VocabListRowCumulative> cumulative = new ArrayList<>();
//
//        final int s = vocablist.getTokenCount().size();
//        float runningPercent = 0;
//        for (int i = 0; i < sortedByFreq.size(); i++) {
//            VocabListRow vocabListRow = sortedByFreq.get(i);
//            float myPercent = (float)vocabListRow.getCount() / (float)s;
//            runningPercent += myPercent;
//            cumulative.add(new VocabListRowCumulative(vocabListRow, myPercent, runningPercent));
//        }
//
//        return cumulative;
//    }

    @RequestMapping("/test6")
    public Test6Model test6(
            // eg. http://localhost:8080/test4?input="この俺が俺です。"
            @RequestParam(name="input", defaultValue = "するためにしない。する為に行く。何のためにした？自分の為。する為。") String input
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

        return new Test6Model(cumulative);
    }
}
