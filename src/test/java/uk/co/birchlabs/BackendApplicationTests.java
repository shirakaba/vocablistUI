package uk.co.birchlabs;

import catRecurserPkg.*;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Autowired
	JMDictEntryRepository2 jmDictEntryRepository2;

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
	public void doJoins() {
		Vocablist vocablist = new Vocablist("気候[編集]年間の平均気温は15℃前後で、ここ20年ほどはほぼ横ばいである。 " +
				"最高気温もほぼ横ばいで推移しているが、1日の最高気温が30℃を越える日数、および1日の最低気温が25℃を超える日数は、" +
				"1990年以降、増加傾向にある。",
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
//		Iterable<Integer> ids = Lists.newArrayList(jmDictWordRepository2.getIds(tokensToSearch));
//		List<JMDictEntry> idWordPairs = Lists.newArrayList(jmDictEntryRepository.findAll(ids));
        // All entries with a baseForm() found in jmdict_word.
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
		System.out.println("You're too slow!");
	}

}
