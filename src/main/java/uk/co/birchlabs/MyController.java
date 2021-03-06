package uk.co.birchlabs;

import catRecurserPkg.VocabListRow;
import catRecurserPkg.Vocablist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamiebirch on 21/06/2016.
 */
@Controller
public class MyController {

    MyCoolService myCoolService;

    JMDictPronRepo JMDictPronRepo;


    @Autowired
    public MyController(
            MyCoolService myCoolService,
            JMDictPronRepo JMDictPronRepo
    ) {
        this.myCoolService = myCoolService;
        this.JMDictPronRepo = JMDictPronRepo;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/test")
    public String test(
            @RequestParam(name="kanji", defaultValue = "dat boi") String kanji,
            Model model
    ) {
        model.addAttribute("kanji", kanji);
        return "test";
    }

    @RequestMapping("/test3")
    public String test3(Model model) {
        Vocablist vocablist = new Vocablist("するためにしない。する為に行く。何のためにした？自分の為。する為。");

        List<VocabListRow> sortedByFreq = vocablist.getSortedByFreq();
        model.addAttribute("theList", sortedByFreq);
//        sortedByFreq.get(0).

        return "test3";
    }

    @RequestMapping("/test4")
    public String test4(
            // eg. http://localhost:8080/test4?input="この俺が俺です。"
            @RequestParam(name="input", defaultValue = "するためにしない。する為に行く。何のためにした？自分の為。する為。") String input,
            Model model
    ) {
        Vocablist vocablist = new Vocablist(input, Vocablist.Filtering.MANDATORY);
        List<VocabListRow> sortedByFreq = vocablist.getSortedByFreq();

        List<VocabListRowCumu> cumulative = new ArrayList<>();

        final int s = vocablist.getTokenCount().size();
        float runningPercent = 0;
        for (int i = 0; i < sortedByFreq.size(); i++) {
            VocabListRow vocabListRow = sortedByFreq.get(i);
            float myPercent = (float)vocabListRow.getCount() / (float)s;
            runningPercent += myPercent;
            cumulative.add(new VocabListRowCumu(vocabListRow, myPercent, runningPercent, false, false, false, false, false, false));
        }

//        model.addAttribute("theObject", vocablist);
        model.addAttribute("theList", cumulative);
//        sortedByFreq.get(0).

        return "test4";
    }

}
