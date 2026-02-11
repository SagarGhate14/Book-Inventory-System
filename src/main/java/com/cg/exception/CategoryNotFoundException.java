package com.cg.exception;

public class CategoryNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException(int id) {
		super("Category ID " + id + " does not exist.");
	}
}
