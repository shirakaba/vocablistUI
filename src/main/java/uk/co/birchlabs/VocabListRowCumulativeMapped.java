package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import catRecurserPkg.Sentence;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import uk.co.birchlabs.JMDictEntryRepo2.CollectionMode;
import uk.co.birchlabs.JMDictPronRepo2.Mode;
import uk.co.birchlabs.JMDictPronRepo2.POS;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static uk.co.birchlabs.JMDictPronRepo2.POS.properNouns;

/**
 * Created by jamiebirch on 03/07/2016.
 */
public class VocabListRowCumulativeMapped {
    private final VocabListRowCumu vocabListRowCumu;
    private final Collection<JMDictEntry> e;
    private final ForwardingToken token;
    private final String rowBaseForm;
    private final String tokenHiraganaPron;
    private final String tokenKatakanaPron;
    private final List<EntryReadout> entryReadouts;
    private final List<String> defs;
    private final POS pos;
    private final List<String> exampleSentences;

    private static final Integer MAX_PHONETIC_PROPER_NOUN_ENTRYREADOUTS = 4;
    private static final Integer MAX_EG_SENTENCES = 3;

    private List<String> convertSentencesToStrings(SetMultimap<ForwardingToken, Sentence> exampleSentences) {
        return exampleSentences
                .get(token)
                .stream()
                .unordered()
                .map(Sentence::getSentence)
                .limit(MAX_EG_SENTENCES)
                .collect(Collectors.toList())
                ;
    }

    public VocabListRowCumulativeMapped(
            VocabListRowCumu vocabListRowCumu,
            SetMultimap<ForwardingToken, Sentence> exampleSentences,
            Iterable<JMDictEntry> wordEntries,
            EntriesByMecabPOS hiraganaEntriesByPOS,
            EntriesByMecabPOS katakanaEntriesByPOS
    ) {
        this.vocabListRowCumu = vocabListRowCumu;
        token = vocabListRowCumu.getVocabListRow().getToken();
        rowBaseForm = token.getBaseForm();
        tokenKatakanaPron = token.getReading();
        tokenHiraganaPron = Utils.convertKana(tokenKatakanaPron);
        pos = TokensByMecabPOS.determinePOS(token);
        this.exampleSentences = convertSentencesToStrings(exampleSentences);

        e = new HashSet<>();

        e.addAll(collectEntriesMatchingTokenProperty(wordEntries, CollectionMode.word));
        if(e.isEmpty()) e.addAll(collectEntriesMatchingTokenProperty(hiraganaEntriesByPOS, CollectionMode.pron, Mode.READINGS_IN_HIRAGANA));
        if(e.isEmpty()) e.addAll(collectEntriesMatchingTokenProperty(katakanaEntriesByPOS, CollectionMode.pron, Mode.READINGS_IN_KATAKANA));
        if(e.isEmpty()) {
            entryReadouts = null;
            defs = Lists.newArrayList(String.format("No definitions found in dictionary. Search off-site at:" +
                    "http://www.edrdg.org/cgi-bin/wwwjdic/wwwjdic?QMDJ%s", token.getBaseForm()));
        }
        else {
            entryReadouts = e
                    .stream()
                    .map(entry -> new EntryReadout(entry, token)).collect(Collectors.toList());
            if(pos.equals(properNouns) && (rowBaseForm.equals(tokenHiraganaPron) || rowBaseForm.equals(tokenKatakanaPron))) {
                int count = entryReadouts.size();
                if(count > MAX_PHONETIC_PROPER_NOUN_ENTRYREADOUTS){
                    entryReadouts.subList(MAX_PHONETIC_PROPER_NOUN_ENTRYREADOUTS -1, count -1).clear();
                    entryReadouts.add(new EntryReadout(count - MAX_PHONETIC_PROPER_NOUN_ENTRYREADOUTS, token));
                }
            }
            defs = entryReadouts.stream().map(EntryReadout::getDescription).collect(Collectors.toList());
        }
//        System.out.println("Gotta go fast!");
    }

    public List<String> getExampleSentences() {
//        return Lists.partition(exampleSentences, MAX_EG_SENTENCES).get(0);
        return exampleSentences;
//        return new ArrayList<>(); // For evangelion.txt: 530kB added to a 360kB JSON with 3 sentences
    }

    public List<String> getDefs() {
        return defs;
    }

    public String getBf() {
        return rowBaseForm;
    }

    public Float getIso() { return vocabListRowCumu.getIsolatePercent(); }

    public Float getCumu() { return vocabListRowCumu.getCumulativePercent(); }
    public boolean isFundamental() { return vocabListRowCumu.isFundamental(); }
    public boolean isN5() { return vocabListRowCumu.isN5(); }
    public boolean isN4() { return vocabListRowCumu.isN4(); }
    public boolean isN3() { return vocabListRowCumu.isN3(); }
    public boolean isN2() { return vocabListRowCumu.isN2(); }
    public boolean isN1() { return vocabListRowCumu.isN1(); }

    // Currently gets the MeCab pos. We have access to the more expansive JMdict POS, but this is simpler to read.
    public String getPos() {
        switch (pos) {
            case particles:
                return "particle";
            case verbsAndAux:
                return "verb";
            case adverbs:
                return "adverb";
            case conjunctions:
                return "conjunction";
            case nouns:
                return "noun";
            case properNouns:
                return "proper noun";
            case prefixes:
                return "prefix";
            case adjectives:
                return "adjective";
            case adnominals:
                return "adnominal";
            case exclamations:
                return "exclamation";
            case symbols:
                return "symbol";
            case fillers:
                return "filler";
            case others:
                return "other";
            case unclassified:
                return "unclassified";
            default:
                return "undocumented POS";
        }
    }

    private List<JMDictEntry> collectEntriesMatchingTokenProperty(EntriesByMecabPOS entriesByMecabPOS, CollectionMode collectionMode, Mode mode) {
        List<JMDictEntry> list = new ArrayList<>();

        switch (pos){
            case particles:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getParticlesByPron(), collectionMode, mode));
                break;
            case verbsAndAux:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getVerbsByPron(), collectionMode, mode));
                break;
            case adverbs:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getAdverbsByPron(), collectionMode, mode));
                break;
            case conjunctions:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getConjunctionsByPron(), collectionMode, mode));
                break;
            case nouns:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getNounsByPron(), collectionMode, mode));
                break;
            case properNouns:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getProperNounsByPron(), collectionMode, mode));
                break;
            case prefixes:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getPrefixesByPron(), collectionMode, mode));
                break;
            case adjectives:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getAdjectivesByPron(), collectionMode, mode));
                break;
            case adnominals:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getAdnominalsByPron(), collectionMode, mode));
                break;
            case exclamations:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getExclamationsByPron(), collectionMode, mode));
                break;
            case symbols:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getSymbolsByPron(), collectionMode, mode));
                break;
            case fillers:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getFillersByPron(), collectionMode, mode));
                break;
            case others:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getOthersByPron(), collectionMode, mode));
                break;
            case unclassified:
                list.addAll(collectEntriesMatchingTokenProperty(entriesByMecabPOS.getUnclassifiedByPron(), collectionMode, mode));
                break;
            default:
                throw new IllegalStateException();
        }

        return list;
    }

    private List<JMDictEntry> collectEntriesMatchingTokenProperty(Iterable<JMDictEntry> wordEntries, CollectionMode collectionMode) {
        return collectEntriesMatchingTokenProperty(wordEntries, collectionMode, Mode.READINGS_IN_HIRAGANA);
    }

    private List<JMDictEntry> collectEntriesMatchingTokenProperty(Iterable<JMDictEntry> wordEntries, CollectionMode collectionMode, Mode mode) {
        switch(collectionMode){
            case pron:
                return StreamSupport
                        .stream(wordEntries.spliterator(), false)
                        // take only whose words...data equals the token's baseForm.
                        .filter(
                                entry -> Iterables.tryFind(
                                        entry
                                                .getPron()
                                                .stream()
                                                .map(
                                                        pron -> pron
                                                                .getIdDataKey()
                                                                .getData()
                                                )
                                                .filter(datum -> datum != null)
                                                .collect(Collectors.toList()),
                                        datum -> {
                            /* ISSUES:
                             * 動詞,自立,*,*,サ変・スル,未然レル接続,する,サ,サ -> generally 1157170 (為る)
                             * 名詞,固有名詞,地域,一般,*,*,練馬,ネリマ,ネリマ -> needs jmn_edict
                             */
                            if(token.isVerb()) return datum.equals(rowBaseForm);
                            else if(mode.equals(Mode.READINGS_IN_HIRAGANA)) return datum.equals(tokenHiraganaPron);
                            // confirmed to work: catches 名詞,一般,*,*,*,*,アニメ,アニメ,アニメ
                            else if(mode.equals(Mode.READINGS_IN_KATAKANA)) return datum.equals(tokenKatakanaPron);
                            else throw new IllegalStateException();
                        }
                                )
                                        .isPresent()
                        )
                        .collect(Collectors.toList());
            case word:
                return StreamSupport
                        .stream(wordEntries.spliterator(), false)
                        // take only whose words...data equals the token's baseForm.
                        .filter(
                                entry -> Iterables.tryFind(
                                        entry
                                                .getWords()
                                                .stream()
                                                .map(
                                                        word -> word
                                                                .getIdDataKey()
                                                                .getData()
                                                )
                                                .filter(datum -> datum != null)
                                                .collect(Collectors.toList()),
                                        datum -> datum.equals(rowBaseForm)
                                )
                                        .isPresent()
                        )
                .collect(Collectors.toList());
            default:
                throw new IllegalStateException();
        }

    }
}
