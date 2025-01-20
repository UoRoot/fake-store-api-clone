package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fakestoreapi.clone.domain.entity.Cart;
import com.fakestoreapi.clone.domain.entity.CartItem;
import com.fakestoreapi.clone.domain.entity.User;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CartEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CartItemEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.UserEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.CartPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICartItemRepository;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICartRepository;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IUserRepository;

@ExtendWith(MockitoExtension.class)
class CartRepositoryImplTest {

    @Mock
    private ICartRepository jpaCartRepository;

    @Mock
    private ICartItemRepository jpaCartItemRepository;

    @Mock
    private IUserRepository jpaUserRepository;

    @Mock
    private CartPersistenceMapper mapper;

    @InjectMocks
    private CartRepositoryImpl cartRepository;

    private Cart cart;
    private CartEntity cartEntity;
    private User user;
    private UserEntity userEntity;
    private CartItem cartItem;
    private CartItemEntity cartItemEntity;
    private List<CartItem> cartItems;
    private List<CartItemEntity> cartItemEntities;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .build();

        userEntity = UserEntity.builder()
                .email("test@test.com")
                .build();
        userEntity.setId(1L);

        cartItem = CartItem.builder()
                .id(1L)
                .quantity(2)
                .build();

        cartItemEntity = CartItemEntity.builder()
                .quantity(2)
                .build();
        cartItemEntity.setId(1L);

        cartItems = List.of(cartItem);
        cartItemEntities = List.of(cartItemEntity);

        cart = Cart.builder()
                .id(1L)
                .user(user)
                .date(LocalDate.now())
                .products(cartItems)
                .build();

        cartEntity = CartEntity.builder()
                .user(userEntity)
                .date(LocalDate.now())
                .products(cartItemEntities)
                .build();
        cartEntity.setId(1L);
    }

    @Test
    void findById_ShouldReturnCart_WhenExists() {
        // Arrange
        when(jpaCartRepository.findById(1L)).thenReturn(Optional.of(cartEntity));
        when(mapper.toDomain(cartEntity)).thenReturn(cart);

        // Act
        Optional<Cart> result = cartRepository.findById(1L);

        // Assert
        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(cart.getId(), result.get().getId()),
                () -> assertEquals(cart.getDate(), result.get().getDate()),
                () -> assertEquals(cart.getUser().getId(), result.get().getUser().getId()),
                () -> assertEquals(cart.getProducts().size(), result.get().getProducts().size()));

        verify(jpaCartRepository).findById(1L);
        verify(mapper).toDomain(cartEntity);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(jpaCartRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Cart> result = cartRepository.findById(1L);

        // Assert
        assertAll(
                () -> assertFalse(result.isPresent()),
                () -> verify(jpaCartRepository).findById(1L),
                () -> verify(mapper, never()).toDomain(any()));
    }

    @Test
    void findAll_ShouldReturnAllCarts() {
        // Arrange
        List<CartEntity> entities = List.of(cartEntity);
        when(jpaCartRepository.findAll()).thenReturn(entities);
        when(mapper.toDomain(cartEntity)).thenReturn(cart);

        // Act
        List<Cart> result = cartRepository.findAll();

        // Assert
        assertAll(
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(1, result.size()),
                () -> assertEquals(cart.getId(), result.get(0).getId()),
                () -> assertEquals(cart.getDate(), result.get(0).getDate()),
                () -> assertEquals(cart.getUser().getId(), result.get(0).getUser().getId()),
                () -> assertEquals(cart.getProducts().size(), result.get(0).getProducts().size()));

        verify(jpaCartRepository).findAll();
        verify(mapper).toDomain(cartEntity);
    }

    @Test
    void save_ShouldSaveCartSuccessfully() {
        // Arrange
        when(mapper.toEntity(cart)).thenReturn(cartEntity);
        when(jpaUserRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));
        when(jpaCartItemRepository.findAllById(List.of(1L))).thenReturn(cartItemEntities);
        when(jpaCartRepository.save(cartEntity)).thenReturn(cartEntity);
        when(mapper.toDomain(cartEntity)).thenReturn(cart);

        // Act
        Cart result = cartRepository.save(cart);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(cart.getId(), result.getId()),
                () -> assertEquals(cart.getDate(), result.getDate()),
                () -> assertEquals(cart.getUser().getId(), result.getUser().getId()),
                () -> assertEquals(cart.getProducts().size(), result.getProducts().size()));

        verify(mapper).toEntity(cart);
        verify(jpaUserRepository).findById(user.getId());
        verify(jpaCartItemRepository).findAllById(List.of(1L));
        verify(jpaCartRepository).save(cartEntity);
        verify(mapper).toDomain(cartEntity);
    }

    @Test
    void save_ShouldHandleEmptyProductsList() {
        // Arrange
        Cart cartWithoutProducts = Cart.builder()
                .id(1L)
                .user(user)
                .date(LocalDate.now())
                .products(new ArrayList<>())
                .build();

        CartEntity cartEntityWithoutProducts = CartEntity.builder()
                .user(userEntity)
                .date(LocalDate.now())
                .products(new ArrayList<>())
                .build();
        cartEntityWithoutProducts.setId(1L);

        when(mapper.toEntity(cartWithoutProducts)).thenReturn(cartEntityWithoutProducts);
        when(jpaUserRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));
        when(jpaCartItemRepository.findAllById(List.of())).thenReturn(new ArrayList<>());
        when(jpaCartRepository.save(cartEntityWithoutProducts)).thenReturn(cartEntityWithoutProducts);
        when(mapper.toDomain(cartEntityWithoutProducts)).thenReturn(cartWithoutProducts);

        // Act
        Cart result = cartRepository.save(cartWithoutProducts);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(cartWithoutProducts.getId(), result.getId()),
                () -> assertEquals(cartWithoutProducts.getDate(), result.getDate()),
                () -> assertEquals(cartWithoutProducts.getUser().getId(), result.getUser().getId()),
                () -> assertTrue(result.getProducts().isEmpty()));

        verify(mapper).toEntity(cartWithoutProducts);
        verify(jpaUserRepository).findById(user.getId());
        verify(jpaCartItemRepository).findAllById(List.of());
        verify(jpaCartRepository).save(cartEntityWithoutProducts);
        verify(mapper).toDomain(cartEntityWithoutProducts);
    }
}