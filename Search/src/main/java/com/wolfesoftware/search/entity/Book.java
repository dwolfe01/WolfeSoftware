package com.wolfesoftware.search.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;

import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;

@Indexed
@Entity
public class Book {

    @Id
    @GeneratedValue
    private long id;
    @Field
    private String title;
    @Field
    private String author;
	@Field
    @DateBridge(resolution=Resolution.DAY)
    private LocalDateTime releaseDate;
    @Version
	private Long version;
    @Field
    @Lob
    @FieldBridge(impl = ByteToStringBridge.class)
    private byte[] fullText;
    
    public Book() {
    }

    public Book(String title, String author, LocalDateTime releaseDate, String fullText) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.fullText = fullText.getBytes();
    }
    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDateTime getReleaseDate() {
		return releaseDate;
	}
	
	public String getFullText() {
		return new String(fullText);
	}

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}' + "\n" + getFullText();
    }
}