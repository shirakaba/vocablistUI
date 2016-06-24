package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 22/06/2016.
 */
public class Test6Model {
    List<VocabListRowCumulativeMapped> cumulative;

    public Test6Model(List<VocabListRowCumulativeMapped> cumulative) {
        this.cumulative = cumulative;
    }

    public List<VocabListRowCumulativeMapped> getCumulative() {
        return cumulative;
    }
}
