package com.fakestoreapi.clone.infrastructure.persistence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fakestoreapi.clone.domain.entity.Category;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CategoryEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.ProductPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.impl.ProductRepositoryImpl;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    @Mock
    private IProductRepository jpaProductRepository;

    @Mock
    private ProductPersistenceMapper mapper;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    private Product product;
    private ProductEntity productEntity;
    private Category category;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1)
                .name("Electronics")
                .build();

        categoryEntity = CategoryEntity.builder()
                .id(1)
                .name("Electronics")
                .build();

        product = Product.builder()
                .id(1L)
                .title("Smartphone")
                .price(new BigDecimal("999.99"))
                .description("Latest model")
                .image("phone.jpg")
                .category(category)
                .build();

        productEntity = ProductEntity.builder()
                .title("Smartphone")
                .price(new BigDecimal("999.99"))
                .description("Latest model")
                .image("phone.jpg")
                .category(categoryEntity)
                .build();
        productEntity.setId(1L);
    }

    @Test
    void save_ShouldSaveProductSuccessfully() {
        // Arrange
        when(mapper.toEntity(product)).thenReturn(productEntity);
        when(jpaProductRepository.save(productEntity)).thenReturn(productEntity);
        when(mapper.toDomain(productEntity)).thenReturn(product);

        // Act
        Product savedProduct = productRepository.save(product);

        // Assert
        assertAll(
                () -> assertNotNull(savedProduct),
                () -> assertEquals(product.getId(), savedProduct.getId()),
                () -> assertEquals(product.getTitle(), savedProduct.getTitle()));

        verify(jpaProductRepository).save(any(ProductEntity.class));
        verify(mapper).toEntity(any(Product.class));
        verify(mapper).toDomain(any(ProductEntity.class));
    }

    @Test
    void findById_ShouldReturnProduct_WhenProductExists() {
        // Arrange
        when(jpaProductRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        when(mapper.toDomain(productEntity)).thenReturn(product);

        // Act
        Optional<Product> found = productRepository.findById(1L);

        // Assert
        assertAll(
                () -> assertTrue(found.isPresent()),
                () -> assertEquals(product.getId(), found.get().getId()));

        verify(jpaProductRepository).findById(1L);
        verify(mapper).toDomain(productEntity);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenProductNotExists() {
        // Arrange
        when(jpaProductRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Product> found = productRepository.findById(99L);

        // Assert
        assertTrue(found.isEmpty());
        verify(jpaProductRepository).findById(99L);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void findAll_ShouldReturnAllProducts() {
        // Arrange
        List<ProductEntity> entities = List.of(productEntity);
        when(jpaProductRepository.findAll()).thenReturn(entities);
        when(mapper.toDomain(productEntity)).thenReturn(product);

        // Act
        List<Product> products = productRepository.findAll();

        // Assert
        assertAll(
                () -> assertFalse(products.isEmpty()),
                () -> assertEquals(1, products.size()),
                () -> assertEquals(product.getId(), products.get(0).getId()));

        verify(jpaProductRepository).findAll();
        verify(mapper, times(1)).toDomain(any());
    }

    @Test
    void update_ShouldUpdateProduct_WhenProductExists() {
        // Arrange
        when(jpaProductRepository.findById(product.getId())).thenReturn(Optional.of(productEntity));
        when(jpaProductRepository.save(productEntity)).thenReturn(productEntity);
        when(mapper.toDomain(productEntity)).thenReturn(product);
        doNothing().when(mapper).updateEntity(product, productEntity);

        // Act
        Product updated = productRepository.update(product);

        // Assert
        assertAll(
                () -> assertNotNull(updated),
                () -> assertEquals(product.getId(), updated.getId()));

        verify(jpaProductRepository).findById(product.getId());
        verify(jpaProductRepository).save(productEntity);
        verify(mapper).updateEntity(product, productEntity);
        verify(mapper).toDomain(productEntity);
    }

    @Test
    void delete_ShouldDeleteProduct() {
        // Arrange
        doNothing().when(jpaProductRepository).deleteById(1L);

        // Act
        productRepository.delete(1L);

        // Assert
        verify(jpaProductRepository).deleteById(1L);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenProductExists() {
        // Arrange
        when(jpaProductRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean exists = productRepository.existsById(1L);

        // Assert
        assertTrue(exists);
        verify(jpaProductRepository).existsById(1L);
    }

    @Test
    void findByCategoryName_ShouldReturnProducts() {
        // Arrange
        List<ProductEntity> entities = List.of(productEntity);
        when(jpaProductRepository.findByCategoryName("Electronics")).thenReturn(entities);
        when(mapper.toDomain(productEntity)).thenReturn(product);

        // Act
        List<Product> products = productRepository.findByCategoryName("Electronics");

        // Assert
        assertAll(
                () -> assertFalse(products.isEmpty()),
                () -> assertEquals(1, products.size()),
                () -> assertEquals("Electronics", products.get(0).getCategory().getName()));

        verify(jpaProductRepository).findByCategoryName("Electronics");
        verify(mapper, times(1)).toDomain(any());
    }
}