package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 15/07/2016.
 */
public class Quiz {
    private final List<QuizRow> quiz;
    private final String label;
    private final String[] allowedTypes;
    private final Integer max;

    public Quiz(List<QuizRow> quiz, String label) {
        if(quiz.isEmpty()) allowedTypes = new String[]{};
        else allowedTypes = new String[]{quiz.get(0).getType()};

        this.quiz = quiz;
        this.label = label;
        this.max = 2;
    }

    public List<QuizRow> getQuiz() {
        return quiz;
    }

    public String getLabel() {
        return label;
    }

    public String[] getAllowedTypes() {
        return allowedTypes;
    }

    public Integer getMax() {
        return max;
    }
}
