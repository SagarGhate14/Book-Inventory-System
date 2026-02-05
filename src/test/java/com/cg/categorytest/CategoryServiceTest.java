package com.cg.categorytest;

import com.cg.dto.CategoryDTO;
import com.cg.entity.Category;
import com.cg.repository.CategoryRepository;
import com.cg.service.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category cat1;
    private Category cat2;

    @BeforeEach
    void setUp() {
        cat1 = new Category();
        cat1.setCategoryId(1);
        cat1.setCategoryName("Fiction");

        cat2 = new Category();
        cat2.setCategoryId(2);
        cat2.setCategoryName("Science");
    }

    @Test
    void testGetAllCategories() {
        List<Category> list = new ArrayList<>();
        list.add(cat1);
        list.add(cat2);

        when(categoryRepository.findAll()).thenReturn(list);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("Fiction", result.get(0).getCategoryName());
    }

    @Test
    void testToDTOListMapping() {
        List<Category> entities = new ArrayList<>();
        entities.add(cat1);

        List<CategoryDTO> dtos = categoryService.toDTOList(entities);

        assertNotNull(dtos);
        assertEquals(1, dtos.get(0).getCategoryId());
        assertEquals("Fiction", dtos.get(0).getCategoryName());
    }

    @Test
    void testDeleteCategory() {
        // ⛳️ FIX: Stub existsById to return true so the service 'if' check passes
        when(categoryRepository.existsById(2)).thenReturn(true);

        categoryService.deleteCategory(2);

        // Verify the repository delete was actually called
        verify(categoryRepository, times(1)).deleteById(2);
    }
}
