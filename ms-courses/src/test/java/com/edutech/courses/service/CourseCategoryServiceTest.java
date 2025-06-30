package com.edutech.courses.service;

import com.edutech.common.dto.CourseCategoryDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.courses.entity.CourseCategory;
import com.edutech.courses.mapper.CourseCategoryMapper;
import com.edutech.courses.repository.CourseCategoryRepository;
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
class CourseCategoryServiceTest {

    @Mock
    private CourseCategoryRepository courseCategoryRepository;

    @Mock
    private CourseCategoryMapper courseCategoryMapper;

    @InjectMocks
    private CourseCategoryService courseCategoryService;

    private CourseCategory courseCategory;
    private CourseCategoryDTO courseCategoryDTO;

    @BeforeEach
    void setUp() {
        courseCategory = new CourseCategory();
        courseCategory.setId(1);
        courseCategory.setName("Programación");
        courseCategory.setDescription("Categoría de cursos de programación");

        courseCategoryDTO = new CourseCategoryDTO();
        courseCategoryDTO.setId(1);
        courseCategoryDTO.setName("Programación");
        courseCategoryDTO.setDescription("Categoría de cursos de programación");
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        List<CourseCategory> categories = Arrays.asList(courseCategory);
        when(courseCategoryRepository.findAll()).thenReturn(categories);
        when(courseCategoryMapper.toDTO(courseCategory)).thenReturn(courseCategoryDTO);

        // Act
        List<CourseCategoryDTO> result = courseCategoryService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Programación", result.get(0).getName());
        verify(courseCategoryRepository).findAll();
        verify(courseCategoryMapper).toDTO(courseCategory);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(courseCategoryRepository.findById(1)).thenReturn(Optional.of(courseCategory));
        when(courseCategoryMapper.toDTO(courseCategory)).thenReturn(courseCategoryDTO);

        // Act
        CourseCategoryDTO result = courseCategoryService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Programación", result.getName());
        verify(courseCategoryRepository).findById(1);
        verify(courseCategoryMapper).toDTO(courseCategory);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(courseCategoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> courseCategoryService.findById(999));
        verify(courseCategoryRepository).findById(999);
        verify(courseCategoryMapper, never()).toDTO(any());
    }

    @Test
    void testCreate_Success() {
        // Arrange
        when(courseCategoryMapper.toEntity(courseCategoryDTO)).thenReturn(courseCategory);
        when(courseCategoryRepository.save(courseCategory)).thenReturn(courseCategory);
        when(courseCategoryMapper.toDTO(courseCategory)).thenReturn(courseCategoryDTO);

        // Act
        CourseCategoryDTO result = courseCategoryService.create(courseCategoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Programación", result.getName());
        verify(courseCategoryMapper).toEntity(courseCategoryDTO);
        verify(courseCategoryRepository).save(courseCategory);
        verify(courseCategoryMapper).toDTO(courseCategory);
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        CourseCategoryDTO updateDTO = new CourseCategoryDTO();
        updateDTO.setName("Programación Avanzada");
        updateDTO.setDescription("Categoría de cursos de programación avanzada");

        CourseCategory updatedEntity = new CourseCategory();
        updatedEntity.setId(1);
        updatedEntity.setName("Programación Avanzada");
        updatedEntity.setDescription("Categoría de cursos de programación avanzada");

        CourseCategoryDTO updatedDTO = new CourseCategoryDTO();
        updatedDTO.setId(1);
        updatedDTO.setName("Programación Avanzada");
        updatedDTO.setDescription("Categoría de cursos de programación avanzada");

        when(courseCategoryRepository.findById(1)).thenReturn(Optional.of(courseCategory));
        when(courseCategoryMapper.toEntity(updateDTO)).thenReturn(courseCategory);
        when(courseCategoryRepository.save(any(CourseCategory.class))).thenReturn(updatedEntity);
        when(courseCategoryMapper.toDTO(updatedEntity)).thenReturn(updatedDTO);

        // Act
        CourseCategoryDTO result = courseCategoryService.update(1, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Programación Avanzada", result.getName());
        verify(courseCategoryRepository).findById(1);
        verify(courseCategoryMapper).toEntity(updateDTO);
        verify(courseCategoryRepository).save(any(CourseCategory.class));
        verify(courseCategoryMapper).toDTO(updatedEntity);
    }

    @Test
    void testUpdate_NotFound() {
        // Arrange
        when(courseCategoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> courseCategoryService.update(999, courseCategoryDTO));
        verify(courseCategoryRepository).findById(999);
        verify(courseCategoryRepository, never()).save(any());
    }

    @Test
    void testDelete_Success() {
        // Arrange
        when(courseCategoryRepository.findById(1)).thenReturn(Optional.of(courseCategory));

        // Act
        courseCategoryService.delete(1);

        // Assert
        verify(courseCategoryRepository).findById(1);
        verify(courseCategoryRepository).delete(courseCategory);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(courseCategoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> courseCategoryService.delete(999));
        verify(courseCategoryRepository).findById(999);
        verify(courseCategoryRepository, never()).delete(any());
    }
}
