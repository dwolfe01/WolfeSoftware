package com.wolfesoftware.smarthome.web;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLBoss {
	    // This method applies the xslFilename to inFilename and returns a String...
	    public static String xsl(String xslFile, String input) throws TransformerException {
	            TransformerFactory factory = TransformerFactory.newInstance();
	            InputStream stylesheet = XMLBoss.class.getClassLoader().getResourceAsStream(xslFile);
	            Source stylesheetSource = new StreamSource(stylesheet);
	            Transformer transformer = factory.newTransformer(stylesheetSource);
	            Source inputSource = new StreamSource(new StringReader(input));
	            StringWriter result = new StringWriter();
	            Result outputResult = new StreamResult(result);
	            transformer.transform(inputSource, outputResult);
	            return result.toString();
	    }
}
