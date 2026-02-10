package com.cg.categorytest;

import com.cg.dto.CategoryDTO;
import com.cg.entity.Category;
import com.cg.exception.GlobalException.BadRequestException;
import com.cg.exception.GlobalException.CategoryNotFoundException;
import com.cg.repository.CategoryRepository;
import com.cg.service.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Mock
    private CategoryRepository categoryRepo;

    @InjectMocks
    private CategoryService categoryService;

    private Category sampleCategory;

    @BeforeEach
    void setUp() {
        // Prepare a sample category for testing
        sampleCategory = new Category();
        sampleCategory.setCategoryId(10);
        sampleCategory.setCategoryName("Programming");
    }

    // -------------------------------------------------------------------------
    // POSITIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Positive: Successfully add a category.
     */
    @Test
    void testAddCategory_Positive() {
        // Arrange
        when(categoryRepo.save(any(Category.class))).thenReturn(sampleCategory);

        // Act
        Category saved = categoryService.addCategory(sampleCategory);

        // Assert
        assertNotNull(saved);
        assertEquals("Programming", saved.getCategoryName());
        verify(categoryRepo, times(1)).save(sampleCategory);
    }

    /**
     * 2. Positive: Successfully get a category by valid ID.
     */
    @Test
    void testGetCategoryById_Positive() {
        // Arrange
        when(categoryRepo.findById(10)).thenReturn(Optional.of(sampleCategory));

        // Act
        Category found = categoryService.getCategoryById(10);

        // Assert
        assertEquals(10, found.getCategoryId());
        assertEquals("Programming", found.getCategoryName());
    }

    /**
     * 3. Positive: Verify DTO conversion logic (toDTO).
     */
    @Test
    void testToDTO_Positive() {
        // Act
        CategoryDTO dto = categoryService.toDTO(sampleCategory);

        // Assert
        assertNotNull(dto);
        assertEquals(10, dto.getCategoryId());
        assertEquals("Programming", dto.getCategoryName());
    }

    // -------------------------------------------------------------------------
    // NEGATIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Negative: Throw BadRequestException when adding a null category.
     */
    @Test
    void testAddCategory_Negative_NullInput() {
        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            categoryService.addCategory(null);
        });
        // Verify repository was never called
        verify(categoryRepo, never()).save(any());
    }

    /**
     * 2. Negative: Throw CategoryNotFoundException when ID does not exist for deletion.
     */
    @Test
    void testDeleteCategory_Negative_NotFound() {
        // Arrange: Mock existence check to return false
        when(categoryRepo.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteCategory(99);
        });

        // Verify deleteById was never reached
        verify(categoryRepo, never()).deleteById(99);
    }

    /**
     * 3. Negative: Throw CategoryNotFoundException when updating a non-existent category.
     */
    @Test
    void testUpdateCategory_Negative_NotFound() {
        // Arrange: Repository says this ID doesn't exist
        when(categoryRepo.existsById(10)).thenReturn(false);

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.updateCategory(sampleCategory);
        });

        // Verify save was never called
        verify(categoryRepo, never()).save(any());
    }
}
