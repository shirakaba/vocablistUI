package uk.co.birchlabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jamiebirch on 21/06/2016.
 */
@RestController
public class MyRestController {

    @Autowired
    JMDictPronService jmDictPronService;

    @RequestMapping("/generate")
    @CrossOrigin
    public Generator generator(
            // eg. http://localhost:8080/test4?input="この俺が俺です。"
            @RequestParam(name="makequiz", defaultValue = "true") boolean makeQuiz,
            @RequestParam(name="maxarticles", defaultValue = "2") Integer maxArticles,
            @RequestParam(name="filtering", defaultValue = "mandatory") String filtering,
            @RequestParam(name="egs", defaultValue = "0") Integer egs,
            @RequestParam(name="minyield", defaultValue = "0.01") Float minYield,
            @RequestParam(name="partition", defaultValue = "0") Integer partition,
            @RequestParam(name="limit", defaultValue = "95") Float limit,
            @RequestParam(name="input", defaultValue = "するためにしない。する為に行く。何のためにした？自分の為。する為。") String input
    ) {
        return jmDictPronService.generator(makeQuiz, maxArticles, filtering, egs, minYield, partition, limit, input);
    }
}
