package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static uk.co.birchlabs.JMDictEntry.START_OF_PROPER_NOUNS_ID;
import static uk.co.birchlabs.SQLiteDialect.MAX_HOST_PARAMETERS;

/**
 * Created by jamiebirch on 23/06/2016.
 */
@Repository
public class JMDictPronRepo2 {

    @PersistenceContext
//    @Autowired
    EntityManager em;

    public enum Mode {
        READINGS_IN_HIRAGANA,
        READINGS_IN_KATAKANA
    }

    @Deprecated
    public Iterable<JMDictPron> getSome(Iterable<ForwardingToken> tokensToSearch, Mode mode) {
        List<String> readingsToQuery = new ArrayList<>();
        tokensToSearch.forEach(forwardingToken -> {
            // TODO: hard mode: subtract any しか for which the POS (eg. noun) doesn't match the offered POS (such as particle)
            // There are ~fifteen しか, and multiple of の, よく, も, ない, いる found.
            switch(mode){ // Note: most likely could search by baseForm for all of these. Not sure there's ever any difference in these cases.
                case READINGS_IN_HIRAGANA:
                    if(forwardingToken.isVerb()) readingsToQuery.add(forwardingToken.getBaseForm()); // search for verbsAndAux by their baseform eg. する
                    else readingsToQuery.add(Utils.convertKana(forwardingToken.getReading())); // search for native words in hiragana eg. として
                    break;
                case READINGS_IN_KATAKANA:
                    readingsToQuery.add(forwardingToken.getReading()); // search for possible loan words in their native katakana. eg. キャンパス
                    break;
                default:
                    break;
            }
        });

        TypedQuery<JMDictPron> query = em.createQuery(
                "SELECT a FROM JMDictPronunciation a " +
                        "WHERE a.data IN :data",
                JMDictPron.class
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
        properNouns,
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
            "aux-adj", // for -づらい, -ほしい
            "adj"
    );

    public static final List<String> adverbs = Lists.newArrayList(
            "adj-na", // an extra option for たぶん, あまり
            "adj-no", // an extra option for たぶん, あまり
            "adv",    // for most
            "adv-n",
            "exp",    // for なにか, あんなに
            "adv-to"
    );

    public static final List<String> adfixes = Lists.newArrayList(
            "n-pref",  // 夫
            "n-suf",   // 男
            "pref",   // go, o,
            "suf"     // go
    );

    public static final List<String> nouns = Lists.newArrayList(
            "ctr",
            "exp",
            "int",   // adnominals in Mecab
            "n",
            "n-adv",
            "n-t",
            "num",
            "adj-t", // for 暗然 (あんぜん), 決然 (けつぜん)
            "adj-f", // for 移動 (いどう), 遺伝子 (いでんし), 月間 (げっかん)
            "pn"
    );

    public static final List<String> properNouns = Lists.newArrayList(
            "pn"
    );


    public static final List<String> verbsAndAux = Lists.newArrayList(
            "aux",     // Mecab tags です and であろう as 助動詞; jmdict tags them as aux.
            "aux-adj", // Mecab tags らしい and -ない as 助動詞; jmdict tags them as aux.
            "aux-v",
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

    public static final List<String> all = Lists.newArrayList(
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
            "ctr",
            "exp",
            "int",
            "iv",
            "n",
            "n-adv",
            "n-pref",
            "n-suf",
            "n-t",
            "num",
            "pn",
            "pref",
            "prt",
            "suf",
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


    /**
     * Calls getEntriesFromPron() by the 'search readings in hiragana' mode.
     * @param tokensToSearch - Tokens to search for the readings of in jmdict_pronunciation.
     * @param pos - the POS tagged by MeCab.
     * @return - an iterable of eligible JMDictEntrys.
     */
    public Iterable<JMDictEntry> getEntriesFromPron(Iterable<ForwardingToken> tokensToSearch, POS pos) {
        return getEntriesFromPron(tokensToSearch, Mode.READINGS_IN_HIRAGANA, pos);
    }

    /**
     * Searches in jmdict_pronunciation by the readings of a Token in hiragana format.
     * @param tokensToSearch - Tokens to search for the readings of in jmdict_pronunciation.
     * @param mode - READINGS_IN_HIRAGANA to search the table in hiragana, or READINGS_IN_KATAKANA to search in katakana.
     * @param pos - the POS tagged by MeCab.
     * @return - an iterable of eligible JMDictEntrys.
     */
    public Iterable<JMDictEntry> getEntriesFromPron(Iterable<ForwardingToken> tokensToSearch, Mode mode, POS pos) {
        Set<String> readingsToQuery = new HashSet<>();
        List<String> acceptablePOS;
        final String restrictPOSClause;
        final String properNounsClause;
        boolean ignoreProperNouns = true;

        switch (mode) { // Note: most likely could search by baseForm for all of these. Not sure there's ever any difference in these cases.
            case READINGS_IN_HIRAGANA:
                tokensToSearch.forEach(forwardingToken -> {
                    if (forwardingToken.isVerb()) readingsToQuery.add(forwardingToken.getBaseForm()); // search for verbsAndAux by their baseform eg. する
                    else readingsToQuery.add(Utils.convertKana(forwardingToken.getReading())); // search for native words in hiragana eg. として
                });
                break;
            case READINGS_IN_KATAKANA:
                tokensToSearch.forEach(forwardingToken ->
                    readingsToQuery.add(forwardingToken.getReading()) // search for possible loan words in their native katakana. eg. キャンパス
                );
                break;
            default:
                throw new NotImplementedException();
        }


        switch(pos){
            case particles:
            case conjunctions:
//                acceptablePOS = particles;
                restrictPOSClause = "AND s.particles = 1 ";
                break;
            case adverbs:
//                acceptablePOS = adverbs;
                restrictPOSClause = "AND s.adverbs = 1 ";
                break;
            case adjectives:
//                acceptablePOS = adjectives;
                restrictPOSClause = "AND s.adjectives = 1 ";
                break;
            case adnominals:
//                acceptablePOS = adnominals;
                restrictPOSClause = "AND s.adnominals = 1 ";
                break;
            case prefixes:
//                acceptablePOS = adfixes;
                restrictPOSClause = "AND s.adfixes = 1 ";
                break;
            case nouns:
//                acceptablePOS = nouns;
                restrictPOSClause = "AND s.nouns = 1 ";
                break;
            case properNouns:
//                acceptablePOS = properNouns;
                restrictPOSClause = "AND s.propernouns = 1 ";
                ignoreProperNouns = false;
                break;
            case verbsAndAux:
//                acceptablePOS = verbsAndAux;
                restrictPOSClause = "AND s.verbsandaux = 1 ";
                break;
            // Take all you can find
            case exclamations:
            case symbols:
            case fillers:
            case others:
            case unclassified:
                restrictPOSClause = "";
//                acceptablePOS = all;
                break;
            default:
                throw new IllegalStateException();
        }


        if(ignoreProperNouns) properNounsClause = "WHERE a.id < " + START_OF_PROPER_NOUNS_ID + " ";
        else properNounsClause = "WHERE a.id > " + (START_OF_PROPER_NOUNS_ID - 1) + " ";
//        restrictPOSClause = "AND t.senseDataKey.data IN :acceptablePOS ";

        if(readingsToQuery.isEmpty()) return new ArrayList<>(); // bail out if nothing to search database with.

        List<List<String>> partitionedReadingsToQuery = Lists.partition(
//                readingsToQuery.stream().collect(Collectors.toList()), MAX_HOST_PARAMETERS - acceptablePOS.size()
                readingsToQuery.stream().collect(Collectors.toList()), MAX_HOST_PARAMETERS
        );

        List<JMDictEntry> resultList = new ArrayList<>();
        partitionedReadingsToQuery.parallelStream().forEach(partition -> {
            TypedQuery<JMDictEntry> query = em.createQuery(
                    "SELECT a " + // JOIN FETCH is certainly faster (2s).
                            "FROM JMDictEntry a " +
                            "JOIN FETCH JMDictPron p " +
                            "  ON a.id = p.idDataKey.id " +
                            "JOIN FETCH JMDictSense s " +
                            "  ON s.id = a.id "
//                            + "JOIN JMDictType t " +
//                            "ON t.senseDataKey.sense = s.data "
                            + properNounsClause
                            + " AND p.idDataKey.data IN :readingsToQuery "
                            + restrictPOSClause
                            + " GROUP BY p.idDataKey.id"
                    ,
                    JMDictEntry.class
            );
            query.setParameter("readingsToQuery", partition);
//            query.setParameter("acceptablePOS", acceptablePOS);
            resultList.addAll(query.getResultList());
        });

        return resultList;
    }
}
