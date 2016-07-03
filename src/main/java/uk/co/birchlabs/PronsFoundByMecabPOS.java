package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 03/07/2016.
 */
public class PronsFoundByMecabPOS {

    private final List<String>
            particlesFound,
            verbsFound,
            adverbsFound,
            conjunctionsFound,
            nounsFound,
            prefixesFound,
            adjectivesFound,
            adnominalsFound,
            exclamationsFound,
            symbolsFound,
            fillersFound,
            othersFound,
            unclassifiedFound;

    // currently assuming e to be hiragana
    public PronsFoundByMecabPOS(EntriesByMecabPOS e) {
        particlesFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getParticlesByPron(), JMDictEntryRepo2.CollectionMode.pron);
        verbsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getVerbsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        adverbsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getAdverbsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        conjunctionsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getConjunctionsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        nounsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getNounsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        prefixesFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getPrefixesByPron(), JMDictEntryRepo2.CollectionMode.pron);
        adjectivesFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getAdjectivesByPron(), JMDictEntryRepo2.CollectionMode.pron);
        adnominalsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getAdnominalsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        exclamationsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getExclamationsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        symbolsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getSymbolsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        fillersFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getFillersByPron(), JMDictEntryRepo2.CollectionMode.pron);
        othersFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getOthersByPron(), JMDictEntryRepo2.CollectionMode.pron);
        unclassifiedFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getUnclassifiedByPron(), JMDictEntryRepo2.CollectionMode.pron);
    }

    public List<String> getParticlesFound() {
        return particlesFound;
    }

    public List<String> getVerbsFound() {
        return verbsFound;
    }

    public List<String> getAdverbsFound() {
        return adverbsFound;
    }

    public List<String> getConjunctionsFound() {
        return conjunctionsFound;
    }

    public List<String> getNounsFound() {
        return nounsFound;
    }

    public List<String> getPrefixesFound() {
        return prefixesFound;
    }

    public List<String> getAdjectivesFound() {
        return adjectivesFound;
    }

    public List<String> getAdnominalsFound() {
        return adnominalsFound;
    }

    public List<String> getExclamationsFound() {
        return exclamationsFound;
    }

    public List<String> getSymbolsFound() {
        return symbolsFound;
    }

    public List<String> getFillersFound() {
        return fillersFound;
    }

    public List<String> getOthersFound() {
        return othersFound;
    }

    public List<String> getUnclassifiedFound() {
        return unclassifiedFound;
    }
}
