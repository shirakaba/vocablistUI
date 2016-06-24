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

//        id // searches jmdict_word by baseForm
//                .addAll( // TODO: test whether matching by baseForm ever succesfully adds anything to the list.
//                        StreamSupport
//                                .stream(idWordPairs.spliterator(), false)
//                                .filter(wordPair -> wordPair
//                                        .getData()
//                                        .equals(
//                                                vocabListRowCumulative
//                                                        .getVocabListRow()
//                                                        .getToken()
//                                                        .getBaseForm()
//                                        )
//                                )
//                                .map(JMDictWord::getId)
//                                .collect(Collectors.toList())
//                );
//        if (id.isEmpty()) {
//            id // searches jmdict_pronunciation by pronunciation in hiragana (or if verb: by hiragana baseForm)
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
            id // searches jmdict_pronunciation by pronunciation in katakana
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
    }
}
