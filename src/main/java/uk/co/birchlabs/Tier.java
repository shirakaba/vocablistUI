package uk.co.birchlabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jamiebirch on 14/07/2016.
 */
public class Tier {
    private final List<Test> tests;
//    private final Test kanjiQuestions;
//    private final Test pronQuestions;
//    private final Test defQuestions;

    public Tier(List<Question> kanjiQuestions, List<Question> pronQuestions, List<Question> defQuestions, String tierAlpha) {
//        this.kanjiQuestions = new Test(kanjiQuestions, "kanji" + tierAlpha);
//        this.pronQuestions = new Test(pronQuestions, "pron" + tierAlpha);
//        this.defQuestions = new Test(defQuestions, "def" + tierAlpha);

        tests = new ArrayList<>(
                Arrays.asList(new Test(kanjiQuestions, "kanji", tierAlpha), new Test(pronQuestions, "pron", tierAlpha), new Test(defQuestions, "def", tierAlpha))
        );
    }

    public List<Test> getTests() {
        return tests;
    }

    //    public Test getKanjiQuiz() {
//        return kanjiQuestions;
//    }
//
//    public Test getPronQuiz() {
//        return pronQuestions;
//    }
//
//    public Test getDefQuiz() {
//        return defQuestions;
//    }
}
