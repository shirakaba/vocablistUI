package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import com.google.common.collect.Iterables;
import uk.co.birchlabs.JMDictEntryRepo2.CollectionMode;
import uk.co.birchlabs.JMDictPronRepo2.Mode;
import uk.co.birchlabs.JMDictPronRepo2.POS;

import java.util.ArrayList;
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
    private final ForwardingToken token;
    private final String rowBaseForm;
    private final String tokenHiraganaPron;
    private final String tokenKatakanaPron;
    private final List<EntryReadout> entryReadouts;
//    private final String representativePron;

    public VocabListRowCumulativeMapped2(
            VocabListRowCumulative vocabListRowCumulative,
            Iterable<JMDictEntry> wordEntries,
            EntriesByMecabPOS hiraganaEntriesByPOS,
            EntriesByMecabPOS katakanaEntriesByPOS
    ) {
        this.vocabListRowCumulative = vocabListRowCumulative;
        token = vocabListRowCumulative.getVocabListRow().getToken();
        rowBaseForm = token.getBaseForm();
        tokenKatakanaPron = token.getReading();
        tokenHiraganaPron = Utils.convertKana(this.tokenKatakanaPron);
        e = new HashSet<>();

        e.addAll(collectEntriesMatchingTokenProperty(wordEntries, CollectionMode.word));
        if(e.isEmpty()) e.addAll(collectEntriesMatchingTokenProperty(hiraganaEntriesByPOS, CollectionMode.pron, Mode.READINGS_IN_HIRAGANA));
        if(e.isEmpty()) e.addAll(collectEntriesMatchingTokenProperty(katakanaEntriesByPOS, CollectionMode.pron, Mode.READINGS_IN_KATAKANA));
        // else: no matches were found. May need a placeholder. Eventually should integrate jmn_edict.

        // JMDictPronRepo2.getEntriesFromPron() got us all the
        entryReadouts = e
                .stream()
                .map(entry -> new EntryReadout(entry, vocabListRowCumulative.getVocabListRow().getToken()))
                .collect(Collectors.toList())
        ;

//        System.out.println("Gotta go fast!");
    }

    // TODO: decide which fields beyond entryReadouts to expose to the website with getters.

    public List<EntryReadout> getEntryReadouts() {
        return entryReadouts;
    }

    private List<JMDictEntry> collectEntriesMatchingTokenProperty(EntriesByMecabPOS entriesByMecabPOS, CollectionMode collectionMode, Mode mode) {
        List<JMDictEntry> list = new ArrayList<>();
        // TODO: expose POS as a field, and possibly give the enum a toString method so browser can display POS.
        POS pos = TokensByMecabPOS.determinePOS(vocabListRowCumulative.getVocabListRow().getToken());

        switch (pos){
            case particles:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getParticlesByPron(), collectionMode, mode));
                break;
            case verbsAndAux:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getVerbsByPron(), collectionMode, mode));
                break;
            case adverbs:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getAdverbsByPron(), collectionMode, mode));
                break;
            case conjunctions:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getConjunctionsByPron(), collectionMode, mode));
                break;
            case nouns:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getNounsByPron(), collectionMode, mode));
                break;
            case properNouns:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getProperNounsByPron(), collectionMode, mode));
                break;
            case prefixes:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getPrefixesByPron(), collectionMode, mode));
                break;
            case adjectives:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getAdjectivesByPron(), collectionMode, mode));
                break;
            case adnominals:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getAdnominalsByPron(), collectionMode, mode));
                break;
            case exclamations:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getExclamationsByPron(), collectionMode, mode));
                break;
            case symbols:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getSymbolsByPron(), collectionMode, mode));
                break;
            case fillers:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getFillersByPron(), collectionMode, mode));
                break;
            case others:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getOthersByPron(), collectionMode, mode));
                break;
            case unclassified:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getUnclassifiedByPron(), collectionMode, mode));
                break;
            default:
                throw new IllegalStateException();
        }

        return list;
    }

    private List<JMDictEntry> collectEntriesMatchingTokenProperty(Iterable<JMDictEntry> wordEntries, CollectionMode collectionMode) {
        return collectEntriesMatchingTokenProperty(wordEntries, collectionMode, Mode.READINGS_IN_HIRAGANA);
    }

    private List<JMDictEntry> collectEntriesMatchingTokenProperty(Iterable<JMDictEntry> wordEntries, CollectionMode collectionMode, Mode mode) {
        switch(collectionMode){
            case pron:
                return StreamSupport
                        .stream(wordEntries.spliterator(), false)
                        // take only whose words...data equals the token's baseForm.
                        .filter(
                                entry -> Iterables.tryFind(
                                        entry
                                                .getPron()
                                                .stream()
                                                .map(
                                                        pron -> pron
                                                                .getIdDataKey()
                                                                .getData()
                                                )
                                                .filter(datum -> datum != null)
                                                .collect(Collectors.toList()),
                                        datum -> {
                            /* ISSUES:
                             * 動詞,自立,*,*,サ変・スル,未然レル接続,する,サ,サ -> generally 1157170 (為る)
                             * 名詞,固有名詞,地域,一般,*,*,練馬,ネリマ,ネリマ -> needs jmn_edict
                             */
                            if(token.isVerb()) return datum.equals(rowBaseForm);
                            else if(mode.equals(Mode.READINGS_IN_HIRAGANA)) return datum.equals(tokenHiraganaPron);
                            // confirmed to work: catches 名詞,一般,*,*,*,*,アニメ,アニメ,アニメ
                            else if(mode.equals(Mode.READINGS_IN_KATAKANA)) return datum.equals(tokenKatakanaPron);
                            else throw new IllegalStateException();
                        }
                                )
                                        .isPresent()
                        )
                        .collect(Collectors.toList());
            case word:
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
            default:
                throw new IllegalStateException();
        }

    }
}
