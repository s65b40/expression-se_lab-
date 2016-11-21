package lab1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Testjunitmtl {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMiddleTolast() {
		compute t = new compute();
		StringBuilder res= t.MiddleTolast("(x+2)*9*y-96/4*(6*x*y*6)+3x");
		System.out.println(res);
		String tp = res.toString();
		assertEquals("#x##2#+#9##*##y##*##96##4##/##6##x##*##y##*##6#*#*##-##3##x##*##+#", tp);
	}
	//StringBuilder res= t.MiddleTolast("(x+2)*9*y-96/4*(6*x*y*6)+3x");
	//StringBuilder cont = new StringBuilder("#2##x##*##9##+#");
	//assertEquals(res,cont);
	//fail("Not yet implemented");

}
