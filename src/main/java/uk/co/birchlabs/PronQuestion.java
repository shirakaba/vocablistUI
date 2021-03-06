package uk.co.birchlabs;

import java.util.regex.Pattern;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_END_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_START_KEY;

/**
 * Given info of the pronunciation, user must match to kanji and definition.
 */
public class PronQuestion implements Question {
    private final int hashCode;
    private final String info; // しと
    private final String target; // 使徒：disciple ･ apostle

    /**
     * Expects input like: 使徒 [しと]：disciple ･ apostle
     *        ... or like: スタッフ：(1) staff; (2) stuff
     */
    public PronQuestion(VocabListRowCumulativeMapped row) {

        EntryReadout firstEntryReadout = row.getEntryReadouts().get(0);
        String fullDef = firstEntryReadout.getDescription();
        if(firstEntryReadout.descHasKanji()) {
            this.info = fullDef.split(Pattern.quote(PRONS_START_KEY), 2)[1].split(Pattern.quote(PRONS_END_KEY), 2)[0];
            // しと
            this.target =
//                    fullDef.split(Pattern.quote(PRONS_START_KEY), 2)[0] + MEANINGS_START_KEY +
                    fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[1];
            // 使徒：disciple ･ apostle
        }
        else {
            this.info = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[0]; // スタッフ
            this.target = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[1]; // (1) staff; (2) stuff
        }

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
