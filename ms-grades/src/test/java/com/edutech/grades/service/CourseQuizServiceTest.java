package com.edutech.grades.service;

import com.edutech.common.dto.QuizDTO;
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
    private QuizDTO quizDTO;

    @BeforeEach
    void setUp() {
        courseQuiz = new CourseQuiz();
        courseQuiz.setId(1);
        courseQuiz.setCourseId(1);
        courseQuiz.setTitle("Quiz de Prueba");
        courseQuiz.setDescription("Descripción del quiz de prueba");
        courseQuiz.setQuizType("Multiple Choice");
        courseQuiz.setCreatedAt(Instant.now());

        quizDTO = new QuizDTO();
        quizDTO.setId(1);
        quizDTO.setCourseId(1);
        quizDTO.setTitle("Quiz de Prueba");
        quizDTO.setDescription("Descripción del quiz de prueba");
        quizDTO.setQuizType("Multiple Choice");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<CourseQuiz> courseQuizzes = Arrays.asList(courseQuiz);
        List<QuizDTO> expectedDTOs = Arrays.asList(quizDTO);
        
        when(courseQuizRepository.findAll()).thenReturn(courseQuizzes);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(quizDTO);

        // Act
        List<QuizDTO> result = courseQuizService.findAll();

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
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(quizDTO);

        // Act
        QuizDTO result = courseQuizService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(quizDTO.getTitle(), result.getTitle());
        assertEquals(quizDTO.getCourseId(), result.getCourseId());
        verify(courseQuizRepository).findById(1);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(courseQuizRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> courseQuizService.findById(1));
        verify(courseQuizRepository).findById(1);
        verify(courseQuizMapper, never()).toDTO(any());
    }

    @Test
    void testFindByCourseId() {
        // Arrange
        List<CourseQuiz> courseQuizzes = Arrays.asList(courseQuiz);
        List<QuizDTO> expectedDTOs = Arrays.asList(quizDTO);
        when(courseQuizRepository.findByCourseIdOrderByCreatedAtDesc(1)).thenReturn(courseQuizzes);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(quizDTO);

        // Act
        List<QuizDTO> result = courseQuizService.findByCourseId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDTOs.get(0).getTitle(), result.get(0).getTitle());
        verify(courseQuizRepository).findByCourseIdOrderByCreatedAtDesc(1);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testFindByQuizType() {
        // Arrange
        String quizType = "Multiple Choice";
        List<CourseQuiz> courseQuizzes = Arrays.asList(courseQuiz);
        List<QuizDTO> expectedDTOs = Arrays.asList(quizDTO);
        when(courseQuizRepository.findByQuizTypeOrderByCreatedAtDesc(quizType)).thenReturn(courseQuizzes);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(quizDTO);

        // Act
        List<QuizDTO> result = courseQuizService.findByQuizType(quizType);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDTOs.get(0).getQuizType(), result.get(0).getQuizType());
        verify(courseQuizRepository).findByQuizTypeOrderByCreatedAtDesc(quizType);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testCreate() {
        // Arrange
        when(courseQuizMapper.toEntity(quizDTO)).thenReturn(courseQuiz);
        when(courseQuizRepository.save(courseQuiz)).thenReturn(courseQuiz);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(quizDTO);

        // Act
        QuizDTO result = courseQuizService.create(quizDTO);

        // Assert
        assertNotNull(result);
        assertEquals(quizDTO.getTitle(), result.getTitle());
        verify(courseQuizMapper).toEntity(quizDTO);
        verify(courseQuizRepository).save(courseQuiz);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testUpdate() {
        // Arrange
        when(courseQuizRepository.existsById(1)).thenReturn(true);
        when(courseQuizMapper.toEntity(quizDTO)).thenReturn(courseQuiz);
        when(courseQuizRepository.save(any(CourseQuiz.class))).thenReturn(courseQuiz);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(quizDTO);

        // Act
        QuizDTO result = courseQuizService.update(1, quizDTO);

        // Assert
        assertNotNull(result);
        assertEquals(quizDTO.getTitle(), result.getTitle());
        verify(courseQuizRepository).existsById(1);
        verify(courseQuizMapper).toEntity(quizDTO);
        verify(courseQuizRepository).save(any(CourseQuiz.class));
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
    void testDeleteNotFound() {
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
        when(courseQuizRepository.countByCourseId(1)).thenReturn(5L);

        // Act
        long result = courseQuizService.countByCourseId(1);

        // Assert
        assertEquals(5L, result);
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
