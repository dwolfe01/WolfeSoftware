package com.wolfesoftware.smarthome;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class FileLoadingHelper {
	
	public String getFileContentsFromClassPath(String filename) {
		InputStream inputStream = getInputStreamFromClassPath(filename);
		try {
			return IOUtils.toString(inputStream, "UTF8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "";
	}

	public File getFileFromClassPath(String filename) {
		ClassLoader classLoader = FileLoadingHelper.class.getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		return file;
	}

	public FileInputStream getInputStreamFromAbsolutePath(String absolutePath) throws FileNotFoundException {
		return new FileInputStream(new File(absolutePath));
	}

	public InputStream getInputStreamFromClassPath(String filename) {
		return this.getClass().getClassLoader().getResourceAsStream(filename);
	}

}
