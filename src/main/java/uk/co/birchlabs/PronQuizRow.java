package uk.co.birchlabs;

import java.util.regex.Pattern;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_END_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class PronQuizRow implements QuizRow {
    private final String bf;
    private final String def;

    /**
     * Expects input like: 使徒 [しと]：disciple ･ apostle
     *        ... or like: スタッフ：(1) staff; (2) stuff
     * @param bf
     * @param def
     */
    public PronQuizRow(String bf, String def) {
        this.bf = bf;
        String kanjiAndMeanings = def.split(Pattern.quote(MEANINGS_START_KEY), 2)[0];
        if(kanjiAndMeanings.contains(PRONS_START_KEY) && kanjiAndMeanings.contains(PRONS_END_KEY))
            this.def = def.split(Pattern.quote(PRONS_START_KEY), 2)[1].split(Pattern.quote(PRONS_END_KEY), 2)[0];
        else this.def = kanjiAndMeanings;
    }

    @Override
    public String getInfoPortion() {
        return bf;
    }

    @Override
    public String getQuizPortion() {
        return def;
    }
}
