package common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlDocumentTransformer {

	private static TransformerFactory transformerFactory;
	private static DocumentBuilderFactory dbf;

	private static void init () {
		transformerFactory = TransformerFactory.newInstance();
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setIgnoringComments(false);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setNamespaceAware(true);
	}

	public static String toXMLString(Document document) {
		DOMSource domSource = new DOMSource(document);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		try {
			Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
				transformer.transform(domSource, result);
			} catch (NullPointerException nllptrexc) {
				if (transformerFactory == null) {
					init();
					transformer = transformerFactory.newTransformer();
					transformer.transform(domSource, result);
				} else {
					nllptrexc.printStackTrace();
				}
			}
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * Reads an XML based InputStream, and returns a Document object
	 * @param inputStream
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document xmlInputStreamToDocument (InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbf.newDocumentBuilder();
		} catch (NullPointerException nllptr) {
			init();
			dBuilder = dbf.newDocumentBuilder();
		}
		Document doc = dBuilder.parse(inputStream);
		// InputStream needs to be closed each time to reset the cursor at start
		// so we avoid the following [Fatal Error] :1:1: Premature end of file.
		inputStream.close();
		return doc;
	}

}