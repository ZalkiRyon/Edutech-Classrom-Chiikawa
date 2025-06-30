package com.edutech.payments.controller;

import com.edutech.common.dto.PaymentDTO;
import com.edutech.payments.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private ObjectMapper objectMapper;
    private PaymentDTO paymentDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        paymentDTO = new PaymentDTO();
        paymentDTO.setId(1);
        paymentDTO.setUserId(1);
        paymentDTO.setAmount(new BigDecimal("100.00"));
        paymentDTO.setPaymentDate(Instant.now());
        paymentDTO.setPaymentMethod("Tarjeta de Crédito");
        paymentDTO.setPaymentInstitution("Banco Test");
        paymentDTO.setTransactionId("TXN123456");
        paymentDTO.setStatus("COMPLETADO");
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange
        List<PaymentDTO> payments = Arrays.asList(paymentDTO);
        when(paymentService.findAll()).thenReturn(payments);

        // Act & Assert
        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.paymentDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.paymentDTOList[0].amount").value(100.00))
                .andExpect(jsonPath("$._embedded.paymentDTOList[0].paymentMethod").value("Tarjeta de Crédito"))
                .andExpect(jsonPath("$._embedded.paymentDTOList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(paymentService).findAll();
    }

    @Test
    void testFindById() throws Exception {
        // Arrange
        when(paymentService.findById(1)).thenReturn(paymentDTO);

        // Act & Assert
        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.paymentMethod").value("Tarjeta de Crédito"))
                .andExpect(jsonPath("$.status").value("COMPLETADO"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-payments.href").exists())
                .andExpect(jsonPath("$._links.user-payments.href").exists());

        verify(paymentService).findById(1);
    }

    @Test
    void testFindByUserId() throws Exception {
        // Arrange
        List<PaymentDTO> payments = Arrays.asList(paymentDTO);
        when(paymentService.findByUserId(1)).thenReturn(payments);

        // Act & Assert
        mockMvc.perform(get("/api/payments/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.paymentDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.paymentDTOList[0].userId").value(1))
                .andExpect(jsonPath("$._embedded.paymentDTOList[0].amount").value(100.00))
                .andExpect(jsonPath("$._embedded.paymentDTOList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(paymentService).findByUserId(1);
    }

    @Test
    void testCreate() throws Exception {
        // Arrange
        when(paymentService.create(any(PaymentDTO.class))).thenReturn(paymentDTO);

        // Act & Assert
        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isOk()) // El controlador devuelve 200, no 201
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.paymentMethod").value("Tarjeta de Crédito"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-payments.href").exists());

        verify(paymentService).create(any(PaymentDTO.class));
    }

    @Test
    void testUpdate() throws Exception {
        // Arrange
        when(paymentService.update(anyInt(), any(PaymentDTO.class))).thenReturn(paymentDTO);

        // Act & Assert
        mockMvc.perform(put("/api/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.paymentMethod").value("Tarjeta de Crédito"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-payments.href").exists());

        verify(paymentService).update(eq(1), any(PaymentDTO.class));
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        doNothing().when(paymentService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/payments/1"))
                .andExpect(status().isNoContent());

        verify(paymentService).delete(1);
    }
}
