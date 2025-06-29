package com.edutech.courses.integration;

import com.edutech.common.dto.CourseDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.courses.ClassroomCoursesModuleApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ClassroomCoursesModuleApplication.class)
@AutoConfigureWebMvc
@TestPropertySource(properties = {
    "eureka.client.enabled=false",
    "spring.cloud.discovery.enabled=false"
})
class CourseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCourse_ShouldReturnHateoasResponse() throws Exception {
        // Given
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle("Integration Test Course");
        courseDTO.setDescription("A course for integration testing");
        courseDTO.setInstructorId(1);
        courseDTO.setCategoryId(1);
        courseDTO.setManagerId(1);
        courseDTO.setPublishDate(LocalDate.now());
        courseDTO.setPrice(new BigDecimal("99.99"));
        courseDTO.setImage("test-course.jpg");
        courseDTO.setStatus("ACTIVE");

        // When & Then
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test Course"))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.courses").exists())
                .andExpect(jsonPath("$._links.update").exists())
                .andExpect(jsonPath("$._links.delete").exists());
    }

    @Test
    void getAllCourses_ShouldReturnHateoasCollectionResponse() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void getCourseById_ShouldReturnHateoasResponse() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/courses/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.courses").exists())
                .andExpect(jsonPath("$._links.update").exists())
                .andExpect(jsonPath("$._links.delete").exists());
    }

    @Test
    void updateCourse_ShouldReturnUpdatedHateoasResponse() throws Exception {
        // Given
        CourseDTO updateDTO = new CourseDTO();
        updateDTO.setTitle("Updated Integration Test Course");
        updateDTO.setDescription("Updated description");
        updateDTO.setInstructorId(1);
        updateDTO.setCategoryId(1);
        updateDTO.setManagerId(1);
        updateDTO.setPublishDate(LocalDate.now());
        updateDTO.setPrice(new BigDecimal("149.99"));
        updateDTO.setImage("updated-course.jpg");
        updateDTO.setStatus("ACTIVE");

        // When & Then
        mockMvc.perform(put("/api/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Integration Test Course"))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.update").exists());
    }
}
