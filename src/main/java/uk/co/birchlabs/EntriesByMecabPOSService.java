package uk.co.birchlabs;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getParticles(), mode, JMDictPronRepo2.POS.particles)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getVerbsAndAux(), mode, JMDictPronRepo2.POS.verbsAndAux)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdverbs(), mode, JMDictPronRepo2.POS.adverbs)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getConjunctions(), mode, JMDictPronRepo2.POS.conjunctions)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getNouns(), mode, JMDictPronRepo2.POS.nouns)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getProperNouns(), mode, JMDictPronRepo2.POS.properNouns)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getPrefixes(), mode, JMDictPronRepo2.POS.prefixes)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdjectives(), mode, JMDictPronRepo2.POS.adjectives)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getAdnominals(), mode, JMDictPronRepo2.POS.adnominals)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getExclamations(), mode, JMDictPronRepo2.POS.exclamations)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getSymbols(), mode, JMDictPronRepo2.POS.symbols)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getFillers(), mode, JMDictPronRepo2.POS.fillers)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getOthers(), mode, JMDictPronRepo2.POS.others)),
                Sets.newHashSet(jmDictPronRepo2.getEntriesFromPron(tokensByMecabPOS.getUnclassified(), mode, JMDictPronRepo2.POS.unclassified))
        );
    }
}
