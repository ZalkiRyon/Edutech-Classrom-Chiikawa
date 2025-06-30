package com.edutech.grades.controller;

import com.edutech.common.dto.CourseQuizQuestionDTO;
import com.edutech.grades.service.CourseQuizQuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CourseQuizQuestionControllerTest {

    @Mock
    private CourseQuizQuestionService courseQuizQuestionService;

    @InjectMocks
    private CourseQuizQuestionController courseQuizQuestionController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CourseQuizQuestionDTO courseQuizQuestionDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseQuizQuestionController).build();
        objectMapper = new ObjectMapper();
        
        courseQuizQuestionDTO = new CourseQuizQuestionDTO();
        courseQuizQuestionDTO.setId(1);
        courseQuizQuestionDTO.setQuizId(1);
        courseQuizQuestionDTO.setQuestionText("¿Cuál es la respuesta correcta?");
        courseQuizQuestionDTO.setCorrectOption("A");
        courseQuizQuestionDTO.setOptionA("Opción A");
        courseQuizQuestionDTO.setOptionB("Opción B");
        courseQuizQuestionDTO.setOptionC("Opción C");
        courseQuizQuestionDTO.setOptionD("Opción D");
        courseQuizQuestionDTO.setOrderIndex(1);
    }

    @Test
    void testGetAllCourseQuizQuestions() throws Exception {
        // Arrange
        List<CourseQuizQuestionDTO> questions = Arrays.asList(courseQuizQuestionDTO);
        when(courseQuizQuestionService.findAll()).thenReturn(questions);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz-question"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(courseQuizQuestionService).findAll();
    }

    @Test
    void testGetCourseQuizQuestionById() throws Exception {
        // Arrange
        when(courseQuizQuestionService.findById(1)).thenReturn(courseQuizQuestionDTO);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz-question/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(courseQuizQuestionService).findById(1);
    }

    @Test
    void testGetQuestionsByQuizId() throws Exception {
        // Arrange
        List<CourseQuizQuestionDTO> questions = Arrays.asList(courseQuizQuestionDTO);
        when(courseQuizQuestionService.findByQuizId(1)).thenReturn(questions);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz-question/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(courseQuizQuestionService).findByQuizId(1);
    }

    @Test
    void testGetQuestionsByQuizIdOrdered() throws Exception {
        // Arrange
        List<CourseQuizQuestionDTO> questions = Arrays.asList(courseQuizQuestionDTO);
        when(courseQuizQuestionService.findByQuizIdOrderByOrderIndex(1)).thenReturn(questions);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz-question/quiz/1/ordered"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(courseQuizQuestionService).findByQuizIdOrderByOrderIndex(1);
    }

    @Test
    void testGetQuestionsByQuizIdOrderedAsc() throws Exception {
        // Arrange
        List<CourseQuizQuestionDTO> questions = Arrays.asList(courseQuizQuestionDTO);
        when(courseQuizQuestionService.findByQuizIdOrderByOrderIndexAsc(1)).thenReturn(questions);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz-question/quiz/1/ordered/asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(courseQuizQuestionService).findByQuizIdOrderByOrderIndexAsc(1);
    }

    @Test
    void testGetQuestionsByQuizIdOrderedDesc() throws Exception {
        // Arrange
        List<CourseQuizQuestionDTO> questions = Arrays.asList(courseQuizQuestionDTO);
        when(courseQuizQuestionService.findByQuizIdOrderByOrderIndexDesc(1)).thenReturn(questions);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz-question/quiz/1/ordered/desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(courseQuizQuestionService).findByQuizIdOrderByOrderIndexDesc(1);
    }

    @Test
    void testCountQuestionsByQuizId() throws Exception {
        // Arrange
        when(courseQuizQuestionService.countByQuizId(1)).thenReturn(5L);

        // Act & Assert
        mockMvc.perform(get("/api/course-quiz-question/quiz/1/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("5"));

        verify(courseQuizQuestionService).countByQuizId(1);
    }

    @Test
    void testCreateCourseQuizQuestion() throws Exception {
        // Arrange
        when(courseQuizQuestionService.create(any(CourseQuizQuestionDTO.class))).thenReturn(courseQuizQuestionDTO);

        // Act & Assert
        mockMvc.perform(post("/api/course-quiz-question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseQuizQuestionDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(courseQuizQuestionService).create(any(CourseQuizQuestionDTO.class));
    }

    @Test
    void testUpdateCourseQuizQuestion() throws Exception {
        // Arrange
        when(courseQuizQuestionService.update(eq(1), any(CourseQuizQuestionDTO.class))).thenReturn(courseQuizQuestionDTO);

        // Act & Assert
        mockMvc.perform(put("/api/course-quiz-question/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseQuizQuestionDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(courseQuizQuestionService).update(eq(1), any(CourseQuizQuestionDTO.class));
    }

    @Test
    void testDeleteCourseQuizQuestion() throws Exception {
        // Arrange
        doNothing().when(courseQuizQuestionService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/course-quiz-question/1"))
                .andExpect(status().isNoContent());

        verify(courseQuizQuestionService).delete(1);
    }

    @Test
    void testDeleteQuestionsByQuizId() throws Exception {
        // Arrange
        doNothing().when(courseQuizQuestionService).deleteByQuizId(1);

        // Act & Assert
        mockMvc.perform(delete("/api/course-quiz-question/quiz/1"))
                .andExpect(status().isNoContent());

        verify(courseQuizQuestionService).deleteByQuizId(1);
    }
}
