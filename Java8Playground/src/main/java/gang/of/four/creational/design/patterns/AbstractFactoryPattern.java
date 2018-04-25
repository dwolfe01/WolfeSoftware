package gang.of.four.creational.design.patterns;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AbstractFactoryPattern {

	abstract class DocumentQuerierAbstractFactory {
		abstract Document getDocument(String input);
	}

	class DocumentQuerierXMLFactory extends DocumentQuerierAbstractFactory {

		@Override
		Document getDocument(String input) {
			return new XMLDocument(input);
		}

	}

	class DocumentQuerierMapFactory extends DocumentQuerierAbstractFactory {
		
		@Override
		Document getDocument(String input) {
			return new MapDocument(input);
		}
		
	}

	public static void main(String... args) {
		AbstractFactoryPattern afp = new AbstractFactoryPattern();
		String testInput = "Miranda Kerr=20th April;Jason Momoa=1st August;Vera Farmiga=6th August";
		DocumentQuerierAbstractFactory dqaf = afp.new DocumentQuerierXMLFactory();
		queryDocument(testInput, dqaf);
		dqaf = afp.new DocumentQuerierMapFactory();
		queryDocument(testInput, dqaf);
	}
	
	interface Document {
		public String getValueFromKey(String key);
	}

	private static void queryDocument(String testInput, DocumentQuerierAbstractFactory dqaf) {
		Document document = dqaf.getDocument(testInput);
		System.out.println(document.toString());
		System.out.println(document.getValueFromKey("Jason Momoa"));
	}
	
	class MapDocument implements Document {
		
		Map<String,String> map = new HashMap<>();

		public MapDocument(String source) {
			String[] keyValuePairs = source.split(";");
			for (String keyValuePair : keyValuePairs) {
				String[] keyAndValue = keyValuePair.split("=");
				map.put(keyAndValue[0], keyAndValue[1]);
			}
		}

		@Override
		public String getValueFromKey(String key) {
			return this.map.get(key);
		}
		
		public String toString() {
			return map.toString();
		}
		
	}
	
	class XMLDocument implements Document {
		org.w3c.dom.Document doc;
		
		public XMLDocument(String source)  {
			String[] keyValuePairs = source.split(";");
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			Node root = doc.appendChild(doc.createElement("map"));
			for (String keyValuePair : keyValuePairs) {
				String[] keyAndValue = keyValuePair.split("=");
				Element key = getElement("key", keyAndValue[0]);
				Element value = getElement("value", keyAndValue[1]);
				Element pairs = doc.createElement("pair");
				pairs.appendChild(key);
				pairs.appendChild(value);
				root.appendChild(pairs);
			}
		}

		private Element getElement(String key, String value) {
			Element element = doc.createElement(key);
			element.appendChild(doc.createTextNode(value));
			return element;
		}

		@Override
		public String getValueFromKey(String key) {
			XPath xPath = XPathFactory.newInstance().newXPath();
			try {
				return (String) xPath.compile("/map/pair[key[text()=\""+ key + "\"]]/value/text()").evaluate(doc, XPathConstants.STRING);
			} catch (XPathExpressionException e) {
				return null;
			}
		}
		
		public String toString() {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DOMSource source = new DOMSource(doc);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(bos);
			try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bos.toString();
		}

	}

}
