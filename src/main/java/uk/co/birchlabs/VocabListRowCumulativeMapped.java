package uk.co.birchlabs;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by jamiebirch on 24/06/2016.
 */
/* http://stackoverflow.com/questions/4362104/strange-jackson-exception-being-thrown-when-serializing-hibernate-object */
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
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

        id // searches jmdict_word by baseForm
                .addAll( // TODO: test whether matching by baseForm ever successfully adds anything to the list.
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
        if (id.isEmpty()) {
            id // searches jmdict_pronunciation by pronunciation in hiragana (or if verb: by hiragana baseForm)
                    .addAll(
                            StreamSupport
                                    .stream(hiraganaPairs.spliterator(), false)
                                    .filter(wordPair -> wordPair
                                            .getData()
                                            .equals(
                                                    vocabListRowCumulative
                                                            .getVocabListRow()
                                                            .getToken()
                                                            .getBaseForm()
                                            )
                                    )
                                    .map(JMDictPronunciation::getId)
                                    .collect(Collectors.toList())
                    );
        }
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

//    @JsonProperty
    public VocabListRowCumulative getVocabListRowCumulative() {
        return vocabListRowCumulative;
    }

    // currently not exposing id; only planning to consume it on the backend, so needn't add to JSON.
////    @JsonProperty
//    public Collection<Integer> getId() {
//        return id;
//    }
}
