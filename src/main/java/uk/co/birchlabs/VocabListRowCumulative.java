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
    private final boolean n5;
    private final boolean n4;
    private final boolean n3;
    private final boolean n2;
    private final boolean n1;

    public VocabListRowCumulative(VocabListRow vocabListRow,
                                  Float isolatePercent,
                                  Float cumulativePercent,
                                  boolean fundamental,
                                  boolean n5,
                                  boolean n4,
                                  boolean n3,
                                  boolean n2,
                                  boolean n1
    ) {
        this.vocabListRow = vocabListRow;
        this.isolatePercent = isolatePercent;
        this.cumulativePercent = cumulativePercent;
        this.fundamental = fundamental;
        this.n5 = n5;
        this.n4 = n4;
        this.n3 = n3;
        this.n2 = n2;
        this.n1 = n1;
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

    public boolean isN5() {
        return n5;
    }

    public boolean isN4() {
        return n4;
    }

    public boolean isN3() {
        return n3;
    }

    public boolean isN2() {
        return n2;
    }

    public boolean isN1() {
        return n1;
    }
}
