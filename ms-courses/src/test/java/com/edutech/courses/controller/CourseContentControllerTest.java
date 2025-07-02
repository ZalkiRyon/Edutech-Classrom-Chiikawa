package com.edutech.courses.controller;

import com.edutech.common.dto.CourseContentDTO;
import com.edutech.courses.service.CourseContentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseContentController.class)
class CourseContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseContentService courseContentService;

    private ObjectMapper objectMapper;
    private CourseContentDTO courseContentDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        courseContentDTO = new CourseContentDTO();
        courseContentDTO.setId(1);
        courseContentDTO.setCourseId(1);
        courseContentDTO.setTitle("Test Content");
        courseContentDTO.setContentType("VIDEO");
        courseContentDTO.setUrl("http://test.com/video.mp4");
        courseContentDTO.setOrderIndex(1);
    }

    @Test
    void testGetCourseContentsByCourseId() throws Exception {
        // Arrange
        List<CourseContentDTO> contents = Arrays.asList(courseContentDTO);
        when(courseContentService.findByCourseId(eq(1))).thenReturn(contents);

        // Act & Assert
        mockMvc.perform(get("/api/course-contents/course/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courseContentDTOList").exists())
                .andExpect(jsonPath("$._embedded.courseContentDTOList[0].title").value("Test Content"))
                .andExpect(jsonPath("$._embedded.courseContentDTOList[0].contentType").value("VIDEO"))
                .andExpect(jsonPath("$._embedded.courseContentDTOList[0].courseId").value(1))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.course-contents.href").exists());
    }

    @Test
    void testCreateCourseContent() throws Exception {
        // Arrange
        when(courseContentService.create(any(CourseContentDTO.class))).thenReturn(courseContentDTO);

        // Act & Assert
        mockMvc.perform(post("/api/course-contents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseContentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Content"));
    }

    @Test
    void testUpdateCourseContent() throws Exception {
        // Arrange
        CourseContentDTO updatedDTO = new CourseContentDTO();
        updatedDTO.setId(1);
        updatedDTO.setCourseId(1);
        updatedDTO.setTitle("Updated Content");
        updatedDTO.setContentType("TEXT");
        updatedDTO.setUrl("http://test.com/text.pdf");
        updatedDTO.setOrderIndex(2);

        when(courseContentService.update(eq(1), any(CourseContentDTO.class))).thenReturn(updatedDTO);

        // Act & Assert
        mockMvc.perform(put("/api/course-contents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Content"));
    }

    @Test
    void testDeleteCourseContent() throws Exception {
        // Arrange
        doNothing().when(courseContentService).delete(eq(1));

        // Act & Assert
        mockMvc.perform(delete("/api/course-contents/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}