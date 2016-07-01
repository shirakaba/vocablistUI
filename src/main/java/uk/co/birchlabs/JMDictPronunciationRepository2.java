package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import com.google.common.collect.Lists;
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

    public static final List<String> adnominals = Lists.newArrayList(
            "adj-pn" // いわゆる, どの, この, おおきな
            // "adj-t",
            // "exp", // another option for ああいう
            // "adj-f"
    );

    public static final List<String> adjectives = Lists.newArrayList(
            "adj-i",
            "adj-na",
            "adj-no",
            "adj-pn",
            "adj-t",
            "adj-f",
            "adj"
    );

    public static final List<String> adverbs = Lists.newArrayList(
            "adj-na", // an extra option for たぶん, あまり
            "adj-no", // an extra option for たぶん, あまり
            "adv", // for most
            "adv-n",
            "exp", // for なにか, あんなに
            "adv-to"
    );

    public static final List<String> adfixes = Lists.newArrayList(
            "n-pref", // 夫
            "n-suf", // 男
            "pref", // go, o,
            "suf" // go
    );

    public static final List<String> nouns = Lists.newArrayList(
            "ctr",
            "exp",
            "int", // adnominals in Mecab
            "n",
            "n-adv",
            "n-t",
            "num",
            "adj-t", // for 暗然 (あんぜん), 決然 (けつぜん)
            "adj-f", // for 移動 (いどう), 遺伝子 (いでんし), 月間 (げっかん)
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

    public static final List<String> particles = Lists.newArrayList(
            "prt",
            "aux", // for -te
            "exp", // for niyotte
            "conj" // Included so we can handle conjunctions and particles in same acceptablePOS set 'keredomo'.
    );

    /**
     * Calls getEntriesFromPronunciation() by the 'search readings in hiragana' mode.
     * @param tokensToSearch - Tokens to search for the readings of in jmdict_pronunciation.
     * @param pos - the POS tagged by MeCab.
     * @return - an iterable of eligible JMDictEntrys.
     */
    public Iterable<JMDictEntry> getEntriesFromPronunciation(Iterable<ForwardingToken> tokensToSearch, POS pos) {
        return getEntriesFromPronunciation(tokensToSearch, Mode.READINGS_IN_HIRAGANA, pos);
    }

    /**
     * Searches in jmdict_pronunciation by the readings of a Token in hiragana format.
     * @param tokensToSearch - Tokens to search for the readings of in jmdict_pronunciation.
     * @param mode - READINGS_IN_HIRAGANA to search the table in hiragana, or READINGS_IN_KATAKANA to search in katakana.
     * @param pos - the POS tagged by MeCab.
     * @return - an iterable of eligible JMDictEntrys.
     */
    public Iterable<JMDictEntry> getEntriesFromPronunciation(Iterable<ForwardingToken> tokensToSearch, Mode mode, POS pos) {
        List<String> readingsToQuery = new ArrayList<>();
        List<String> acceptablePOS;
        final String restrictPOSClause;
        boolean restrictPOS = true;

        tokensToSearch.forEach(token -> {
                    if (token.isVerb()) readingsToQuery.add(token.getBaseForm()); // search for verbs by their baseform eg. する
                    else readingsToQuery.add(Utils.convertKana(token.getReading())); // search for native words in hiragana eg. として
                }
        );


        switch(pos){
            case particles:
            case conjunctions:
                acceptablePOS = particles;
                break;
            case adverbs:
                acceptablePOS = adverbs;
                break;
            case adjectives:
                acceptablePOS = adjectives;
                break;
            case adnominals:
                acceptablePOS = adnominals;
                break;
            case prefixes:
                acceptablePOS = adfixes;
                break;
            case nouns:
                acceptablePOS = nouns;
                break;
            case verbsAndAux:
                acceptablePOS = verbs;
                break;
            // Take all you can find
            case exclamations:
            case symbols:
            case fillers:
            case others:
            case unclassified:
                acceptablePOS = new ArrayList<>();
                restrictPOS = false;
                break;
            default:
                throw new IllegalStateException();
        }

        if(restrictPOS) restrictPOSClause = "AND t.senseDataKey.data IN :acceptablePOS ";
        else restrictPOSClause = "";

        TypedQuery<JMDictEntry> query = em.createQuery(
                "SELECT a " + // TODO: compare speed by just selecting a.id and rejoining later
                        "FROM JMDictEntry a " +
                        "JOIN JMDictPronunciation p " +
                        "  ON a.id = p.idDataKey.id " +
                        "JOIN JMDictSense s " + // somehow can't seem to join to Sense and Type
                        "  ON s.id = a.id "
                        + "JOIN JMDictType t " +
                        "ON t.senseDataKey.sense = s.data "
                        + "WHERE p.idDataKey.data IN :readingsToQuery "
                        + restrictPOSClause
//                        + "GROUP BY p.idDataKey.id"
                ,
                JMDictEntry.class
        );
        query.setParameter("readingsToQuery", readingsToQuery);
        if(restrictPOS) query.setParameter("acceptablePOS", acceptablePOS);
//        query.setMaxResults(50);
        return query.getResultList();
    }
}
