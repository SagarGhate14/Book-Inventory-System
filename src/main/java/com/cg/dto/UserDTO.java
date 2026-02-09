package com.cg.dto;



public class UserDTO {

    private int userId;
    private String userName;
    private String email;
    private String role;
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