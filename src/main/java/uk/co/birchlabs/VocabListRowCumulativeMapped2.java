package uk.co.birchlabs;

import com.google.common.collect.Iterables;
import uk.co.birchlabs.JMDictEntryRepo2.CollectionMode;
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
        if(e.isEmpty()) e.addAll(collectEntriesMatchingTokenProperty(hiraganaEntriesByPOS));
        if(e.isEmpty()) e.addAll(collectEntriesMatchingTokenProperty(katakanaEntriesByPOS));
        // else: no matches were found. May need a placeholder. Eventually should integrate jmn_edict.

        System.out.println("Gotta go fast!");
    }


    // TODO: ascertain whether these are handling hiragana/katakana correctly (particularly with verbs).
    // Likely isn't, as の isn't getting any list additions.
    private List<JMDictEntry> collectEntriesMatchingTokenProperty(EntriesByMecabPOS entriesByMecabPOS) {
        List<JMDictEntry> list = new ArrayList<>();
        // TODO: expose POS as a field, and possibly give the enum a toString method so browser can display POS.
        POS pos = TokensByMecabPOS.determinePOS(vocabListRowCumulative.getVocabListRow().getToken());

        switch (pos){
            case particles:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getParticlesByPron()));
                break;
            case verbsAndAux:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getVerbsByPron()));
                break;
            case adverbs:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getAdverbsByPron()));
                break;
            case conjunctions:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getConjunctionsByPron()));
                break;
            case nouns:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getNounsByPron()));
                break;
            case prefixes:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getPrefixesByPron()));
                break;
            case adjectives:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getAdjectivesByPron()));
                break;
            case adnominals:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getAdnominalsByPron()));
                break;
            case exclamations:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getExclamationsByPron()));
                break;
            case symbols:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getSymbolsByPron()));
                break;
            case fillers:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getFillersByPron()));
                break;
            case others:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getOthersByPron()));
                break;
            case unclassified:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getUnclassifiedByPron()));
                break;
            default:
                throw new IllegalStateException();
        }

        return list;
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
