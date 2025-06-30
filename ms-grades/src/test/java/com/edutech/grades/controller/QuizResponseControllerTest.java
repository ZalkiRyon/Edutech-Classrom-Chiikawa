package com.edutech.grades.controller;

import com.edutech.common.dto.QuizResponseDTO;
import com.edutech.grades.service.QuizResponseService;
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

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class QuizResponseControllerTest {

    @Mock
    private QuizResponseService quizResponseService;

    @InjectMocks
    private QuizResponseController quizResponseController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private QuizResponseDTO quizResponseDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(quizResponseController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        quizResponseDTO = new QuizResponseDTO();
        quizResponseDTO.setId(1);
        quizResponseDTO.setQuizId(1);
        quizResponseDTO.setStudentId(1);
        quizResponseDTO.setSelectedOption("A");
        quizResponseDTO.setResponseContent("Respuesta de prueba");
        quizResponseDTO.setSubmittedAt(Instant.now());
    }

    @Test
    void testGetAllQuizResponses() throws Exception {
        // Arrange
        List<QuizResponseDTO> responses = Arrays.asList(quizResponseDTO);
        when(quizResponseService.findAll()).thenReturn(responses);

        // Act & Assert
        mockMvc.perform(get("/api/quiz-response"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(quizResponseService).findAll();
    }

    @Test
    void testGetQuizResponseById() throws Exception {
        // Arrange
        when(quizResponseService.findById(1)).thenReturn(quizResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/api/quiz-response/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(quizResponseService).findById(1);
    }

    @Test
    void testGetQuizResponsesByQuizId() throws Exception {
        // Arrange
        List<QuizResponseDTO> responses = Arrays.asList(quizResponseDTO);
        when(quizResponseService.findByQuizId(1)).thenReturn(responses);

        // Act & Assert
        mockMvc.perform(get("/api/quiz-response/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(quizResponseService).findByQuizId(1);
    }

    @Test
    void testGetQuizResponsesByStudentId() throws Exception {
        // Arrange
        List<QuizResponseDTO> responses = Arrays.asList(quizResponseDTO);
        when(quizResponseService.findByStudentId(1)).thenReturn(responses);

        // Act & Assert
        mockMvc.perform(get("/api/quiz-response/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(quizResponseService).findByStudentId(1);
    }

    @Test
    void testGetQuizResponsesByQuizIdAndStudentId() throws Exception {
        // Arrange
        List<QuizResponseDTO> responses = Arrays.asList(quizResponseDTO);
        when(quizResponseService.findByQuizIdAndStudentId(1, 1)).thenReturn(responses);

        // Act & Assert
        mockMvc.perform(get("/api/quiz-response/quiz/1/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(quizResponseService).findByQuizIdAndStudentId(1, 1);
    }

    @Test
    void testCreateQuizResponse() throws Exception {
        // Arrange
        when(quizResponseService.create(any(QuizResponseDTO.class))).thenReturn(quizResponseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/quiz-response")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizResponseDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(quizResponseService).create(any(QuizResponseDTO.class));
    }

    @Test
    void testUpdateQuizResponse() throws Exception {
        // Arrange
        when(quizResponseService.update(eq(1), any(QuizResponseDTO.class))).thenReturn(quizResponseDTO);

        // Act & Assert
        mockMvc.perform(put("/api/quiz-response/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizResponseDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(quizResponseService).update(eq(1), any(QuizResponseDTO.class));
    }

    @Test
    void testDeleteQuizResponse() throws Exception {
        // Arrange
        doNothing().when(quizResponseService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/quiz-response/1"))
                .andExpect(status().isNoContent());

        verify(quizResponseService).delete(1);
    }
}
