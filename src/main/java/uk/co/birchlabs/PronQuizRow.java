package uk.co.birchlabs;

import java.util.regex.Pattern;

import static uk.co.birchlabs.EntryReadout.MEANINGS_START_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_END_KEY;
import static uk.co.birchlabs.EntryReadout.PRONS_START_KEY;

/**
 * Given info of the pronunciation, user must match to kanji and definition.
 */
public class PronQuizRow implements QuizRow {
    private final String info; // しと
    private final String target; // 使徒：disciple ･ apostle

    /**
     * Expects input like: 使徒 [しと]：disciple ･ apostle
     *        ... or like: スタッフ：(1) staff; (2) stuff
     */
    public PronQuizRow(VocabListRowCumulativeMapped row) {
        String fullDef = row.getDefs().get(0);
        if(EntryReadout.descHasKanji(fullDef)) {
            this.info = fullDef.split(Pattern.quote(PRONS_START_KEY), 2)[1].split(Pattern.quote(PRONS_END_KEY), 2)[0];
            // しと
            this.target = fullDef.split(Pattern.quote(PRONS_START_KEY), 2)[0]
                    + MEANINGS_START_KEY + fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[1];
            // 使徒：disciple ･ apostle
        }
        else {
            this.info = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[0]; // スタッフ
            this.target = fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[1]; // (1) staff; (2) stuff
        }


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
