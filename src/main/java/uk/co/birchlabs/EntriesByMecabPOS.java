package uk.co.birchlabs;

import java.util.List;
import java.util.HashSet;

/**
 * Created by jamiebirch on 03/07/2016.
 */
public class EntriesByMecabPOS {

    private final HashSet<JMDictEntry>
            particlesByPron,
            verbsByPron,
            adverbsByPron,
            conjunctionsByPron,
            nounsByPron,
            properNounsByPron,
            prefixesByPron,
            adjectivesByPron,
            adnominalsByPron,
            exclamationsByPron,
            symbolsByPron,
            fillersByPron,
            othersByPron,
            unclassifiedByPron;


    public EntriesByMecabPOS(
            HashSet<JMDictEntry> particlesByPron,
            HashSet<JMDictEntry> verbsByPron,
            HashSet<JMDictEntry> adverbsByPron,
            HashSet<JMDictEntry> conjunctionsByPron,
            HashSet<JMDictEntry> nounsByPron,
            HashSet<JMDictEntry> properNounsByPron,
            HashSet<JMDictEntry> prefixesByPron,
            HashSet<JMDictEntry> adjectivesByPron,
            HashSet<JMDictEntry> adnominalsByPron,
            HashSet<JMDictEntry> exclamationsByPron,
            HashSet<JMDictEntry> symbolsByPron,
            HashSet<JMDictEntry> fillersByPron,
            HashSet<JMDictEntry> othersByPron,
            HashSet<JMDictEntry> unclassifiedByPron
    ) {
        this.particlesByPron = particlesByPron;
        this.verbsByPron = verbsByPron;
        this.adverbsByPron = adverbsByPron;
        this.conjunctionsByPron = conjunctionsByPron;
        this.nounsByPron = nounsByPron;
        this.properNounsByPron = properNounsByPron;
        this.prefixesByPron = prefixesByPron;
        this.adjectivesByPron = adjectivesByPron;
        this.adnominalsByPron = adnominalsByPron;
        this.exclamationsByPron = exclamationsByPron;
        this.symbolsByPron = symbolsByPron;
        this.fillersByPron = fillersByPron;
        this.othersByPron = othersByPron;
        this.unclassifiedByPron = unclassifiedByPron;
    }


    public HashSet<JMDictEntry> getParticlesByPron() {
        return particlesByPron;
    }

    public HashSet<JMDictEntry> getVerbsByPron() {
        return verbsByPron;
    }

    public HashSet<JMDictEntry> getAdverbsByPron() {
        return adverbsByPron;
    }

    public HashSet<JMDictEntry> getConjunctionsByPron() {
        return conjunctionsByPron;
    }

    public HashSet<JMDictEntry> getNounsByPron() {
        return nounsByPron;
    }

    public HashSet<JMDictEntry> getProperNounsByPron() {
        return properNounsByPron;
    }

    public HashSet<JMDictEntry> getPrefixesByPron() {
        return prefixesByPron;
    }

    public HashSet<JMDictEntry> getAdjectivesByPron() {
        return adjectivesByPron;
    }

    public HashSet<JMDictEntry> getAdnominalsByPron() {
        return adnominalsByPron;
    }

    public HashSet<JMDictEntry> getExclamationsByPron() {
        return exclamationsByPron;
    }

    public HashSet<JMDictEntry> getSymbolsByPron() {
        return symbolsByPron;
    }

    public HashSet<JMDictEntry> getFillersByPron() {
        return fillersByPron;
    }

    public HashSet<JMDictEntry> getOthersByPron() {
        return othersByPron;
    }

    public HashSet<JMDictEntry> getUnclassifiedByPron() {
        return unclassifiedByPron;
    }
}
