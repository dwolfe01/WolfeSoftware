package com.wolfesoftware.gds.users;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UsersAPI implements Users{
	
	static EntityManager entityManager;

	static {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("users");
		entityManager = factory.createEntityManager();
	}
	
	public UsersAPI() {
	}
	
	public void setPersitenceUnit(String persistenceUnit) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnit);
		entityManager = factory.createEntityManager();
	}

	public int addUser(User user) {
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		return 1;
	}
	
	public List<User> listUsers() {
		entityManager.getTransaction().begin();
		Query q = entityManager.createQuery("from User");
		List<User> results = q.getResultList();
		entityManager.getTransaction().commit();
		return results;
	}

	@Override
	public User findUser(String id) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, id);
		entityManager.getTransaction().commit();
		return user;
	}

	@Override
	public User findUserWithPasswordCheck(String id, String password) {
		User user = null;
		Query q = entityManager.createQuery("SELECT u FROM User u WHERE u.id LIKE :id AND password LIKE :password");
		q.setParameter("id", id);
		q.setParameter("password", password);
		List<User> resultList = q.getResultList();
		if (1==resultList.size()) {
			user = resultList.get(0);
		}
		return user;
	}

	@Override
	public void removeUserById(String id) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, id);
		if (user!=null) {
			entityManager.remove(user);
		}
		entityManager.getTransaction().commit();
		
	}

}
