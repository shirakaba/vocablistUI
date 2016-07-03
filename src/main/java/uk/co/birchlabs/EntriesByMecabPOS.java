package uk.co.birchlabs;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.birchlabs.JMDictPronRepo2.Mode;
import uk.co.birchlabs.JMDictPronRepo2.POS;

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
        this.prefixesByPron = prefixesByPron;
        this.adjectivesByPron = adjectivesByPron;
        this.adnominalsByPron = adnominalsByPron;
        this.exclamationsByPron = exclamationsByPron;
        this.symbolsByPron = symbolsByPron;
        this.fillersByPron = fillersByPron;
        this.othersByPron = othersByPron;
        this.unclassifiedByPron = unclassifiedByPron;
    }

    //    public EntriesByMecabPOS(TokensByMecabPOS tokensByMecabPOS, Mode mode) {
//        this.tokensByMecabPOS = tokensByMecabPOS;
//        this.mode = mode;
//    }
//
//    public void init() {
//        particlesByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getParticles(), mode, POS.particles));
//        verbsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getVerbsAndAux(), mode, POS.verbsAndAux));
//        adverbsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdverbs(), mode, POS.adverbs));
//        conjunctionsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getConjunctions(), mode, POS.conjunctions));
//        nounsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getNouns(), mode, POS.nouns));
//        prefixesByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getPrefixes(), mode, POS.prefixes));
//        adjectivesByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdjectives(), mode, POS.adjectives));
//        adnominalsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdnominals(), mode, POS.adnominals));
//        exclamationsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getExclamations(), mode, POS.exclamations));
//        symbolsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getSymbols(), mode, POS.symbols));
//        fillersByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getFillers(), mode, POS.fillers));
//        othersByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getOthers(), mode, POS.others));
//        unclassifiedByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getUnclassified(), mode, POS.unclassified));
//    }



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
