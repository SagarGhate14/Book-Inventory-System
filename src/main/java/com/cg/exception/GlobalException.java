package com.cg.exception;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.http.HttpStatus;

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




    // 3. Global Fallback
    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Server Error");
        model.addAttribute("errorMessage", "An unexpected error occurred.");
        model.addAttribute("statusCode", 500);
        System.err.println("CRASH DETECTED: " + ex.getMessage());
        ex.printStackTrace(); //
        return "error/custom-error";
    }
    
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(org.springframework.dao.DataIntegrityViolationException ex, RedirectAttributes ra) {
        // 1. Create the friendly message
        String message = "Cannot delete: This record is linked to existing books. Delete the books first!";
        
        // 2. Add it as a Flash Attribute (this creates the popup)
        ra.addFlashAttribute("error", message);
        
        // 3. Redirect back to the authors list (or wherever they were)
        return "redirect:/books/list"; 
    }
    
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNoResourceFound(NoResourceFoundException ex) {
        // Do nothing. This prevents the "CRASH DETECTED" log for missing icons.
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
