package com.edutech.grades.service;

import com.edutech.common.dto.CourseQuizQuestionDTO;
import com.edutech.grades.entity.CourseQuiz;
import com.edutech.grades.entity.CourseQuizQuestion;
import com.edutech.grades.mapper.CourseQuizQuestionMapperManual;
import com.edutech.grades.repository.CourseQuizQuestionRepository;
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
class CourseQuizQuestionServiceTest {

    @Mock
    private CourseQuizQuestionRepository courseQuizQuestionRepository;

    @Mock
    private CourseQuizQuestionMapperManual courseQuizQuestionMapper;

    @Mock
    private CourseQuizRepository courseQuizRepository;

    @InjectMocks
    private CourseQuizQuestionService courseQuizQuestionService;

    private CourseQuizQuestion courseQuizQuestion;
    private CourseQuizQuestionDTO courseQuizQuestionDTO;

    @BeforeEach
    void setUp() {
        courseQuizQuestion = new CourseQuizQuestion();
        courseQuizQuestion.setId(1);
        courseQuizQuestion.setQuizId(1);
        courseQuizQuestion.setQuestionText("¿Cuál es la respuesta correcta?");
        courseQuizQuestion.setCorrectOption("A");
        courseQuizQuestion.setOptionA("Opción A");
        courseQuizQuestion.setOptionB("Opción B");
        courseQuizQuestion.setOptionC("Opción C");
        courseQuizQuestion.setOptionD("Opción D");
        courseQuizQuestion.setOrderIndex(1);
        courseQuizQuestion.setCreatedAt(Instant.now());

        courseQuizQuestionDTO = new CourseQuizQuestionDTO();
        courseQuizQuestionDTO.setId(1);
        courseQuizQuestionDTO.setQuizId(1);
        courseQuizQuestionDTO.setQuestionText("¿Cuál es la respuesta correcta?");
        courseQuizQuestionDTO.setCorrectOption("A");
        courseQuizQuestionDTO.setOptionA("Opción A");
        courseQuizQuestionDTO.setOptionB("Opción B");
        courseQuizQuestionDTO.setOptionC("Opción C");
        courseQuizQuestionDTO.setOptionD("Opción D");
        courseQuizQuestionDTO.setOrderIndex(1);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<CourseQuizQuestion> questions = Arrays.asList(courseQuizQuestion);
        when(courseQuizQuestionRepository.findAll()).thenReturn(questions);
        when(courseQuizQuestionMapper.toDTO(courseQuizQuestion)).thenReturn(courseQuizQuestionDTO);

        // Act
        List<CourseQuizQuestionDTO> result = courseQuizQuestionService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseQuizQuestionDTO.getQuestionText(), result.get(0).getQuestionText());
        verify(courseQuizQuestionRepository).findAll();
        verify(courseQuizQuestionMapper).toDTO(courseQuizQuestion);
    }

    @Test
    void testFindById() {
        // Arrange
        when(courseQuizQuestionRepository.findById(1)).thenReturn(Optional.of(courseQuizQuestion));
        when(courseQuizQuestionMapper.toDTO(courseQuizQuestion)).thenReturn(courseQuizQuestionDTO);

        // Act
        CourseQuizQuestionDTO result = courseQuizQuestionService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(courseQuizQuestionDTO.getQuestionText(), result.getQuestionText());
        verify(courseQuizQuestionRepository).findById(1);
        verify(courseQuizQuestionMapper).toDTO(courseQuizQuestion);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(courseQuizQuestionRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> courseQuizQuestionService.findById(1));
        verify(courseQuizQuestionRepository).findById(1);
        verify(courseQuizQuestionMapper, never()).toDTO(any());
    }

    @Test
    void testFindByQuizId() {
        // Arrange
        List<CourseQuizQuestion> questions = Arrays.asList(courseQuizQuestion);
        when(courseQuizQuestionRepository.findByQuizId(1)).thenReturn(questions);
        when(courseQuizQuestionMapper.toDTO(courseQuizQuestion)).thenReturn(courseQuizQuestionDTO);

        // Act
        List<CourseQuizQuestionDTO> result = courseQuizQuestionService.findByQuizId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseQuizQuestionDTO.getQuizId(), result.get(0).getQuizId());
        verify(courseQuizQuestionRepository).findByQuizId(1);
        verify(courseQuizQuestionMapper).toDTO(courseQuizQuestion);
    }

    @Test
    void testFindByQuizIdOrderByOrderIndex() {
        // Arrange
        List<CourseQuizQuestion> questions = Arrays.asList(courseQuizQuestion);
        when(courseQuizQuestionRepository.findByQuizIdOrderByOrderIndex(1)).thenReturn(questions);
        when(courseQuizQuestionMapper.toDTO(courseQuizQuestion)).thenReturn(courseQuizQuestionDTO);

        // Act
        List<CourseQuizQuestionDTO> result = courseQuizQuestionService.findByQuizIdOrderByOrderIndex(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseQuizQuestionDTO.getOrderIndex(), result.get(0).getOrderIndex());
        verify(courseQuizQuestionRepository).findByQuizIdOrderByOrderIndex(1);
        verify(courseQuizQuestionMapper).toDTO(courseQuizQuestion);
    }

    @Test
    void testFindByQuizIdOrderByOrderIndexAsc() {
        // Arrange
        List<CourseQuizQuestion> questions = Arrays.asList(courseQuizQuestion);
        when(courseQuizQuestionRepository.findByQuizIdOrderByOrderIndexAsc(1)).thenReturn(questions);
        when(courseQuizQuestionMapper.toDTO(courseQuizQuestion)).thenReturn(courseQuizQuestionDTO);

        // Act
        List<CourseQuizQuestionDTO> result = courseQuizQuestionService.findByQuizIdOrderByOrderIndexAsc(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(courseQuizQuestionRepository).findByQuizIdOrderByOrderIndexAsc(1);
        verify(courseQuizQuestionMapper).toDTO(courseQuizQuestion);
    }

    @Test
    void testFindByQuizIdOrderByOrderIndexDesc() {
        // Arrange
        List<CourseQuizQuestion> questions = Arrays.asList(courseQuizQuestion);
        when(courseQuizQuestionRepository.findByQuizIdOrderByOrderIndexDesc(1)).thenReturn(questions);
        when(courseQuizQuestionMapper.toDTO(courseQuizQuestion)).thenReturn(courseQuizQuestionDTO);

        // Act
        List<CourseQuizQuestionDTO> result = courseQuizQuestionService.findByQuizIdOrderByOrderIndexDesc(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(courseQuizQuestionRepository).findByQuizIdOrderByOrderIndexDesc(1);
        verify(courseQuizQuestionMapper).toDTO(courseQuizQuestion);
    }

    @Test
    void testCountByQuizId() {
        // Arrange
        when(courseQuizQuestionRepository.countByQuizId(1)).thenReturn(5L);

        // Act
        long result = courseQuizQuestionService.countByQuizId(1);

        // Assert
        assertEquals(5L, result);
        verify(courseQuizQuestionRepository).countByQuizId(1);
    }

    @Test
    void testCreate() {
        // Arrange
        CourseQuiz courseQuiz = new CourseQuiz();
        courseQuiz.setId(1);
        
        when(courseQuizRepository.findById(1)).thenReturn(Optional.of(courseQuiz));
        when(courseQuizQuestionMapper.toEntity(courseQuizQuestionDTO)).thenReturn(courseQuizQuestion);
        when(courseQuizQuestionRepository.save(courseQuizQuestion)).thenReturn(courseQuizQuestion);
        when(courseQuizQuestionMapper.toDTO(courseQuizQuestion)).thenReturn(courseQuizQuestionDTO);

        // Act
        CourseQuizQuestionDTO result = courseQuizQuestionService.create(courseQuizQuestionDTO);

        // Assert
        assertNotNull(result);
        assertEquals(courseQuizQuestionDTO.getQuestionText(), result.getQuestionText());
        verify(courseQuizRepository).findById(1);
        verify(courseQuizQuestionMapper).toEntity(courseQuizQuestionDTO);
        verify(courseQuizQuestionRepository).save(courseQuizQuestion);
        verify(courseQuizQuestionMapper).toDTO(courseQuizQuestion);
    }

    @Test
    void testUpdate() {
        // Arrange
        CourseQuiz courseQuiz = new CourseQuiz();
        courseQuiz.setId(1);
        
        when(courseQuizQuestionRepository.findById(1)).thenReturn(Optional.of(courseQuizQuestion));
        when(courseQuizRepository.findById(1)).thenReturn(Optional.of(courseQuiz));
        when(courseQuizQuestionMapper.toEntity(courseQuizQuestionDTO)).thenReturn(courseQuizQuestion);
        when(courseQuizQuestionRepository.save(any(CourseQuizQuestion.class))).thenReturn(courseQuizQuestion);
        when(courseQuizQuestionMapper.toDTO(courseQuizQuestion)).thenReturn(courseQuizQuestionDTO);

        // Act
        CourseQuizQuestionDTO result = courseQuizQuestionService.update(1, courseQuizQuestionDTO);

        // Assert
        assertNotNull(result);
        assertEquals(courseQuizQuestionDTO.getQuestionText(), result.getQuestionText());
        verify(courseQuizQuestionRepository).findById(1);
        verify(courseQuizRepository).findById(1);
        verify(courseQuizQuestionMapper).toEntity(courseQuizQuestionDTO);
        verify(courseQuizQuestionRepository).save(any(CourseQuizQuestion.class));
        verify(courseQuizQuestionMapper).toDTO(courseQuizQuestion);
    }

    @Test
    void testUpdateNotFound() {
        // Arrange
        when(courseQuizQuestionRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> courseQuizQuestionService.update(1, courseQuizQuestionDTO));
        verify(courseQuizQuestionRepository).findById(1);
        verify(courseQuizQuestionRepository, never()).save(any());
    }

    @Test
    void testDelete() {
        // Arrange
        when(courseQuizQuestionRepository.findById(1)).thenReturn(Optional.of(courseQuizQuestion));

        // Act
        courseQuizQuestionService.delete(1);

        // Assert
        verify(courseQuizQuestionRepository).findById(1);
        verify(courseQuizQuestionRepository).delete(courseQuizQuestion);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        when(courseQuizQuestionRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> courseQuizQuestionService.delete(1));
        verify(courseQuizQuestionRepository).findById(1);
        verify(courseQuizQuestionRepository, never()).delete(any());
    }

    @Test
    void testDeleteByQuizId() {
        // Act
        courseQuizQuestionService.deleteByQuizId(1);

        // Assert
        verify(courseQuizQuestionRepository).deleteByQuizId(1);
    }
}
