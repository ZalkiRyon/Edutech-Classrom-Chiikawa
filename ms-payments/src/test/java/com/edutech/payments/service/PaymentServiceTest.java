package com.edutech.payments.service;

import com.edutech.common.dto.PaymentDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.payments.client.UserClient;
import com.edutech.payments.entity.Payment;
import com.edutech.payments.mapper.PaymentMapper;
import com.edutech.payments.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;
    private PaymentDTO paymentDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setId(1);
        payment.setUserId(1);
        payment.setAmount(new BigDecimal("100.00"));
        payment.setPaymentDate(Instant.now());
        payment.setPaymentMethod("Tarjeta de Crédito");
        payment.setPaymentInstitution("Banco Test");
        payment.setTransactionId("TXN123456");
        payment.setStatus("COMPLETADO");

        paymentDTO = new PaymentDTO();
        paymentDTO.setId(1);
        paymentDTO.setUserId(1);
        paymentDTO.setAmount(new BigDecimal("100.00"));
        paymentDTO.setPaymentDate(Instant.now());
        paymentDTO.setPaymentMethod("Tarjeta de Crédito");
        paymentDTO.setPaymentInstitution("Banco Test");
        paymentDTO.setTransactionId("TXN123456");
        paymentDTO.setStatus("COMPLETADO");

        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Juan");
        userDTO.setLastName("Pérez");
        userDTO.setEmail("test@example.com");
        userDTO.setRoleId(2);
        userDTO.setIsActive(true);
        userDTO.setCreatedAt(Instant.now());
        userDTO.setUpdatedAt(Instant.now());
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        List<Payment> payments = Arrays.asList(payment);
        when(paymentRepository.findAll()).thenReturn(payments);
        when(paymentMapper.toDTO(payment)).thenReturn(paymentDTO);

        // Act
        List<PaymentDTO> result = paymentService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(paymentDTO.getId(), result.get(0).getId());
        verify(paymentRepository).findAll();
        verify(paymentMapper).toDTO(payment);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDTO(payment)).thenReturn(paymentDTO);

        // Act
        PaymentDTO result = paymentService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(paymentDTO.getId(), result.getId());
        assertEquals(paymentDTO.getAmount(), result.getAmount());
        verify(paymentRepository).findById(1);
        verify(paymentMapper).toDTO(payment);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(paymentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> paymentService.findById(999));
        verify(paymentRepository).findById(999);
        verify(paymentMapper, never()).toDTO(any());
    }

    @Test
    void testFindByUserId_Success() {
        // Arrange
        List<Payment> payments = Arrays.asList(payment);
        when(paymentRepository.findByUserId(1)).thenReturn(payments);
        when(paymentMapper.toDTO(payment)).thenReturn(paymentDTO);

        // Act
        List<PaymentDTO> result = paymentService.findByUserId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(paymentDTO.getUserId(), result.get(0).getUserId());
        verify(paymentRepository).findByUserId(1);
        verify(paymentMapper).toDTO(payment);
    }

    @Test
    void testCreate_Success() {
        // Arrange
        when(userClient.findById(1)).thenReturn(userDTO);
        when(paymentMapper.toEntity(paymentDTO)).thenReturn(payment);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(paymentMapper.toDTO(payment)).thenReturn(paymentDTO);

        // Act
        PaymentDTO result = paymentService.create(paymentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(paymentDTO.getId(), result.getId());
        verify(userClient).findById(1);
        verify(paymentRepository).save(any(Payment.class));
        verify(paymentMapper).toEntity(paymentDTO);
        verify(paymentMapper).toDTO(payment);
    }

    @Test
    void testCreate_UserNotFound() {
        // Arrange
        when(userClient.findById(1)).thenThrow(new ResourceNotFoundException("Usuario no encontrado"));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> paymentService.create(paymentDTO));
        verify(userClient).findById(1);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));
        when(userClient.findById(1)).thenReturn(userDTO);
        when(paymentMapper.toEntity(paymentDTO)).thenReturn(payment);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(paymentMapper.toDTO(payment)).thenReturn(paymentDTO);

        // Act
        PaymentDTO result = paymentService.update(1, paymentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(paymentDTO.getId(), result.getId());
        verify(paymentRepository).findById(1);
        verify(userClient).findById(1);
        verify(paymentRepository).save(any(Payment.class));
        verify(paymentMapper).toEntity(paymentDTO);
        verify(paymentMapper).toDTO(payment);
    }

    @Test
    void testUpdate_PaymentNotFound() {
        // Arrange
        when(paymentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> paymentService.update(999, paymentDTO));
        verify(paymentRepository).findById(999);
        verify(userClient, never()).findById(anyInt());
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void testUpdate_UserNotFound() {
        // Arrange
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));
        when(userClient.findById(1)).thenThrow(new ResourceNotFoundException("Usuario no encontrado"));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> paymentService.update(1, paymentDTO));
        verify(paymentRepository).findById(1);
        verify(userClient).findById(1);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void testDelete_Success() {
        // Arrange
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        // Act
        paymentService.delete(1);

        // Assert
        verify(paymentRepository).findById(1);
        verify(paymentRepository).delete(payment);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(paymentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> paymentService.delete(999));
        verify(paymentRepository).findById(999);
        verify(paymentRepository, never()).delete(any());
    }
}
