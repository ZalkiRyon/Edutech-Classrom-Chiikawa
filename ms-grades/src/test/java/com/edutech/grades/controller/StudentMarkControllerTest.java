package com.edutech.grades.controller;

import com.edutech.common.dto.StudentMarkDTO;
import com.edutech.grades.service.StudentMarkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentMarkControllerTest {

    @Mock
    private StudentMarkService studentMarkService;

    @InjectMocks
    private StudentMarkController studentMarkController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private StudentMarkDTO studentMarkDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentMarkController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        studentMarkDTO = new StudentMarkDTO();
        studentMarkDTO.setId(1);
        studentMarkDTO.setStudentId(1);
        studentMarkDTO.setQuizId(1);
        studentMarkDTO.setMark(new BigDecimal("8.5"));
        studentMarkDTO.setComments("Excelente trabajo");
        studentMarkDTO.setGradedAt(Instant.now());
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange
        List<StudentMarkDTO> marks = Arrays.asList(studentMarkDTO);
        when(studentMarkService.findAll()).thenReturn(marks);

        // Act & Assert
        mockMvc.perform(get("/api/student-marks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentMarkService).findAll();
    }

    @Test
    void testFindById() throws Exception {
        // Arrange
        when(studentMarkService.findById(1)).thenReturn(studentMarkDTO);

        // Act & Assert
        mockMvc.perform(get("/api/student-marks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentMarkService).findById(1);
    }

    @Test
    void testFindByStudentId() throws Exception {
        // Arrange
        List<StudentMarkDTO> marks = Arrays.asList(studentMarkDTO);
        when(studentMarkService.findByStudentId(1)).thenReturn(marks);

        // Act & Assert
        mockMvc.perform(get("/api/student-marks/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentMarkService).findByStudentId(1);
    }

    @Test
    void testFindByQuizId() throws Exception {
        // Arrange
        List<StudentMarkDTO> marks = Arrays.asList(studentMarkDTO);
        when(studentMarkService.findByQuizId(1)).thenReturn(marks);

        // Act & Assert
        mockMvc.perform(get("/api/student-marks/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentMarkService).findByQuizId(1);
    }

    @Test
    void testCreate() throws Exception {
        // Arrange
        when(studentMarkService.create(any(StudentMarkDTO.class))).thenReturn(studentMarkDTO);

        // Act & Assert
        mockMvc.perform(post("/api/student-marks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentMarkDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentMarkService).create(any(StudentMarkDTO.class));
    }

    @Test
    void testUpdate() throws Exception {
        // Arrange
        when(studentMarkService.update(eq(1), any(StudentMarkDTO.class))).thenReturn(studentMarkDTO);

        // Act & Assert
        mockMvc.perform(put("/api/student-marks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentMarkDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentMarkService).update(eq(1), any(StudentMarkDTO.class));
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        doNothing().when(studentMarkService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/student-marks/1"))
                .andExpect(status().isNoContent());

        verify(studentMarkService).delete(1);
    }
}
