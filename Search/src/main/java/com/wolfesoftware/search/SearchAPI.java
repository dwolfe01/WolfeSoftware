package com.wolfesoftware.search;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import com.wolfesoftware.search.entity.Book;

public class SearchAPI {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("search");
	EntityManager em = emf.createEntityManager();

	public void createIndexedBook(Book book) {
		em.getTransaction().begin();
		em.persist(book);
		em.getTransaction().commit();
	}

	public List<Book> search(String term) {
		 FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(em);
		 org.hibernate.search.query.dsl.QueryBuilder queryBuilder = fullTextEm.getSearchFactory().buildQueryBuilder().forEntity(Book.class).get();
		 org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("fullText").ignoreFieldBridge().matching(term).createQuery();
		 List<Book> results = fullTextEm.createFullTextQuery(query).getResultList();
		 return results;
	}
	
}
