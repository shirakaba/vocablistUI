package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamiebirch on 29/06/2016.
 */
@Repository
public class JMDictEntryRepository2 {
    @PersistenceContext
    EntityManager em;

    public Iterable<JMDictEntry> getEntries(Iterable<ForwardingToken> tokensToSearch) {
        List<String> baseFormsToQuery = new ArrayList<>();
        tokensToSearch.forEach(forwardingToken -> baseFormsToQuery.add(forwardingToken.getBaseForm()));
        TypedQuery<JMDictEntry> query = em.createQuery(
                "SELECT a " +
                        "FROM JMDictEntry a " +
                        // only need to specify the join because we're using a WHERE clause on it?
                        "JOIN FETCH JMDictWord w " +
                        "  ON a.id = w.idDataKey.id " +
                        "WHERE w.idDataKey.data IN :data " +
                        "GROUP BY w.idDataKey.id",
                JMDictEntry.class
        );
        query.setParameter("data", baseFormsToQuery);
        return query.getResultList();
    }
}
