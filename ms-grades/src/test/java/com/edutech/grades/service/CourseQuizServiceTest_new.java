package com.edutech.grades.service;

import com.edutech.common.dto.CourseQuizDTO;
import com.edutech.grades.entity.CourseQuiz;
import com.edutech.grades.mapper.CourseQuizMapperManual;
import com.edutech.grades.repository.CourseQuizRepository;
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
class CourseQuizServiceTest {

    @Mock
    private CourseQuizRepository courseQuizRepository;

    @Mock
    private CourseQuizMapperManual courseQuizMapper;

    @InjectMocks
    private CourseQuizService courseQuizService;

    private CourseQuiz courseQuiz;
    private CourseQuizDTO courseQuizDTO;

    @BeforeEach
    void setUp() {
        courseQuiz = new CourseQuiz();
        courseQuiz.setId(1);
        courseQuiz.setCourseId(1);
        courseQuiz.setTitle("Quiz de Prueba");
        courseQuiz.setDescription("Descripción del quiz de prueba");
        courseQuiz.setQuizType("Multiple Choice");
        courseQuiz.setCreatedAt(Instant.now());

        courseQuizDTO = new CourseQuizDTO();
        courseQuizDTO.setId(1);
        courseQuizDTO.setCourseId(1);
        courseQuizDTO.setTitle("Quiz de Prueba");
        courseQuizDTO.setDescription("Descripción del quiz de prueba");
        courseQuizDTO.setQuizType("Multiple Choice");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<CourseQuiz> courseQuizzes = Arrays.asList(courseQuiz);
        List<CourseQuizDTO> expectedDTOs = Arrays.asList(courseQuizDTO);

        when(courseQuizRepository.findAll()).thenReturn(courseQuizzes);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        List<CourseQuizDTO> result = courseQuizService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDTOs.get(0).getTitle(), result.get(0).getTitle());
        verify(courseQuizRepository).findAll();
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testFindById() {
        // Arrange
        when(courseQuizRepository.findById(1)).thenReturn(Optional.of(courseQuiz));
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        CourseQuizDTO result = courseQuizService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(courseQuizDTO.getTitle(), result.getTitle());
        assertEquals(courseQuizDTO.getCourseId(), result.getCourseId());
        verify(courseQuizRepository).findById(1);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(courseQuizRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> courseQuizService.findById(1));
        verify(courseQuizRepository).findById(1);
    }

    @Test
    void testFindByCourseId() {
        // Arrange
        List<CourseQuiz> courseQuizzes = Arrays.asList(courseQuiz);
        List<CourseQuizDTO> expectedDTOs = Arrays.asList(courseQuizDTO);

        when(courseQuizRepository.findByCourseId(1)).thenReturn(courseQuizzes);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        List<CourseQuizDTO> result = courseQuizService.findByCourseId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDTOs.get(0).getTitle(), result.get(0).getTitle());
        verify(courseQuizRepository).findByCourseId(1);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testFindByQuizType() {
        // Arrange
        String quizType = "Multiple Choice";
        List<CourseQuiz> courseQuizzes = Arrays.asList(courseQuiz);
        List<CourseQuizDTO> expectedDTOs = Arrays.asList(courseQuizDTO);

        when(courseQuizRepository.findByQuizType(quizType)).thenReturn(courseQuizzes);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        List<CourseQuizDTO> result = courseQuizService.findByQuizType(quizType);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDTOs.get(0).getQuizType(), result.get(0).getQuizType());
        verify(courseQuizRepository).findByQuizType(quizType);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testCreate() {
        // Arrange
        when(courseQuizMapper.toEntity(courseQuizDTO)).thenReturn(courseQuiz);
        when(courseQuizRepository.save(courseQuiz)).thenReturn(courseQuiz);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        CourseQuizDTO result = courseQuizService.create(courseQuizDTO);

        // Assert
        assertNotNull(result);
        assertEquals(courseQuizDTO.getTitle(), result.getTitle());
        verify(courseQuizRepository).save(courseQuiz);
        verify(courseQuizMapper).toEntity(courseQuizDTO);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testUpdate() {
        // Arrange
        when(courseQuizRepository.findById(1)).thenReturn(Optional.of(courseQuiz));
        when(courseQuizMapper.toEntity(courseQuizDTO)).thenReturn(courseQuiz);
        when(courseQuizRepository.save(courseQuiz)).thenReturn(courseQuiz);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        CourseQuizDTO result = courseQuizService.update(1, courseQuizDTO);

        // Assert
        assertNotNull(result);
        assertEquals(courseQuizDTO.getTitle(), result.getTitle());
        verify(courseQuizRepository).findById(1);
        verify(courseQuizRepository).save(courseQuiz);
        verify(courseQuizMapper).toEntity(courseQuizDTO);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testDelete() {
        // Arrange
        when(courseQuizRepository.findById(1)).thenReturn(Optional.of(courseQuiz));

        // Act
        courseQuizService.delete(1);

        // Assert
        verify(courseQuizRepository).findById(1);
        verify(courseQuizRepository).delete(courseQuiz);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(courseQuizRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> courseQuizService.delete(1));
        verify(courseQuizRepository).findById(1);
        verify(courseQuizRepository, never()).delete(any());
    }

    @Test
    void testExistsById() {
        // Arrange
        when(courseQuizRepository.existsById(1)).thenReturn(true);

        // Act
        boolean result = courseQuizService.existsById(1);

        // Assert
        assertTrue(result);
        verify(courseQuizRepository).existsById(1);
    }

    @Test
    void testCountByCourseId() {
        // Arrange
        when(courseQuizRepository.countByCourseId(1)).thenReturn(3L);

        // Act
        long result = courseQuizService.countByCourseId(1);

        // Assert
        assertEquals(3L, result);
        verify(courseQuizRepository).countByCourseId(1);
    }

    @Test
    void testExistsByCourseId() {
        // Arrange
        when(courseQuizRepository.existsByCourseId(1)).thenReturn(true);

        // Act
        boolean result = courseQuizService.existsByCourseId(1);

        // Assert
        assertTrue(result);
        verify(courseQuizRepository).existsByCourseId(1);
    }
}
