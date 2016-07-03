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

    public VocabListRowCumulativeMapped2(
            VocabListRowCumulative vocabListRowCumulative,
            Iterable<JMDictEntry> wordEntries,
            EntriesByMecabPOS hiraganaEntriesByPOS,
            EntriesByMecabPOS katakanaEntriesByPOS
    ) {
        final String rowBaseForm = vocabListRowCumulative.getVocabListRow().getToken().getBaseForm();
        this.vocabListRowCumulative = vocabListRowCumulative;
        e = new HashSet<>();

//        JMDictEntryRepo2.collectWordsOrPronOfEntries((List<JMDictEntry>)e, CollectionMode.word);

//        e // searches jmdict_word by baseForm
//                .addAll( // TODO: test whether matching by baseForm ever successfully adds anything to the list.
//                        StreamSupport
//                                .stream(wordEntries.spliterator(), false)
//                                .filter(entry -> entry
//                                        .getIdDataKey()
//                                        .getData()
//                                        .equals(
//                                                vocabListRowCumulative
//                                                        .getVocabListRow()
//                                                        .getToken()
//                                                        .getBaseForm()
//                                        )
//                                )
//                                .map(word -> word.getIdDataKey().getId())
////                                .map(JMDictWord::getId)
//                                .collect(Collectors.toList())
//                );

//        wordEntries


//        vocabListRowCumulative
//                .getVocabListRow()
//                .getToken();



        e // searches jmdict_word by baseForm
                .addAll( // TODO: test whether matching by baseForm ever successfully adds anything to the list.
                        StreamSupport
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
                        .collect(Collectors.toList())
                );
        System.out.println("Gotta go fast!");
    }
}
