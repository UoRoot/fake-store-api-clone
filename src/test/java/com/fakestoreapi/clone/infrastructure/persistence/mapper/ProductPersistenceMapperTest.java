package com.fakestoreapi.clone.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fakestoreapi.clone.domain.entity.Category;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CategoryEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;

public class ProductPersistenceMapperTest {

    @Mock
    private CategoryPersistenceMapper categoryMapper;

    @InjectMocks
    private ProductPersistenceMapperImpl mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toDomain_ShouldMapAllProperties() {
        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1)
                .name("Electronics")
                .imageUrl("category-image.jpg")
                .build();

        ProductEntity entity = ProductEntity.builder()
                .title("Laptop")
                .price(new BigDecimal("999.99"))
                .description("A great laptop")
                .image("laptop-image.jpg")
                .category(categoryEntity)
                .build();
        entity.setId(1L);

        Category expectedCategory = Category.builder()
                .id(1)
                .name("Electronics")
                .imageUrl("category-image.jpg")
                .build();

        when(categoryMapper.toDomain(categoryEntity)).thenReturn(expectedCategory);

        // Act
        Product result = mapper.toDomain(entity);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(entity.getId(), result.getId()),
                () -> assertEquals(entity.getTitle(), result.getTitle()),
                () -> assertEquals(entity.getPrice(), result.getPrice()),
                () -> assertEquals(entity.getDescription(), result.getDescription()),
                () -> assertEquals(entity.getImage(), result.getImage()),
                () -> assertEquals(expectedCategory, result.getCategory()));
        verify(categoryMapper).toDomain(categoryEntity);
    }

    @Test
    void toEntity_ShouldMapAllProperties() {
        // Arrange
        Category category = Category.builder()
                .id(1)
                .name("Electronics")
                .imageUrl("category-image.jpg")
                .build();

        Product product = Product.builder()
                .id(1L)
                .title("Laptop")
                .price(new BigDecimal("999.99"))
                .description("A great laptop")
                .image("laptop-image.jpg")
                .category(category)
                .build();

        CategoryEntity expectedCategoryEntity = CategoryEntity.builder()
                .id(1)
                .name("Electronics")
                .imageUrl("category-image.jpg")
                .build();

        when(categoryMapper.toEntity(category)).thenReturn(expectedCategoryEntity);

        // Act
        ProductEntity result = mapper.toEntity(product);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(product.getTitle(), result.getTitle()),
                () -> assertEquals(product.getPrice(), result.getPrice()),
                () -> assertEquals(product.getDescription(), result.getDescription()),
                () -> assertEquals(product.getImage(), result.getImage()),
                () -> assertEquals(expectedCategoryEntity, result.getCategory()));
        verify(categoryMapper).toEntity(category);
    }

    @Test
    void updateEntity_ShouldUpdateOnlyNonNullProperties() {
        // Arrange
        Category newCategory = Category.builder()
                .id(2)
                .name("New Electronics")
                .imageUrl("new-category-image.jpg")
                .build();

        Product updates = Product.builder()
                .title("Updated Laptop")
                .price(new BigDecimal("1099.99"))
                .category(newCategory)
                .build();

        CategoryEntity originalCategoryEntity = CategoryEntity.builder()
                .id(1)
                .name("Electronics")
                .imageUrl("category-image.jpg")
                .build();

        ProductEntity existingEntity = ProductEntity.builder()
                .title("Laptop")
                .price(new BigDecimal("999.99"))
                .description("A great laptop")
                .image("laptop-image.jpg")
                .category(originalCategoryEntity)
                .build();
        existingEntity.setId(1L);

        CategoryEntity newCategoryEntity = CategoryEntity.builder()
                .id(2)
                .name("New Electronics")
                .imageUrl("new-category-image.jpg")
                .build();

        when(categoryMapper.toEntity(newCategory)).thenReturn(newCategoryEntity);

        // Act
        mapper.updateEntity(updates, existingEntity);

        // Assert
        assertAll(
                () -> assertEquals(1L, existingEntity.getId()),
                () -> assertEquals(updates.getTitle(), existingEntity.getTitle()),
                () -> assertEquals(updates.getPrice(), existingEntity.getPrice()),
                () -> assertEquals("A great laptop", existingEntity.getDescription()),
                () -> assertEquals("laptop-image.jpg", existingEntity.getImage()),
                () -> assertEquals(newCategoryEntity, existingEntity.getCategory()));
        verify(categoryMapper).toEntity(newCategory);
    }
}