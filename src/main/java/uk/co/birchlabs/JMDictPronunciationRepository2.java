package uk.co.birchlabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by jamiebirch on 23/06/2016.
 */
@Repository
public class JMDictPronunciationRepository2 {

    @Autowired
    EntityManager em;

    public Iterable<JMDictPronunciation> getSome(Iterable<String> readings) {
        TypedQuery<JMDictPronunciation> query = em.createQuery(
                "SELECT a FROM JMDictPronunciation a " +
                        "WHERE a.data IN :data",
                JMDictPronunciation.class
        );
        query.setParameter("data", readings);
        // offset
//        query.setFirstResult(100)
        // page size
//        query.setMaxResults(10)
        return query.getResultList();
    }
}
