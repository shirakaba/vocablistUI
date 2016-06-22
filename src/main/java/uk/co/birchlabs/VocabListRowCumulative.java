package uk.co.birchlabs;

import catRecurserPkg.VocabListRow;

/**
 * Created by jamiebirch on 22/06/2016.
 */
public class VocabListRowCumulative {
    private final VocabListRow vocabListRow;
    private final Float cumulativePercent;
    private final Float isolatePercent;
    private final boolean fundamental;
    private final boolean jlpt4;
    private final boolean jlpt3;
    private final boolean jlpt2;
    private final boolean jlpt1;

    public VocabListRowCumulative(VocabListRow vocabListRow,
                                  Float isolatePercent,
                                  Float cumulativePercent,
                                  boolean fundamental,
                                  boolean jlpt4,
                                  boolean jlpt3,
                                  boolean jlpt2,
                                  boolean jlpt1
    ) {
        this.vocabListRow = vocabListRow;
        this.isolatePercent = isolatePercent;
        this.cumulativePercent = cumulativePercent;
        this.fundamental = fundamental;
        this.jlpt4 = jlpt4;
        this.jlpt3 = jlpt3;
        this.jlpt2 = jlpt2;
        this.jlpt1 = jlpt1;
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

    public boolean isFundamental() {
        return fundamental;
    }

    public boolean isJlpt4() {
        return jlpt4;
    }

    public boolean isJlpt3() {
        return jlpt3;
    }

    public boolean isJlpt2() {
        return jlpt2;
    }

    public boolean isJlpt1() {
        return jlpt1;
    }
}
