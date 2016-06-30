package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Deprecated
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
        return query.getResultList();
    }

    public enum POS {
        particles,
        verbsAndAux,
        adverbs,
        conjunctions,
        nouns,
        prefixes,
        adjectives,
        adnominals,
        exclamations,
        symbols,
        fillers,
        others,
        unclassified
    }

    public static final List<String> grammatical = Lists.newArrayList(
            "adj-i",
            "adj-na",
            "adj-no",
            "adj-pn",
            "adj-t",
            "adj-f",
            "adj",
            "adv",
            "adv-n",
            "adv-to",
            "aux",
            "aux-v",
            "aux-adj",
            "conj",
            "pref",
            "prt",
            "suf"
    );

    public static final List<String> nouns = Lists.newArrayList(
            "ctr",
            "exp",
            "int", // adnominal in Mecab
            "n",
            "n-adv",
            "n-pref",
            "n-suf",
            "n-t",
            "num",
            "pn"
    );


    public static final List<String> verbs = Lists.newArrayList(
            "iv",
            "v1",
            "v2a-s",
            "v4h",
            "v4r",
            "v5",
            "v5aru",
            "v5b",
            "v5g",
            "v5k",
            "v5k-s",
            "v5m",
            "v5n",
            "v5r",
            "v5r-i",
            "v5s",
            "v5t",
            "v5u",
            "v5u-s",
            "v5uru",
            "v5z",
            "vz",
            "vi",
            "vk",
            "vn",
            "vs",
            "vs-c",
            "vs-i",
            "vs-s",
            "vt"
    );

    public Iterable<JMDictEntry> getEntriesFromPronunciation(Iterable<ForwardingToken> tokensToSearch, Mode mode, POS pos) {
        List<String> readingsToQuery = new ArrayList<>();
        List<String> acceptablePOS;
        boolean secondParameter = true;

        tokensToSearch.forEach(token -> {
                    if (token.isVerb()) readingsToQuery.add(token.getBaseForm()); // search for verbs by their baseform eg. する
                    else readingsToQuery.add(Utils.convertKana(token.getReading())); // search for native words in hiragana eg. として
                }
        );

        final String restrictPOSClause;

        switch(pos){
            case conjunctions:
            case particles:
            case adverbs:
            case adjectives:
            case adnominals:
                acceptablePOS = grammatical;
                restrictPOSClause = "AND t.senseDataKey.data IN :acceptablePOS ";
                break;
            case prefixes:
            case nouns:
            // generally will have been found by kanji baseForm
                restrictPOSClause = "AND t.senseDataKey.data IN :acceptablePOS ";
                acceptablePOS = nouns;
                break;
            case verbsAndAux:
                restrictPOSClause = "AND t.senseDataKey.data IN :acceptablePOS ";
                acceptablePOS = verbs;
                break;
            // Take all you can find
            case exclamations:
            case symbols:
            case fillers:
            case others:
            case unclassified:
                acceptablePOS = new ArrayList<>();
                restrictPOSClause = "";
                secondParameter = false;
                break;
            default:
                acceptablePOS = new ArrayList<>();
                restrictPOSClause = "";
                secondParameter = false;
                throw new IllegalStateException();
        }

        TypedQuery<JMDictEntry> query = em.createQuery(
                "SELECT a " +
                        "FROM JMDictEntry a " +
//                        "JOIN JMDictPronunciation p " +
//                        "  ON a.id = p.idDataKey.id " +
                        "JOIN JMDictSense s " + // somehow can't seem to join to Sense and Type
                        "  ON s.id = a.id "
//                        + "JOIN JMDictType t " +
//                        "ON t.senseDataKey.sense = s.data "
//                        + "WHERE p.idDataKey.data IN :readingsToQuery "
//                        + restrictPOSClause
//                        + "GROUP BY p.idDataKey.id"
                ,
                JMDictEntry.class
        );
//        query.setParameter("readingsToQuery", readingsToQuery);
//        if(secondParameter) query.setParameter("acceptablePOS", acceptablePOS);
//        query.setMaxResults(50);
        return query.getResultList();
    }
}
