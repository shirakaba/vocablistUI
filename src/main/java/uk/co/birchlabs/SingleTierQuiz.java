package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 14/07/2016.
 */
public class SingleTierQuiz {
    private final List<QuizRow> kanjiQuiz;
    private final List<QuizRow> pronQuiz;
    private final List<QuizRow> defQuiz;

    public SingleTierQuiz(List<QuizRow> kanjiQuiz, List<QuizRow> pronQuiz, List<QuizRow> defQuiz) {
        this.kanjiQuiz = kanjiQuiz;
        this.pronQuiz = pronQuiz;
        this.defQuiz = defQuiz;
    }

    public List<QuizRow> getKanjiQuiz() {
        return kanjiQuiz;
    }

    public List<QuizRow> getPronQuiz() {
        return pronQuiz;
    }

    public List<QuizRow> getDefQuiz() {
        return defQuiz;
    }
}
