package com.cg.dto;

public class AuthorDTO {
	private int authorId;
	private String authorName;
	private String authorEmail;
	private String authorCountry;

	public AuthorDTO() {
		
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


	}
