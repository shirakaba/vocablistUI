package uk.co.birchlabs;

import catRecurserPkg.*;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.birchlabs.JMDictEntryRepo2.CollectionMode;
import uk.co.birchlabs.JMDictPronRepo2.Mode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static uk.co.birchlabs.JMDictPronRepo2.POS;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Autowired
	JMDictEntryRepo2 jmDictEntryRepo2;

    @Autowired
	JMDictPronRepo2 jmDictPronRepo2;

//	@Autowired
//	JMDictWordRepository2 jmDictWordRepository2;

	@Autowired
	JMDictEntryRepo jmDictEntryRepo;

//	@Autowired
//	private MyCoolService myCoolService;

	public BackendApplicationTests() {
	}

//	@Test
//	public void contextLoads() {
//		assertEquals("oh shit whaddup", myCoolService.doCoolThing());
//	}

	@Test
	public void doJoins() throws IOException {
		String nerima = new String(Files.readAllBytes(Paths.get("src/test/java/uk/co/birchlabs/nerima.txt")));
		Vocablist vocablist = new Vocablist(
//				"気候[編集]年間の平均気温は15℃前後で、ここ20年ほどはほぼ横ばいである。 " +
//				"最高気温もほぼ横ばいで推移しているが、1日の最高気温が30℃を越える日数、および1日の最低気温が25℃を超える日数は、" +
//				"1990年以降、増加傾向にある。"
				nerima
				,
				Vocablist.Filtering.MANDATORY);
		List<VocabListRow> sortedByFreq = vocablist.getSortedByFreq();

		List<VocabListRowCumulative> cumulative = new ArrayList<>();

		final int s = vocablist.getTokenCount().size();
		float runningPercent = 0;
		for (int i = 0; i < sortedByFreq.size(); i++) {
			VocabListRow vocabListRow = sortedByFreq.get(i);
			float myPercent = (float)vocabListRow.getCount() / (float)s;
			runningPercent += myPercent;
			boolean fundamental = Vocablist.filterOut(vocabListRow.getToken(), Vocablist.Filtering.FUNDAMENTAL);
			boolean n5 = Filter.N5_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
			boolean n4 = Filter.N4_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
			boolean n3 = Filter.N3_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
			boolean n2 = Filter.N2_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
			boolean n1 = Filter2.N1_BLACKLIST.contains(vocabListRow.getToken().getBaseForm());
			cumulative.add(new VocabListRowCumulative(vocabListRow,
					myPercent,
					runningPercent,
					fundamental,
					n5,
					n4,
					n3,
					n2,
					n1
			));
		}

		Set<ForwardingToken> tokensToSearch = new HashSet<>();
		sortedByFreq.forEach(vocablistRow -> tokensToSearch.add(vocablistRow.getToken()));
		List<JMDictEntry> wordEntries = Lists.newArrayList(jmDictEntryRepo2.getEntries(tokensToSearch));
        List<String> baseFormsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(wordEntries, CollectionMode.word);

        tokensToSearch.removeIf(token -> baseFormsFound.contains(token.getBaseForm()));

        TokensByMecabPOS tokensByMecabPOS = new TokensByMecabPOS(tokensToSearch);
//        List<ForwardingToken>
//                particles = new ArrayList<>(),
//                verbsAndAux = new ArrayList<>(), // includes auxiliaries because we conglomerate いる into one row.
//                adverbs = new ArrayList<>(),
//                conjunctions = new ArrayList<>(),
//                nouns = new ArrayList<>(),
//                prefixes = new ArrayList<>(),
//                adjectives = new ArrayList<>(),
//                adnominals = new ArrayList<>(),
//                exclamations = new ArrayList<>(),
//                symbols = new ArrayList<>(),
//                fillers = new ArrayList<>(),
//                others = new ArrayList<>(),
//                unclassified = new ArrayList<>();
//
//		tokensToSearch.forEach(t -> {
//            String firstFeature = t.getAllFeaturesArray()[0];
//            if (firstFeature.startsWith("助詞")) particles.add(t);
//            else if (t.isVerb()) verbsAndAux.add(t);
//            else if (firstFeature.startsWith("副詞")) adverbs.add(t);
//            else if (firstFeature.startsWith("接続詞")) conjunctions.add(t);
//            else if (firstFeature.startsWith("名詞")) nouns.add(t);
//            else if (firstFeature.startsWith("接頭詞")) prefixes.add(t);
//            else if (firstFeature.startsWith("形容詞")) adjectives.add(t);
//            else if (firstFeature.startsWith("連体詞")) adnominals.add(t);
//            else if (firstFeature.startsWith("感動詞")) exclamations.add(t);
//            else if (firstFeature.startsWith("フィラー")) fillers.add(t);
//            else if (firstFeature.startsWith("その他")) others.add(t);
//            else if (firstFeature.startsWith("記号")) symbols.add(t);
//            else unclassified.add(t);
//        });


        EntriesByMecabPOS entriesByMecabPOSHiragana = new EntriesByMecabPOS(tokensByMecabPOS);
		// List of all JMDictEntrys with a valid hiragana reading
//        List<JMDictEntry>
//                particlesByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(particles, POS.particles)),
//				// size = 48 for 26 verbsAndAux (mainly due to suru/aru/iru). Costs 5 seconds.
//                verbsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(verbsAndAux, POS.verbsAndAux)),
//                adverbsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(adverbs, POS.adverbs)),
//                conjunctionsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(conjunctions, POS.conjunctions)),
//				// size = 136 for 151 nouns (due to some being katakana, some being pronouns.
//                nounsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(nouns, POS.nouns)),
//                prefixesByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(prefixes, POS.prefixes)),
//                adjectivesByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(adjectives, POS.adjectives)),
//                adnominalsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(adnominals, POS.adnominals)),
//                exclamationsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(exclamations, POS.exclamations)),
//                symbolsByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(symbols, POS.symbols)),
//                fillersByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(fillers, POS.fillers)),
//                othersByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(others, POS.others)),
//                unclassifiedByPron = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(unclassified, POS.unclassified))
//        ;

        List<String>
                particlesFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(particlesByPron, CollectionMode.pron),
                verbsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(verbsByPron, CollectionMode.pron),
                adverbsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(adverbsByPron, CollectionMode.pron),
                conjunctionsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(conjunctionsByPron, CollectionMode.pron),
                nounsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(nounsByPron, CollectionMode.pron),
                prefixesFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(prefixesByPron, CollectionMode.pron),
                adjectivesFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(adjectivesByPron, CollectionMode.pron),
                adnominalsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(adnominalsByPron, CollectionMode.pron),
                exclamationsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(exclamationsByPron, CollectionMode.pron),
                symbolsFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(symbolsByPron, CollectionMode.pron),
                fillersFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(fillersByPron, CollectionMode.pron),
                othersFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(othersByPron, CollectionMode.pron),
                unclassifiedFound = JMDictEntryRepo2.collectWordsOrPronOfEntries(unclassifiedByPron, CollectionMode.pron)
        ;
        // tokensToSearch == 220
        particles.forEach(token -> { if(particlesFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        verbsAndAux.forEach(token -> { if(verbsFound.contains(token.getBaseForm())) tokensToSearch.remove(token); });
        adverbs.forEach(token -> { if(adverbsFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        conjunctions.forEach(token -> { if(conjunctionsFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        nouns.forEach(token -> { if(nounsFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        prefixes.forEach(token -> { if(prefixesFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        adjectives.forEach(token -> { if(adjectivesFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        adnominals.forEach(token -> { if(adnominalsFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        exclamations.forEach(token -> { if(exclamationsFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        symbols.forEach(token -> { if(symbolsFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        fillers.forEach(token -> { if(fillersFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        others.forEach(token -> { if(othersFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        unclassified.forEach(token -> { if(unclassifiedFound.contains(Utils.convertKana(token.getReading()))) tokensToSearch.remove(token); });
        // tokensToSearch == 109

        List<JMDictEntry> // needs to be new lists so we can stream through them, treating katakana
        particlesByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(particles, Mode.READINGS_IN_KATAKANA, POS.particles)),
        verbsByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(verbsAndAux, Mode.READINGS_IN_KATAKANA, POS.verbsAndAux)),
        adverbsByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(adverbs, Mode.READINGS_IN_KATAKANA, POS.adverbs)),
        conjunctionsByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(conjunctions, Mode.READINGS_IN_KATAKANA, POS.conjunctions)),
        nounsByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(nouns, Mode.READINGS_IN_KATAKANA, POS.nouns)), // size: 57
        prefixesByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(prefixes, Mode.READINGS_IN_KATAKANA, POS.prefixes)),
        adjectivesByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(adjectives, Mode.READINGS_IN_KATAKANA, POS.adjectives)),
        adnominalsByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(adnominals, Mode.READINGS_IN_KATAKANA, POS.adnominals)),
        exclamationsByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(exclamations, Mode.READINGS_IN_KATAKANA, POS.exclamations)),
        symbolsByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(symbols, Mode.READINGS_IN_KATAKANA, POS.symbols)),
        fillersByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(fillers, Mode.READINGS_IN_KATAKANA, POS.fillers)),
        othersByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(others, Mode.READINGS_IN_KATAKANA, POS.others)),
        unclassifiedByPron2 = Lists.newArrayList(jmDictPronRepo2.getEntriesFromPron(unclassified, Mode.READINGS_IN_KATAKANA, POS.unclassified));

        // Error:(169, 37) java: local variables referenced from a lambda expression must be final or effectively final, so rebuilding nounsFound
        List<String>
//        particlesFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(particlesByPron, CollectionMode.pron);
//        verbsFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(verbsByPron, CollectionMode.pron);
//        adverbsFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(adverbsByPron, CollectionMode.pron);
//        conjunctionsFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(conjunctionsByPron, CollectionMode.pron);
        nounsFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(nounsByPron, CollectionMode.pron)
//        prefixesFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(prefixesByPron, CollectionMode.pron);
//        adjectivesFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(adjectivesByPron, CollectionMode.pron);
//        adnominalsFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(adnominalsByPron, CollectionMode.pron);
//        exclamationsFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(exclamationsByPron, CollectionMode.pron);
//        symbolsFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(symbolsByPron, CollectionMode.pron);
//        fillersFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(fillersByPron, CollectionMode.pron);
//        othersFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(othersByPron, CollectionMode.pron);
//        unclassifiedFound2 = JMDictEntryRepo2.collectWordsOrPronOfEntries(unclassifiedByPron, CollectionMode.pron);
        ;


        // TODO: figure out whether verb's token.getReading() needs convertKana (probably does), or at least remove it altogether.
//        particles.forEach(token -> { if(particlesFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        verbsAndAux.forEach(token -> { if(verbsFound2.contains(token.getBaseForm())) tokensToSearch.remove(token); });
//        adverbs.forEach(token -> { if(adverbsFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        conjunctions.forEach(token -> { if(conjunctionsFound2.contains(token.getReading())) tokensToSearch.remove(token); });
        nouns.forEach(token -> { if(nounsFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        prefixes.forEach(token -> { if(prefixesFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        adjectives.forEach(token -> { if(adjectivesFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        adnominals.forEach(token -> { if(adnominalsFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        exclamations.forEach(token -> { if(exclamationsFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        symbols.forEach(token -> { if(symbolsFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        fillers.forEach(token -> { if(fillersFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        others.forEach(token -> { if(othersFound2.contains(token.getReading())) tokensToSearch.remove(token); });
//        unclassified.forEach(token -> { if(unclassifiedFound2.contains(token.getReading())) tokensToSearch.remove(token); });
        // tokensToSearch == 65. All seem to be proper nouns, or conjugative particles like -ta.

//        cumulative.forEach(row -> {
//            ForwardingToken token = row.getVocabListRow().getToken();
//            String feature1 = token.getAllFeaturesArray()[0];
//
//            if (feature1.startsWith("助詞")) particles.add(t);
//            else if (token.isVerb()) verbsAndAux.add(t);
//            else if (feature1.startsWith("副詞")) adverbs.add(t);
//            else if (feature1.startsWith("接続詞")) conjunctions.add(t);
//            else if (feature1.startsWith("名詞")) nouns.add(t);
//            else if (feature1.startsWith("接頭詞")) prefixes.add(t);
//            else if (feature1.startsWith("形容詞")) adjectives.add(t);
//            else if (feature1.startsWith("連体詞")) adnominals.add(t);
//            else if (feature1.startsWith("感動詞")) exclamations.add(t);
//            else if (feature1.startsWith("フィラー")) fillers.add(t);
//            else if (feature1.startsWith("その他")) others.add(t);
//            else if (feature1.startsWith("記号")) symbols.add(t);
//            else unclassified.add(t);
//        });

        List<VocabListRowCumulativeMapped> list = cumulative
                .stream()
                .map(
                        row ->
                                new VocabListRowCumulativeMapped(
                                        row,
                                        particlesByPron,
                                        verbsByPron,
                                        adverbsByPron,
                                        conjunctionsByPron,
                                        nounsByPron,
                                        prefixesByPron,
                                        adjectivesByPron,
                                        adnominalsByPron,
                                        exclamationsByPron,
                                        symbolsByPron,
                                        fillersByPron,
                                        othersByPron,
                                        unclassifiedByPron,
                                        particlesByPron2,
                                        verbsByPron2,
                                        adverbsByPron2,
                                        conjunctionsByPron2,
                                        nounsByPron2,
                                        prefixesByPron2,
                                        adjectivesByPron2,
                                        adnominalsByPron2,
                                        exclamationsByPron2,
                                        symbolsByPron2,
                                        fillersByPron2,
                                        othersByPron2,
                                        unclassifiedByPron2
                                )
                )
                .collect(Collectors.toList());

		System.out.println("You're too slow!");
	}

}
