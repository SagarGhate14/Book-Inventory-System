package com.cg.exception;

public class BookNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
            
	public BookNotFoundException(int id) {
		super("Book ID " + id + " was not found in the library catalog.");
	}
}
