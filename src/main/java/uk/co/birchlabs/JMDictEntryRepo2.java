package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

import static uk.co.birchlabs.JMDictEntry.START_OF_PROPER_NOUNS_ID;

/**
 * Created by jamiebirch on 29/06/2016.
 */
@Repository
public class JMDictEntryRepo2 {
    @PersistenceContext
    EntityManager em;

    /**
     * Gets all entries by their kanji forms by searching in the jmdict_word table. Will only search for proper nouns if
     * assured it has been given an Iterable of tokens tagged as proper nouns. Generally, this has a great capture rate,
     * but in cases where MeCab tags a proper noun as a general noun (eg. 大江戸 : 名詞,一般,*,*), it will fail to find
     * that word unless there is an entry for it in the table outside of the enamdict entries.
     *
     * We could remove the properNounsClause to search both the jmdict and enamdict portions of the table, but rowids
     * 171,733 - 830,560 of the table are enamdict (ie. 79% of jmdict_word is enamdict proper nouns). Options:
     * 1) Change nothing and risk being unable to match (admittedly rare) mislabelled proper nouns. [easy]
     * 2) Make the searchspace for all tokens five times bigger by removing the clause altogether. [bad]
     * 3) Include general nouns in the Iterable of proper nouns and make the searchspace five times bigger for them. [?]
     * @param tokensToSearch - tokens to search in jmdict_word by their kanji reading.
     * @param ignoreProperNouns - if true: ignore the enamdict (extra proper noun) entries. Else, ignore the jmdict entries.
     * @return - An Iterable of all applicable JMDictEntries.
     */
    public Iterable<JMDictEntry> getEntries(Iterable<ForwardingToken> tokensToSearch, boolean ignoreProperNouns) {
        final String properNounsClause;

        if(ignoreProperNouns) properNounsClause = "WHERE a.id < " + START_OF_PROPER_NOUNS_ID + " ";
        else properNounsClause = "WHERE a.id > " + (START_OF_PROPER_NOUNS_ID - 1) + " ";
        List<String> baseFormsToQuery = new ArrayList<>();
        tokensToSearch.forEach(forwardingToken -> baseFormsToQuery.add(forwardingToken.getBaseForm()));
        TypedQuery<JMDictEntry> query = em.createQuery(
                "SELECT a " +
                        "FROM JMDictEntry a " +
                        // only need to specify the join because we're using a WHERE clause on it?
                        "JOIN FETCH JMDictWord w " +
                        "  ON a.id = w.idDataKey.id " +
                        properNounsClause +
                        " AND w.idDataKey.data IN :data " +
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

    /**
     * Builds a list of all the kanji forms (from jmdict_word) or hiragana forms (from jmdict_pronunciation) that a List
     * JMDictEntries join to.
     * @param entries - the entries to collect the words or pronunciations for.
     * @param mode - if 'word': gets the data from jmdict_word. If 'pron': gets the data from jmdict_pronunciation.
     * @return - a flat list of strings.
     */
    public static HashSet<String> collectWordsOrPronOfEntries(Collection<JMDictEntry> entries, CollectionMode mode){
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
                        .collect(Collectors.toCollection(HashSet::new)
                        );
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
                        .collect(Collectors.toCollection(HashSet::new)
                        );
            default:
                throw new IllegalStateException();
        }
    }
}
