package com.wolfesoftware.gds.step.definitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import com.wolfesoftware.gds.endtoend.WebFactory;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	
	private final WebDriver driver = WebFactory.getDriver();
	
	@When("I navigate to (.*)")
	public void navigateToGDS(String url) {
		driver.get(WebFactory.getHost());
		System.out.println(driver.getPageSource());
		System.err.println("hello world");
	}
	
	@Then("I should get status code (.*)")
	public void getStatusCode() {
		assertEquals("404", "xxx");
	}
	
	@When("I enter credentials (.*) (.*)")
	public void enterCredentials(String username, String password) {
		driver.get(WebFactory.getHost());
	}
	
	@Then ("I should be on URL: (.*)")
	public void checkURL(String url) {
		assertEquals(WebFactory.getHost() + url, driver.getCurrentUrl());
	}

}
