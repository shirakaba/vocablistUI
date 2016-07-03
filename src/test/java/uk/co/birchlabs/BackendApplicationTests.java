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


        EntriesByMecabPOS entriesByMecabPOSHiragana = new EntriesByMecabPOS(tokensByMecabPOS, Mode.READINGS_IN_HIRAGANA);

		// List of all JMDictEntrys with a valid hiragana reading
        PronsFoundByMecabPOS pronsFoundByMecabPOSHiragana = new PronsFoundByMecabPOS(entriesByMecabPOSHiragana);

        // tokensToSearch == 220
        TokensByMecabPOS.updateTokensRemainingToBeSearched(tokensByMecabPOS, pronsFoundByMecabPOSHiragana, tokensToSearch, Mode.READINGS_IN_HIRAGANA);
        // tokensToSearch == 109

        EntriesByMecabPOS entriesByMecabPOSKatakana = new EntriesByMecabPOS(tokensByMecabPOS, Mode.READINGS_IN_KATAKANA);

        PronsFoundByMecabPOS pronsFoundByMecabPOSKatakana = new PronsFoundByMecabPOS(entriesByMecabPOSKatakana);

        TokensByMecabPOS.updateTokensRemainingToBeSearched(tokensByMecabPOS, pronsFoundByMecabPOSKatakana, tokensToSearch, Mode.READINGS_IN_KATAKANA);
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
                                        entriesByMecabPOSHiragana,
                                        entriesByMecabPOSKatakana
                                )
                )
                .collect(Collectors.toList());

		System.out.println("You're too slow!");
	}

}
