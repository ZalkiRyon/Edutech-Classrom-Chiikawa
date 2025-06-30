package com.edutech.support.controller;

import com.edutech.common.dto.SupportTicketDTO;
import com.edutech.support.service.SupportTicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupportTicketController.class)
class SupportTicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupportTicketService supportTicketService;

    private ObjectMapper objectMapper;
    private SupportTicketDTO supportTicketDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        supportTicketDTO = new SupportTicketDTO();
        supportTicketDTO.setId(1);
        supportTicketDTO.setUserId(1);
        supportTicketDTO.setSupportUserId(2);
        supportTicketDTO.setSubject("Problema con el acceso al curso");
        supportTicketDTO.setDescription("No puedo acceder al contenido del curso después del pago");
        supportTicketDTO.setStatus("ABIERTO");
        supportTicketDTO.setCreatedAt(Instant.now());
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange
        List<SupportTicketDTO> tickets = Arrays.asList(supportTicketDTO);
        when(supportTicketService.findAll()).thenReturn(tickets);

        // Act & Assert
        mockMvc.perform(get("/api/support-tickets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0].subject").value("Problema con el acceso al curso"))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0].status").value("ABIERTO"))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(supportTicketService).findAll();
    }

    @Test
    void testFindById() throws Exception {
        // Arrange
        when(supportTicketService.findById(1)).thenReturn(supportTicketDTO);

        // Act & Assert
        mockMvc.perform(get("/api/support-tickets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.subject").value("Problema con el acceso al curso"))
                .andExpect(jsonPath("$.description").value("No puedo acceder al contenido del curso después del pago"))
                .andExpect(jsonPath("$.status").value("ABIERTO"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.supportUserId").value(2))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-tickets.href").exists())
                .andExpect(jsonPath("$._links.user-tickets.href").exists())
                .andExpect(jsonPath("$._links.support-user-tickets.href").exists());

        verify(supportTicketService).findById(1);
    }

    @Test
    void testFindByUserId() throws Exception {
        // Arrange
        List<SupportTicketDTO> tickets = Arrays.asList(supportTicketDTO);
        when(supportTicketService.findByUserId(1)).thenReturn(tickets);

        // Act & Assert
        mockMvc.perform(get("/api/support-tickets/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0].userId").value(1))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0].subject").value("Problema con el acceso al curso"))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-tickets.href").exists());

        verify(supportTicketService).findByUserId(1);
    }

    @Test
    void testFindBySupportUserId() throws Exception {
        // Arrange
        List<SupportTicketDTO> tickets = Arrays.asList(supportTicketDTO);
        when(supportTicketService.findBySupportUserId(2)).thenReturn(tickets);

        // Act & Assert
        mockMvc.perform(get("/api/support-tickets/support-user/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0].supportUserId").value(2))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0].subject").value("Problema con el acceso al curso"))
                .andExpect(jsonPath("$._embedded.supportTicketDTOList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-tickets.href").exists());

        verify(supportTicketService).findBySupportUserId(2);
    }

    @Test
    void testCreate() throws Exception {
        // Arrange
        SupportTicketDTO createDTO = new SupportTicketDTO();
        createDTO.setUserId(1);
        createDTO.setSubject("Problema con el acceso al curso");
        createDTO.setDescription("No puedo acceder al contenido del curso después del pago");
        createDTO.setStatus("ABIERTO");

        when(supportTicketService.create(any(SupportTicketDTO.class))).thenReturn(supportTicketDTO);

        // Act & Assert
        mockMvc.perform(post("/api/support-tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk()) // El controlador devuelve 200, no 201
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.subject").value("Problema con el acceso al curso"))
                .andExpect(jsonPath("$.status").value("ABIERTO"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(supportTicketService).create(any(SupportTicketDTO.class));
    }

    @Test
    void testUpdate() throws Exception {
        // Arrange
        SupportTicketDTO updateDTO = new SupportTicketDTO();
        updateDTO.setId(1);
        updateDTO.setUserId(1);
        updateDTO.setSupportUserId(2);
        updateDTO.setSubject("Problema con el acceso al curso - Actualizado");
        updateDTO.setDescription("No puedo acceder al contenido del curso después del pago - Actualizado");
        updateDTO.setStatus("EN_PROGRESO");

        when(supportTicketService.update(anyInt(), any(SupportTicketDTO.class))).thenReturn(updateDTO);

        // Act & Assert
        mockMvc.perform(put("/api/support-tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.subject").value("Problema con el acceso al curso - Actualizado"))
                .andExpect(jsonPath("$.status").value("EN_PROGRESO"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(supportTicketService).update(anyInt(), any(SupportTicketDTO.class));
    }

    @Test
    void testDelete() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/support-tickets/1"))
                .andExpect(status().isNoContent());

        verify(supportTicketService).delete(1);
    }
}