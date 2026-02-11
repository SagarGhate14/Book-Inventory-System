package com.cg.exception;

public class PublisherNotFoundException extends RuntimeException{
         
	private static final long serialVersionUID = 1L;

	public PublisherNotFoundException(int id) {
		super("Publisher ID " + id + " is not registered.");
	}
}
