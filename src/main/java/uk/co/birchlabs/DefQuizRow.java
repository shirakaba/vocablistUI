package uk.co.birchlabs;

import java.util.regex.Pattern;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class DefQuizRow implements QuizRow {
    private final String bf;
    private final String def;

    public DefQuizRow(String bf, String def) {
        this.bf = bf;
        this.def = def.split(Pattern.quote(MEANINGS_START_KEY), 2)[1];
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
