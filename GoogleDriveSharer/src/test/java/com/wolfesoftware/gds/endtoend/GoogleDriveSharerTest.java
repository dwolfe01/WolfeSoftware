package com.wolfesoftware.gds.endtoend;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.wolfesoftware.gds.GoogleDriveSharer;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources/features/GoogleDriveSharer.feature"},
                  glue={"com.wolfesoftware.gds.step.definitions"},
                  plugin = {"pretty", "html:target/test-reports"})
public class GoogleDriveSharerTest {
	
	private static GoogleDriveSharer googleDriveSharer;

	@BeforeClass
	public static void beforeClass(){
		HashMap<String, String> users = new HashMap<String,String>();
		users.put("username", "password");
		googleDriveSharer = new GoogleDriveSharer(users);
	}

	@AfterClass
	public static void afterClass(){
		googleDriveSharer.exit();
	}
	

}