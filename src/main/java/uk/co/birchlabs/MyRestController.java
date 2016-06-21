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

    @Autowired
    JMDictPronunciationRepository JMDictPronunciationRepository;

    @RequestMapping("/thing")
    public JMDictPronunciation thing(
            @RequestParam Integer id
    ) {
        return JMDictPronunciationRepository.findOne(id);
    }
}
