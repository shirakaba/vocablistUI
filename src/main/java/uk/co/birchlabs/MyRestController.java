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

//    @Autowired
//    JMDictPronunciationRepository JMDictPronunciationRepository;
//
//    @Autowired
//    JMDictPronunciationRepository2 jmDictPronunciationRepository2;

    @Autowired
    JMDictPronunciationService jmDictPronunciationService;

//    @RequestMapping("/thing")
//    public JMDictPronunciation thing(
//            @RequestParam Integer id
//    ) {
////        return jmDictPronunciationRepository2.getSome()
//        return JMDictPronunciationRepository.findOne(id);
//    }

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
        return jmDictPronunciationService.test6(input);
    }
}
