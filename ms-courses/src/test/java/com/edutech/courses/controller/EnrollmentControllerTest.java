package com.edutech.courses.controller;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.courses.service.EnrollmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnrollmentController.class)
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentService enrollmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private EnrollmentDTO enrollmentDTO;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        
        enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setId(1);
        enrollmentDTO.setStudentId(15);
        enrollmentDTO.setCourseId(1);
        enrollmentDTO.setEnrolledAt(Instant.now());
        enrollmentDTO.setStatus("ACTIVE");
    }

    @Test
    void testGetAllEnrollments() throws Exception {
        // Arrange
        List<EnrollmentDTO> enrollments = Arrays.asList(enrollmentDTO);
        when(enrollmentService.findAll()).thenReturn(enrollments);

        // Act & Assert
        mockMvc.perform(get("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(enrollmentService).findAll();
    }

    @Test
    void testGetEnrollmentById() throws Exception {
        // Arrange
        when(enrollmentService.findById(1)).thenReturn(enrollmentDTO);

        // Act & Assert
        mockMvc.perform(get("/api/enrollments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(15))
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(enrollmentService).findById(1);
    }

    @Test
    void testCreateEnrollment() throws Exception {
        // Arrange
        when(enrollmentService.create(any(EnrollmentDTO.class))).thenReturn(enrollmentDTO);

        // Act & Assert
        mockMvc.perform(post("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enrollmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(15))
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(enrollmentService).create(any(EnrollmentDTO.class));
    }

    @Test
    void testUpdateEnrollment() throws Exception {
        // Arrange
        when(enrollmentService.update(anyInt(), any(EnrollmentDTO.class))).thenReturn(enrollmentDTO);

        // Act & Assert
        mockMvc.perform(put("/api/enrollments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enrollmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(15))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(enrollmentService).update(anyInt(), any(EnrollmentDTO.class));
    }

    @Test
    void testDeleteEnrollment() throws Exception {
        // Arrange
        doNothing().when(enrollmentService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/enrollments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(enrollmentService).delete(1);
    }

    @Test
    void testGetEnrollmentsByStudent() throws Exception {
        // Arrange
        List<EnrollmentDTO> enrollments = Arrays.asList(enrollmentDTO);
        when(enrollmentService.findByStudentId(15)).thenReturn(enrollments);

        // Act & Assert
        mockMvc.perform(get("/api/enrollments/student/15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-enrollments.href").exists());

        verify(enrollmentService).findByStudentId(15);
    }

    @Test
    void testGetEnrollmentsByCourse() throws Exception {
        // Arrange
        List<EnrollmentDTO> enrollments = Arrays.asList(enrollmentDTO);
        when(enrollmentService.findByCourseId(1)).thenReturn(enrollments);

        // Act & Assert
        mockMvc.perform(get("/api/enrollments/course/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-enrollments.href").exists());

        verify(enrollmentService).findByCourseId(1);
    }

    
}
