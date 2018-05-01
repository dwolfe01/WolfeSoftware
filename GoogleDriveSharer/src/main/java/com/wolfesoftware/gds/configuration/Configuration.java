package com.wolfesoftware.gds.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {
	
	private static Properties configuration;
	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
	
	static {
		configuration = getConfiguration();
	}

	public static String get(String name){
		return configuration.getProperty(name);
	}
	
	
	protected static Properties getConfiguration() {
		Properties config = new Properties();
		InputStream in;
		try {
			in = ClassLoader.getSystemResourceAsStream("application.properties");
			config.load(in);
			in.close();
			String locationOfProperties = System.getProperty("location.of.properties");
			if (null != locationOfProperties) {
				Path path = Paths.get(locationOfProperties);
				in = new FileInputStream(path.toFile());
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
