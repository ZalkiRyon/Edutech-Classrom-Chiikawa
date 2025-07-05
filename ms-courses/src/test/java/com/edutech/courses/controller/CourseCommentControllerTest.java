package com.edutech.courses.controller;

import com.edutech.common.dto.CourseCommentDTO;
import com.edutech.courses.service.CourseCommentService;
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

@WebMvcTest(CourseCommentController.class)
class CourseCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseCommentService courseCommentService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseCommentDTO courseCommentDTO;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        
        courseCommentDTO = new CourseCommentDTO();
        courseCommentDTO.setId(1);
        courseCommentDTO.setCourseId(1);
        courseCommentDTO.setUserId(15);
        courseCommentDTO.setCommentText("Excelente curso! Muy completo.");
        courseCommentDTO.setRating(5);
        courseCommentDTO.setCreatedAt(Instant.now());
    }

    @Test
    void testGetAllCourseComments() throws Exception {
        // Arrange
        List<CourseCommentDTO> comments = Arrays.asList(courseCommentDTO);
        when(courseCommentService.findAll()).thenReturn(comments);

        // Act & Assert
        mockMvc.perform(get("/api/course-comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courseCommentDTOList").exists())
                .andExpect(jsonPath("$._embedded.courseCommentDTOList[0].commentText").value("Excelente curso! Muy completo."))
                .andExpect(jsonPath("$._embedded.courseCommentDTOList[0].rating").value(5))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseCommentService).findAll();
    }

    @Test
    void testGetCourseCommentById() throws Exception {
        // Arrange
        when(courseCommentService.findById(1)).thenReturn(courseCommentDTO);

        // Act & Assert
        mockMvc.perform(get("/api/course-comments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentText").value("Excelente curso! Muy completo."))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-comments.href").exists());

        verify(courseCommentService).findById(1);
    }

    @Test
    void testCreateCourseComment() throws Exception {
        // Arrange
        when(courseCommentService.create(any(CourseCommentDTO.class))).thenReturn(courseCommentDTO);

        // Act & Assert
        mockMvc.perform(post("/api/course-comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseCommentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentText").value("Excelente curso! Muy completo."))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseCommentService).create(any(CourseCommentDTO.class));
    }

    @Test
    void testUpdateCourseComment() throws Exception {
        // Arrange
        when(courseCommentService.update(anyInt(), any(CourseCommentDTO.class))).thenReturn(courseCommentDTO);

        // Act & Assert
        mockMvc.perform(put("/api/course-comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseCommentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentText").value("Excelente curso! Muy completo."))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseCommentService).update(anyInt(), any(CourseCommentDTO.class));
    }

    @Test
    void testDeleteCourseComment() throws Exception {
        // Arrange
        doNothing().when(courseCommentService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/course-comments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(courseCommentService).delete(1);
    }

    @Test
    void testGetCommentsByCourse() throws Exception {
        // Arrange
        List<CourseCommentDTO> comments = Arrays.asList(courseCommentDTO);
        when(courseCommentService.findByCourseId(1)).thenReturn(comments);

        // Act & Assert
        mockMvc.perform(get("/api/course-comments/course/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].commentText").value("Excelente curso! Muy completo."))
                .andExpect(jsonPath("$[0].courseId").value(1))
                .andExpect(jsonPath("$[0].rating").value(5));

        verify(courseCommentService).findByCourseId(1);
    }

    @Test
    void testGetCommentsByUser() throws Exception {
        // Arrange
        List<CourseCommentDTO> comments = Arrays.asList(courseCommentDTO);
        when(courseCommentService.findByUserId(15)).thenReturn(comments);

        // Act & Assert
        mockMvc.perform(get("/api/course-comments/user/15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].commentText").value("Excelente curso! Muy completo."))
                .andExpect(jsonPath("$[0].userId").value(15))
                .andExpect(jsonPath("$[0].rating").value(5));

        verify(courseCommentService).findByUserId(15);
    }
}
