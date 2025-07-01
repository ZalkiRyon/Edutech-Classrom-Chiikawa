package com.edutech.users.service;

import com.edutech.common.dto.RoleDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.users.entity.Role;
import com.edutech.users.mapper.RoleMapperManual;
import com.edutech.users.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepo;

    @Mock
    private RoleMapperManual roleMapper;

    @InjectMocks
    private RoleService roleService;

    private Role testRole;
    private RoleDTO testRoleDTO;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setId(1);
        testRole.setName("ADMIN");
        testRole.setDescription("Administrator role with full access");

        testRoleDTO = new RoleDTO();
        testRoleDTO.setId(1);
        testRoleDTO.setName("ADMIN");
        testRoleDTO.setDescription("Administrator role with full access");
    }

    @Test
    void findAll_ShouldReturnAllRoles() {
        // Arrange
        Role role2 = new Role();
        role2.setId(2);
        role2.setName("USER");
        role2.setDescription("Standard user role");
        
        List<Role> roles = Arrays.asList(testRole, role2);
        
        RoleDTO roleDTO2 = new RoleDTO();
        roleDTO2.setId(2);
        roleDTO2.setName("USER");
        roleDTO2.setDescription("Standard user role");
        
        List<RoleDTO> roleDTOs = Arrays.asList(testRoleDTO, roleDTO2);

        when(roleRepo.findAll()).thenReturn(roles);
        when(roleMapper.toDTO(testRole)).thenReturn(testRoleDTO);
        when(roleMapper.toDTO(role2)).thenReturn(roleDTO2);

        // Act
        List<RoleDTO> result = roleService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getName());
        assertEquals("USER", result.get(1).getName());
        verify(roleRepo).findAll();
        verify(roleMapper, times(2)).toDTO(any(Role.class));
    }

    @Test
    void findById_WhenRoleExists_ShouldReturnRole() {
        // Arrange
        when(roleRepo.findById(1)).thenReturn(Optional.of(testRole));
        when(roleMapper.toDTO(testRole)).thenReturn(testRoleDTO);

        // Act
        RoleDTO result = roleService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("ADMIN", result.getName());
        assertEquals("Administrator role with full access", result.getDescription());
        verify(roleRepo).findById(1);
        verify(roleMapper).toDTO(testRole);
    }

    @Test
    void findById_WhenRoleNotExists_ShouldThrowResourceNotFoundException() {
        // Arrange
        when(roleRepo.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> roleService.findById(999)
        );
        
        assertEquals("Rol no encontrado", exception.getMessage());
        verify(roleRepo).findById(999);
        verify(roleMapper, never()).toDTO(any());
    }

    @Test
    void create_WithValidData_ShouldCreateAndReturnRole() {
        // Arrange
        RoleDTO inputDTO = new RoleDTO();
        inputDTO.setName("INSTRUCTOR");
        inputDTO.setDescription("Instructor role for course management");

        Role inputRole = new Role();
        inputRole.setName("INSTRUCTOR");
        inputRole.setDescription("Instructor role for course management");

        Role savedRole = new Role();
        savedRole.setId(3);
        savedRole.setName("INSTRUCTOR");
        savedRole.setDescription("Instructor role for course management");

        RoleDTO expectedDTO = new RoleDTO();
        expectedDTO.setId(3);
        expectedDTO.setName("INSTRUCTOR");
        expectedDTO.setDescription("Instructor role for course management");

        when(roleMapper.toEntity(inputDTO)).thenReturn(inputRole);
        when(roleRepo.save(inputRole)).thenReturn(savedRole);
        when(roleMapper.toDTO(savedRole)).thenReturn(expectedDTO);

        // Act
        RoleDTO result = roleService.create(inputDTO);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals("INSTRUCTOR", result.getName());
        assertEquals("Instructor role for course management", result.getDescription());
        verify(roleMapper).toEntity(inputDTO);
        verify(roleRepo).save(inputRole);
        verify(roleMapper).toDTO(savedRole);
    }

    @Test
    void update_WhenRoleExists_ShouldUpdateAndReturnRole() {
        // Arrange
        RoleDTO updateDTO = new RoleDTO();
        updateDTO.setId(1);
        updateDTO.setName("SUPER_ADMIN");
        updateDTO.setDescription("Super administrator with enhanced privileges");

        Role updatedRole = new Role();
        updatedRole.setId(1);
        updatedRole.setName("SUPER_ADMIN");
        updatedRole.setDescription("Super administrator with enhanced privileges");

        RoleDTO expectedDTO = new RoleDTO();
        expectedDTO.setId(1);
        expectedDTO.setName("SUPER_ADMIN");
        expectedDTO.setDescription("Super administrator with enhanced privileges");

        when(roleRepo.findById(1)).thenReturn(Optional.of(testRole));
        when(roleMapper.toEntity(updateDTO)).thenReturn(updatedRole);
        when(roleRepo.save(updatedRole)).thenReturn(updatedRole);
        when(roleMapper.toDTO(updatedRole)).thenReturn(expectedDTO);

        // Act
        RoleDTO result = roleService.update(1, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("SUPER_ADMIN", result.getName());
        assertEquals("Super administrator with enhanced privileges", result.getDescription());
        verify(roleRepo).findById(1);
        verify(roleMapper).toEntity(updateDTO);
        verify(roleRepo).save(updatedRole);
        verify(roleMapper).toDTO(updatedRole);
    }

    @Test
    void update_WhenRoleNotExists_ShouldThrowResourceNotFoundException() {
        // Arrange
        RoleDTO updateDTO = new RoleDTO();
        updateDTO.setId(999);
        updateDTO.setName("NON_EXISTENT");
        updateDTO.setDescription("This role does not exist");

        when(roleRepo.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> roleService.update(999, updateDTO)
        );
        
        assertEquals("Rol no encontrado", exception.getMessage());
        verify(roleRepo).findById(999);
        verify(roleMapper, never()).toEntity(any());
        verify(roleRepo, never()).save(any());
    }

    @Test
    void delete_WhenRoleExists_ShouldDeleteRole() {
        // Arrange
        when(roleRepo.findById(1)).thenReturn(Optional.of(testRole));
        doNothing().when(roleRepo).delete(testRole);

        // Act
        roleService.delete(1);

        // Assert
        verify(roleRepo).findById(1);
        verify(roleRepo).delete(testRole);
    }

    @Test
    void delete_WhenRoleNotExists_ShouldThrowResourceNotFoundException() {
        // Arrange
        when(roleRepo.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> roleService.delete(999)
        );
        
        assertEquals("Rol no encontrado", exception.getMessage());
        verify(roleRepo).findById(999);
        verify(roleRepo, never()).delete(any());
    }
}
