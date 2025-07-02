package com.edutech.courses.integration;

import com.edutech.common.dto.CourseContentDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.courses.ClassroomCoursesModuleApplication;
import com.edutech.courses.client.UserClient;
import com.edutech.courses.entity.Course;
import com.edutech.courses.entity.CourseCategory;
import com.edutech.courses.entity.CourseContent;
import com.edutech.courses.repository.CourseCategoryRepository;
import com.edutech.courses.repository.CourseContentRepository;
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
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
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
class CourseContentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseContentRepository courseContentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseCategoryRepository courseCategoryRepository;

    @MockBean
    private UserClient userClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Course testCourse;
    private CourseCategory testCategory;

    @BeforeEach
    void setUp() {
        // Mock user client
        UserDTO mockUser = new UserDTO();
        mockUser.setId(1);
        mockUser.setFirstName("Test");
        mockUser.setLastName("User");
        mockUser.setEmail("test@example.com");
        when(userClient.findById(any())).thenReturn(mockUser);

        // Create test data
        testCategory = new CourseCategory();
        testCategory.setName("Test Category");
        testCategory.setDescription("Test Description");
        testCategory = courseCategoryRepository.save(testCategory);

        testCourse = new Course();
        testCourse.setTitle("Test Course");
        testCourse.setDescription("Test Description");
        testCourse.setInstructorId(1);
        testCourse.setManagerId(1);
        testCourse.setPrice(new BigDecimal("99.99"));
        testCourse.setCategoryId(testCategory.getId());
        testCourse.setPublishDate(LocalDate.now());
        testCourse.setImage("https://example.com/image.jpg");
        testCourse.setStatus("ACTIVE");
        testCourse = courseRepository.save(testCourse);
    }

    @Test
    void createCourseContent_ShouldReturnHateoasResponse() throws Exception {
        CourseContentDTO contentDTO = new CourseContentDTO();
        contentDTO.setId(1); // Provide ID to satisfy validation
        contentDTO.setCourseId(testCourse.getId());
        contentDTO.setTitle("Test Content");
        contentDTO.setContentType("VIDEO");
        contentDTO.setUrl("http://test.com/video.mp4");
        contentDTO.setOrderIndex(1);

        mockMvc.perform(post("/api/course-contents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Content"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void getCourseContent_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create content first
        CourseContent content = new CourseContent();
        content.setCourseId(testCourse.getId());
        content.setTitle("Test Content");
        content.setContentType("VIDEO");
        content.setUrl("http://test.com/video.mp4");
        content.setOrderIndex(1);
        content = courseContentRepository.save(content);

        mockMvc.perform(get("/api/course-contents/" + content.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Content"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void updateCourseContent_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create content first
        CourseContent content = new CourseContent();
        content.setCourseId(testCourse.getId());
        content.setTitle("Test Content");
        content.setContentType("VIDEO");
        content.setUrl("http://test.com/video.mp4");
        content.setOrderIndex(1);
        content = courseContentRepository.save(content);

        CourseContentDTO updateDTO = new CourseContentDTO();
        updateDTO.setId(content.getId()); // Set the ID to satisfy @NotNull validation
        updateDTO.setCourseId(testCourse.getId());
        updateDTO.setTitle("Updated Content");
        updateDTO.setContentType("TEXT");
        updateDTO.setUrl("http://test.com/text.pdf");
        updateDTO.setOrderIndex(2);

        mockMvc.perform(put("/api/course-contents/" + content.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Content"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void deleteCourseContent_ShouldReturnNoContent() throws Exception {
        // Given - Create content first
        CourseContent content = new CourseContent();
        content.setCourseId(testCourse.getId());
        content.setTitle("Test Content");
        content.setContentType("VIDEO");
        content.setUrl("http://test.com/video.mp4");
        content.setOrderIndex(1);
        content = courseContentRepository.save(content);

        mockMvc.perform(delete("/api/course-contents/" + content.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCourseContentsByCourse_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create content first
        CourseContent content = new CourseContent();
        content.setCourseId(testCourse.getId());
        content.setTitle("Test Content");
        content.setContentType("VIDEO");
        content.setUrl("http://test.com/video.mp4");
        content.setOrderIndex(1);
        courseContentRepository.save(content);

        mockMvc.perform(get("/api/course-contents/course/" + testCourse.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test Content"));
    }
}
