package uk.co.birchlabs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Autowired
	private MyCoolService myCoolService;

	public BackendApplicationTests() {
	}

	@Test
	public void contextLoads() {
		assertEquals("oh shit whaddup", myCoolService.doCoolThing());
	}

}
