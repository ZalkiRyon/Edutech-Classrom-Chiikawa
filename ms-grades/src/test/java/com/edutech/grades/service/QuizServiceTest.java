package com.edutech.grades.service;

import com.edutech.common.dto.QuizDTO;
import com.edutech.grades.entity.Quiz;
import com.edutech.grades.mapper.QuizMapperManual;
import com.edutech.grades.repository.QuizRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizMapperManual quizMapper;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;
    private QuizDTO quizDTO;

    @BeforeEach
    void setUp() {
        quiz = new Quiz();
        quiz.setId(1);
        quiz.setCourseId(1);
        quiz.setTitle("Test Quiz");
        quiz.setDescription("Test Description");
        quiz.setQuizType("EXAM");
        quiz.setCreatedAt(Instant.now());

        quizDTO = new QuizDTO();
        quizDTO.setId(1);
        quizDTO.setCourseId(1);
        quizDTO.setTitle("Test Quiz");
        quizDTO.setDescription("Test Description");
        quizDTO.setQuizType("EXAM");
        quizDTO.setCreatedAt(Instant.now());
    }

    @Test
    void createQuiz_Success() {
        // Given
        when(quizMapper.toEntity(quizDTO)).thenReturn(quiz);
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);
        when(quizMapper.toDTO(quiz)).thenReturn(quizDTO);

        // When
        QuizDTO result = quizService.createQuiz(quizDTO);

        // Then
        assertNotNull(result);
        assertEquals(quizDTO.getTitle(), result.getTitle());
        verify(quizRepository).save(any(Quiz.class));
        verify(quizMapper).toEntity(quizDTO);
        verify(quizMapper).toDTO(quiz);
    }

    @Test
    void createQuiz_NullInput_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> quizService.createQuiz(null));
    }

    @Test
    void getQuizById_Success() {
        // Given
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));
        when(quizMapper.toDTO(quiz)).thenReturn(quizDTO);

        // When
        Optional<QuizDTO> result = quizService.getQuizById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(quizDTO.getTitle(), result.get().getTitle());
        verify(quizRepository).findById(1);
        verify(quizMapper).toDTO(quiz);
    }

    @Test
    void getQuizById_NotFound() {
        // Given
        when(quizRepository.findById(1)).thenReturn(Optional.empty());

        // When
        Optional<QuizDTO> result = quizService.getQuizById(1);

        // Then
        assertTrue(result.isEmpty());
        verify(quizRepository).findById(1);
        verify(quizMapper, never()).toDTO(any());
    }

    @Test
    void getQuizById_NullId() {
        // When
        Optional<QuizDTO> result = quizService.getQuizById(null);

        // Then
        assertTrue(result.isEmpty());
        verify(quizRepository, never()).findById(any());
    }

    @Test
    void getAllQuizzes_Success() {
        // Given
        List<Quiz> quizzes = Arrays.asList(quiz);
        List<QuizDTO> quizDTOs = Arrays.asList(quizDTO);
        when(quizRepository.findAll()).thenReturn(quizzes);
        when(quizMapper.toDTOList(quizzes)).thenReturn(quizDTOs);

        // When
        List<QuizDTO> result = quizService.getAllQuizzes();

        // Then
        assertEquals(1, result.size());
        assertEquals(quizDTO.getTitle(), result.get(0).getTitle());
        verify(quizRepository).findAll();
        verify(quizMapper).toDTOList(quizzes);
    }

    @Test
    void getQuizzesByCourseId_Success() {
        // Given
        List<Quiz> quizzes = Arrays.asList(quiz);
        List<QuizDTO> quizDTOs = Arrays.asList(quizDTO);
        when(quizRepository.findByCourseId(1)).thenReturn(quizzes);
        when(quizMapper.toDTOList(quizzes)).thenReturn(quizDTOs);

        // When
        List<QuizDTO> result = quizService.getQuizzesByCourseId(1);

        // Then
        assertEquals(1, result.size());
        assertEquals(quizDTO.getCourseId(), result.get(0).getCourseId());
        verify(quizRepository).findByCourseId(1);
        verify(quizMapper).toDTOList(quizzes);
    }

    @Test
    void getQuizzesByCourseId_NullCourseId_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> quizService.getQuizzesByCourseId(null));
    }

    @Test
    void getQuizzesByType_Success() {
        // Given
        List<Quiz> quizzes = Arrays.asList(quiz);
        List<QuizDTO> quizDTOs = Arrays.asList(quizDTO);
        when(quizRepository.findByQuizType("EXAM")).thenReturn(quizzes);
        when(quizMapper.toDTOList(quizzes)).thenReturn(quizDTOs);

        // When
        List<QuizDTO> result = quizService.getQuizzesByType("EXAM");

        // Then
        assertEquals(1, result.size());
        assertEquals(quizDTO.getQuizType(), result.get(0).getQuizType());
        verify(quizRepository).findByQuizType("EXAM");
        verify(quizMapper).toDTOList(quizzes);
    }

    @Test
    void getQuizzesByType_NullType_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> quizService.getQuizzesByType(null));
    }

    @Test
    void getQuizzesByType_EmptyType_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> quizService.getQuizzesByType(""));
    }

    @Test
    void updateQuiz_Success() {
        // Given
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);
        when(quizMapper.toDTO(quiz)).thenReturn(quizDTO);

        // When
        QuizDTO result = quizService.updateQuiz(1, quizDTO);

        // Then
        assertNotNull(result);
        verify(quizRepository).findById(1);
        verify(quizMapper).updateEntityFromDTO(quizDTO, quiz);
        verify(quizRepository).save(quiz);
        verify(quizMapper).toDTO(quiz);
    }

    @Test
    void updateQuiz_NotFound_ThrowsException() {
        // Given
        when(quizRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> quizService.updateQuiz(1, quizDTO));
    }

    @Test
    void updateQuiz_NullId_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> quizService.updateQuiz(null, quizDTO));
    }

    @Test
    void updateQuiz_NullDTO_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> quizService.updateQuiz(1, null));
    }

    @Test
    void deleteQuiz_Success() {
        // Given
        when(quizRepository.existsById(1)).thenReturn(true);

        // When
        boolean result = quizService.deleteQuiz(1);

        // Then
        assertTrue(result);
        verify(quizRepository).existsById(1);
        verify(quizRepository).deleteById(1);
    }

    @Test
    void deleteQuiz_NotFound() {
        // Given
        when(quizRepository.existsById(1)).thenReturn(false);

        // When
        boolean result = quizService.deleteQuiz(1);

        // Then
        assertFalse(result);
        verify(quizRepository).existsById(1);
        verify(quizRepository, never()).deleteById(any());
    }

    @Test
    void deleteQuiz_NullId_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> quizService.deleteQuiz(null));
    }

    @Test
    void existsById_Success() {
        // Given
        when(quizRepository.existsById(1)).thenReturn(true);

        // When
        boolean result = quizService.existsById(1);

        // Then
        assertTrue(result);
        verify(quizRepository).existsById(1);
    }

    @Test
    void existsById_NotFound() {
        // Given
        when(quizRepository.existsById(1)).thenReturn(false);

        // When
        boolean result = quizService.existsById(1);

        // Then
        assertFalse(result);
        verify(quizRepository).existsById(1);
    }

    @Test
    void existsById_NullId() {
        // When
        boolean result = quizService.existsById(null);

        // Then
        assertFalse(result);
        verify(quizRepository, never()).existsById(any());
    }

    @Test
    void countQuizzesByCourseId_Success() {
        // Given
        when(quizRepository.countByCourseId(1)).thenReturn(5L);

        // When
        long result = quizService.countQuizzesByCourseId(1);

        // Then
        assertEquals(5L, result);
        verify(quizRepository).countByCourseId(1);
    }

    @Test
    void countQuizzesByCourseId_NullId() {
        // When
        long result = quizService.countQuizzesByCourseId(null);

        // Then
        assertEquals(0L, result);
        verify(quizRepository, never()).countByCourseId(any());
    }

    @Test
    void existsByCourseId_Success() {
        // Given
        when(quizRepository.existsByCourseId(1)).thenReturn(true);

        // When
        boolean result = quizService.existsByCourseId(1);

        // Then
        assertTrue(result);
        verify(quizRepository).existsByCourseId(1);
    }

    @Test
    void existsByCourseId_NullId() {
        // When
        boolean result = quizService.existsByCourseId(null);

        // Then
        assertFalse(result);
        verify(quizRepository, never()).existsByCourseId(any());
    }
}
