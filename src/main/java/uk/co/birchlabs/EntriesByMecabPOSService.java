package uk.co.birchlabs;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jamiebirch on 03/07/2016.
 */
@Service
public class EntriesByMecabPOSService {

    private final JMDictPronRepo2 jmDictPronRepo2;

    @Autowired
    public EntriesByMecabPOSService(JMDictPronRepo2 jmDictPronRepo2) {
        this.jmDictPronRepo2 = jmDictPronRepo2;
    }

    public EntriesByMecabPOS construct(
            TokensByMecabPOS tokensByMecabPOS,
            JMDictPronRepo2.Mode mode
    ) {
        return new EntriesByMecabPOS(
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getParticles(), mode, JMDictPronRepo2.POS.particles)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getVerbsAndAux(), mode, JMDictPronRepo2.POS.verbsAndAux)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdverbs(), mode, JMDictPronRepo2.POS.adverbs)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getConjunctions(), mode, JMDictPronRepo2.POS.conjunctions)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getNouns(), mode, JMDictPronRepo2.POS.nouns)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getPrefixes(), mode, JMDictPronRepo2.POS.prefixes)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdjectives(), mode, JMDictPronRepo2.POS.adjectives)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdnominals(), mode, JMDictPronRepo2.POS.adnominals)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getExclamations(), mode, JMDictPronRepo2.POS.exclamations)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getSymbols(), mode, JMDictPronRepo2.POS.symbols)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getFillers(), mode, JMDictPronRepo2.POS.fillers)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getOthers(), mode, JMDictPronRepo2.POS.others)),
                Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getUnclassified(), mode, JMDictPronRepo2.POS.unclassified))
        );
    }
}
