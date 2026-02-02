package com.cg.controller;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cg.entity.Inventory;
import com.cg.service.InventoryService;
@Controller
@RequestMapping("/inventories")
public class InventoryController {
    @Autowired
	InventoryService inventoryService;

    // LIST VIEW (Main Page)
    @GetMapping("/list")
    public String showIndex(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory"; 
    }

    // SHOW ADD PAGE
    @GetMapping("/add-form")
    public String showAddForm(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "add"; 
    }

    // SHOW UPDATE PAGE
    @GetMapping("/edit-form/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryById(id));
        return "update"; 
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Inventory inventory) {
        inventoryService.saveInventory(inventory);
        return "redirect:/inventories/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Inventory inventory) {
        inventoryService.updateInventory(inventory.getId(), inventory);
        return "redirect:/inventories/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories/list";
    }
}
=======
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cg.entity.Inventory;
import com.cg.service.InventoryService;
 
@Controller
@RequestMapping("/inventories")
public class InventoryController {
 
    private final InventoryService inventoryService;
 
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
 
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
 
>>>>>>> origin/author
