package com.wolfesoftware.gds.step.definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.wolfesoftware.gds.drive.api.DriveAPI;
import com.wolfesoftware.gds.endtoend.WebFactory;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	
	private final WebDriver driver = WebFactory.getDriver();
	
	@When("I navigate to (.*)")
	public void navigateToGDS(String url) {
		driver.get(WebFactory.getHost() + url);
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

	@Then ("I see a timestamp")
	public void checkTimeStamp() {
		WebElement findElement = driver.findElement(By.className("date"));
		assertNotNull(findElement);
	}
	
	@Then ("I see no image")
	public void noImage() {
		try {
		driver.findElement(By.tagName("img"));
		fail();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			
		}
	}

	@Then ("I upload an image (.*)")
	public void uploadImage(String image) throws IOException {
		String pathToFile = "/tmp/" + image;
		java.io.File file = new java.io.File(this.getClass().getClassLoader().getResource("images/download.jpeg").getFile());
		Files.write(Paths.get(pathToFile), Files.readAllBytes(file.toPath()), StandardOpenOption.CREATE);
		WebElement element = driver.findElement(By.id("upload"));
		element.sendKeys(pathToFile);
		element = driver.findElement(By.id("upload_button"));
		element.click();
		//tear down
		Files.delete(Paths.get(pathToFile));
	}

	@Then ("I see an image (.*)")
	public void checkImageOnPage(String image) throws IOException {
		driver.findElement(By.name(image));
	}
	
	@Then ("I delete that (.*)")
	public void delete(String image) throws IOException {
		WebElement element = driver.findElement(By.name(image));
		String id = element.getAttribute("id");
		DriveAPI driveAPI = new DriveAPI();
		driveAPI.delete(id);;
	}
	
	@Then ("I set a description (.*)")
	public void setDescription(String description) throws IOException, InterruptedException {
		WebElement element = driver.findElement(By.className("description"));
		String id = element.getAttribute("id");
		URL url = new URL(WebFactory.getHost() + "/update/" + id);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(description);
		out.flush();
		out.close();
		con.getResponseCode();
	}

	@Then ("I see a description (.*)")
	public void getDescription(String description) throws IOException {
		WebElement element = driver.findElement(By.className("description"));
		assertEquals(description, element.getText());
	}
}
