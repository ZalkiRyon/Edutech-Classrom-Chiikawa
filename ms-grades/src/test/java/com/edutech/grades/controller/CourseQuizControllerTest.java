package com.edutech.grades.controller;

import com.edutech.common.dto.CourseQuizDTO;
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

    private CourseQuizDTO courseQuizDTO;

    @BeforeEach
    void setUp() {
        courseQuizDTO = new CourseQuizDTO();
        courseQuizDTO.setId(1);
        courseQuizDTO.setCourseId(1);
        courseQuizDTO.setTitle("Quiz de Prueba");
        courseQuizDTO.setDescription("Descripción del quiz de prueba");
        courseQuizDTO.setQuizType("Multiple Choice");
    }

    @Test
    void testGetAllCourseQuizzes() throws Exception {
        // Arrange
        List<CourseQuizDTO> quizzes = Arrays.asList(courseQuizDTO);
        when(courseQuizService.findAll()).thenReturn(quizzes);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courseQuizDTOList").exists())
                .andExpect(jsonPath("$._embedded.courseQuizDTOList[0].title").value("Quiz de Prueba"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseQuizService).findAll();
    }

    @Test
    void testGetCourseQuizById() throws Exception {
        // Arrange
        when(courseQuizService.findById(1)).thenReturn(courseQuizDTO);

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
        List<CourseQuizDTO> quizzes = Arrays.asList(courseQuizDTO);
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
    void testCreateCourseQuiz() throws Exception {
        // Arrange
        when(courseQuizService.create(any(CourseQuizDTO.class))).thenReturn(courseQuizDTO);

        // Act & Assert
        mockMvc.perform(post("/api/course-quiz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseQuizDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Quiz de Prueba"))
                .andExpect(jsonPath("$.courseId").value(1));

        verify(courseQuizService).create(any(CourseQuizDTO.class));
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
}
