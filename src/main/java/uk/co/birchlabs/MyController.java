package uk.co.birchlabs;

import catRecurserPkg.VocabListRow;
import catRecurserPkg.Vocablist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @RequestMapping("/test3")
    public String test3(Model model) {
        Vocablist vocablist = new Vocablist("するためにしない。する為に行く。何のためにした？自分の為。する為。");

        List<VocabListRow> sortedByFreq = vocablist.getSortedByFreq();
        model.addAttribute("theList", sortedByFreq);
//        sortedByFreq.get(0).

        return "test3";
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
}
