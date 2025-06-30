package com.edutech.grades.controller;

import com.edutech.common.dto.QuizDTO;
import com.edutech.grades.service.CourseQuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseQuizController.class)
class CourseQuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseQuizService courseQuizService;

    @Autowired
    private ObjectMapper objectMapper;

    private QuizDTO quizDTO;

    @BeforeEach
    void setUp() {
        quizDTO = new QuizDTO();
        quizDTO.setId(1);
        quizDTO.setCourseId(1);
        quizDTO.setTitle("Quiz de Prueba");
        quizDTO.setDescription("Descripción del quiz de prueba");
        quizDTO.setQuizType("Multiple Choice");
    }

    @Test
    void testGetAllCourseQuizzes() throws Exception {
        // Arrange
        List<QuizDTO> quizzes = Arrays.asList(quizDTO);
        when(courseQuizService.findAll()).thenReturn(quizzes);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.quizDTOList").exists())
                .andExpect(jsonPath("$._embedded.quizDTOList[0].title").value("Quiz de Prueba"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseQuizService).findAll();
    }

    @Test
    void testGetCourseQuizById() throws Exception {
        // Arrange
        when(courseQuizService.findById(1)).thenReturn(quizDTO);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Quiz de Prueba"))
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-quizzes.href").exists())
                .andExpect(jsonPath("$._links.course-quizzes.href").exists());

        verify(courseQuizService).findById(1);
    }

    @Test
    void testGetCourseQuizzesByCourseId() throws Exception {
        // Arrange
        List<QuizDTO> quizzes = Arrays.asList(quizDTO);
        when(courseQuizService.findByCourseId(1)).thenReturn(quizzes);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz/course/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Quiz de Prueba"))
                .andExpect(jsonPath("$[0].courseId").value(1));

        verify(courseQuizService).findByCourseId(1);
    }

    @Test
    void testGetCourseQuizzesByType() throws Exception {
        // Arrange
        List<QuizDTO> quizzes = Arrays.asList(quizDTO);
        when(courseQuizService.findByQuizType("Multiple Choice")).thenReturn(quizzes);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz/type/Multiple Choice")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Quiz de Prueba"))
                .andExpect(jsonPath("$[0].quizType").value("Multiple Choice"));

        verify(courseQuizService).findByQuizType("Multiple Choice");
    }

    @Test
    void testCreateCourseQuiz() throws Exception {
        // Arrange
        when(courseQuizService.create(any(QuizDTO.class))).thenReturn(quizDTO);

        // Act & Assert
        mockMvc.perform(post("/api/course-quiz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Quiz de Prueba"))
                .andExpect(jsonPath("$.courseId").value(1));

        verify(courseQuizService).create(any(QuizDTO.class));
    }

    @Test
    void testUpdateCourseQuiz() throws Exception {
        // Arrange
        when(courseQuizService.update(eq(1), any(QuizDTO.class))).thenReturn(quizDTO);

        // Act & Assert
        mockMvc.perform(put("/api/course-quiz/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Quiz de Prueba"))
                .andExpect(jsonPath("$.courseId").value(1));

        verify(courseQuizService).update(eq(1), any(QuizDTO.class));
    }

    @Test
    void testDeleteCourseQuiz() throws Exception {
        // Arrange
        doNothing().when(courseQuizService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/course-quiz/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(courseQuizService).delete(1);
    }

    @Test
    void testExistsCourseQuizById() throws Exception {
        // Arrange
        when(courseQuizService.existsById(1)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz/1/exists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(courseQuizService).existsById(1);
    }

    @Test
    void testCountCourseQuizzesByCourseId() throws Exception {
        // Arrange
        when(courseQuizService.countByCourseId(1)).thenReturn(5L);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz/course/1/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(courseQuizService).countByCourseId(1);
    }

    @Test
    void testExistsCourseQuizByCourseId() throws Exception {
        // Arrange
        when(courseQuizService.existsByCourseId(1)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz/course/1/exists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(courseQuizService).existsByCourseId(1);
    }
}
