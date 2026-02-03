package com.cg.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cg.dto.InventoryDTO;
import com.cg.service.InventoryService;

@Controller
@RequestMapping("/inventories")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @GetMapping("/list")
    public String showIndex(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory"; 
    }

    @GetMapping("/add-form")
    public String showAddForm(Model model) {
        model.addAttribute("inventory", new InventoryDTO());
        return "add"; 
    }

    @GetMapping("/edit-form/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryById(id));
        return "update"; 
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("inventory") InventoryDTO inventoryDTO) {
        inventoryService.saveInventory(inventoryDTO);
        return "redirect:/inventories/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("inventory") InventoryDTO inventoryDTO) {
        inventoryService.updateInventory(inventoryDTO.getId(), inventoryDTO);
        return "redirect:/inventories/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories/list";
    }
}
