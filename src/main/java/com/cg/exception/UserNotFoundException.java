package com.cg.exception;

public class UserNotFoundException extends RuntimeException{
	
	 private static final long serialVersionUID = 1L;

	    public UserNotFoundException(int id) {
	        super("User account with ID " + id + " was not found in our records.");
	    }

}
