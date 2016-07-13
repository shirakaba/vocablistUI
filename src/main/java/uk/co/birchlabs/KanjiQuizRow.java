package uk.co.birchlabs;

import java.util.regex.Pattern;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_END_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class KanjiQuizRow implements QuizRow {
    private final String info; // becomes pron + definition
    private final String quiz;

    public KanjiQuizRow(String bf, String fullDef) {
        String kanjiAndMeanings = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[0];

        if(kanjiAndMeanings.contains(PRONS_START_KEY) && kanjiAndMeanings.contains(PRONS_END_KEY))
            this.info = fullDef.split(Pattern.quote(PRONS_START_KEY), 2)[1];
        this.quiz = bf;
    }

    @Override
    public String getInfoPortion() {
        return null;
    }

    @Override
    public String getQuizPortion() {
        return null;
    }
}
