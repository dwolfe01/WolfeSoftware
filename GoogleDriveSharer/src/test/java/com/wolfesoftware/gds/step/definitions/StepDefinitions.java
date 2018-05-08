package com.wolfesoftware.gds.step.definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.wolfesoftware.gds.endtoend.WebFactory;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	
	private final WebDriver driver = WebFactory.getDriver();
	
	@When("I navigate to (.*)")
	public void navigateToGDS(String url) {
		driver.get(WebFactory.getHost());
	}
	
	@Then("I should get status code (.*)")
	public void getStatusCode() {
		assertEquals("404", "xxx");
	}
	
	@When("I enter credentials (.*) (.*)")
	public void enterCredentials(String username, String password) {
		driver.findElement(By.id("uname")).sendKeys(username);
		driver.findElement(By.id("psw")).sendKeys(password);
		driver.findElement(By.id("login")).submit();
	}
	
	@Then ("I should be on URL: (.*)")
	public void checkURL(String url) {
		assertEquals(WebFactory.getHost() + url, driver.getCurrentUrl());
	}

	@Then ("I see an age")
	public void checkAge() {
		WebElement findElement = driver.findElement(By.id("age"));
		assertNotNull(findElement);
	}

}
