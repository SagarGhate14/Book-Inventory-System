package com.cg.categorytest;

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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)   // prevents 403 errors if security is enabled
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    // 1) GET /category/list -> Should load category list view with DTOs
    @Test
    void list_shouldRenderCategoryListWithDTOs() throws Exception {
        Category cat1 = new Category();
        cat1.setCategoryId(1);
        cat1.setCategoryName("Fiction");

        Category cat2 = new Category();
        cat2.setCategoryId(2);
        cat2.setCategoryName("Science");

        List<Category> entities = List.of(cat1, cat2);

        CategoryDTO dto1 = new CategoryDTO();
        dto1.setCategoryId(1);
        dto1.setCategoryName("Fiction");

        CategoryDTO dto2 = new CategoryDTO();
        dto2.setCategoryId(2);
        dto2.setCategoryName("Science");

        when(categoryService.getAllCategories()).thenReturn(entities);
        when(categoryService.toDTOList(entities)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/category/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("Category/category-list"))
                .andExpect(model().attributeExists("categories"));
    }

    // 2) GET /category/new -> Should display add-category form
    @Test
    void newForm_shouldRenderAddView() throws Exception {
        mockMvc.perform(get("/category/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("Category/category-add"))
                .andExpect(model().attributeExists("categoryDTO"));
    }

    // 3) POST /category/new -> Should save category and redirect to list
    @Test
    void addCategory_shouldMapDTOToEntityAndSave_thenRedirect() throws Exception {
        Category entity = new Category();
        entity.setCategoryId(0);
        entity.setCategoryName("Mystery");

        when(categoryService.toEntity(any(CategoryDTO.class))).thenReturn(entity);
        when(categoryService.addCategory(entity)).thenReturn(entity);

        mockMvc.perform(post("/category/new")
                        .param("categoryId", "0")
                        .param("categoryName", "Mystery"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/category/list"));

        verify(categoryService, times(1)).toEntity(any(CategoryDTO.class));
        verify(categoryService, times(1)).addCategory(any(Category.class));
    }
}