package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fakestoreapi.clone.domain.entity.User;
import com.fakestoreapi.clone.infrastructure.persistence.entities.UserEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IUserRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private IUserRepository jpaUserRepository;

    @Mock
    private UserPersistenceMapper mapper;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        // Create User.Name
        User.Name userName = new User.Name();
        userName.setFirstname("John");
        userName.setLastname("Doe");

        // Create User.Geolocation
        User.Geolocation userGeo = new User.Geolocation();
        userGeo.setLat("40.7128");
        userGeo.setLon("-74.0060");

        // Create User.Address
        User.Address userAddress = new User.Address();
        userAddress.setCity("New York");
        userAddress.setStreet("Broadway");
        userAddress.setNumber(123);
        userAddress.setZipcode("10001");
        userAddress.setGeolocation(userGeo);

        // Create complete User
        user = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .username("johndoe")
                .password("password123")
                .name(userName)
                .address(userAddress)
                .phone("1234567890")
                .build();

        // Create UserEntity.Name
        UserEntity.Name userEntityName = UserEntity.Name.builder()
                .firstname("John")
                .lastname("Doe")
                .build();

        // Create UserEntity.Geolocation
        UserEntity.Geolocation userEntityGeo = UserEntity.Geolocation.builder()
                .lat("40.7128")
                .lon("-74.0060")
                .build();

        // Create UserEntity.Address
        UserEntity.Address userEntityAddress = UserEntity.Address.builder()
                .city("New York")
                .street("Broadway")
                .number(123)
                .zipcode("10001")
                .geolocation(userEntityGeo)
                .build();

        // Create complete UserEntity
        userEntity = UserEntity.builder()
                .email("john.doe@example.com")
                .username("johndoe")
                .password("password123")
                .name(userEntityName)
                .address(userEntityAddress)
                .phone("1234567890")
                .build();

        userEntity.setId(1L);
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        when(jpaUserRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(mapper.toDomain(userEntity)).thenReturn(user);

        // Act
        Optional<User> found = userRepository.findById(1L);

        // Assert
        assertAll(
                () -> assertTrue(found.isPresent()),
                () -> assertEquals(user.getId(), found.get().getId()),
                () -> assertEquals(user.getEmail(), found.get().getEmail()),
                () -> assertEquals(user.getName().getFirstname(), found.get().getName().getFirstname()),
                () -> assertEquals(user.getAddress().getCity(), found.get().getAddress().getCity()),
                () -> assertEquals(user.getAddress().getGeolocation().getLat(),
                        found.get().getAddress().getGeolocation().getLat()));

        verify(jpaUserRepository).findById(1L);
        verify(mapper).toDomain(userEntity);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenUserNotExists() {
        // Arrange
        when(jpaUserRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<User> found = userRepository.findById(99L);

        // Assert
        assertTrue(found.isEmpty());
        verify(jpaUserRepository).findById(99L);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        // Arrange
        List<UserEntity> entities = List.of(userEntity);
        when(jpaUserRepository.findAll()).thenReturn(entities);
        when(mapper.toDomain(userEntity)).thenReturn(user);

        // Act
        List<User> users = userRepository.findAll();

        // Assert
        assertAll(
                () -> assertFalse(users.isEmpty()),
                () -> assertEquals(1, users.size()),
                () -> assertEquals(user.getId(), users.get(0).getId()),
                () -> assertEquals(user.getEmail(), users.get(0).getEmail()));

        verify(jpaUserRepository).findAll();
        verify(mapper, times(1)).toDomain(any());
    }

    @Test
    void save_ShouldSaveUserSuccessfully() {
        // Arrange
        when(mapper.toEntity(user)).thenReturn(userEntity);
        when(jpaUserRepository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toDomain(userEntity)).thenReturn(user);

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertAll(
                () -> assertNotNull(savedUser),
                () -> assertEquals(user.getId(), savedUser.getId()),
                () -> assertEquals(user.getEmail(), savedUser.getEmail()),
                () -> assertEquals(user.getName().getFirstname(), savedUser.getName().getFirstname()),
                () -> assertEquals(user.getAddress().getCity(), savedUser.getAddress().getCity()));

        verify(jpaUserRepository).save(any(UserEntity.class));
        verify(mapper).toEntity(any(User.class));
        verify(mapper).toDomain(any(UserEntity.class));
    }

    @Test
    void update_ShouldUpdateUser_WhenUserExists() {
        // Arrange
        when(jpaUserRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));
        when(jpaUserRepository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toDomain(userEntity)).thenReturn(user);
        doNothing().when(mapper).updateEntity(user, userEntity);

        // Act
        User updated = userRepository.update(user);

        // Assert
        assertAll(
            () -> assertNotNull(updated),
            () -> assertEquals(user.getId(), updated.getId()),
            () -> assertEquals(user.getEmail(), updated.getEmail()),
            () -> assertEquals(user.getName().getFirstname(), updated.getName().getFirstname())    
        );
        
        verify(jpaUserRepository).findById(user.getId());
        verify(jpaUserRepository).save(userEntity);
        verify(mapper).updateEntity(user, userEntity);
        verify(mapper).toDomain(userEntity);
    }

    @Test
    void delete_ShouldDeleteUser() {
        // Arrange
        doNothing().when(jpaUserRepository).deleteById(1L);

        // Act
        userRepository.delete(1L);

        // Assert
        verify(jpaUserRepository).deleteById(1L);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenUserExists() {
        // Arrange
        when(jpaUserRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean exists = userRepository.existsById(1L);

        // Assert
        assertTrue(exists);
        verify(jpaUserRepository).existsById(1L);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenUserNotExists() {
        // Arrange
        when(jpaUserRepository.existsById(99L)).thenReturn(false);

        // Act
        boolean exists = userRepository.existsById(99L);

        // Assert
        assertFalse(exists);
        verify(jpaUserRepository).existsById(99L);
    }
}
