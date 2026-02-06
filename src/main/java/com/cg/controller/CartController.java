package com.cg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cg.dto.BookDTO;
import com.cg.dto.CartItem;
import com.cg.entity.Book;
import com.cg.service.BookService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
    private BookService bookingService; // You will create this to handle DB saves

    // 1. View the Checkout Page
    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        model.addAttribute("cartItems", cart != null ? cart : new ArrayList<>());
        return "cart/checkout"; // Points to checkout.html
    }

    // 2. The Confirmation Logic (The method you asked about)
    @PostMapping("/confirm")
    public String confirmBooking(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        
        if (cart != null && !cart.isEmpty()) {
            // Logic: Pass the cart to the service to save in DB
           // bookingService.processBooking(cart);
            
        	bookingService.processBooking(cart);
        	
            // Clear the session so the cart is empty for the next purchase
            session.removeAttribute("cart");
            
            model.addAttribute("message", "Your books have been successfully booked!");
            return "cart/booking-confirmed"; // Points to booking-success.html
        }
        
        return "redirect:/books/list"; // Redirect if cart was empty
    }
    
    @PostMapping("/add")
    public String addToCart(@RequestParam int id, HttpSession session,RedirectAttributes ra) {
        // Get existing cart or create new one
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) { cart = new ArrayList<>(); }

        // Fetch book and add to cart
        Book book = bookingService.findIdByBook(id);
        BookDTO bookDTO = bookingService.toDTO(book);
        cart.add(new CartItem(book.getBookId(), book.getTitle(), book.getPrice()));

        session.setAttribute("cart", cart);
        ra.addFlashAttribute("successMessage", "Book added to cart successfully!");
        return "redirect:/books/list"; // Refresh the list
    }

    
    @GetMapping("/booking-confirmed")
    public String showConfirmedPage() {
        return "cart/booking-confirmed";
    }


}
