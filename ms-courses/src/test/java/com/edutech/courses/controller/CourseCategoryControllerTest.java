package com.edutech.courses.controller;

import com.edutech.common.dto.CourseCategoryDTO;
import com.edutech.common.dto.CourseDTO;
import com.edutech.courses.service.CourseCategoryService;
import com.edutech.courses.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseCategoryController.class)
class CourseCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseCategoryService courseCategoryService;

    @MockBean
    private CourseService courseService;

    private ObjectMapper objectMapper;
    private CourseCategoryDTO courseCategoryDTO;
    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        courseCategoryDTO = new CourseCategoryDTO();
        courseCategoryDTO.setId(1);
        courseCategoryDTO.setName("Programación");
        courseCategoryDTO.setDescription("Categoría de cursos de programación");

        courseDTO = new CourseDTO();
        courseDTO.setId(1);
        courseDTO.setTitle("Curso de Java");
        courseDTO.setDescription("Aprende Java desde cero");
        courseDTO.setPrice(BigDecimal.valueOf(99.99));
        courseDTO.setCategoryId(1);
    }

    @Test
    void testGetAllCourseCategories() throws Exception {
        // Arrange
        List<CourseCategoryDTO> categories = Arrays.asList(courseCategoryDTO);
        when(courseCategoryService.findAll()).thenReturn(categories);

        // Act & Assert
        mockMvc.perform(get("/api/course-categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courseCategoryDTOList").exists())
                .andExpect(jsonPath("$._embedded.courseCategoryDTOList[0].name").value("Programación"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseCategoryService).findAll();
    }

    @Test
    void testGetCourseCategoryById() throws Exception {
        // Arrange
        when(courseCategoryService.findById(1)).thenReturn(courseCategoryDTO);

        // Act & Assert
        mockMvc.perform(get("/api/course-categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Programación"))
                .andExpect(jsonPath("$.description").value("Categoría de cursos de programación"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.course-categories.href").exists())
                .andExpect(jsonPath("$._links.update.href").exists())
                .andExpect(jsonPath("$._links.delete.href").exists());

        verify(courseCategoryService).findById(1);
    }

    @Test
    void testGetCoursesByCategoryId() throws Exception {
        // Arrange
        List<CourseDTO> courses = Arrays.asList(courseDTO);
        when(courseService.findByCategoryId(1)).thenReturn(courses);

        // Act & Assert
        mockMvc.perform(get("/api/course-categories/1/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courseDTOList").exists())
                .andExpect(jsonPath("$._embedded.courseDTOList[0].title").value("Curso de Java"))
                .andExpect(jsonPath("$._embedded.courseDTOList[0].description").value("Aprende Java desde cero"))
                .andExpect(jsonPath("$._embedded.courseDTOList[0].price").value(99.99))
                .andExpect(jsonPath("$._embedded.courseDTOList[0].categoryId").value(1))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.category.href").exists());

        verify(courseService).findByCategoryId(1);
    }

    @Test
    void testCreateCourseCategory() throws Exception {
        // Arrange
        when(courseCategoryService.create(any(CourseCategoryDTO.class))).thenReturn(courseCategoryDTO);

        // Act & Assert
        mockMvc.perform(post("/api/course-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseCategoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Programación"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseCategoryService).create(any(CourseCategoryDTO.class));
    }

    @Test
    void testUpdateCourseCategory() throws Exception {
        // Arrange
        CourseCategoryDTO updatedDTO = new CourseCategoryDTO();
        updatedDTO.setId(1);
        updatedDTO.setName("Programación Avanzada");
        updatedDTO.setDescription("Categoría actualizada");

        when(courseCategoryService.update(eq(1), any(CourseCategoryDTO.class))).thenReturn(updatedDTO);

        // Act & Assert
        mockMvc.perform(put("/api/course-categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Programación Avanzada"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(courseCategoryService).update(eq(1), any(CourseCategoryDTO.class));
    }

    @Test
    void testDeleteCourseCategory() throws Exception {
        // Arrange
        doNothing().when(courseCategoryService).delete(1);

        // Act & Assert
        mockMvc.perform(delete("/api/course-categories/1"))
                .andExpect(status().isNoContent());

        verify(courseCategoryService).delete(1);
    }
}
