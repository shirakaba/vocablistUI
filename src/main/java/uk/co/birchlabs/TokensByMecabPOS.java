package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jamiebirch on 03/07/2016.
 */
public class TokensByMecabPOS {

    private final List<ForwardingToken>
            particles = new ArrayList<>(),
            verbsAndAux = new ArrayList<>(), // includes auxiliaries because we conglomerate いる into one row.
            adverbs = new ArrayList<>(),
            conjunctions = new ArrayList<>(),
            nouns = new ArrayList<>(),
            prefixes = new ArrayList<>(),
            adjectives = new ArrayList<>(),
            adnominals = new ArrayList<>(),
            exclamations = new ArrayList<>(),
            symbols = new ArrayList<>(),
            fillers = new ArrayList<>(),
            others = new ArrayList<>(),
            unclassified = new ArrayList<>();

    public TokensByMecabPOS(Set<ForwardingToken> tokensToSearch) {
        tokensToSearch.forEach(t -> {
            String firstFeature = t.getAllFeaturesArray()[0];
            if (firstFeature.startsWith("助詞")) particles.add(t);
            else if (t.isVerb()) verbsAndAux.add(t);
            else if (firstFeature.startsWith("副詞")) adverbs.add(t);
            else if (firstFeature.startsWith("接続詞")) conjunctions.add(t);
            else if (firstFeature.startsWith("名詞")) nouns.add(t);
            else if (firstFeature.startsWith("接頭詞")) prefixes.add(t);
            else if (firstFeature.startsWith("形容詞")) adjectives.add(t);
            else if (firstFeature.startsWith("連体詞")) adnominals.add(t);
            else if (firstFeature.startsWith("感動詞")) exclamations.add(t);
            else if (firstFeature.startsWith("フィラー")) fillers.add(t);
            else if (firstFeature.startsWith("その他")) others.add(t);
            else if (firstFeature.startsWith("記号")) symbols.add(t);
            else unclassified.add(t);
        });
    }

    public List<ForwardingToken> getParticles() {
        return particles;
    }

    public List<ForwardingToken> getVerbsAndAux() {
        return verbsAndAux;
    }

    public List<ForwardingToken> getAdverbs() {
        return adverbs;
    }

    public List<ForwardingToken> getConjunctions() {
        return conjunctions;
    }

    public List<ForwardingToken> getNouns() {
        return nouns;
    }

    public List<ForwardingToken> getPrefixes() {
        return prefixes;
    }

    public List<ForwardingToken> getAdjectives() {
        return adjectives;
    }

    public List<ForwardingToken> getAdnominals() {
        return adnominals;
    }

    public List<ForwardingToken> getExclamations() {
        return exclamations;
    }

    public List<ForwardingToken> getSymbols() {
        return symbols;
    }

    public List<ForwardingToken> getFillers() {
        return fillers;
    }

    public List<ForwardingToken> getOthers() {
        return others;
    }

    public List<ForwardingToken> getUnclassified() {
        return unclassified;
    }
}
