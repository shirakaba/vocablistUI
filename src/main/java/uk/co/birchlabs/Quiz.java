package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 15/07/2016.
 */
public class Quiz {
    private final List<QuizRow> quiz;
    private final String type;
    private final String[] allowedTypes;
    private final Integer max;

    public Quiz(List<QuizRow> quiz, String type) {
        if(quiz.isEmpty()) allowedTypes = new String[]{};
        else allowedTypes = new String[]{type};

        this.quiz = quiz;
        this.type = type;
        this.max = 2;
    }

    public List<QuizRow> getQuiz() {
        return quiz;
    }

    public String getType() {
        return type;
    }

    public String[] getAllowedTypes() {
        return allowedTypes;
    }

    public Integer getMax() {
        return max;
    }
}
