package com.cg.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler({ AuthorNotFoundException.class, BookNotFoundException.class, InventoryNotFoundException.class,
			CategoryNotFoundException.class, PublisherNotFoundException.class })
	public String handleNotFound(RuntimeException ex, Model model) {
		model.addAttribute("errorTitle", "Resource Not Found");
		model.addAttribute("errorMessage", ex.getMessage()); // Carries the "Why"
		model.addAttribute("statusCode", 404);
		return "error/custom-error";
	}

	@ExceptionHandler(BadRequestException.class)
	public String handleBadRequest(RuntimeException ex, Model model) {
		model.addAttribute("errorTitle", "Invalid Request");
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("statusCode", 400);
		return "error/custom-error";
	}

	@ExceptionHandler(Exception.class)
	public String handleGeneral(Exception ex, Model model) {
		model.addAttribute("errorTitle", "Server Error");
		model.addAttribute("errorMessage", "An internal error occurred: " + ex.getMessage());
		model.addAttribute("statusCode", 500);
		return "error/custom-error";
	}

	// Static Inner Exceptions
	public static class AuthorNotFoundException extends RuntimeException {
		public AuthorNotFoundException(int id) {
			super("Author with ID " + id + " does not exist in our records.");
		}
	}

	public static class BookNotFoundException extends RuntimeException {
		public BookNotFoundException(int id) {
			super("Book ID " + id + " was not found in the library catalog.");
		}
	}

	public static class InventoryNotFoundException extends RuntimeException {
		public InventoryNotFoundException(int id) {
			super("Inventory record #" + id + " is missing.");
		}
	}

	public static class PublisherNotFoundException extends RuntimeException {
		public PublisherNotFoundException(int id) {
			super("Publisher ID " + id + " is not registered.");
		}
	}

	public static class CategoryNotFoundException extends RuntimeException {
		public CategoryNotFoundException(int id) {
			super("Category ID " + id + " does not exist.");
		}
	}

	public static class BadRequestException extends RuntimeException {
		public BadRequestException(String msg) {
			super(msg);
		}
	}
}
