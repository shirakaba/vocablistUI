package uk.co.birchlabs;

import catRecurserPkg.VocabListRow;

/**
 * Created by jamiebirch on 22/06/2016.
 */
public class VocabListRowCumulative {
    private final VocabListRow vocabListRow;
    private final Float cumulativePercent;
    private final Float isolatePercent;

    public VocabListRowCumulative(VocabListRow vocabListRow, Float isolatePercent, Float cumulativePercent) {
        this.vocabListRow = vocabListRow;
        this.isolatePercent = isolatePercent;
        this.cumulativePercent = cumulativePercent;
    }

    public VocabListRow getVocabListRow() {
        return vocabListRow;
    }

    public Float getIsolatePercent() {
        return isolatePercent;
    }

    public Float getCumulativePercent() {
        return cumulativePercent;
    }
}
