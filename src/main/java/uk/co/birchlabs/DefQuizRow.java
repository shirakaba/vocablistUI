package uk.co.birchlabs;

import java.util.regex.Pattern;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class DefQuizRow implements QuizRow {
    private final String info; // disciple ･ apostle
    private final String target; //  使徒 [しと]

    /**
     * Expects input like: 使徒 [しと]：disciple ･ apostle
     *        ... or like: スタッフ：(1) staff; (2) stuff
     */
    public DefQuizRow(VocabListRowCumulativeMapped row) {
        EntryReadout firstEntryReadout = row.getEntryReadouts().get(0);
        String fullDef = firstEntryReadout.getDescription();
        this.info = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[1];
        this.target = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[0];
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
