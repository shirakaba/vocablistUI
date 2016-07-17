package uk.co.birchlabs;

import java.util.regex.Pattern;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class DefQuestion implements Question {
    private final int hashCode;
    private final String info; // disciple ･ apostle
    private final String target; //  使徒 [しと]

    /**
     * Expects input like: 使徒 [しと]：disciple ･ apostle
     *        ... or like: スタッフ：(1) staff; (2) stuff
     */
    public DefQuestion(VocabListRowCumulativeMapped row) {
        EntryReadout firstEntryReadout = row.getEntryReadouts().get(0);
        String fullDef = firstEntryReadout.getDescription();
        this.info = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[1];
        this.target = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[0];

        hashCode = calculateHash(this.info);
    }

    private static int calculateHash(String comparator) {
        return comparator.hashCode();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object o) { return o.hashCode() == hashCode(); }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public String getTarget() {
        return target;
    }

}
