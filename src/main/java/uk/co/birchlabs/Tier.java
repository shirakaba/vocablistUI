package uk.co.birchlabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jamiebirch on 14/07/2016.
 */
public class Tier {
    private final List<Test> tests;

    public Tier(List<Question> kanjiQuestions, List<Question> pronQuestions, List<Question> defQuestions, String tierAlpha) {

        tests = new ArrayList<>(
                Arrays.asList(new Test(kanjiQuestions, "kanji", tierAlpha), new Test(pronQuestions, "pron", tierAlpha), new Test(defQuestions, "def", tierAlpha))
        );
    }

    public List<Test> getTests() {
        return tests;
    }
}
