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
//        , HIRAGANA_VERBS
    }

    public Iterable<JMDictPronunciation> getSome(Iterable<ForwardingToken> tokensToSearch, Mode mode) {
        List<String> readingsToQuery = new ArrayList<>();
        tokensToSearch.forEach(forwardingToken -> {
            // TODO: hard mode: subtract any しか for which the POS (eg. noun) doesn't match the offered POS (such as particle)
            // There are ~fifteen しか, and multiple of の, よく, も, ない, いる found.
            switch(mode){ // Note: most likely could search by baseForm for all of these. Not sure there's ever any difference in these cases.
                case READINGS_IN_HIRAGANA:
                    if(forwardingToken.isVerb()) readingsToQuery.add(forwardingToken.getBaseForm()); // search for verbs by their baseform eg. する
                    else readingsToQuery.add(Utils.convertKana(forwardingToken.getReading())); // search for native words in hiragana eg. として
                    break;
                case READINGS_IN_KATAKANA:
                    readingsToQuery.add(forwardingToken.getReading()); // search for possible loan words in their native katakana. eg. キャンパス
                    break;
                default:
                    break;
//                case HIRAGANA_VERBS:
//                    break;
            }
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
