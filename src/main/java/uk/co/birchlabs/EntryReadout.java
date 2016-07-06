package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import java.util.List;
import java.util.Set;
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

    public EntryReadout(JMDictEntry entry, ForwardingToken token) {
        this.entry = entry;
        this.token = token;
        this.senses = entry.getSenses();
        senses.forEach(sense -> sensesToDefs.putAll(sense, extractDefs(sense)));
        senses.forEach(sense -> sensesToPOS.putAll(sense, extractPOS(sense)));
        description = initDescription();
    }

    private List<String> extractDefs (JMDictSense sense) {
        return sense.getDefs().stream().map(def -> def.getSenseDataKey().getData()).collect(Collectors.toList());
    }

    private List<String> extractPOS (JMDictSense sense) {
        return sense.getTypes().stream().map(def -> def.getSenseDataKey().getData()).collect(Collectors.toList());
    }

    private String initDescription() {
        StringBuilder sb = new StringBuilder();
//        sb.append(token.getBaseForm());
        List<JMDictWord> words = entry.getWords();
        List<JMDictPron> prons = entry.getPron();

        for(int i = 0; i < words.size(); i++){
            sb.append(words.get(i).getIdDataKey().getData());
            if(i+1 < words.size()) sb.append(" / ");
        }

        if(!words.isEmpty()) sb.append(" [");
        for(int i = 0; i < prons.size(); i++){
            sb.append(prons.get(i).getIdDataKey().getData());
            if(i+1 < prons.size()) sb.append(" / ");
        }
        if(!words.isEmpty()) sb.append(']');

        sb.append(' ');

//        // For verbs, just show baseform. For non-verbs, show reading provided it differs from baseform.
//        if(!token.isVerb())
//            if(!token.getBaseForm().equals(Utils.convertKana(token.getReading())))
//                sb.append(String.format(" [%s]", Utils.convertKana(token.getReading())));

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

    public String getDescription() {
        return description;
    }
}
