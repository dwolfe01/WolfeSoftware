package com.wolfesoftware.smarthome.web;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.transform.TransformerException;

import org.junit.Test;

public class XMLBossTest {
	
	
	@Test
	public void shouldRunXSLT() throws TransformerException{
		String xml = "<xml>XMLBoss</xml>";
		String result = XMLBoss.xsl("style.xsl", xml);
		assertEquals("XMLBoss",result);
	}
	
	@Test
	public void shouldReadFile() throws TransformerException{
		InputStream stylesheet = this.getClass().getClassLoader().getResourceAsStream("style.xsl");
        new BufferedReader(new InputStreamReader(stylesheet)).lines().forEach(System.out::println);
	}

}
