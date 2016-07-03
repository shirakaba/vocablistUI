package uk.co.birchlabs;

import com.google.common.collect.Iterables;
import uk.co.birchlabs.JMDictEntryRepo2.CollectionMode;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by jamiebirch on 03/07/2016.
 */
public class VocabListRowCumulativeMapped2 {
    private final VocabListRowCumulative vocabListRowCumulative;
    private final Collection<JMDictEntry> e;
    private final String rowBaseForm;

    public VocabListRowCumulativeMapped2(
            VocabListRowCumulative vocabListRowCumulative,
            Iterable<JMDictEntry> wordEntries,
            EntriesByMecabPOS hiraganaEntriesByPOS,
            EntriesByMecabPOS katakanaEntriesByPOS
    ) {
        this.vocabListRowCumulative = vocabListRowCumulative;
        this.rowBaseForm = vocabListRowCumulative.getVocabListRow().getToken().getBaseForm();
        e = new HashSet<>();

        e.addAll(collectEntriesMatchingTokenProperty(wordEntries));

        if(e.isEmpty()) e.addAll(collectEntriesMatchingTokenProperty(wordEntries));


        System.out.println("Gotta go fast!");
    }

    private List<JMDictEntry> collectEntriesMatchingTokenProperty(Iterable<JMDictEntry> wordEntries) {
        return StreamSupport
                .stream(wordEntries.spliterator(), false)
                // take only whose words...data equals the token's baseForm.
                .filter(
                        entry -> Iterables.tryFind(
                                entry
                                        .getWords()
                                        .stream()
                                        .map(
                                                word -> word
                                                        .getIdDataKey()
                                                        .getData()
                                        )
                                        .filter(datum -> datum != null)
                                        .collect(Collectors.toList()),
                                datum -> datum.equals(rowBaseForm)
                        )
                                .isPresent()
                )
        .collect(Collectors.toList());
    }
}
