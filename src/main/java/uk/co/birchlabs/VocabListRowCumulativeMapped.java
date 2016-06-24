package uk.co.birchlabs;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by jamiebirch on 24/06/2016.
 */
public class VocabListRowCumulativeMapped {
    private final VocabListRowCumulative vocabListRowCumulative;
    private final Collection<Integer> id;

    public VocabListRowCumulativeMapped(
            VocabListRowCumulative vocabListRowCumulative,
            Iterable<JMDictWord> idWordPairs,
            Iterable<JMDictPronunciation> hiraganaPairs,
            Iterable<JMDictPronunciation> katakanaPairs
    ) {
        this.vocabListRowCumulative = vocabListRowCumulative;


        id = new HashSet<>();

        id
                .addAll(
                        StreamSupport
                                .stream(idWordPairs.spliterator(), false)
                                .filter(wordPair -> wordPair
                                        .getData()
                                        .equals(
                                                vocabListRowCumulative
                                                        .getVocabListRow()
                                                        .getToken()
                                                        .getBaseForm()
                                        )
                                )
                                .map(JMDictWord::getId)
                                .collect(Collectors.toList())
                );
//        if (id.isEmpty()) {
//            id
//                    .addAll(
//                            StreamSupport
//                                    .stream(hiraganaPairs.spliterator(), false)
//                                    .filter(wordPair -> wordPair
//                                            .getData()
//                                            .equals(
//                                                    vocabListRowCumulative
//                                                            .getVocabListRow()
//                                                            .getToken()
//                                                            .getBaseForm()
//                                            )
//                                    )
//                                    .map(JMDictPronunciation::getId)
//                                    .collect(Collectors.toList())
//                    );
//        }
        if (id.isEmpty()) {
            id
                    // Note: if this collection is null, all further added collections seem to become null with it.
                    // Note: this needs to happen only if id is empty thus far
                    .addAll(
                            StreamSupport
                                    .stream(hiraganaPairs.spliterator(), false)
                                    .filter(wordPair -> Utils.convertKana(wordPair
                                            .getData())
                                            .equals(
                                                    vocabListRowCumulative
                                                            .getVocabListRow()
                                                            .getToken()
                                                            .getReading() // token's readings are in katakana.
                                            )
                                    )
                                    .map(JMDictPronunciation::getId)
                                    .collect(Collectors.toList())
                    );
        }
        if (id.isEmpty()) {
            id
                    // Note: this also needs to happen only if id is empty thus far
                    .addAll(
                            StreamSupport
                                    .stream(katakanaPairs.spliterator(), false)
                                    .filter(wordPair -> wordPair
                                            .getData()
                                            .equals(
                                                    vocabListRowCumulative
                                                            .getVocabListRow()
                                                            .getToken()
                                                            .getReading()
                                            )
                                    )
                                    .map(JMDictPronunciation::getId)
                                    .collect(Collectors.toList())
                    );
        }


//        this.id = id;
    }
}
