/**
 *Copyright (C) 2003 DFKI GmbH. All rights reserved.
 */
package marytts.language.nl;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
 * @author Anna Welker
 *
 *
 */
public class PreprocessTest {

	private static Preprocess module;

	@BeforeSuite
	public static void setUpBeforeClass() {
		module = new Preprocess();
	}

	@DataProvider(name = "DocData")
	private Object[][] numberExpansionDocData() {
		// @formatter:off
		return new Object[][] { { "1", "een" },
								{ "2", "twee" },
								{ "3", "drie" },
								{ "4", "vier" },
								{ "42", "twee\u00AD\u00EBn\u00ADveertig"},
								{ "1e", "eerste"},
								{ "2e", "tweede" },
								{ "3e", "derde" },
								{ "4e", "vierde" } };
		// @formatter:on
	}

	@DataProvider(name = "NumExpandData")
	private Object[][] numberExpansionDocDataCardinal() {
		// @formatter:off
		return new Object[][] { { "1", "een" },
								{ "2", "twee" },
								{ "3", "drie" },
								{ "4", "vier" },
								};
		// @formatter:on
	}

	@DataProvider(name = "OrdinalExpandData")
	private Object[][] numberExpansionDocDataOrdinal() {
		// @formatter:off
		return new Object[][] { { "2", "tweede" },
								{ "3", "derde" },
								{ "4", "vierde" } };
		// @formatter:on
	}

	@Test(dataProvider = "NumExpandData")
	public void testExpandNum(String token, String word) {
		double x = Double.parseDouble(token);
		String actual = module.expandNumber(x);
		Assert.assertEquals(actual, word);
	}

	@Test(dataProvider = "OrdinalExpandData")
	public void testExpandOrdinal(String token, String word) {
		double x = Double.parseDouble(token);
		String actual = module.expandOrdinal(x);
		Assert.assertEquals(actual, word);
	}
}
