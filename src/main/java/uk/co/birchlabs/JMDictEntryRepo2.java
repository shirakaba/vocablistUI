package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jamiebirch on 29/06/2016.
 */
@Repository
public class JMDictEntryRepo2 {
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


    public enum CollectionMode {
        pron,
        word
    }

    public static List<String> collectWordsOrPronOfEntries(List<JMDictEntry> entries, CollectionMode mode){
        switch (mode) {
            case pron:
                return entries
                        .stream()
                        .flatMap(
                                entry -> entry
                                        .getPron()
                                        .stream()
                                        .map(
                                                pron -> pron
                                                        .getIdDataKey()
                                                        .getData()
                                        )
                        )
                        .collect(Collectors.toList()
                        );
            break;
            case word:
                return entries
                        .stream()
                        .flatMap(
                                entry -> entry
                                        .getWords()
                                        .stream()
                                        .map(
                                                word -> word
                                                        .getIdDataKey()
                                                        .getData()
                                        )
                        )
                        .collect(Collectors.toList()
                        );
            break;
        }
    }
}
