package com.edutech.courses.service;

import com.edutech.common.dto.CourseCommentDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.courses.entity.CourseComment;
import com.edutech.courses.mapper.CourseCommentMapperManual;
import com.edutech.courses.repository.CourseCommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseCommentServiceTest {

    @Mock
    private CourseCommentRepository courseCommentRepository;

    @Mock
    private CourseCommentMapperManual courseCommentMapper;

    @InjectMocks
    private CourseCommentService courseCommentService;

    private CourseComment courseComment;
    private CourseCommentDTO courseCommentDTO;

    @BeforeEach
    void setUp() {
        courseComment = new CourseComment();
        courseComment.setId(1);
        courseComment.setCourseId(1);
        courseComment.setUserId(15);
        courseComment.setCommentText("Excelente curso, muy recomendado");
        courseComment.setRating(5);
        courseComment.setCreatedAt(Instant.now());

        courseCommentDTO = new CourseCommentDTO();
        courseCommentDTO.setId(1);
        courseCommentDTO.setCourseId(1);
        courseCommentDTO.setUserId(15);
        courseCommentDTO.setCommentText("Excelente curso, muy recomendado");
        courseCommentDTO.setRating(5);
        courseCommentDTO.setCreatedAt(Instant.now());
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        List<CourseComment> comments = Arrays.asList(courseComment);
        when(courseCommentRepository.findAll()).thenReturn(comments);
        when(courseCommentMapper.toDTO(courseComment)).thenReturn(courseCommentDTO);

        // Act
        List<CourseCommentDTO> result = courseCommentService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Excelente curso, muy recomendado", result.get(0).getCommentText());
        assertEquals(5, result.get(0).getRating());
        verify(courseCommentRepository).findAll();
        verify(courseCommentMapper).toDTO(courseComment);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(courseCommentRepository.findById(1)).thenReturn(Optional.of(courseComment));
        when(courseCommentMapper.toDTO(courseComment)).thenReturn(courseCommentDTO);

        // Act
        CourseCommentDTO result = courseCommentService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Excelente curso, muy recomendado", result.getCommentText());
        assertEquals(5, result.getRating());
        verify(courseCommentRepository).findById(1);
        verify(courseCommentMapper).toDTO(courseComment);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(courseCommentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> courseCommentService.findById(999));
        verify(courseCommentRepository).findById(999);
        verify(courseCommentMapper, never()).toDTO(any());
    }

    @Test
    void testFindByCourseId_Success() {
        // Arrange
        List<CourseComment> comments = Arrays.asList(courseComment);
        when(courseCommentRepository.findByCourseIdOrderByCreatedAtDesc(1)).thenReturn(comments);
        when(courseCommentMapper.toDTO(courseComment)).thenReturn(courseCommentDTO);

        // Act
        List<CourseCommentDTO> result = courseCommentService.findByCourseId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Excelente curso, muy recomendado", result.get(0).getCommentText());
        verify(courseCommentRepository).findByCourseIdOrderByCreatedAtDesc(1);
        verify(courseCommentMapper).toDTO(courseComment);
    }

    @Test
    void testFindByUserId_Success() {
        // Arrange
        List<CourseComment> comments = Arrays.asList(courseComment);
        when(courseCommentRepository.findByUserIdOrderByCreatedAtDesc(15)).thenReturn(comments);
        when(courseCommentMapper.toDTO(courseComment)).thenReturn(courseCommentDTO);

        // Act
        List<CourseCommentDTO> result = courseCommentService.findByUserId(15);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(15, result.get(0).getUserId());
        verify(courseCommentRepository).findByUserIdOrderByCreatedAtDesc(15);
        verify(courseCommentMapper).toDTO(courseComment);
    }

    @Test
    void testCreate_Success() {
        // Arrange
        when(courseCommentMapper.toEntity(courseCommentDTO)).thenReturn(courseComment);
        when(courseCommentRepository.save(courseComment)).thenReturn(courseComment);
        when(courseCommentMapper.toDTO(courseComment)).thenReturn(courseCommentDTO);

        // Act
        CourseCommentDTO result = courseCommentService.create(courseCommentDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Excelente curso, muy recomendado", result.getCommentText());
        assertEquals(5, result.getRating());
        verify(courseCommentMapper).toEntity(courseCommentDTO);
        verify(courseCommentRepository).save(courseComment);
        verify(courseCommentMapper).toDTO(courseComment);
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        CourseCommentDTO updateDTO = new CourseCommentDTO();
        updateDTO.setCommentText("Curso actualizado - muy bueno");
        updateDTO.setRating(4);

        CourseComment updatedEntity = new CourseComment();
        updatedEntity.setId(1);
        updatedEntity.setCommentText("Curso actualizado - muy bueno");
        updatedEntity.setRating(4);

        CourseCommentDTO updatedDTO = new CourseCommentDTO();
        updatedDTO.setId(1);
        updatedDTO.setCommentText("Curso actualizado - muy bueno");
        updatedDTO.setRating(4);

        when(courseCommentRepository.existsById(1)).thenReturn(true);
        when(courseCommentMapper.toEntity(updateDTO)).thenReturn(courseComment);
        when(courseCommentRepository.save(any(CourseComment.class))).thenReturn(updatedEntity);
        when(courseCommentMapper.toDTO(updatedEntity)).thenReturn(updatedDTO);

        // Act
        CourseCommentDTO result = courseCommentService.update(1, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Curso actualizado - muy bueno", result.getCommentText());
        assertEquals(4, result.getRating());
        verify(courseCommentRepository).existsById(1);
        verify(courseCommentMapper).toEntity(updateDTO);
        verify(courseCommentRepository).save(any(CourseComment.class));
        verify(courseCommentMapper).toDTO(updatedEntity);
    }

    @Test
    void testUpdate_NotFound() {
        // Arrange
        when(courseCommentRepository.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> courseCommentService.update(999, courseCommentDTO));
        verify(courseCommentRepository).existsById(999);
        verify(courseCommentRepository, never()).save(any());
    }

    @Test
    void testDelete_Success() {
        // Arrange
        when(courseCommentRepository.findById(1)).thenReturn(Optional.of(courseComment));

        // Act
        courseCommentService.delete(1);

        // Assert
        verify(courseCommentRepository).findById(1);
        verify(courseCommentRepository).delete(courseComment);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(courseCommentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> courseCommentService.delete(999));
        verify(courseCommentRepository).findById(999);
        verify(courseCommentRepository, never()).delete(any());
    }
}
