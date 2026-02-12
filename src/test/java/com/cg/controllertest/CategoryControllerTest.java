package com.cg.controllertest;

import com.cg.controller.CategoryController;

import com.cg.dto.CategoryDTO;
import com.cg.entity.Category;
import com.cg.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)   // prevents 403 errors if security is enabled
public class CategoryControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    // POSITIVE TEST CASES

    
     // 1. Positive: Successfully view the list of categories.
     
    @Test
    void testAllCategory_Positive() throws Exception {
        // Arrange
        when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());
        
        // Act & Assert
        mockMvc.perform(get("/category/list")
                .with(csrf())) // This provides the dummy CSRF token for the template
                .andExpect(status().isOk())
                .andExpect(view().name("Category/category-list"))
                .andExpect(model().attributeExists("categories"));
    }

    // Positive: Successfully add a new category with valid data.
     
    @Test
    void testAddCategory_Positive() throws Exception {
        mockMvc.perform(post("/category/new")
                .with(csrf()) // Required if Spring Security is active
                .param("categoryName", "Science & Fiction")) 
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/category/list"));

        verify(categoryService, times(1)).addCategory(any());
    }

    // Positive: Successfully delete a category.
     
    @Test
    void testDeleteCategory_Positive() throws Exception {
        mockMvc.perform(delete("/category/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/category/list"));

        verify(categoryService).deleteCategory(1);
    }

    // NEGATIVE TEST CASES (Validation & Logic)

    // Negative: Add Category fails because name is too short (triggers @Size).
     
    @Test
    void testAddCategory_Negative_TooShort() throws Exception {
        mockMvc.perform(post("/category/new")
                .with(csrf())
                .param("categoryName", "IT")) // Only 2 chars, min is 3
                .andExpect(status().isOk())
                .andExpect(view().name("Category/category-add"))
                .andExpect(model().attributeHasFieldErrors("categoryDTO", "categoryName"));
    }

    // Negative: Add Category fails due to illegal characters (triggers @Pattern).
     
    @Test
    void testAddCategory_Negative_InvalidPattern() throws Exception {
        mockMvc.perform(post("/category/new")
                .with(csrf())
                .param("categoryName", "Category_123!")) // Numbers and ! are forbidden
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("categoryDTO", "categoryName"));
    }

   // Negative: Update Category fails because name is blank (triggers @NotBlank).
     
    @Test
    void testUpdateCategory_Negative_Blank() throws Exception {
        mockMvc.perform(put("/category/update")
                .with(csrf())
                .param("categoryId", "1")
                .param("categoryName", "")) // Blank name
                .andExpect(status().isOk())
                // Based on your code logic, it returns the "add" view on update error
                .andExpect(view().name("Category/category-add")) 
                .andExpect(model().attributeHasFieldErrors("categoryDTO", "categoryName"));
        
        verify(categoryService, never()).updateCategory(any());
    }
}