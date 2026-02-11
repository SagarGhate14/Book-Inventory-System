package com.cg.exception;

public class AuthorNotFoundException extends RuntimeException{
	     
	private static final long serialVersionUID = 1L;

	public AuthorNotFoundException(int id) {
		super("Author with ID " + id + " does not exist in our records.");
	}

}
