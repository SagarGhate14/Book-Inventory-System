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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * 3 service tests for CategoryService
 */
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

    // 1) getAllCategories returns repository list unchanged
    @Test
    void getAllCategories_shouldReturnAllFromRepository() {
        when(categoryRepository.findAll()).thenReturn(List.of(cat1, cat2));

        List<Category> result = categoryService.getAllCategories();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCategoryId()).isEqualTo(1);
        assertThat(result.get(0).getCategoryName()).isEqualTo("Fiction");
        assertThat(result.get(1).getCategoryId()).isEqualTo(2);
        assertThat(result.get(1).getCategoryName()).isEqualTo("Science");
    }

    // 2) toDTOList maps entities to DTO list (using toDTO)
    @Test
    void toDTOList_shouldMapEntitiesToDTOs() {
        List<Category> entities = List.of(cat1, cat2);

        List<CategoryDTO> dtos = categoryService.toDTOList(entities);

        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getCategoryId()).isEqualTo(1);
        assertThat(dtos.get(0).getCategoryName()).isEqualTo("Fiction");
        assertThat(dtos.get(1).getCategoryId()).isEqualTo(2);
        assertThat(dtos.get(1).getCategoryName()).isEqualTo("Science");
    }

    // 3) deleteCategory delegates to repository.deleteById
    @Test
    void deleteCategory_shouldInvokeRepositoryDeleteById() {
        doNothing().when(categoryRepository).deleteById(2);

        categoryService.deleteCategory(2);

        verify(categoryRepository, times(1)).deleteById(2);
    }
}
