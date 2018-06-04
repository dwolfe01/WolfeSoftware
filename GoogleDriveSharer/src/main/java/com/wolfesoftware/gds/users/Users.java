package com.wolfesoftware.gds.users;

import java.util.List;

public interface Users {
	
	int addUser(User user);
	List<User> listUsers();
	User findUser(String id);
	User findUserWithPasswordCheck(String id, String password);
	void removeUserById(String id);
	public void setPersitenceUnit(String persistenceUnit) ;//this is to set the system onto a test database

}
