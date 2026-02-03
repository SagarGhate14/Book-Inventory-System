package com.cg.controller;

import com.cg.dto.InventoryDTO;
import com.cg.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/list")
    public String showIndex(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory/inventory";
    }

    @GetMapping("/add-form")
    public String showAddForm(Model model) {
        model.addAttribute("inventory", new InventoryDTO());
        return "inventory/add";
    }

    @GetMapping("/edit-form/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryById(id));
        return "inventory/update";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("inventory") InventoryDTO dto) {
        inventoryService.saveInventory(dto);
        return "redirect:/inventories/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("inventory") InventoryDTO dto) {
        inventoryService.updateInventory(dto.getInventoryId(), dto);
        return "redirect:/inventories/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories/list";
    }
}
