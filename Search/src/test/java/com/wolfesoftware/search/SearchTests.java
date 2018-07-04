package com.wolfesoftware.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.wolfesoftware.search.entity.Book;

public class SearchTests {
	
	SearchAPI searchAPI = new SearchAPI();

	@Before
	public void createContent() throws IOException {
		searchAPI.createIndexedBook(new Book("Moby Dick","Herman Melville",LocalDateTime.now(), getBookText("mobydick.txt")));
		searchAPI.createIndexedBook(new Book("Adventures of Huckleberry Finn","Mark Twain",LocalDateTime.now(), getBookText("huckleberryfinn.txt")));
		searchAPI.createIndexedBook(new Book("Treasure Island","Robert Louis Stevenson",LocalDateTime.now(), getBookText("treasureisland.txt")));
		searchAPI.createIndexedBook(new Book("Great Expectations","Charles Dickens",LocalDateTime.now(), getBookText("greatexpectations.txt")));
		searchAPI.createIndexedBook(new Book("Sense and Sensibility","Jane Austen",LocalDateTime.now(), getBookText("senseandsensibility.txt")));
		searchAPI.createIndexedBook(new Book("Beyond Good and Evil","Friedrich Wilhelm Nietzsche",LocalDateTime.now(), getBookText("beyondgoodandevil.txt")));
	}

	private String getBookText(String name) throws IOException {
		Path path = Paths.get("src/test/resources/books/" + name);
		return Files.lines(path).collect(
	            Collectors.joining("\n"));
	}
	
	@Test
	public void search() {
		List<Book> books = searchAPI.search("pro");
		books.stream().forEach(b -> System.out.println(b.getTitle()));
	}
}
