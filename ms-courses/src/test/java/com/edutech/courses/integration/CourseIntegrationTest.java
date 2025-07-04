package com.edutech.courses.integration;

import com.edutech.common.dto.CourseDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.courses.ClassroomCoursesModuleApplication;
import com.edutech.courses.client.UserClient;
import com.edutech.courses.entity.Course;
import com.edutech.courses.entity.CourseCategory;
import com.edutech.courses.repository.CourseCategoryRepository;
import com.edutech.courses.repository.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ClassroomCoursesModuleApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "eureka.client.enabled=false",
    "spring.cloud.discovery.enabled=false",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.database=h2",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.show-sql=false"
})
@Transactional
class CourseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserClient userClient;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseCategoryRepository categoryRepository;

    private CourseCategory testCategory;  // Store the test category

    @BeforeEach
    void setUp() {
        // Clean up database
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        
        // Create a test category
        CourseCategory category = new CourseCategory();
        category.setName("Test Category");
        category.setDescription("Category for testing");
        testCategory = categoryRepository.save(category);
        testCategory = category; // Assign to the testCategory field
        
        // Mock the UserClient to return a valid user for any ID
        UserDTO mockUser = new UserDTO();
        mockUser.setId(1);
        mockUser.setFirstName("Test");
        mockUser.setLastName("User");
        mockUser.setEmail("test@edutech.com");
        mockUser.setRoleId(1);
        mockUser.setIsActive(true);
        mockUser.setCreatedAt(Instant.now());
        mockUser.setUpdatedAt(Instant.now());
        
        when(userClient.findById(anyInt())).thenReturn(mockUser);
    }

    @Test
    void getAllCourses_ShouldReturnHateoasCollectionResponse() throws Exception {
        // When & Then - should return empty collection when no courses exist
        mockMvc.perform(get("/api/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void createCourse_ShouldReturnHateoasResponse() throws Exception {
        // Given
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle("Integration Test Course");
        courseDTO.setDescription("A course for integration testing");
        courseDTO.setInstructorId(1);
        courseDTO.setCategoryId(testCategory.getId());  // Use actual category ID
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
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void getCourseById_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create a course first
        Course course = new Course();
        course.setTitle("Test Course");
        course.setDescription("Test Description");
        course.setInstructorId(1);
        course.setCategoryId(1);
        course.setManagerId(1);
        course.setPublishDate(LocalDate.now());
        course.setPrice(new BigDecimal("99.99"));
        course.setImage("test.jpg");
        course.setStatus("ACTIVE");
        course = courseRepository.save(course);

        // When & Then
        mockMvc.perform(get("/api/courses/" + course.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Course"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void updateCourse_ShouldReturnUpdatedHateoasResponse() throws Exception {
        // Given - Create a course first
        Course course = new Course();
        course.setTitle("Original Title");
        course.setDescription("Original Description");
        course.setInstructorId(1);
        course.setCategoryId(1);
        course.setManagerId(1);
        course.setPublishDate(LocalDate.now());
        course.setPrice(new BigDecimal("99.99"));
        course.setImage("original.jpg");
        course.setStatus("ACTIVE");
        course = courseRepository.save(course);

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
        mockMvc.perform(put("/api/courses/" + course.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Integration Test Course"))
                .andExpect(jsonPath("$._links.self").exists());
    }
}
