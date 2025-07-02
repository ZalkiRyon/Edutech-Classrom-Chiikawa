package com.edutech.courses.integration;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.courses.ClassroomCoursesModuleApplication;
import com.edutech.courses.client.UserClient;
import com.edutech.courses.entity.Course;
import com.edutech.courses.entity.CourseCategory;
import com.edutech.courses.entity.Enrollment;
import com.edutech.courses.repository.CourseCategoryRepository;
import com.edutech.courses.repository.CourseRepository;
import com.edutech.courses.repository.EnrollmentRepository;
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
class EnrollmentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserClient userClient;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseCategoryRepository categoryRepository;

    private Course testCourse;

    @BeforeEach
    void setUp() {
        // Clean up database
        enrollmentRepository.deleteAll();
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        
        // Create a test category
        CourseCategory category = new CourseCategory();
        category.setName("Test Category");
        category.setDescription("Category for testing");
        category = categoryRepository.save(category);
        
        // Create a test course
        testCourse = new Course();
        testCourse.setTitle("Test Course");
        testCourse.setDescription("Test Description");
        testCourse.setInstructorId(1);
        testCourse.setCategoryId(category.getId());
        testCourse.setManagerId(1);
        testCourse.setPublishDate(LocalDate.now());
        testCourse.setPrice(new BigDecimal("99.99"));
        testCourse.setImage("test.jpg");
        testCourse.setStatus("ACTIVE");
        testCourse = courseRepository.save(testCourse);
        
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
    void getAllEnrollments_ShouldReturnHateoasCollectionResponse() throws Exception {
        mockMvc.perform(get("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void createEnrollment_ShouldReturnHateoasResponse() throws Exception {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setStudentId(15);
        enrollmentDTO.setCourseId(testCourse.getId());
        enrollmentDTO.setEnrolledAt(Instant.now());
        enrollmentDTO.setStatus("ACTIVE");

        mockMvc.perform(post("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enrollmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(15))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void getEnrollmentById_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create an enrollment first
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(15);
        enrollment.setCourseId(testCourse.getId());
        enrollment.setEnrolledAt(Instant.now());
        enrollment.setStatus("ACTIVE");
        enrollment = enrollmentRepository.save(enrollment);

        mockMvc.perform(get("/api/enrollments/" + enrollment.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(15))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void getEnrollmentsByStudent_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create an enrollment first
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(15);
        enrollment.setCourseId(testCourse.getId());
        enrollment.setEnrolledAt(Instant.now());
        enrollment.setStatus("ACTIVE");
        enrollmentRepository.save(enrollment);

        mockMvc.perform(get("/api/enrollments/student/15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void getEnrollmentsByCourse_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create an enrollment first
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(15);
        enrollment.setCourseId(testCourse.getId());
        enrollment.setEnrolledAt(Instant.now());
        enrollment.setStatus("ACTIVE");
        enrollmentRepository.save(enrollment);

        mockMvc.perform(get("/api/enrollments/course/" + testCourse.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self").exists());
    }
}
