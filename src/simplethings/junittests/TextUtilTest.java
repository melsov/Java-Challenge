/**
 * 
 */
package simplethings.junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import SingleThings.TextUtil;

/**
 * @author didyouloseyourdog
 *
 */
public class TextUtilTest 
{
	
	@Test
	public void eraseTest() {
		TextUtil tu = new TextUtil("HarrysGame.txt");
		tu.erase();
		assertEquals("Number of lines should be zero after erase.", 0, tu.getLength());
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
