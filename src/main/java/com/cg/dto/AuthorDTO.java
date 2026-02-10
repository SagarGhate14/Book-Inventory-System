package com.cg.dto;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AuthorDTO {
	private int authorId;
	
	
	@NotBlank(message = "Author name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s.]+$", message = "Name can only contain letters, spaces, and dots")
    private String authorName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String authorEmail;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 50, message = "Country name is too short or too long")
    private String authorCountry;

 
	public AuthorDTO() {
		
	}
	
		
	public AuthorDTO(int authorId, String authorName, String authorEmail, String authorCountry) {
		super();
		this.authorId = authorId;
		this.authorName = authorName;
		this.authorEmail = authorEmail;
		this.authorCountry = authorCountry;
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
