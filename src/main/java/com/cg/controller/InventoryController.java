package com.cg.controller;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cg.entity.Inventory;
import com.cg.service.InventoryService;


 
@Controller
@RequestMapping("/inventories")
public class InventoryController{
 
   @Autowired
   InventoryService inventoryService;
    // SHOW INVENTORY PAGE
    @GetMapping
    public String showInventoryPage(Model model) {
        model.addAttribute("inventory", new Inventory());
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory"; // inventory.html
    }
 
    // SAVE INVENTORY
    @PostMapping
    public String saveInventory(@ModelAttribute Inventory inventory) {
        inventoryService.saveInventory(inventory);
        return "redirect:/inventories";
    }
 
    // DELETE INVENTORY
    @GetMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories";
    }
}
