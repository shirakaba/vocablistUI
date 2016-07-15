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
    private final String target; // [しと]：disciple ･ apostle
    private final String type;

    /**
     * Expects input like: 使徒 [しと]：disciple ･ apostle
     *        ... or like: スタッフ：(1) staff; (2) stuff
     * Undefined behaviour if firstEntryReadout is "No definitions found..."
     */
    public KanjiQuizRow(VocabListRowCumulativeMapped row, String type) {
        this.type = type;

        EntryReadout firstEntryReadout = row.getEntryReadouts().get(0);
        String fullDef = firstEntryReadout.getDescription();
        this.info = row.getBf();

        if(firstEntryReadout.descHasKanji()) {
            this.target = PRONS_START_KEY + fullDef.split(Pattern.quote(PRONS_START_KEY), 2)[1].split(Pattern.quote(PRONS_END_KEY))[0] + PRONS_END_KEY
                    + MEANINGS_START_KEY + fullDef.split(Pattern.quote(MEANINGS_START_KEY), 2)[1];
        }
        else throw new IllegalStateException("This definition has no kanji form to test upon; it is purely phonetic.");
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public String getType() {
        return type;
    }
}