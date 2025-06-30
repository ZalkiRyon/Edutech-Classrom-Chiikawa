package com.edutech.grades.controller;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edutech.common.dto.QuizDTO;
import com.edutech.grades.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(QuizController.class)
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Autowired
    private ObjectMapper objectMapper;

    private QuizDTO quizDTO;

    @BeforeEach
    void setUp() {
        quizDTO = new QuizDTO();
        quizDTO.setId(1);
        quizDTO.setCourseId(1);
        quizDTO.setTitle("Test Quiz");
        quizDTO.setDescription("Test Description");
        quizDTO.setQuizType("EXAM");
        quizDTO.setCreatedAt(Instant.now());
    }

    @Test
    void createQuiz_Success() throws Exception {
        // Given
        when(quizService.createQuiz(any(QuizDTO.class))).thenReturn(quizDTO);

        // When & Then
        mockMvc.perform(post("/api/grades/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Quiz"))
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.quizType").value("EXAM"));
    }

    @Test
    void createQuiz_InvalidData_BadRequest() throws Exception {
        // Given
        QuizDTO invalidQuiz = new QuizDTO();
        // Missing required fields

        // When & Then
        mockMvc.perform(post("/api/grades/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidQuiz)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getQuizById_Success() throws Exception {
        // Given
        when(quizService.getQuizById(1)).thenReturn(Optional.of(quizDTO));

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Quiz"));
    }

    @Test
    void getQuizById_NotFound() throws Exception {
        // Given
        when(quizService.getQuizById(1)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllQuizzes_Success() throws Exception {
        // Given
        List<QuizDTO> quizzes = Arrays.asList(quizDTO);
        when(quizService.getAllQuizzes()).thenReturn(quizzes);

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Quiz"));
    }

    @Test
    void getAllQuizzes_WithCourseIdFilter() throws Exception {
        // Given
        List<QuizDTO> quizzes = Arrays.asList(quizDTO);
        when(quizService.getQuizzesByCourseId(1)).thenReturn(quizzes);

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes?courseId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].courseId").value(1));
    }

    @Test
    void getAllQuizzes_WithQuizTypeFilter() throws Exception {
        // Given
        List<QuizDTO> quizzes = Arrays.asList(quizDTO);
        when(quizService.getQuizzesByType("EXAM")).thenReturn(quizzes);

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes?quizType=EXAM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].quizType").value("EXAM"));
    }

    @Test
    void getAllQuizzes_WithKeywordFilter() throws Exception {
        // Given
        List<QuizDTO> quizzes = Arrays.asList(quizDTO);
        when(quizService.searchQuizzesByTitle("Test")).thenReturn(quizzes);

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes?keyword=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test Quiz"));
    }

    @Test
    void getQuizzesByCourseId_Success() throws Exception {
        // Given
        List<QuizDTO> quizzes = Arrays.asList(quizDTO);
        when(quizService.getQuizzesByCourseId(1)).thenReturn(quizzes);

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].courseId").value(1));
    }

    @Test
    void getQuizzesByType_Success() throws Exception {
        // Given
        List<QuizDTO> quizzes = Arrays.asList(quizDTO);
        when(quizService.getQuizzesByType("EXAM")).thenReturn(quizzes);

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes/type/EXAM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].quizType").value("EXAM"));
    }

    @Test
    void updateQuiz_Success() throws Exception {
        // Given
        QuizDTO updatedQuiz = new QuizDTO();
        updatedQuiz.setId(1);
        updatedQuiz.setCourseId(1);
        updatedQuiz.setTitle("Updated Quiz");
        updatedQuiz.setDescription("Updated Description");
        updatedQuiz.setQuizType("EXAM");
        
        when(quizService.updateQuiz(eq(1), any(QuizDTO.class))).thenReturn(updatedQuiz);

        // When & Then
        mockMvc.perform(put("/api/grades/quizzes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedQuiz)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Quiz"));
    }

    @Test
    void updateQuiz_NotFound() throws Exception {
        // Given
        when(quizService.updateQuiz(eq(1), any(QuizDTO.class)))
                .thenThrow(new RuntimeException("Quiz not found with ID: 1"));

        // When & Then
        mockMvc.perform(put("/api/grades/quizzes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteQuiz_Success() throws Exception {
        // Given
        when(quizService.deleteQuiz(1)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/api/grades/quizzes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteQuiz_NotFound() throws Exception {
        // Given
        when(quizService.deleteQuiz(1)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/api/grades/quizzes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void existsById_Success() throws Exception {
        // Given
        when(quizService.existsById(1)).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void countQuizzesByCourseId_Success() throws Exception {
        // Given
        when(quizService.countQuizzesByCourseId(1)).thenReturn(5L);

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes/course/1/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void existsByCourseId_Success() throws Exception {
        // Given
        when(quizService.existsByCourseId(1)).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/api/grades/quizzes/course/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
