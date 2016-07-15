package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 14/07/2016.
 */
public class SingleTierQuiz {
    private final Quiz kanjiQuiz;
    private final Quiz pronQuiz;
    private final Quiz defQuiz;

    public SingleTierQuiz(List<QuizRow> kanjiQuiz, List<QuizRow> pronQuiz, List<QuizRow> defQuiz, String label) {
        this.kanjiQuiz = new Quiz(kanjiQuiz, label);
        this.pronQuiz = new Quiz(pronQuiz, label);
        this.defQuiz = new Quiz(defQuiz, label);
    }

    public Quiz getKanjiQuiz() {
        return kanjiQuiz;
    }

    public Quiz getPronQuiz() {
        return pronQuiz;
    }

    public Quiz getDefQuiz() {
        return defQuiz;
    }
}
