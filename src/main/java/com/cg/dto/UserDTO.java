package com.cg.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

    private int userId;
    
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String userName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "User role is required")
    @Pattern(regexp = "^(ADMIN|STAFF|VIEWER)$", message = "Role must be ADMIN, STAFF, or VIEWER")
    private String role;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", 
             message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    private String password;

    public UserDTO() { }

	public UserDTO(int userId, String userName, String email, String role, String password) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.role = role;
		this.password = password;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
}