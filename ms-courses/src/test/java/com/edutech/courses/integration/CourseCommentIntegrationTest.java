package com.edutech.courses.integration;

import com.edutech.common.dto.CourseCommentDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.courses.ClassroomCoursesModuleApplication;
import com.edutech.courses.client.UserClient;
import com.edutech.courses.entity.Course;
import com.edutech.courses.entity.CourseCategory;
import com.edutech.courses.entity.CourseComment;
import com.edutech.courses.repository.CourseCategoryRepository;
import com.edutech.courses.repository.CourseCommentRepository;
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
class CourseCommentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserClient userClient;

    @Autowired
    private CourseCommentRepository courseCommentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseCategoryRepository categoryRepository;

    private Course testCourse;

    @BeforeEach
    void setUp() {
        // Clean up database
        courseCommentRepository.deleteAll();
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
        
        // Mock UserClient
        UserDTO mockUser = new UserDTO();
        mockUser.setId(1);
        mockUser.setFirstName("Test");
        mockUser.setLastName("User");
        mockUser.setEmail("test@edutech.com");
        
        when(userClient.findById(anyInt())).thenReturn(mockUser);
    }

    @Test
    void getAllCourseComments_ShouldReturnHateoasCollectionResponse() throws Exception {
        mockMvc.perform(get("/api/course-comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void createCourseComment_ShouldReturnHateoasResponse() throws Exception {
        CourseCommentDTO commentDTO = new CourseCommentDTO();
        // The DTO requires an ID due to @NotNull validation, even for creation
        commentDTO.setId(999); // Use a non-conflicting ID
        commentDTO.setCourseId(testCourse.getId());
        commentDTO.setUserId(15);
        commentDTO.setCommentText("Buen curso!");
        commentDTO.setRating(5);
        commentDTO.setCreatedAt(Instant.now());

        mockMvc.perform(post("/api/course-comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentText").value("Buen curso!"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void getCourseCommentById_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create comment first
        CourseComment comment = new CourseComment();
        comment.setCourseId(testCourse.getId());
        comment.setUserId(15);  // Correcto: la entidad usa userId, no studentId
        comment.setCommentText("Buen curso!");  // Correcto: la entidad usa commentText, no comment
        comment.setRating(5);
        comment.setCreatedAt(Instant.now());  // Correcto: la entidad usa createdAt, no commentedAt
        comment = courseCommentRepository.save(comment);

        mockMvc.perform(get("/api/course-comments/" + comment.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentText").value("Buen curso!"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void getCourseCommentsByCourse_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create comment first
        CourseComment comment = new CourseComment();
        comment.setCourseId(testCourse.getId());
        comment.setUserId(15);  // Correcto: la entidad usa userId, no studentId
        comment.setCommentText("Buen curso!");  // Correcto: la entidad usa commentText, no comment
        comment.setRating(5);
        comment.setCreatedAt(Instant.now());  // Correcto: la entidad usa createdAt, no commentedAt
        courseCommentRepository.save(comment);

        mockMvc.perform(get("/api/course-comments/course/" + testCourse.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].commentText").value("Buen curso!"));
    }
}
