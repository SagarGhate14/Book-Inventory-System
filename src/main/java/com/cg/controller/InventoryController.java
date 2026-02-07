package com.cg.controller;

import com.cg.dto.InventoryDTO;
import com.cg.entity.Book;
import com.cg.entity.Inventory;
import com.cg.entity.Status;
import com.cg.service.BookService;
import com.cg.service.IInventoryService;
import com.cg.service.InventoryService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    @Transactional(readOnly = true)
    public String showIndex(Model model) {
    	
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory/inventory-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
    	List<Book> books = bookService.getAllBooks();
        System.out.println("Books found: " + (books != null ? books.size() : "NULL"));
        
        model.addAttribute("inventory", new InventoryDTO());
        model.addAttribute("books", books != null ? books : new ArrayList<>()); // Prevent null
        return "inventory/inventory-add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("inventory") InventoryDTO inventoryDTO) {
   
    	int safeQuantity = Math.max(0, inventoryDTO.getQuantity());
        
        Inventory inventory = inventoryService.toEntity(inventoryDTO);
        inventory.setQuantity(safeQuantity);
        
        // 2. Set Status using the exact strings the DB expects
        // Note: Use the exact strings found in your 'SHOW CREATE TABLE' command
        inventory.setStatus(safeQuantity > 0 ? "AVAILABLE" : "OUT_OF_STOCK");

        // 3. Link Book
        Book book = bookService.findIdByBook(inventoryDTO.getBookId());
        inventory.setBook(book);

        inventoryService.saveInventory(inventory);
        return "redirect:/inventories/list";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable("id") int id,Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        InventoryDTO inventoryDTO = inventoryService.toDTO(inventory);
        model.addAttribute("inventory",inventoryDTO);
        model.addAttribute("books",bookService.getAllBooks());
        return "inventory/inventory-edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories/list";
    }
    
    @PostMapping("/edit")
    public String updateInventory(@ModelAttribute InventoryDTO inventoryDTO,RedirectAttributes redirectAttributes, BindingResult result) {
    	Inventory currentInventory = inventoryService.getInventoryById(inventoryDTO.getInventoryId());
    	
        if (result.hasErrors()) {
            // This will print the EXACT reason for the 500 error in your console
            result.getAllErrors().forEach(System.out::println); 
            return "redirect:/inventories/list";
        }

    	

    	if (currentInventory == null) {
            redirectAttributes.addFlashAttribute("error", "Inventory record not found.");
            return "redirect:/inventories/list";
        }

        // 2. CHECK: Is this book already assigned to a DIFFERENT inventory record?
        Inventory existingWithBook = inventoryService.findBookById(inventoryDTO.getBookId());
        if (existingWithBook != null && !existingWithBook.getInventoryId().equals(inventoryDTO.getInventoryId())) {
            redirectAttributes.addFlashAttribute("error", "This book is already assigned to another inventory slot.");
            return "redirect:/inventories/list";
        }

        // 3. Fetch the Book entity
        Book book = bookService.findIdByBook(inventoryDTO.getBookId());
        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Selected Book ID does not exist.");
            return "redirect:/inventories/list";
        }

        // 4. Update fields
        currentInventory.setQuantity(inventoryDTO.getQuantity());
        currentInventory.setBook(book);
        
        // Logic: Set status based on quantity
        String status = (inventoryDTO.getQuantity() > 0) ? "AVAILABLE" : "OUT_OF_STOCK";
        currentInventory.setStatus(status);

        // 5. Save
        inventoryService.saveInventory(currentInventory);
        
        redirectAttributes.addFlashAttribute("success", "Inventory updated successfully!");
        return "redirect:/inventories/list";
    }
    
}
