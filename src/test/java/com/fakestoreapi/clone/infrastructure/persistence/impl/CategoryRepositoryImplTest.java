package com.fakestoreapi.clone.infrastructure.persistence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fakestoreapi.clone.domain.entity.Category;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CategoryEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.CategoryPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.impl.CategoryRepositoryImpl;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryImplTest {

    @Mock
    private ICategoryRepository jpaCategoryRepository;

    @Mock
    private CategoryPersistenceMapper mapper;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepository;

    private Category category;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1)
                .name("Electronics")
                .imageUrl("electronics.jpg")
                .build();

        categoryEntity = CategoryEntity.builder()
                .id(1)
                .name("Electronics")
                .imageUrl("electronics.jpg")
                .build();
    }

    @Test
    void existsById_ShouldReturnTrue_WhenCategoryExists() {
        // Arrange
        when(jpaCategoryRepository.existsById(1)).thenReturn(true);

        // Act
        boolean exists = categoryRepository.existsById(1);

        // Assert
        assertTrue(exists);
        verify(jpaCategoryRepository).existsById(1);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenCategoryNotExists() {
        // Arrange
        when(jpaCategoryRepository.existsById(99)).thenReturn(false);

        // Act
        boolean exists = categoryRepository.existsById(99);

        // Assert
        assertFalse(exists);
        verify(jpaCategoryRepository).existsById(99);
    }

    @Test
    void findById_ShouldReturnCategory_WhenCategoryExists() {
        // Arrange
        when(jpaCategoryRepository.findById(1)).thenReturn(Optional.of(categoryEntity));
        when(mapper.toDomain(categoryEntity)).thenReturn(category);

        // Act
        Optional<Category> found = categoryRepository.findById(1);

        // Assert
        assertTrue(found.isPresent());
        assertEquals(category.getId(), found.get().getId());
        assertEquals(category.getName(), found.get().getName());
        assertEquals(category.getImageUrl(), found.get().getImageUrl());
        verify(jpaCategoryRepository).findById(1);
        verify(mapper).toDomain(categoryEntity);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenCategoryNotExists() {
        // Arrange
        when(jpaCategoryRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<Category> found = categoryRepository.findById(99);

        // Assert
        assertTrue(found.isEmpty());
        verify(jpaCategoryRepository).findById(99);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void findAll_ShouldReturnAllCategories() {
        // Arrange
        List<CategoryEntity> entities = List.of(categoryEntity);
        when(jpaCategoryRepository.findAll()).thenReturn(entities);
        when(mapper.toDomain(categoryEntity)).thenReturn(category);

        // Act
        List<Category> categories = categoryRepository.findAll();

        // Assert
        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
        assertEquals(category.getId(), categories.get(0).getId());
        assertEquals(category.getName(), categories.get(0).getName());
        assertEquals(category.getImageUrl(), categories.get(0).getImageUrl());
        verify(jpaCategoryRepository).findAll();
        verify(mapper, times(1)).toDomain(any());
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoCategoriesExist() {
        // Arrange
        when(jpaCategoryRepository.findAll()).thenReturn(List.of());

        // Act
        List<Category> categories = categoryRepository.findAll();

        // Assert
        assertTrue(categories.isEmpty());
        verify(jpaCategoryRepository).findAll();
        verify(mapper, never()).toDomain(any());
    }
}