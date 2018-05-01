package com.wolfesoftware.gds.configuration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Properties;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void shouldLoadConfiguration() {
		Properties configuration = Configuration.getConfiguration();
		assertEquals("this_is_a_test_property", configuration.getProperty("test.property"));
	}

	@Test
	public void shouldOverrideConfigurationFromCommandLineSystem() {
		File file = new File(this.getClass().getClassLoader().getResource("override.properties").getFile());
		System.setProperty("location.of.properties", file.getAbsolutePath());
		System.out.println(System.getProperty("location.of.properties"));
		Properties configuration = Configuration.getConfiguration();
		assertEquals("override.property", configuration.getProperty("test.property"));
		System.clearProperty("location.of.properties");
	}

}
