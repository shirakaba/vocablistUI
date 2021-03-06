package uk.co.birchlabs;

import catRecurserPkg.ForwardingToken;
import uk.co.birchlabs.JMDictPronRepo2.Mode;
import uk.co.birchlabs.JMDictPronRepo2.POS;

import java.util.HashSet;
import java.util.HashSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jamiebirch on 03/07/2016.
 */
public class TokensByMecabPOS {

    private final HashSet<ForwardingToken>
            particles = new HashSet<>(),
            verbsAndAux = new HashSet<>(), // includes auxiliaries because we conglomerate いる into one row.
            adverbs = new HashSet<>(),
            conjunctions = new HashSet<>(),
            nouns = new HashSet<>(),
            properNouns = new HashSet<>(),
            prefixes = new HashSet<>(),
            adjectives = new HashSet<>(),
            adnominals = new HashSet<>(),
            exclamations = new HashSet<>(),
            symbols = new HashSet<>(),
            fillers = new HashSet<>(),
            others = new HashSet<>(),
            unclassified = new HashSet<>();

    public TokensByMecabPOS(Set<ForwardingToken> tokensToSearch) {
        tokensToSearch.forEach(t -> {
            POS pos = determinePOS(t);

            switch(pos){
                case particles:
                    particles.add(t);
                    break;
                case verbsAndAux:
                    verbsAndAux.add(t);
                    break;
                case adverbs:
                    adverbs.add(t);
                    break;
                case conjunctions:
                    conjunctions.add(t);
                    break;
                case nouns:
                    nouns.add(t);
                    break;
                case properNouns:
                    properNouns.add(t);
                    break;
                case prefixes:
                    prefixes.add(t);
                    break;
                case adjectives:
                    adjectives.add(t);
                    break;
                case adnominals:
                    adnominals.add(t);
                    break;
                case exclamations:
                    exclamations.add(t);
                    break;
                case symbols:
                    symbols.add(t);
                    break;
                case fillers:
                    fillers.add(t);
                    break;
                case others:
                    others.add(t);
                    break;
                case unclassified:
                    unclassified.add(t);
                    break;
                default:
                    unclassified.add(t);
                    break;
            }
        });
    }

    public static POS determinePOS(ForwardingToken token){
        String feature1 = token.getAllFeaturesArray()[0];
        if (feature1.startsWith("助詞")) return POS.particles;
        else if (token.isVerb()) return POS.verbsAndAux;
        else if (feature1.startsWith("副詞")) return POS.adverbs;
        else if (feature1.startsWith("接続詞")) return POS.conjunctions;
        else if (feature1.startsWith("名詞")) {
            if(token.getAllFeaturesArray()[1].startsWith("固有名詞")) return POS.properNouns;
            else return POS.nouns;
        }
        else if (feature1.startsWith("接頭詞")) return POS.prefixes;
        else if (feature1.startsWith("形容詞")) return POS.adjectives;
        else if (feature1.startsWith("連体詞")) return POS.adnominals;
        else if (feature1.startsWith("感動詞")) return POS.exclamations;
        else if (feature1.startsWith("フィラー")) return POS.fillers;
        else if (feature1.startsWith("その他")) return POS.others;
        else if (feature1.startsWith("記号")) return POS.symbols;
        else return POS.unclassified;
    }

    public HashSet<ForwardingToken> getParticles() {
        return particles;
    }

    public HashSet<ForwardingToken> getVerbsAndAux() {
        return verbsAndAux;
    }

    public HashSet<ForwardingToken> getAdverbs() {
        return adverbs;
    }

    public HashSet<ForwardingToken> getConjunctions() {
        return conjunctions;
    }

    public HashSet<ForwardingToken> getNouns() {
        return nouns;
    }

    public HashSet<ForwardingToken> getProperNouns() {
        return properNouns;
    }

    public HashSet<ForwardingToken> getPrefixes() {
        return prefixes;
    }

    public HashSet<ForwardingToken> getAdjectives() {
        return adjectives;
    }

    public HashSet<ForwardingToken> getAdnominals() {
        return adnominals;
    }

    public HashSet<ForwardingToken> getExclamations() {
        return exclamations;
    }

    public HashSet<ForwardingToken> getSymbols() {
        return symbols;
    }

    public HashSet<ForwardingToken> getFillers() {
        return fillers;
    }

    public HashSet<ForwardingToken> getOthers() {
        return others;
    }

    public HashSet<ForwardingToken> getUnclassified() {
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
                t.getProperNouns().forEach(token -> { if(p.getProperNounsFound().contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
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
                t.getProperNouns().forEach(token -> { if(p.getProperNounsFound().contains(token.getReading())) tokensToSearch.remove(token); });
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
