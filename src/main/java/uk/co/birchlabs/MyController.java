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
import java.util.stream.Collectors;

/**
 * Created by jamiebirch on 21/06/2016.
 */
@Controller
public class MyController {

    MyCoolService myCoolService;

    JMDictPronunciationRepository JMDictPronunciationRepository;

    Repo repo;

    @Autowired
    public MyController(
            MyCoolService myCoolService,
            JMDictPronunciationRepository JMDictPronunciationRepository,
            Repo repo
    ) {
        this.myCoolService = myCoolService;
        this.JMDictPronunciationRepository = JMDictPronunciationRepository;
        this.repo = repo;
    }

    @RequestMapping("/test")
    public String test(
            @RequestParam(name="kanji", defaultValue = "dat boi") String kanji,
            Model model
    ) {
        model.addAttribute("kanji", kanji);
        return "test";
    }

    @RequestMapping("/test2")
    public String test2(
            // kanji2 is a requestable parameter in the HTML scope. It has type String.
//            @RequestParam(name="k2", defaultValue = "defaultVal") Integer id,
//            @RequestParam(name="pron") JMDictPronunciation pron,
            Model model
    ) {
        Iterable<JMDictPronunciation> all = repo.getSome();
        model.addAttribute("myIterable", all);
//        model.add
//        model.addAttribute("pron", pron);

//        pron = new JMDictPronunciation();
//        pron.setId(1000000);

        return "test2";
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

        List<VocabListRowCumulative> cumulative = new ArrayList<>();

        final int s = vocablist.getTokenCount().size();
        float runningPercent = 0;
        for (int i = 0; i < sortedByFreq.size(); i++) {
            VocabListRow vocabListRow = sortedByFreq.get(i);
            float myPercent = (float)vocabListRow.getCount() / (float)s;
            runningPercent += myPercent;
            cumulative.add(new VocabListRowCumulative(vocabListRow, myPercent, runningPercent));
        }

//        model.addAttribute("theObject", vocablist);
        model.addAttribute("theList", cumulative);
//        sortedByFreq.get(0).

        return "test4";
    }

}
