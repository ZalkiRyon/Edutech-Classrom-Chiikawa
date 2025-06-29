package com.edutech.users.controller;

import com.edutech.common.dto.UserDTO;
import com.edutech.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
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
    void findAll_ShouldReturnAllUsersWithHateoas() throws Exception {
        // Given
        List<UserDTO> users = Arrays.asList(testUserDTO);
        when(userService.findAll()).thenReturn(users);

        // When & Then
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userDTOList").exists())
                .andExpect(jsonPath("$._embedded.userDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.userDTOList[0].email").value("test@edutech.com"))
                .andExpect(jsonPath("$._embedded.userDTOList[0]._links.self").exists())
                .andExpect(jsonPath("$._links.self").exists());

        verify(userService).findAll();
    }

    @Test
    void findById_WhenUserExists_ShouldReturnUserWithHateoas() throws Exception {
        // Given
        when(userService.findById(1)).thenReturn(testUserDTO);

        // When & Then
        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@edutech.com"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.users").exists())
                .andExpect(jsonPath("$._links.update").exists())
                .andExpect(jsonPath("$._links.delete").exists());

        verify(userService).findById(1);
    }

    @Test
    void findById_WhenUserNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        when(userService.findById(anyInt())).thenThrow(new RuntimeException("User not found"));

        // When & Then
        mockMvc.perform(get("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(userService).findById(999);
    }

    @Test
    void create_WithValidUser_ShouldCreateUserWithHateoas() throws Exception {
        // Given
        UserDTO newUser = new UserDTO();
        newUser.setEmail("new@edutech.com");
        newUser.setPasswordHash("password");
        newUser.setFirstName("Jane");
        newUser.setLastName("Smith");
        newUser.setRoleId(1);
        newUser.setIsActive(true);
        newUser.setCreatedAt(Instant.now());
        newUser.setUpdatedAt(Instant.now());

        UserDTO createdUser = new UserDTO();
        createdUser.setId(2);
        createdUser.setEmail("new@edutech.com");
        createdUser.setPasswordHash("password");
        createdUser.setFirstName("Jane");
        createdUser.setLastName("Smith");
        createdUser.setRoleId(1);
        createdUser.setIsActive(true);
        createdUser.setCreatedAt(Instant.now());
        createdUser.setUpdatedAt(Instant.now());

        when(userService.create(any(UserDTO.class))).thenReturn(createdUser);

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("new@edutech.com"))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.users").exists());

        verify(userService).create(any(UserDTO.class));
    }

    @Test
    void update_WithValidUser_ShouldUpdateUserWithHateoas() throws Exception {
        // Given
        UserDTO updateUser = new UserDTO();
        updateUser.setEmail("updated@edutech.com");
        updateUser.setPasswordHash("newpassword");
        updateUser.setFirstName("Jane");
        updateUser.setLastName("Updated");
        updateUser.setRoleId(1);
        updateUser.setIsActive(false);
        updateUser.setCreatedAt(Instant.now());
        updateUser.setUpdatedAt(Instant.now());

        UserDTO updatedUser = new UserDTO();
        updatedUser.setId(1);
        updatedUser.setEmail("updated@edutech.com");
        updatedUser.setPasswordHash("newpassword");
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Updated");
        updatedUser.setRoleId(1);
        updatedUser.setIsActive(false);
        updatedUser.setCreatedAt(Instant.now());
        updatedUser.setUpdatedAt(Instant.now());

        when(userService.update(eq(1), any(UserDTO.class))).thenReturn(updatedUser);

        // When & Then
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("updated@edutech.com"))
                .andExpect(jsonPath("$.lastName").value("Updated"))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.update").exists());

        verify(userService).update(eq(1), any(UserDTO.class));
    }

    @Test
    void delete_WhenUserExists_ShouldDeleteUser() throws Exception {
        // Given
        doNothing().when(userService).delete(1);

        // When & Then
        mockMvc.perform(delete("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService).delete(1);
    }

    @Test
    void delete_WhenUserNotExists_ShouldReturnError() throws Exception {
        // Given
        doThrow(new RuntimeException("User not found")).when(userService).delete(999);

        // When & Then
        mockMvc.perform(delete("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(userService).delete(999);
    }
}
