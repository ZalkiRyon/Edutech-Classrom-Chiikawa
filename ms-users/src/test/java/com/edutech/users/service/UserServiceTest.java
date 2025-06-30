package com.edutech.users.service;

import com.edutech.common.dto.UserDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.users.entity.User;
import com.edutech.users.mapper.UserMapperManual;
import com.edutech.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private UserMapperManual userMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setEmail("test@edutech.com");
        testUser.setPasswordHash("password123");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setRoleId(1);
        testUser.setIsActive(true);
        testUser.setCreatedAt(Instant.now());
        testUser.setUpdatedAt(Instant.now());

        testUserDTO = new UserDTO();
        testUserDTO.setId(1);
        testUserDTO.setEmail("test@edutech.com");
        testUserDTO.setPasswordHash("password123");
        testUserDTO.setFirstName("John");
        testUserDTO.setLastName("Doe");
        testUserDTO.setRoleId(1);
        testUserDTO.setIsActive(true);
        testUserDTO.setCreatedAt(Instant.now());
        testUserDTO.setUpdatedAt(Instant.now());
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        // Given
        List<User> users = Arrays.asList(testUser);
        
        when(userRepo.findAll()).thenReturn(users);
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // When
        List<UserDTO> result = userService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(testUserDTO.getEmail(), result.get(0).getEmail());
        verify(userRepo).findAll();
        verify(userMapper).toDTO(testUser);
    }

    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        // Given
        when(userRepo.findById(1)).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // When
        UserDTO result = userService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(testUserDTO.getEmail(), result.getEmail());
        verify(userRepo).findById(1);
        verify(userMapper).toDTO(testUser);
    }

    @Test
    void findById_WhenUserNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(999));
        verify(userRepo).findById(999);
        verify(userMapper, never()).toDTO(any(User.class));
    }

    @Test
    void findByEmail_WhenUserExists_ShouldReturnUser() {
        // Given
        when(userRepo.findByEmail("test@edutech.com")).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // When
        UserDTO result = userService.findByEmail("test@edutech.com");

        // Then
        assertNotNull(result);
        assertEquals(testUserDTO.getEmail(), result.getEmail());
        verify(userRepo).findByEmail("test@edutech.com");
        verify(userMapper).toDTO(testUser);
    }

    @Test
    void create_WithNewEmail_ShouldCreateAndReturnUser() {
        // Given
        when(userRepo.findByEmail("test@edutech.com")).thenReturn(Optional.empty());
        when(userMapper.toEntity(testUserDTO)).thenReturn(testUser);
        when(userRepo.save(testUser)).thenReturn(testUser);
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // When
        UserDTO result = userService.create(testUserDTO);

        // Then
        assertNotNull(result);
        assertEquals(testUserDTO.getEmail(), result.getEmail());
        verify(userRepo).findByEmail("test@edutech.com");
        verify(userMapper).toEntity(testUserDTO);
        verify(userRepo).save(testUser);
        verify(userMapper).toDTO(testUser);
    }

    @Test
    void create_WithExistingEmail_ShouldThrowResponseStatusException() {
        // Given
        when(userRepo.findByEmail("test@edutech.com")).thenReturn(Optional.of(testUser));

        // When & Then
        assertThrows(ResponseStatusException.class, () -> userService.create(testUserDTO));
        verify(userRepo).findByEmail("test@edutech.com");
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void update_WhenUserExistsAndEmailNotChanged_ShouldUpdateUser() {
        // Given
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setEmail("test@edutech.com");
        existingUser.setFirstName("John");

        when(userRepo.findById(1)).thenReturn(Optional.of(existingUser));
        when(userMapper.toEntity(testUserDTO)).thenReturn(testUser);
        when(userRepo.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // When
        UserDTO result = userService.update(1, testUserDTO);

        // Then
        assertNotNull(result);
        assertEquals(testUserDTO.getEmail(), result.getEmail());
        verify(userRepo).findById(1);
        verify(userMapper).toEntity(testUserDTO);
        verify(userRepo).save(any(User.class));
        verify(userMapper).toDTO(testUser);
    }

    @Test
    void update_WhenUserNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userService.update(999, testUserDTO));
        verify(userRepo).findById(999);
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void update_WhenEmailChangedToExistingEmail_ShouldThrowResponseStatusException() {
        // Given
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setEmail("old@edutech.com");

        UserDTO updateDTO = new UserDTO();
        updateDTO.setEmail("existing@edutech.com");
        updateDTO.setFirstName("Jane");

        when(userRepo.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepo.existsByEmail("existing@edutech.com")).thenReturn(true);

        // When & Then
        assertThrows(ResponseStatusException.class, () -> userService.update(1, updateDTO));
        verify(userRepo).findById(1);
        verify(userRepo).existsByEmail("existing@edutech.com");
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void delete_WhenUserExists_ShouldDeleteUser() {
        // Given
        when(userRepo.findById(1)).thenReturn(Optional.of(testUser));

        // When
        userService.delete(1);

        // Then
        verify(userRepo).findById(1);
        verify(userRepo).delete(testUser);
    }

    @Test
    void delete_WhenUserNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userService.delete(999));
        verify(userRepo).findById(999);
        verify(userRepo, never()).delete(any(User.class));
    }
}
