package uk.co.birchlabs;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class JMDictWordRepository2 {

    @PersistenceContext
//    @Autowired
            EntityManager em;

    public Iterable<JMDictWord> getSome(Iterable<String> readings) {
        TypedQuery<JMDictWord> query = em.createQuery(
                "SELECT a FROM JMDictWord a " +
                        "WHERE a.data IN :data",
                JMDictWord.class
        );
        query.setParameter("data", readings);
        // offset
//        query.setFirstResult(100)
        // page size
//        query.setMaxResults(10)
        return query.getResultList();
    }
}
