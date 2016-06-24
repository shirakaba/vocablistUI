package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamiebirch on 23/06/2016.
 */
@Repository
public class JMDictPronunciationRepository2 {

    @PersistenceContext
//    @Autowired
    EntityManager em;

    public enum Mode {
        READINGS_IN_HIRAGANA,
        READINGS_IN_KATAKANA
    }

    public Iterable<JMDictPronunciation> getSome(Iterable<ForwardingToken> tokensToSearch, Mode mode) {
        List<String> readingsToQuery = new ArrayList<>();
        tokensToSearch.forEach(forwardingToken -> {
            if(mode.equals(Mode.READINGS_IN_HIRAGANA)) readingsToQuery.add(Utils.convertKana(forwardingToken.getReading())); // search for native words in hiragana
            else readingsToQuery.add(forwardingToken.getReading()); // search for possible loan words in their native katakana.
        });

        TypedQuery<JMDictPronunciation> query = em.createQuery(
                "SELECT a FROM JMDictPronunciation a " +
                        "WHERE a.data IN :data",
                JMDictPronunciation.class
        );
        query.setParameter("data", readingsToQuery);
        // offset
//        query.setFirstResult(100)
        // page size
//        query.setMaxResults(10)
        return query.getResultList();
    }
}
