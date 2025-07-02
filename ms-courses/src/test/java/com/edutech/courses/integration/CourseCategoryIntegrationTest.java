package com.edutech.courses.integration;

import com.edutech.common.dto.CourseCategoryDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.courses.ClassroomCoursesModuleApplication;
import com.edutech.courses.client.UserClient;
import com.edutech.courses.entity.CourseCategory;
import com.edutech.courses.repository.CourseCategoryRepository;
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
class CourseCategoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserClient userClient;

    @Autowired
    private CourseCategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        // Clean up database
        categoryRepository.deleteAll();
        
        // Mock UserClient
        UserDTO mockUser = new UserDTO();
        mockUser.setId(1);
        mockUser.setFirstName("Test");
        mockUser.setLastName("User");
        mockUser.setEmail("test@edutech.com");
        
        when(userClient.findById(anyInt())).thenReturn(mockUser);
    }

    @Test
    void getAllCourseCategories_ShouldReturnHateoasCollectionResponse() throws Exception {
        mockMvc.perform(get("/api/course-categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void createCourseCategory_ShouldReturnHateoasResponse() throws Exception {
        CourseCategoryDTO categoryDTO = new CourseCategoryDTO();
        categoryDTO.setName("Programming");
        categoryDTO.setDescription("Programming courses category");

        mockMvc.perform(post("/api/course-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Programming"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void getCourseCategoryById_ShouldReturnHateoasResponse() throws Exception {
        // Given - Create category first
        CourseCategory category = new CourseCategory();
        category.setName("Programming");
        category.setDescription("Programming courses category");
        category = categoryRepository.save(category);

        mockMvc.perform(get("/api/course-categories/" + category.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Programming"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void updateCourseCategory_ShouldReturnUpdatedHateoasResponse() throws Exception {
        // Given - Create category first
        CourseCategory category = new CourseCategory();
        category.setName("Programming");
        category.setDescription("Programming courses category");
        category = categoryRepository.save(category);

        CourseCategoryDTO updateDTO = new CourseCategoryDTO();
        updateDTO.setName("Advanced Programming");
        updateDTO.setDescription("Advanced programming courses category");

        mockMvc.perform(put("/api/course-categories/" + category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Advanced Programming"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    void deleteCourseCategory_ShouldReturnNoContent() throws Exception {
        // Given - Create category first
        CourseCategory category = new CourseCategory();
        category.setName("Programming");
        category.setDescription("Programming courses category");
        category = categoryRepository.save(category);

        mockMvc.perform(delete("/api/course-categories/" + category.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
