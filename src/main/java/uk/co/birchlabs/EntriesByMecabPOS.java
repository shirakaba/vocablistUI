package uk.co.birchlabs;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.birchlabs.JMDictPronRepo2.Mode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamiebirch on 03/07/2016.
 */
public class EntriesByMecabPOS {

    @Autowired
    JMDictPronRepo2 jmDictPronRepo2;

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

    public EntriesByMecabPOS(TokensByMecabPOS tokensByMecabPOS, Mode mode) {
        particlesByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getParticles(), mode, JMDictPronRepo2.POS.particles));
        verbsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getVerbsAndAux(), mode, JMDictPronRepo2.POS.verbsAndAux));
        adverbsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdverbs(), mode, JMDictPronRepo2.POS.adverbs));
        conjunctionsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getConjunctions(), mode, JMDictPronRepo2.POS.conjunctions));
        nounsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getNouns(), mode, JMDictPronRepo2.POS.nouns));
        prefixesByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getPrefixes(), mode, JMDictPronRepo2.POS.prefixes));
        adjectivesByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdjectives(), mode, JMDictPronRepo2.POS.adjectives));
        adnominalsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdnominals(), mode, JMDictPronRepo2.POS.adnominals));
        exclamationsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getExclamations(), mode, JMDictPronRepo2.POS.exclamations));
        symbolsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getSymbols(), mode, JMDictPronRepo2.POS.symbols));
        fillersByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getFillers(), mode, JMDictPronRepo2.POS.fillers));
        othersByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getOthers(), mode, JMDictPronRepo2.POS.others));
        unclassifiedByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getUnclassified(), mode, JMDictPronRepo2.POS.unclassified));
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
