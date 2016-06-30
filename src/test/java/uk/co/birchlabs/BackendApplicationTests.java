package uk.co.birchlabs;

import catRecurserPkg.*;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static uk.co.birchlabs.JMDictPronunciationRepository2.Mode.READINGS_IN_HIRAGANA;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Autowired
	JMDictEntryRepository2 jmDictEntryRepository2;

    @Autowired
    JMDictPronunciationRepository2 jmDictPronunciationRepository2;

//	@Autowired
//	JMDictWordRepository2 jmDictWordRepository2;

	@Autowired
	JMDictEntryRepository jmDictEntryRepository;

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
		List<JMDictEntry> wordEntries = Lists.newArrayList(jmDictEntryRepository2.getEntries(tokensToSearch));
        List<String> baseFormsFound = wordEntries
                .stream()
                .flatMap(
                        entry -> entry
                                .getWords()
                                .stream()
                                .map(
                                        word -> word
                                                .getIdDataKey()
                                                .getData()
                                )
                )
                .collect(Collectors.toList()
                );
        tokensToSearch.removeIf(token -> baseFormsFound.contains(token.getBaseForm()));


        List<ForwardingToken>
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

        List<JMDictEntry>
                particlesByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(particles,
                JMDictPronunciationRepository2.POS.particles)),

                verbsByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(verbsAndAux,
                JMDictPronunciationRepository2.POS.verbsAndAux)), // size = 48 for 26 verbsAndAux (mainly due to suru/aru/iru)

                adverbsByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(adverbs,
                JMDictPronunciationRepository2.POS.adverbs)),

                conjunctionsByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(conjunctions,
                JMDictPronunciationRepository2.POS.conjunctions)),

                nounsByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(nouns,
                JMDictPronunciationRepository2.POS.nouns)), // size = 136 for 151 nouns (due to some being katakana, some being pronouns.

                prefixesByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(prefixes,
                JMDictPronunciationRepository2.POS.prefixes)),

                adjectivesByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(adjectives,
                JMDictPronunciationRepository2.POS.adjectives)),

                adnominalsByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(adnominals,
                JMDictPronunciationRepository2.POS.adnominals)),

                exclamationsByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(exclamations,
                JMDictPronunciationRepository2.POS.exclamations)),

                symbolsByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(symbols,
                JMDictPronunciationRepository2.POS.symbols)),

                fillersByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(fillers,
                JMDictPronunciationRepository2.POS.fillers)),

                othersByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(others,
                JMDictPronunciationRepository2.POS.others)),

                unclassifiedByPron = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(unclassified,
                JMDictPronunciationRepository2.POS.unclassified))
        ;



//        List<JMDictEntry> particlesByPronNoCond = Lists.newArrayList(jmDictPronunciationRepository2.getEntriesFromPronunciation(particles,
//                JMDictPronunciationRepository2.POS.others)); // size = 109 for 29 particles

        List<String> particlesByPronAsStrings = particlesByPron // crude debugging; only returns rows if they have a kanji form.
                .stream()
                .flatMap(
                        entry -> entry
                                .getWords()
                                .stream()
                                .map(
                                        word -> word
                                                .getIdDataKey()
                                                .getData()
                                )
                )
                .collect(Collectors.toList()
                );

//        List<JMDictEntry> pronunEntries = Lists.newArrayList(jmDictEntryRepository2.getEntries(tokensToSearch, READINGS_IN_HIRAGANA));
//        pronunEntries.forEach(reading -> wordsFound.add(Utils.convertKana(reading.getIdDataKey().getData())));
//        tokensToSearch.removeIf(token -> wordsFound.contains(token.getReading()));
		System.out.println("You're too slow!");
	}

}
