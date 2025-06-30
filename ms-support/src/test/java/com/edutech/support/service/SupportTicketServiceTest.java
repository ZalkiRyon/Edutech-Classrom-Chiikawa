package com.edutech.support.service;

import com.edutech.common.dto.SupportTicketDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.support.client.UserClient;
import com.edutech.support.entity.SupportTicket;
import com.edutech.support.mapper.SupportTicketMapper;
import com.edutech.support.repository.SupportTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportTicketServiceTest {

    @Mock
    private SupportTicketRepository supportTicketRepository;

    @Mock
    private SupportTicketMapper supportTicketMapper;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private SupportTicketService supportTicketService;

    private SupportTicket supportTicket;
    private SupportTicketDTO supportTicketDTO;
    private UserDTO userDTO;
    private UserDTO supportUserDTO;

    @BeforeEach
    void setUp() {
        supportTicket = new SupportTicket();
        supportTicket.setId(1);
        supportTicket.setUserId(1);
        supportTicket.setSupportUserId(2);
        supportTicket.setSubject("Problema con el acceso al curso");
        supportTicket.setDescription("No puedo acceder al contenido del curso después del pago");
        supportTicket.setStatus("ABIERTO");
        supportTicket.setCreatedAt(Instant.now());

        supportTicketDTO = new SupportTicketDTO();
        supportTicketDTO.setId(1);
        supportTicketDTO.setUserId(1);
        supportTicketDTO.setSupportUserId(2);
        supportTicketDTO.setSubject("Problema con el acceso al curso");
        supportTicketDTO.setDescription("No puedo acceder al contenido del curso después del pago");
        supportTicketDTO.setStatus("ABIERTO");
        supportTicketDTO.setCreatedAt(Instant.now());

        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setEmail("usuario1@example.com");

        supportUserDTO = new UserDTO();
        supportUserDTO.setId(2);
        supportUserDTO.setEmail("soporte1@example.com");
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        List<SupportTicket> tickets = Arrays.asList(supportTicket);
        when(supportTicketRepository.findAll()).thenReturn(tickets);
        when(supportTicketMapper.toDTO(supportTicket)).thenReturn(supportTicketDTO);

        // Act
        List<SupportTicketDTO> result = supportTicketService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(supportTicketDTO.getId(), result.get(0).getId());
        assertEquals(supportTicketDTO.getSubject(), result.get(0).getSubject());
        verify(supportTicketRepository).findAll();
        verify(supportTicketMapper).toDTO(supportTicket);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(supportTicketRepository.findById(1)).thenReturn(Optional.of(supportTicket));
        when(supportTicketMapper.toDTO(supportTicket)).thenReturn(supportTicketDTO);

        // Act
        SupportTicketDTO result = supportTicketService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(supportTicketDTO.getId(), result.getId());
        assertEquals(supportTicketDTO.getSubject(), result.getSubject());
        assertEquals(supportTicketDTO.getStatus(), result.getStatus());
        verify(supportTicketRepository).findById(1);
        verify(supportTicketMapper).toDTO(supportTicket);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(supportTicketRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> supportTicketService.findById(999));
        verify(supportTicketRepository).findById(999);
        verify(supportTicketMapper, never()).toDTO(any());
    }

    @Test
    void testFindByUserId_Success() {
        // Arrange
        List<SupportTicket> tickets = Arrays.asList(supportTicket);
        when(supportTicketRepository.findByUserId(1)).thenReturn(tickets);
        when(supportTicketMapper.toDTO(supportTicket)).thenReturn(supportTicketDTO);

        // Act
        List<SupportTicketDTO> result = supportTicketService.findByUserId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(supportTicketDTO.getUserId(), result.get(0).getUserId());
        verify(supportTicketRepository).findByUserId(1);
        verify(supportTicketMapper).toDTO(supportTicket);
    }

    @Test
    void testFindBySupportUserId_Success() {
        // Arrange
        List<SupportTicket> tickets = Arrays.asList(supportTicket);
        when(supportTicketRepository.findBySupportUserId(2)).thenReturn(tickets);
        when(supportTicketMapper.toDTO(supportTicket)).thenReturn(supportTicketDTO);

        // Act
        List<SupportTicketDTO> result = supportTicketService.findBySupportUserId(2);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(supportTicketDTO.getSupportUserId(), result.get(0).getSupportUserId());
        verify(supportTicketRepository).findBySupportUserId(2);
        verify(supportTicketMapper).toDTO(supportTicket);
    }

    @Test
    void testCreate_Success() {
        // Arrange
        when(userClient.findById(1)).thenReturn(userDTO);
        when(userClient.findById(2)).thenReturn(supportUserDTO);
        when(supportTicketMapper.toEntity(supportTicketDTO)).thenReturn(supportTicket);
        when(supportTicketRepository.save(any(SupportTicket.class))).thenReturn(supportTicket);
        when(supportTicketMapper.toDTO(supportTicket)).thenReturn(supportTicketDTO);

        // Act
        SupportTicketDTO result = supportTicketService.create(supportTicketDTO);

        // Assert
        assertNotNull(result);
        assertEquals(supportTicketDTO.getId(), result.getId());
        assertEquals(supportTicketDTO.getSubject(), result.getSubject());
        verify(userClient).findById(1);
        verify(userClient).findById(2);
        verify(supportTicketRepository).save(any(SupportTicket.class));
        verify(supportTicketMapper).toEntity(supportTicketDTO);
        verify(supportTicketMapper).toDTO(supportTicket);
    }

    @Test
    void testCreate_WithoutSupportUser_Success() {
        // Arrange
        supportTicketDTO.setSupportUserId(null);
        when(userClient.findById(1)).thenReturn(userDTO);
        when(supportTicketMapper.toEntity(supportTicketDTO)).thenReturn(supportTicket);
        when(supportTicketRepository.save(any(SupportTicket.class))).thenReturn(supportTicket);
        when(supportTicketMapper.toDTO(supportTicket)).thenReturn(supportTicketDTO);

        // Act
        SupportTicketDTO result = supportTicketService.create(supportTicketDTO);

        // Assert
        assertNotNull(result);
        assertEquals(supportTicketDTO.getId(), result.getId());
        verify(userClient).findById(1);
        verify(userClient, never()).findById(2);
        verify(supportTicketRepository).save(any(SupportTicket.class));
    }

    @Test
    void testCreate_UserNotFound() {
        // Arrange
        when(userClient.findById(1)).thenThrow(new ResourceNotFoundException("Usuario no encontrado"));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> supportTicketService.create(supportTicketDTO));
        verify(userClient).findById(1);
        verify(supportTicketRepository, never()).save(any());
    }

    @Test
    void testCreate_SupportUserNotFound() {
        // Arrange
        when(userClient.findById(1)).thenReturn(userDTO);
        when(userClient.findById(2)).thenThrow(new ResourceNotFoundException("Usuario de soporte no encontrado"));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> supportTicketService.create(supportTicketDTO));
        verify(userClient).findById(1);
        verify(userClient).findById(2);
        verify(supportTicketRepository, never()).save(any());
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        when(supportTicketRepository.findById(1)).thenReturn(Optional.of(supportTicket));
        when(userClient.findById(1)).thenReturn(userDTO);
        when(userClient.findById(2)).thenReturn(supportUserDTO);
        when(supportTicketMapper.toEntity(supportTicketDTO)).thenReturn(supportTicket);
        when(supportTicketRepository.save(any(SupportTicket.class))).thenReturn(supportTicket);
        when(supportTicketMapper.toDTO(supportTicket)).thenReturn(supportTicketDTO);

        // Act
        SupportTicketDTO result = supportTicketService.update(1, supportTicketDTO);

        // Assert
        assertNotNull(result);
        assertEquals(supportTicketDTO.getId(), result.getId());
        verify(supportTicketRepository).findById(1);
        verify(userClient).findById(1);
        verify(userClient).findById(2);
        verify(supportTicketRepository).save(any(SupportTicket.class));
    }

    @Test
    void testUpdate_TicketNotFound() {
        // Arrange
        when(supportTicketRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> supportTicketService.update(999, supportTicketDTO));
        verify(supportTicketRepository).findById(999);
        verify(userClient, never()).findById(anyInt());
        verify(supportTicketRepository, never()).save(any());
    }

    @Test
    void testDelete_Success() {
        // Arrange
        when(supportTicketRepository.findById(1)).thenReturn(Optional.of(supportTicket));

        // Act
        supportTicketService.delete(1);

        // Assert
        verify(supportTicketRepository).findById(1);
        verify(supportTicketRepository).delete(supportTicket);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(supportTicketRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> supportTicketService.delete(999));
        verify(supportTicketRepository).findById(999);
        verify(supportTicketRepository, never()).delete(any());
    }
}
