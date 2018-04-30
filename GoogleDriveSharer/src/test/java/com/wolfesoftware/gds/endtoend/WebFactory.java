package com.wolfesoftware.gds.endtoend;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebFactory {
	
	protected static WebDriver driver;
	
	static{
		Capabilities caps = new DesiredCapabilities();
		((DesiredCapabilities) caps).setJavascriptEnabled(true);
		((DesiredCapabilities) caps).setCapability("takesScreenshot", true);
		((DesiredCapabilities) caps).setCapability(
		PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
		"/Users/dwolfe/development/phantomjs-2.1.1-macosx/bin/phantomjs"
		);
		driver = new PhantomJSDriver();
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static String getHost() {
		return "http://localhost:4568";
	}

}
