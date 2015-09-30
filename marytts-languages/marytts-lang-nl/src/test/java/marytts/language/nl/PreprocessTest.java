/**
 *Copyright (C) 2003 DFKI GmbH. All rights reserved.
 */
package marytts.language.nl;

import marytts.language.nl.Preprocess;
import marytts.util.dom.DomUtils;

import org.custommonkey.xmlunit.*;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.Reporter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



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

	@Test(dataProvider = "DocData")
	public void testSpellout(String tokenised, String expected) throws Exception, ParserConfigurationException, SAXException,
			IOException {
		Document tokenisedDoc;
		Document expectedDoc;
		String tokens = "<maryxml xmlns=\"http://mary.dfki.de/2002/MaryXML\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"0.5\" xml:lang=\"nl\"><p><s><t>"
				+ tokenised + "</t></s></p></maryxml>";
		tokenisedDoc = DomUtils.parseDocument(tokens);
		String words = "<maryxml xmlns=\"http://mary.dfki.de/2002/MaryXML\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"0.5\" xml:lang=\"nl\"><p><s><mtu orig=\""
				+ tokenised + "\"><t>" + expected + "</t></mtu></s></p></maryxml>";
		
		Reporter.log(tokens);
		Reporter.log(words);
		
		expectedDoc = DomUtils.parseDocument(words);
		module.checkForNumbers(tokenisedDoc);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(tokenisedDoc), new StreamResult(writer));
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		Reporter.log(output);
		Diff diff = XMLUnit.compareXML(expectedDoc, tokenisedDoc);
		Assert.assertTrue(diff.identical());
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
