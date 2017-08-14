package com.wolfesoftware.smarthome;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class FileLoadingHelperTest{

	@Test
	public void shouldGetFileContentsFromFile() {
		String result = new FileLoadingHelper().getFileContentsFromClassPath("test.txt");
		assertEquals("Hello World!!", result);
	}
	
	@Test
	public void shouldGetFileContentsFromFileInPackage() {
		String result = new FileLoadingHelper().getFileContentsFromClassPath("com/wolfesoftware/testWithPackage.txt");
		assertEquals("Hello World!!", result);
	}
	
	@Test
	public void shouldGetFileContentsFromJar() {
		new FileLoadingHelper().getFileContentsFromClassPath("junit/extensions/ActiveTestSuite.class");
	}
	
	@Test
	public void shouldGetInputStreamFromClasspath() throws IOException {
		InputStream is = new FileLoadingHelper().getInputStreamFromClassPath("junit/extensions/ActiveTestSuite.class");
		assertNotNull(is);
	}

	@Test
	public void shouldGetInputStreamFromAbsolutePath() throws IOException {
		String absolutePath = new FileLoadingHelper().getFileFromClassPath("test.txt").getAbsolutePath();
		FileInputStream fis = new FileLoadingHelper().getInputStreamFromAbsolutePath(absolutePath);
		assertEquals("Hello World!!", IOUtils.toString(fis, "UTF8"));
		fis.close();
	}
	
}
