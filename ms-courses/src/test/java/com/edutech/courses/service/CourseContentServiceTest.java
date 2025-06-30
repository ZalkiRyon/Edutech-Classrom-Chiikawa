package com.edutech.courses.service;

import com.edutech.common.dto.CourseContentDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.courses.entity.CourseContent;
import com.edutech.courses.mapper.CourseContentMapperManual;
import com.edutech.courses.repository.CourseContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseContentServiceTest {

    @Mock
    private CourseContentRepository courseContentRepository;

    @Mock
    private CourseContentMapperManual courseContentMapper;

    @InjectMocks
    private CourseContentService courseContentService;

    private CourseContent courseContent;
    private CourseContentDTO courseContentDTO;

    @BeforeEach
    void setUp() {
        courseContent = new CourseContent();
        courseContent.setId(1);
        courseContent.setCourseId(1);
        courseContent.setTitle("Introducción al Curso");
        courseContent.setContentType("Video");
        courseContent.setUrl("https://example.com/video1.mp4");
        courseContent.setOrderIndex(1);

        courseContentDTO = new CourseContentDTO();
        courseContentDTO.setId(1);
        courseContentDTO.setCourseId(1);
        courseContentDTO.setTitle("Introducción al Curso");
        courseContentDTO.setContentType("Video");
        courseContentDTO.setUrl("https://example.com/video1.mp4");
        courseContentDTO.setOrderIndex(1);
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        List<CourseContent> contents = Arrays.asList(courseContent);
        when(courseContentRepository.findAll()).thenReturn(contents);
        when(courseContentMapper.toDTO(courseContent)).thenReturn(courseContentDTO);

        // Act
        List<CourseContentDTO> result = courseContentService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Introducción al Curso", result.get(0).getTitle());
        verify(courseContentRepository).findAll();
        verify(courseContentMapper).toDTO(courseContent);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(courseContentRepository.findById(1)).thenReturn(Optional.of(courseContent));
        when(courseContentMapper.toDTO(courseContent)).thenReturn(courseContentDTO);

        // Act
        CourseContentDTO result = courseContentService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Introducción al Curso", result.getTitle());
        assertEquals("Video", result.getContentType());
        verify(courseContentRepository).findById(1);
        verify(courseContentMapper).toDTO(courseContent);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(courseContentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> courseContentService.findById(999));
        verify(courseContentRepository).findById(999);
        verify(courseContentMapper, never()).toDTO(any());
    }

    @Test
    void testFindByCourseId_Success() {
        // Arrange
        List<CourseContent> contents = Arrays.asList(courseContent);
        when(courseContentRepository.findByCourseIdOrderByOrderIndexAsc(1)).thenReturn(contents);
        when(courseContentMapper.toDTO(courseContent)).thenReturn(courseContentDTO);

        // Act
        List<CourseContentDTO> result = courseContentService.findByCourseId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Introducción al Curso", result.get(0).getTitle());
        verify(courseContentRepository).findByCourseIdOrderByOrderIndexAsc(1);
        verify(courseContentMapper).toDTO(courseContent);
    }

    @Test
    void testCreate_Success() {
        // Arrange
        when(courseContentMapper.toEntity(courseContentDTO)).thenReturn(courseContent);
        when(courseContentRepository.save(courseContent)).thenReturn(courseContent);
        when(courseContentMapper.toDTO(courseContent)).thenReturn(courseContentDTO);

        // Act
        CourseContentDTO result = courseContentService.create(courseContentDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Introducción al Curso", result.getTitle());
        verify(courseContentMapper).toEntity(courseContentDTO);
        verify(courseContentRepository).save(courseContent);
        verify(courseContentMapper).toDTO(courseContent);
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        CourseContentDTO updateDTO = new CourseContentDTO();
        updateDTO.setTitle("Introducción Actualizada");
        updateDTO.setContentType("PDF");
        updateDTO.setUrl("https://example.com/documento.pdf");

        CourseContent updatedEntity = new CourseContent();
        updatedEntity.setId(1);
        updatedEntity.setTitle("Introducción Actualizada");
        updatedEntity.setContentType("PDF");
        updatedEntity.setUrl("https://example.com/documento.pdf");

        CourseContentDTO updatedDTO = new CourseContentDTO();
        updatedDTO.setId(1);
        updatedDTO.setTitle("Introducción Actualizada");
        updatedDTO.setContentType("PDF");
        updatedDTO.setUrl("https://example.com/documento.pdf");

        when(courseContentRepository.existsById(1)).thenReturn(true);
        when(courseContentMapper.toEntity(updateDTO)).thenReturn(courseContent);
        when(courseContentRepository.save(any(CourseContent.class))).thenReturn(updatedEntity);
        when(courseContentMapper.toDTO(updatedEntity)).thenReturn(updatedDTO);

        // Act
        CourseContentDTO result = courseContentService.update(1, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Introducción Actualizada", result.getTitle());
        assertEquals("PDF", result.getContentType());
        verify(courseContentRepository).existsById(1);
        verify(courseContentMapper).toEntity(updateDTO);
        verify(courseContentRepository).save(any(CourseContent.class));
        verify(courseContentMapper).toDTO(updatedEntity);
    }

    @Test
    void testUpdate_NotFound() {
        // Arrange
        when(courseContentRepository.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> courseContentService.update(999, courseContentDTO));
        verify(courseContentRepository).existsById(999);
        verify(courseContentRepository, never()).save(any());
    }

    @Test
    void testDelete_Success() {
        // Arrange
        when(courseContentRepository.findById(1)).thenReturn(Optional.of(courseContent));

        // Act
        courseContentService.delete(1);

        // Assert
        verify(courseContentRepository).findById(1);
        verify(courseContentRepository).delete(courseContent);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(courseContentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> courseContentService.delete(999));
        verify(courseContentRepository).findById(999);
        verify(courseContentRepository, never()).delete(any());
    }
}
