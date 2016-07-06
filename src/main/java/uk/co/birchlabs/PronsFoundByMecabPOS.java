package uk.co.birchlabs;

import java.util.HashSet;

/**
 * Created by jamiebirch on 03/07/2016.
 */
public class PronsFoundByMecabPOS {

    private final HashSet<String>
            particlesFound,
            verbsFound,
            adverbsFound,
            conjunctionsFound,
            nounsFound,
            properNounsFound,
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
        properNounsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getProperNounsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        prefixesFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getPrefixesByPron(), JMDictEntryRepo2.CollectionMode.pron);
        adjectivesFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getAdjectivesByPron(), JMDictEntryRepo2.CollectionMode.pron);
        adnominalsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getAdnominalsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        exclamationsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getExclamationsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        symbolsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getSymbolsByPron(), JMDictEntryRepo2.CollectionMode.pron);
        fillersFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getFillersByPron(), JMDictEntryRepo2.CollectionMode.pron);
        othersFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getOthersByPron(), JMDictEntryRepo2.CollectionMode.pron);
        unclassifiedFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(e.getUnclassifiedByPron(), JMDictEntryRepo2.CollectionMode.pron);
    }

    public HashSet<String> getParticlesFound() {
        return particlesFound;
    }

    public HashSet<String> getVerbsFound() {
        return verbsFound;
    }

    public HashSet<String> getAdverbsFound() {
        return adverbsFound;
    }

    public HashSet<String> getConjunctionsFound() {
        return conjunctionsFound;
    }

    public HashSet<String> getNounsFound() {
        return nounsFound;
    }

    public HashSet<String> getProperNounsFound() {
        return properNounsFound;
    }

    public HashSet<String> getPrefixesFound() {
        return prefixesFound;
    }

    public HashSet<String> getAdjectivesFound() {
        return adjectivesFound;
    }

    public HashSet<String> getAdnominalsFound() {
        return adnominalsFound;
    }

    public HashSet<String> getExclamationsFound() {
        return exclamationsFound;
    }

    public HashSet<String> getSymbolsFound() {
        return symbolsFound;
    }

    public HashSet<String> getFillersFound() {
        return fillersFound;
    }

    public HashSet<String> getOthersFound() {
        return othersFound;
    }

    public HashSet<String> getUnclassifiedFound() {
        return unclassifiedFound;
    }
}
