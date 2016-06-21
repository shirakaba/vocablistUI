package uk.co.birchlabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by jamiebirch on 21/06/2016.
 */
@Repository
public class Repo {

    @Autowired
    EntityManager entityManager;

    public Iterable<JMDictPronunciation> getSome() {
        TypedQuery<JMDictPronunciation> query = entityManager.createQuery(
                "SELECT a FROM JMDictPronunciation a",
                JMDictPronunciation.class
        );
        query.setMaxResults(10);
        return query.getResultList();
    }
}
