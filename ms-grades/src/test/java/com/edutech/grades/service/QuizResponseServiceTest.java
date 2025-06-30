package com.edutech.grades.service;

import com.edutech.common.dto.QuizResponseDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.grades.entity.CourseQuiz;
import com.edutech.grades.entity.QuizResponse;
import com.edutech.grades.mapper.QuizResponseMapperManual;
import com.edutech.grades.repository.CourseQuizRepository;
import com.edutech.grades.repository.QuizResponseRepository;
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
class QuizResponseServiceTest {

    @Mock
    private QuizResponseRepository quizResponseRepository;

    @Mock
    private QuizResponseMapperManual quizResponseMapper;

    @Mock
    private CourseQuizRepository courseQuizRepository;

    @InjectMocks
    private QuizResponseService quizResponseService;

    private QuizResponse quizResponse;
    private QuizResponseDTO quizResponseDTO;

    @BeforeEach
    void setUp() {
        quizResponse = new QuizResponse();
        quizResponse.setId(1);
        quizResponse.setQuizId(1);
        quizResponse.setStudentId(1);
        quizResponse.setSelectedOption("A");
        quizResponse.setResponseContent("Respuesta de prueba");
        quizResponse.setSubmittedAt(Instant.now());

        quizResponseDTO = new QuizResponseDTO();
        quizResponseDTO.setId(1);
        quizResponseDTO.setQuizId(1);
        quizResponseDTO.setStudentId(1);
        quizResponseDTO.setSelectedOption("A");
        quizResponseDTO.setResponseContent("Respuesta de prueba");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<QuizResponse> quizResponses = Arrays.asList(quizResponse);
        when(quizResponseRepository.findAll()).thenReturn(quizResponses);
        when(quizResponseMapper.toDTO(quizResponse)).thenReturn(quizResponseDTO);

        // Act
        List<QuizResponseDTO> result = quizResponseService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(quizResponseDTO.getSelectedOption(), result.get(0).getSelectedOption());
        verify(quizResponseRepository).findAll();
        verify(quizResponseMapper).toDTO(quizResponse);
    }

    @Test
    void testFindById() {
        // Arrange
        when(quizResponseRepository.findById(1)).thenReturn(Optional.of(quizResponse));
        when(quizResponseMapper.toDTO(quizResponse)).thenReturn(quizResponseDTO);

        // Act
        QuizResponseDTO result = quizResponseService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(quizResponseDTO.getSelectedOption(), result.getSelectedOption());
        assertEquals(quizResponseDTO.getQuizId(), result.getQuizId());
        verify(quizResponseRepository).findById(1);
        verify(quizResponseMapper).toDTO(quizResponse);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(quizResponseRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> quizResponseService.findById(1));
        assertNotNull(exception);
        verify(quizResponseRepository).findById(1);
        verify(quizResponseMapper, never()).toDTO(any());
    }

    @Test
    void testFindByQuizId() {
        // Arrange
        List<QuizResponse> quizResponses = Arrays.asList(quizResponse);
        when(quizResponseRepository.findByQuizId(1)).thenReturn(quizResponses);
        when(quizResponseMapper.toDTO(quizResponse)).thenReturn(quizResponseDTO);

        // Act
        List<QuizResponseDTO> result = quizResponseService.findByQuizId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(quizResponseDTO.getQuizId(), result.get(0).getQuizId());
        verify(quizResponseRepository).findByQuizId(1);
        verify(quizResponseMapper).toDTO(quizResponse);
    }

    @Test
    void testFindByStudentId() {
        // Arrange
        List<QuizResponse> quizResponses = Arrays.asList(quizResponse);
        when(quizResponseRepository.findByStudentId(1)).thenReturn(quizResponses);
        when(quizResponseMapper.toDTO(quizResponse)).thenReturn(quizResponseDTO);

        // Act
        List<QuizResponseDTO> result = quizResponseService.findByStudentId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(quizResponseDTO.getStudentId(), result.get(0).getStudentId());
        verify(quizResponseRepository).findByStudentId(1);
        verify(quizResponseMapper).toDTO(quizResponse);
    }

    @Test
    void testFindByQuizIdAndStudentId() {
        // Arrange
        List<QuizResponse> quizResponses = Arrays.asList(quizResponse);
        when(quizResponseRepository.findByStudentIdAndQuizId(1, 1)).thenReturn(quizResponses);
        when(quizResponseMapper.toDTO(quizResponse)).thenReturn(quizResponseDTO);

        // Act
        List<QuizResponseDTO> result = quizResponseService.findByQuizIdAndStudentId(1, 1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(quizResponseDTO.getQuizId(), result.get(0).getQuizId());
        assertEquals(quizResponseDTO.getStudentId(), result.get(0).getStudentId());
        verify(quizResponseRepository).findByStudentIdAndQuizId(1, 1);
        verify(quizResponseMapper).toDTO(quizResponse);
    }

    @Test
    void testCreate() {
        // Arrange
        CourseQuiz courseQuiz = new CourseQuiz();
        courseQuiz.setId(1);
        
        when(courseQuizRepository.findById(1)).thenReturn(Optional.of(courseQuiz));
        when(quizResponseMapper.toEntity(quizResponseDTO)).thenReturn(quizResponse);
        when(quizResponseRepository.save(quizResponse)).thenReturn(quizResponse);
        when(quizResponseMapper.toDTO(quizResponse)).thenReturn(quizResponseDTO);

        // Act
        QuizResponseDTO result = quizResponseService.create(quizResponseDTO);

        // Assert
        assertNotNull(result);
        assertEquals(quizResponseDTO.getSelectedOption(), result.getSelectedOption());
        verify(courseQuizRepository).findById(1);
        verify(quizResponseMapper).toEntity(quizResponseDTO);
        verify(quizResponseRepository).save(quizResponse);
        verify(quizResponseMapper).toDTO(quizResponse);
    }

    @Test
    void testUpdate() {
        // Arrange
        CourseQuiz courseQuiz = new CourseQuiz();
        courseQuiz.setId(1);
        
        when(quizResponseRepository.findById(1)).thenReturn(Optional.of(quizResponse));
        when(courseQuizRepository.findById(1)).thenReturn(Optional.of(courseQuiz));
        when(quizResponseMapper.toEntity(quizResponseDTO)).thenReturn(quizResponse);
        when(quizResponseRepository.save(any(QuizResponse.class))).thenReturn(quizResponse);
        when(quizResponseMapper.toDTO(quizResponse)).thenReturn(quizResponseDTO);

        // Act
        QuizResponseDTO result = quizResponseService.update(1, quizResponseDTO);

        // Assert
        assertNotNull(result);
        assertEquals(quizResponseDTO.getSelectedOption(), result.getSelectedOption());
        verify(quizResponseRepository).findById(1);
        verify(courseQuizRepository).findById(1);
        verify(quizResponseMapper).toEntity(quizResponseDTO);
        verify(quizResponseRepository).save(any(QuizResponse.class));
        verify(quizResponseMapper).toDTO(quizResponse);
    }

    @Test
    void testUpdateNotFound() {
        // Arrange
        when(quizResponseRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> quizResponseService.update(1, quizResponseDTO));
        assertNotNull(exception);
        verify(quizResponseRepository).findById(1);
        verify(quizResponseRepository, never()).save(any());
        verify(quizResponseMapper, never()).toDTO(any());
    }

    @Test
    void testDelete() {
        // Arrange
        when(quizResponseRepository.findById(1)).thenReturn(Optional.of(quizResponse));

        // Act
        quizResponseService.delete(1);

        // Assert
        verify(quizResponseRepository).findById(1);
        verify(quizResponseRepository).delete(quizResponse);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        when(quizResponseRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> quizResponseService.delete(1));
        verify(quizResponseRepository).findById(1);
        verify(quizResponseRepository, never()).delete(any());
    }
}
