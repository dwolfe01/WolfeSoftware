package com.wolfesoftware.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class LogMessageDAO {
	private EntityManager em;

	public LogMessageDAO() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("hypersql");
		em = factory.createEntityManager();
	}

	public void save(LogMessage cle) {
		em.getTransaction().begin();
		em.persist(cle);
		em.getTransaction().commit();
	}

	public List<LogMessage> getAllLogFileEntity() {
		em.getTransaction().begin();
        Query q = em.createQuery("from LogMessage");
		List<LogMessage> results = q.getResultList();
		em.getTransaction().commit();
		return results;
	}
	
	@Override
	public void finalize() {
		em.close();
	}
	
	public Long getCountOfLogFileEntities() {
		em.getTransaction().begin();
        Query q = em.createQuery("select count(*) from LogMessage");
		Long result = (Long) q.getSingleResult();
		em.getTransaction().commit();
		return result;
	}

}
