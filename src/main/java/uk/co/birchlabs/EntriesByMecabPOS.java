package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 03/07/2016.
 */
public class EntriesByMecabPOS {

    private final List<JMDictEntry>
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
            List<JMDictEntry> particlesByPron,
            List<JMDictEntry> verbsByPron,
            List<JMDictEntry> adverbsByPron,
            List<JMDictEntry> conjunctionsByPron,
            List<JMDictEntry> nounsByPron,
            List<JMDictEntry> properNounsByPron,
            List<JMDictEntry> prefixesByPron,
            List<JMDictEntry> adjectivesByPron,
            List<JMDictEntry> adnominalsByPron,
            List<JMDictEntry> exclamationsByPron,
            List<JMDictEntry> symbolsByPron,
            List<JMDictEntry> fillersByPron,
            List<JMDictEntry> othersByPron,
            List<JMDictEntry> unclassifiedByPron
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


    public List<JMDictEntry> getParticlesByPron() {
        return particlesByPron;
    }

    public List<JMDictEntry> getVerbsByPron() {
        return verbsByPron;
    }

    public List<JMDictEntry> getAdverbsByPron() {
        return adverbsByPron;
    }

    public List<JMDictEntry> getConjunctionsByPron() {
        return conjunctionsByPron;
    }

    public List<JMDictEntry> getNounsByPron() {
        return nounsByPron;
    }

    public List<JMDictEntry> getProperNounsByPron() {
        return properNounsByPron;
    }

    public List<JMDictEntry> getPrefixesByPron() {
        return prefixesByPron;
    }

    public List<JMDictEntry> getAdjectivesByPron() {
        return adjectivesByPron;
    }

    public List<JMDictEntry> getAdnominalsByPron() {
        return adnominalsByPron;
    }

    public List<JMDictEntry> getExclamationsByPron() {
        return exclamationsByPron;
    }

    public List<JMDictEntry> getSymbolsByPron() {
        return symbolsByPron;
    }

    public List<JMDictEntry> getFillersByPron() {
        return fillersByPron;
    }

    public List<JMDictEntry> getOthersByPron() {
        return othersByPron;
    }

    public List<JMDictEntry> getUnclassifiedByPron() {
        return unclassifiedByPron;
    }
}
