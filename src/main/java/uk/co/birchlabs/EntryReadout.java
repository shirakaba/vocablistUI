package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by jamiebirch on 04/07/2016.
 */
public class EntryReadout {
    private final ForwardingToken token;
    private final JMDictEntry entry;
    private final String description;
    private final List<JMDictSense> senses;
    private final SetMultimap<JMDictSense, String> sensesToDefs = HashMultimap.create();
    private final SetMultimap<JMDictSense, String> sensesToPOS = HashMultimap.create();
    private final List<JMDictWord> words;
    private final List<JMDictPron> prons;


    public EntryReadout(JMDictEntry entry, ForwardingToken token) {
        this.entry = entry;
        words = entry.getWords();
        prons = entry.getPron();
        this.token = token;
        this.senses = entry.getSenses();
        senses.forEach(sense -> sensesToDefs.putAll(sense, extractDefs(sense)));
        senses.forEach(sense -> sensesToPOS.putAll(sense, extractPOS(sense)));
        description = initDescription();

    }

    public EntryReadout(Number count, ForwardingToken token) {
        this.entry = null;
        words = null;
        prons = null;
        this.token = token;
        this.senses = null;
        description = String.format("%d more results hidden. Search off-site at:" +
                "http://www.edrdg.org/cgi-bin/wwwjdic/wwwjdic?2MUE%s", count.intValue(), token.getBaseForm());
    }

    private List<String> extractDefs (JMDictSense sense) {
        return sense.getDefs().stream().map(def -> def.getSenseDataKey().getData()).collect(Collectors.toList());
    }

    private List<String> extractPOS (JMDictSense sense) {
        return sense.getTypes().stream().map(def -> def.getSenseDataKey().getData()).collect(Collectors.toList());
    }

    public static final String MEANINGS_START_KEY = "：";
    public static final String PRONS_START_KEY = " [";
    public static final String PRONS_END_KEY = "]";
    public static final String EXTRA_WORD_KEY = " / ";
    public static final String EXTRA_READING_KEY = " / ";

    private String initDescription() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < words.size(); i++){
            sb.append(words.get(i).getIdDataKey().getData());
            if(i+1 < words.size()) sb.append(EXTRA_WORD_KEY);
        }

        if(descHasKanji()) sb.append(PRONS_START_KEY);
        for(int i = 0; i < prons.size(); i++){
            sb.append(prons.get(i).getIdDataKey().getData());
            if(i+1 < prons.size()) sb.append(EXTRA_READING_KEY);
        }
        if(!words.isEmpty()) sb.append(PRONS_END_KEY);

        sb.append(MEANINGS_START_KEY);

        for(int i = 0; i < senses.size(); i++){
            if(senses.size() > 1) sb.append(String.format("(%d) ", i + 1));
            sb.append(
                    Joiner.on(" ･ ")
                    .join(
                            sensesToDefs.get(senses.get(i))
                    )
            );
            if (i < senses.size()-1) sb.append(";  ");
        }
//        sb.append('.');

        return sb.toString();
    }

    public List<String> getPronsAsStrings() {
        return prons.stream().map(pron -> pron.getIdDataKey().getData()).collect(Collectors.toList());
    }

    public boolean descHasKanji(){
        return !words.isEmpty();
    }
    public boolean descHasMultipleProns(){
        return prons.size() > 1;
    }
    public boolean descHasMultipleSenses(){
        return senses.size() > 1;
    }

    public String getDescription() {
        return description;
    }

//    public static boolean descHasKanji(String fullDesc){
//        String kanjiAndMeanings = fullDesc.split(Pattern.quote(MEANINGS_START_KEY), 2)[0]; // 使徒 [しと]
//        if(kanjiAndMeanings.contains(PRONS_START_KEY) && kanjiAndMeanings.contains(PRONS_END_KEY)) return true;
//        else return false;
//    }

}
