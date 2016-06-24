package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public class JMDictWordRepository2 {

    @PersistenceContext
//    @Autowired
            EntityManager em;

    public Iterable<JMDictWord> getSome(Iterable<ForwardingToken> tokensToSearch) {
        List<String> baseFormsToQuery = new ArrayList<>();
        tokensToSearch.forEach(forwardingToken -> baseFormsToQuery.add(forwardingToken.getBaseForm()));

        TypedQuery<JMDictWord> query = em.createQuery(
                "SELECT a FROM JMDictWord a " +
                        "WHERE a.data IN :data",
                JMDictWord.class
        );
        query.setParameter("data", baseFormsToQuery);
        // offset
//        query.setFirstResult(100)
        // page size
//        query.setMaxResults(10)
        return query.getResultList();
    }
}
