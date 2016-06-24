package uk.co.birchlabs;

import java.util.Collection;

/**
 * Created by jamiebirch on 24/06/2016.
 */
public class VocabListRowCumulativeMapped {
    private final VocabListRowCumulative vocabListRowCumulative;
    private final Collection<Integer> id;

    public VocabListRowCumulativeMapped(VocabListRowCumulative vocabListRowCumulative, Collection<Integer> id) {
        this.vocabListRowCumulative = vocabListRowCumulative;
        this.id = id;
    }
}
