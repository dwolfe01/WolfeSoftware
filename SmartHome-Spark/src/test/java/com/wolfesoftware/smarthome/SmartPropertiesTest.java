package com.wolfesoftware.smarthome;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class SmartPropertiesTest {

	@Test
	public void shouldLoadConfiguration() {
		Properties configuration = SmartProperties.getConfiguration();
		assertEquals("this_is_a_test_property", configuration.getProperty("test.property"));
	}

	@Test
	public void shouldOverrideConfigurationFromCommandLineSystem() {
		String pathToOverrideProperties = new FileLoadingHelper().getFileFromClassPath("override.properties")
				.getAbsolutePath();
		System.setProperty("location.of.properties", pathToOverrideProperties);
		System.out.println(System.getProperty("location.of.properties"));
		Properties configuration = SmartProperties.getConfiguration();
		assertEquals("override_property", configuration.getProperty("test.property"));
		System.clearProperty("location.of.properties");
	}

}
