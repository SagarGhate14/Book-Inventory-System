package com.cg.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    // 1. Handle ALL "Not Found" cases
    @ExceptionHandler({
        AuthorNotFoundException.class, 
        BookNotFoundException.class, 
        InventoryNotFoundException.class,
        CategoryNotFoundException.class 
    })
    public String handleNotFound(RuntimeException ex, Model model) {
        model.addAttribute("errorTitle", "Resource Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("statusCode", 404);
        return "error/custom-error";
    }

    // 2. Handle Bad Input / Validation errors
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
        System.err.println("CRASH DETECTED: " + e.getMessage());
        ex.printStackTrace(); //
        return "error/custom-error";
    }

    /* --- Simple Custom Exceptions --- */

    public static class CategoryNotFoundException extends RuntimeException {
        public CategoryNotFoundException(int id) { super("Category ID " + id + " not found."); }
    }

    public static class AuthorNotFoundException extends RuntimeException {
        public AuthorNotFoundException(int id) { super("Author ID " + id + " not found."); }
    }
 
 public static class PublisherNotFoundException extends RuntimeException {
     public PublisherNotFoundException(int id) { super("Publisher ID " + id + " not found."); }
 }


    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) { super(message); }
    }

    public static class BookNotFoundException extends RuntimeException {
        public BookNotFoundException(int id) { super("Book ID " + id + " not found."); }
    }

    public static class InventoryNotFoundException extends RuntimeException {
        public InventoryNotFoundException(int id) { super("Inventory ID " + id + " not found."); }
    }
}
