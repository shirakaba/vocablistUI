package uk.co.birchlabs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Autowired
	JMDictPronService jmDictPronService;

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

    @Autowired
    EntriesByMecabPOSService entriesByMecabPOSService;

	public BackendApplicationTests() {
	}

//	@Test
//	public void contextLoads() {
//		assertEquals("oh shit whaddup", myCoolService.doCoolThing());
//	}



	@Test
	public void speedTest() throws IOException {
//		// 22s for 15 articles; 37s for 100 articles; 34s for 1000 articles (there probably aren't that many)
//		jmDictPronService.generator(
//				false, // makeQuiz
//				1, // maxArticles
//				"mandatory", // filtering
//				0, // egs
//				new Float(0.0), // minYield
//				30,  // partition
//				new Float(100.0), // percentLimit
//				"東方Project" // input
//		);

		// 22s for 1000 articles
		// 10s for 4 articles, unlimited partition
		jmDictPronService.generator(
				false, // makeQuiz
				1, // maxArticles
				"n3", // filtering
				0, // egs
				new Float(0.0), // minYield
				30,  // partition
				new Float(100.0), // percentLimit
				"東方Project" // input
		);
	}

}
