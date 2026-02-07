package com.cg.controller;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
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

import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

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

    
    
    @GetMapping("/payment")
    public String showPayment(HttpSession session, Model model) throws Exception {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) return "redirect:/books/list";

        double total = cart.stream().mapToDouble(CartItem::getPrice).sum();

        // 1. Initialize Razorpay Client (Use your TEST Keys)
        RazorpayClient client = new RazorpayClient("rzp_test_SD9JCpIiU4BRWR", "r6QvnJP18tjLeSgM7UbbNCSs");
        
        int amountInPaise = (int) (total * 100);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_123456");

        com.razorpay.Order order = client.orders.create(orderRequest);

        model.addAttribute("cartItems", cart);
        model.addAttribute("totalPrice", total);
        model.addAttribute("amount", amountInPaise);
        model.addAttribute("razorpayOrderId", order.get("id")); // Passed to JS
        return "cart/payment";
    }
    
    
    
    
    @GetMapping("/verify-payment")
    public String verifyPayment(
        @RequestParam("payment_id") String paymentId,
        @RequestParam("order_id") String orderId,
        @RequestParam("signature") String signature,
        HttpSession session, Principal principal, RedirectAttributes ra) {

        try {
            // 1. Prepare verification options
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", orderId);
            options.put("razorpay_payment_id", paymentId);
            options.put("razorpay_signature", signature);

            // 2. CRITICAL: Use your EXACT Test Secret Key here
            String secret = "r6QvnJP18tjLeSgM7UbbNCSs"; 
            
            // This is the robust method recommended for 2026
            boolean isValid = Utils.verifyPaymentSignature(options, secret);

            if (isValid && principal != null) {
                List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
                
                // Check if session expired during payment
                if (cart == null) {
                    ra.addFlashAttribute("error", "Session expired. Please retry.");
                    return "redirect:/books/list";
                }

                bookingService.processBooking(cart);
                session.removeAttribute("cart");
                return "redirect:/cart/booking-confirmed";
            }
        } catch (Exception e) {
            // If this prints, your Secret Key is likely wrong or the Razorpay library crashed
            System.err.println("Verification Failed: " + e.getMessage());
            e.printStackTrace(); 
        }
        
        ra.addFlashAttribute("error", "Payment verification failed.");
        return "redirect:/cart/payment";
    }

  





}
