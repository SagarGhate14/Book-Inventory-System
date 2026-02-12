package com.cg.entity;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "book_users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int userId;
	
	  @NotBlank(message = "Username is required")
	    @Size(min = 3, max = 50)
	    @Column(nullable = false, unique = true, length = 50)
	    private String userName;

	    @NotBlank(message = "Email is required")
	    @Email(message = "Please provide a valid email address")
	    @Column(nullable = false, unique = true, length = 100)
	    private String email;

	    @NotBlank(message = "Password is required")
	    @Size(min = 8, message = "Password must be at least 8 characters")
	    @Column(nullable = false)
	    private String password;

	    @NotBlank(message = "Role is required")
	    @Column(nullable = false)
	    private String role;
	

	
	public User() {
		
	}




	public User(String userName, String email, String password, String role) {

		this.userName = userName;
		this.email = email;
		this.password = password;
		this.role = role;
	}




	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}

	
}