package com.wolfesoftware.smarthome;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartProperties {
	
	private static FileLoadingHelper fileLoadingHelper;
	private static Properties configuration;
	private static final Logger LOGGER = LoggerFactory.getLogger(SmartProperties.class);
	
	static {
		fileLoadingHelper = new FileLoadingHelper();
		configuration = getConfiguration();
	}

	public static String get(String name){
		return configuration.getProperty(name);
	}
	
	
	protected static Properties getConfiguration() {
		Properties config = new Properties();
		InputStream in;
		try {
			in = fileLoadingHelper.getInputStreamFromClassPath("smartHome.properties");
			config.load(in);
			in.close();
			String locationOfProperties = System.getProperty("location.of.properties");
			if (null != locationOfProperties) {
				in = fileLoadingHelper.getInputStreamFromAbsolutePath(locationOfProperties);
				config.load(in);
				in.close();
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("CONFIGURATION" + config.toString());
		return config;
	}

}
