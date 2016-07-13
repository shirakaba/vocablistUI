package uk.co.birchlabs;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class QuizRow {
    private final String bf;
    private final String def;

    // TODO: convert to factory method so we can test alternately on def, pronun(s), and kanjiform(s)
    public QuizRow(String bf, String def) {
        this.bf = bf;
        this.def = def.split(MEANINGS_START_KEY, 2)[1];
    }

    public String getBf() {
        return bf;
    }

    public String getDef() {
        return def;
    }
}
