package uk.co.birchlabs;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jamiebirch on 28/06/2016.
 */
@Repository
public class JMDictDefinitionRepository2 {
    @PersistenceContext
//    @Autowired
    EntityManager em;

    public Iterable<JMDictDefinition> getAllWithId(Integer id){
        List<Integer> idsToQuery = new ArrayList<>();
//        ids.forEach(id -> idsToQuery.add(id.getId()));

        TypedQuery<JMDictDefinition> query = em.createQuery(
                "SELECT a FROM JMDictDefinition a " +
                        "WHERE a.data = :id",
                JMDictDefinition.class
        );
        query.setParameter("id", idsToQuery);

        return query.getResultList();
    }
}
