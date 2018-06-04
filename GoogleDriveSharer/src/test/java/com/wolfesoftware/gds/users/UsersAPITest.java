package com.wolfesoftware.gds.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UsersAPITest {
	
	Users users = new UsersAPI();
	
	
	@Before
	public void tearUp() {
		users.setPersitenceUnit("users-test");
		User user = new User();
		user.setFirstName("Kenny");
		user.setLastName("Belvoir");
		user.setId("kenny@oal.com");
		user.setPassword("password");
		users.addUser(user);
	}
	
	@After
	public void tearDown() {
		users.removeUserById("kenny@oal.com");
	}
	
	@Test
	public void shouldListUsersInDatabase() {
		List<User> listUsers = users.listUsers();
		assertEquals(listUsers.get(0).getId(),"kenny@oal.com");
	}
	
	@Test
	public void shouldFindUserInDatabase() {
		User user = users.findUser("kenny@oal.com");
		assertEquals(user.getId(),"kenny@oal.com");
	}
	
	@Test
	public void shouldCheckPasswordFalse() {
		User user = users.findUserWithPasswordCheck("kenny@oal.com","nonpassword");
		assertNull(user);
	}

	@Test
	public void shouldCheckPassword() {
		User user = users.findUserWithPasswordCheck("kenny@oal.com","password");
		assertNotNull(user);
	}

}
