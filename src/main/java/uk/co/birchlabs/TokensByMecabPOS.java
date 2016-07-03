package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import uk.co.birchlabs.JMDictPronRepo2.Mode;

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

    // Currently assumes pronsFoundByMecabPOS to be hiragana.
    static void updateTokensRemainingToBeSearched(TokensByMecabPOS t, PronsFoundByMecabPOS p, Set<ForwardingToken> tokensToSearch, Mode mode){
        switch(mode){
            case READINGS_IN_HIRAGANA:
                t.getParticles().forEach(token -> { if(p.getParticlesFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getVerbsAndAux().forEach(token -> { if(p.getVerbsFound().contains(token.getBaseForm())) tokensToSearch.remove(token); });
                t.getAdverbs().forEach(token -> { if(p.getAdverbsFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getConjunctions().forEach(token -> { if(p.getConjunctionsFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getNouns().forEach(token -> { if(p.getNounsFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getPrefixes().forEach(token -> { if(p.getPrefixesFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getAdjectives().forEach(token -> { if(p.getAdjectivesFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getAdnominals().forEach(token -> { if(p.getAdnominalsFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getExclamations().forEach(token -> { if(p.getExclamationsFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getSymbols().forEach(token -> { if(p.getSymbolsFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getFillers().forEach(token -> { if(p.getFillersFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getOthers().forEach(token -> { if(p.getOthersFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                t.getUnclassified().forEach(token -> { if(p.getUnclassifiedFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
                break;
            case READINGS_IN_KATAKANA:
                t.getParticles().forEach(token -> { if(p.getParticlesFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getVerbsAndAux().forEach(token -> { if(p.getVerbsFound().contains(Utils.convertKana(token.getBaseForm()))) tokensToSearch.remove(token); });
                t.getAdverbs().forEach(token -> { if(p.getAdverbsFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getConjunctions().forEach(token -> { if(p.getConjunctionsFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getNouns().forEach(token -> { if(p.getNounsFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getPrefixes().forEach(token -> { if(p.getPrefixesFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getAdjectives().forEach(token -> { if(p.getAdjectivesFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getAdnominals().forEach(token -> { if(p.getAdnominalsFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getExclamations().forEach(token -> { if(p.getExclamationsFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getSymbols().forEach(token -> { if(p.getSymbolsFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getFillers().forEach(token -> { if(p.getFillersFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getOthers().forEach(token -> { if(p.getOthersFound().contains(token.getReading())) tokensToSearch.remove(token); });
                t.getUnclassified().forEach(token -> { if(p.getUnclassifiedFound().contains(token.getReading())) tokensToSearch.remove(token); });
                break;
            default:
                throw new IllegalStateException();
        }
    }
}
