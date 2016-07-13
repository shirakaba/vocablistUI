package uk.co.birchlabs;

import java.util.regex.Pattern;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_END_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class KanjiQuizRow implements QuizRow {
    private final String info; // 使徒
    private final String quizTarget; // [しと]：disciple ･ apostle

    /**
     * Expects input like: 使徒 [しと]：disciple ･ apostle
     *        ... or like: スタッフ：(1) staff; (2) stuff
     * @param bf
     * @param fullDef
     */
    public KanjiQuizRow(String bf, String fullDef) {
        this.info = bf;

        String kanjiAndMeanings = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[0]; // 使徒 [しと]
        if(kanjiAndMeanings.contains(PRONS_START_KEY) && kanjiAndMeanings.contains(PRONS_END_KEY)) {
            this.quizTarget = PRONS_START_KEY + fullDef.split(Pattern.quote(PRONS_START_KEY), 2)[1].split(Pattern.quote(PRONS_END_KEY))[0] + PRONS_END_KEY
                    + MEANINGS_START_KEY + fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[1];
        }
        else throw new IllegalStateException("This definition has no kanji form to test upon; it is purely phonetic.");
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
