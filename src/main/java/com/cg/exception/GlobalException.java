package com.cg.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

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
    public String handleDataIntegrityViolation(org.springframework.dao.DataIntegrityViolationException ex, RedirectAttributes ra, jakarta.servlet.http.HttpServletRequest request) {
        String message = "Cannot delete: This record is linked to other data. Please remove dependencies first!";
        String redirectPath = "/books/list"; // Default fallback
        
        String errorMessage = ex.getMostSpecificCause().getMessage().toLowerCase();
        String requestUri = request.getRequestURI();

        // 1. Determine the entity from the URL path or DB error message
        
         if (requestUri.contains("/authors") || errorMessage.contains("author")) {
            message = "Cannot delete Author: This author still has books registered!";
            redirectPath = "/authors/list";
        }
         else if (errorMessage.contains("duplicate") || errorMessage.contains("uk_") || errorMessage.contains("unique")) {
        	    if (requestUri.contains("/inventories")) {
        	        message = "Assignment Error: This book is already assigned to another inventory slot!";
        	        redirectPath = "/inventories/list";
        	    }
        	}
                
         else if(requestUri.contains("/publishers") || errorMessage.contains("publisher")) {
            message = "Cannot delete Publisher: This publisher has active books in the library!";
            redirectPath = "/publishers/list";
        } 
        else if (requestUri.contains("/category") || errorMessage.contains("category")) {
            message = "Cannot delete Category: Books are still assigned to this category!";
            redirectPath = "/category/list";
        } 
        else if (requestUri.contains("/inventories") || errorMessage.contains("inventory")) {
            message = "Cannot delete Inventory: This record is currently linked to an active book!";
            redirectPath = "/inventories/list";
        } 
        

        // 2. Add as Flash Attribute for the popup
        ra.addFlashAttribute("error", message);
        
        // 3. Redirect back to the specific entity list
        return "redirect:" + redirectPath;
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("errorTitle", "Access Denied");
        model.addAttribute("errorMessage", "You do not have permission to access this page or perform this action.");
        model.addAttribute("statusCode", 403);
        return "error/custom-error";
    }

    
    
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNoResourceFound(NoResourceFoundException ex) {
        // Do nothing. This prevents the "CRASH DETECTED" log for missing icons.
    }




	// Static Inner Exceptions
	public static class AuthorNotFoundException extends RuntimeException {
	
		private static final long serialVersionUID = 1L;

		public AuthorNotFoundException(int id) {
			super("Author with ID " + id + " does not exist in our records.");
		}
	}

	public static class BookNotFoundException extends RuntimeException {
		
		private static final long serialVersionUID = 1L;

		public BookNotFoundException(int id) {
			super("Book ID " + id + " was not found in the library catalog.");
		}
	}

	public static class InventoryNotFoundException extends RuntimeException {
		
		private static final long serialVersionUID = 1L;

		public InventoryNotFoundException(int id) {
			super("Inventory record #" + id + " is missing.");
		}
	}

	public static class PublisherNotFoundException extends RuntimeException {
	
		private static final long serialVersionUID = 1L;

		public PublisherNotFoundException(int id) {
			super("Publisher ID " + id + " is not registered.");
		}
	}

	public static class CategoryNotFoundException extends RuntimeException {
	
		private static final long serialVersionUID = 1L;

		public CategoryNotFoundException(int id) {
			super("Category ID " + id + " does not exist.");
		}
	}
	public static class UserNotFoundException extends RuntimeException {
	    
	    private static final long serialVersionUID = 1L;

	    public UserNotFoundException(int id) {
	        super("User account with ID " + id + " was not found in our records.");
	    }
	}


	public static class BadRequestException extends RuntimeException {
	
		private static final long serialVersionUID = 1L;

		public BadRequestException(String msg) {
			super(msg);
		}
	}
}
