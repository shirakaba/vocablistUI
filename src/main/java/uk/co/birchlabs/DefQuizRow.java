package uk.co.birchlabs;

import java.util.regex.Pattern;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_END_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class DefQuizRow implements QuizRow {
    private final String info; // disciple ･ apostle
    private final String quizTarget; //  使徒 [しと]

    /**
     * Expects input like: 使徒 [しと]：disciple ･ apostle
     *        ... or like: スタッフ：(1) staff; (2) stuff
     * @param bf
     * @param fullDef
     */
    public DefQuizRow(String bf, String fullDef) {
        this.info = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[1];
        this.quizTarget = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[0];
    }

    @Override
    public String getInfoPortion() {
        return info;
    }

    @Override
    public String getQuizPortion() {
        return quizTarget;
    }
}
