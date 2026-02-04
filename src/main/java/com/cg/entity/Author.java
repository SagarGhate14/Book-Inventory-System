package com.cg.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "author")
public class Author {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int authorId;
private String authorName;
private String authorEmail;
private String authorCountry;

@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
private List<Book> books;

public Author() {
	
}

public Author(int authorId, String authorName, String authorEmail, String authorCountry, List<Book> books) {
	super();
	this.authorId = authorId;
	this.authorName = authorName;
	this.authorEmail = authorEmail;
	this.authorCountry = authorCountry;
	this.books = books;
}

public int getAuthorId() {
	return authorId;
}

public void setAuthorId(int authorId) {
	this.authorId = authorId;
}

public String getAuthorName() {
	return authorName;
}

public void setAuthorName(String authorName) {
	this.authorName = authorName;
}

public String getAuthorEmail() {
	return authorEmail;
}

public void setAuthorEmail(String authorEmail) {
	this.authorEmail = authorEmail;
}

public String getAuthorCountry() {
	return authorCountry;
}

public void setAuthorCountry(String authorCountry) {
	this.authorCountry = authorCountry;
}

public List<Book> getBooks() {
	return books;
}

public void setBooks(List<Book> books) {
	this.books = books;
}

@Override
public String toString() {
	return "Author [authorId=" + authorId + ", authorName=" + authorName + ", authorEmail=" + authorEmail
			+ ", authorCountry=" + authorCountry + ", books=" + books + "]";
}


}
