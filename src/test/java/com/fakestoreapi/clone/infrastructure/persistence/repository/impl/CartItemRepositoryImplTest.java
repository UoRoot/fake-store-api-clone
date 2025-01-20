package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fakestoreapi.clone.domain.entity.CartItem;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CartItemEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.CartItemPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICartItemRepository;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IProductRepository;

@ExtendWith(MockitoExtension.class)
class CartItemRepositoryImplTest {

    @Mock
    private ICartItemRepository jpaCartItemRepository;
    
    @Mock
    private IProductRepository jpaProductRepository;
    
    @Mock
    private CartItemPersistenceMapper mapper;
    
    @InjectMocks
    private CartItemRepositoryImpl cartItemRepository;
    
    private CartItem cartItem;
    private CartItemEntity cartItemEntity;
    private Product product;
    private ProductEntity productEntity;
    
    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .title("Test Product")
                .price(BigDecimal.TEN)
                .build();
                
        productEntity = ProductEntity.builder()
                .title("Test Product")
                .price(BigDecimal.TEN)
                .build();
        productEntity.setId(1L);

        cartItem = CartItem.builder()
                .id(1L)
                .product(product)
                .quantity(2)
                .build();
        
        cartItemEntity = CartItemEntity.builder()
                .product(productEntity)
                .quantity(2)
                .build();
        cartItemEntity.setId(1L);
    }

    @Test
    void findById_ShouldReturnCartItem_WhenExists() {
        // Arrange
        when(jpaCartItemRepository.findById(1L)).thenReturn(Optional.of(cartItemEntity));
        when(mapper.toDomain(cartItemEntity)).thenReturn(cartItem);

        // Act
        Optional<CartItem> result = cartItemRepository.findById(1L);

        // Assert
        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertEquals(cartItem.getId(), result.get().getId()),
            () -> assertEquals(cartItem.getQuantity(), result.get().getQuantity()),
            () -> assertEquals(cartItem.getProduct().getId(), result.get().getProduct().getId())
        );
        
        verify(jpaCartItemRepository).findById(1L);
        verify(mapper).toDomain(cartItemEntity);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(jpaCartItemRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<CartItem> result = cartItemRepository.findById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(jpaCartItemRepository).findById(1L);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void findAll_ShouldReturnAllCartItems() {
        // Arrange
        List<CartItemEntity> entities = List.of(cartItemEntity);
        when(jpaCartItemRepository.findAll()).thenReturn(entities);
        when(mapper.toDomain(cartItemEntity)).thenReturn(cartItem);

        // Act
        List<CartItem> result = cartItemRepository.findAll();

        // Assert
        assertAll(
            () -> assertFalse(result.isEmpty()),
            () -> assertEquals(1, result.size()),
            () -> assertEquals(cartItem.getId(), result.get(0).getId()),
            () -> assertEquals(cartItem.getQuantity(), result.get(0).getQuantity()),
            () -> assertEquals(cartItem.getProduct().getId(), result.get(0).getProduct().getId())
        );
        
        verify(jpaCartItemRepository).findAll();
        verify(mapper).toDomain(cartItemEntity);
    }

    @Test
    void save_ShouldSaveCartItemSuccessfully() {
        // Arrange
        when(mapper.toEntity(cartItem)).thenReturn(cartItemEntity);
        when(jpaProductRepository.findById(product.getId())).thenReturn(Optional.of(productEntity));
        when(jpaCartItemRepository.save(cartItemEntity)).thenReturn(cartItemEntity);
        when(mapper.toDomain(cartItemEntity)).thenReturn(cartItem);

        // Act
        CartItem result = cartItemRepository.save(cartItem);

        // Assert
        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(cartItem.getId(), result.getId()),
            () -> assertEquals(cartItem.getQuantity(), result.getQuantity()),
            () -> assertEquals(cartItem.getProduct().getId(), result.getProduct().getId())
        );
        
        verify(mapper).toEntity(cartItem);
        verify(jpaProductRepository).findById(product.getId());
        verify(jpaCartItemRepository).save(cartItemEntity);
        verify(mapper).toDomain(cartItemEntity);
    }

    @Test
    void saveAll_ShouldSaveAllCartItemsSuccessfully() {
        // Arrange
        List<CartItem> cartItems = List.of(cartItem);
        List<CartItemEntity> cartItemEntities = List.of(cartItemEntity);
        List<ProductEntity> productEntities = List.of(productEntity);
        
        when(mapper.toEntity(cartItem)).thenReturn(cartItemEntity);
        when(jpaProductRepository.findAllById(List.of(product.getId()))).thenReturn(productEntities);
        when(jpaCartItemRepository.saveAll(anyList())).thenReturn(cartItemEntities);
        when(mapper.toDomain(cartItemEntity)).thenReturn(cartItem);

        // Act
        List<CartItem> result = cartItemRepository.saveAll(cartItems);

        // Assert
        assertAll(
            () -> assertFalse(result.isEmpty()),
            () -> assertEquals(1, result.size()),
            () -> assertEquals(cartItem.getId(), result.get(0).getId()),
            () -> assertEquals(cartItem.getQuantity(), result.get(0).getQuantity()),
            () -> assertEquals(cartItem.getProduct().getId(), result.get(0).getProduct().getId())
        );
        
        verify(mapper).toEntity(cartItem);
        verify(jpaProductRepository).findAllById(List.of(product.getId()));
        verify(jpaCartItemRepository).saveAll(anyList());
        verify(mapper).toDomain(cartItemEntity);
    }
}
