package com.edutech.courses.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edutech.common.dto.CourseDTO;
import com.edutech.courses.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        
        courseDTO = new CourseDTO();
        courseDTO.setId(1);
        courseDTO.setTitle("Programacion en Java");
        courseDTO.setDescription("Aprende Java desde cero");
        courseDTO.setInstructorId(1);
        courseDTO.setCategoryId(1);
        courseDTO.setManagerId(2);
        courseDTO.setPublishDate(LocalDate.now());
        courseDTO.setPrice(new BigDecimal("99.99"));
        courseDTO.setImage("java-course.jpg");
        courseDTO.setStatus("ACTIVE");
    }

    @Test
    void testGetAllCourses() throws Exception {
        // Arrange
        List<CourseDTO> courses = Arrays.asList(courseDTO);
        when(courseService.findAll()).thenReturn(courses);

        // Act & Assert
        mockMvc.perform(get("/api/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courseDTOList").exists())
                .andExpect(jsonPath("$._embedded.courseDTOList[0].title").value("Programacion en Java"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseService).findAll();
    }

    @Test
    void testGetCourseById() throws Exception {
        // Arrange
        when(courseService.findById(1)).thenReturn(courseDTO);

        // Act & Assert
        mockMvc.perform(get("/api/courses/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Programacion en Java"))
                .andExpect(jsonPath("$.description").value("Aprende Java desde cero"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseService).findById(1);
    }

    @Test
    void testCreateCourse() throws Exception {
        // Arrange
        when(courseService.create(any(CourseDTO.class))).thenReturn(courseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Programacion en Java"))
                .andExpect(jsonPath("$.description").value("Aprende Java desde cero"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseService).create(any(CourseDTO.class));
    }

    @Test
    void testUpdateCourse() throws Exception {
        // Arrange
        when(courseService.update(anyInt(), any(CourseDTO.class))).thenReturn(courseDTO);

        // Act & Assert
        mockMvc.perform(put("/api/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Programacion en Java"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseService).update(anyInt(), any(CourseDTO.class));
    }

    @Test
    void testDeleteCourse() throws Exception {
        // Arrange
        doNothing().when(courseService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/courses/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(courseService).delete(1);
    }       
}
