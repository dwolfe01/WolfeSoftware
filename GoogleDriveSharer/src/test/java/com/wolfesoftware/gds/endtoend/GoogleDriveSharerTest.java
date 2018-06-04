package com.wolfesoftware.gds.endtoend;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.wolfesoftware.gds.GoogleDriveSharer;
import com.wolfesoftware.gds.users.User;
import com.wolfesoftware.gds.users.Users;
import com.wolfesoftware.gds.users.UsersAPI;

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
		createTestUserInDatabase();
		googleDriveSharer = new GoogleDriveSharer();
	}

	@AfterClass
	public static void afterClass(){
		googleDriveSharer.exit();
	}
	
	private static void createTestUserInDatabase() {
		Users users = new UsersAPI();
		users.setPersitenceUnit("users-test");
		User user = new User();
		user.setFirstName("test-user");
		user.setLastName("test-user");
		user.setId("username");
		user.setPassword("password");
		users.addUser(user);
	}
}