package uk.co.birchlabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jamiebirch on 21/06/2016.
 */
@RestController
public class MyRestController {

//    @Autowired
//    JMDictPronRepo JMDictPronRepo;
//
//    @Autowired
//    JMDictPronunciationRepository2 jmDictPronunciationRepository2;

    @Autowired
    JMDictPronService jmDictPronService;

//    @Autowired
//    EntityManager em;

//    @RequestMapping("/thing")
//    public JMDictPron thing(
//            @RequestParam Integer id
//    ) {
////        return jmDictPronunciationRepository2.getSome()
//        return JMDictPronRepo.findOne(id);
//    }

//    @RequestMapping("/test5")
//    public List<VocabListRowCumu> test5(
//            // eg. http://localhost:8080/test4?input="この俺が俺です。"
//            @RequestParam(name="input", defaultValue = "するためにしない。する為に行く。何のためにした？自分の為。する為。") String input
//    ) {
//        Vocablist vocablist = new Vocablist(input, Vocablist.Filtering.MANDATORY);
//        List<VocabListRow> sortedByFreq = vocablist.getSortedByFreq();
//
//        List<VocabListRowCumu> list = new ArrayList<>();
//
//        final int s = vocablist.getTokenCount().size();
//        float runningPercent = 0;
//        for (int i = 0; i < sortedByFreq.size(); i++) {
//            VocabListRow vocabListRow = sortedByFreq.get(i);
//            float myPercent = (float)vocabListRow.getCount() / (float)s;
//            runningPercent += myPercent;
//            list.add(new VocabListRowCumu(vocabListRow, myPercent, runningPercent));
//        }
//
//        return list;
//    }

    @RequestMapping("/test6")
    public Test6Model test6(
            // eg. http://localhost:8080/test4?input="この俺が俺です。"
            @RequestParam(name="partition", defaultValue = "0") Integer partition,
            @RequestParam(name="limit", defaultValue = "95") Float limit,
            @RequestParam(name="input", defaultValue = "するためにしない。する為に行く。何のためにした？自分の為。する為。") String input
    ) {
        return jmDictPronService.test6(partition, limit, input);
    }
}
