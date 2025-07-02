package com.edutech.grades.service;

import com.edutech.common.dto.CourseDTO;
import com.edutech.common.dto.CourseQuizDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.grades.client.CourseClient;
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

    @Mock
    private CourseClient courseClient;

    @InjectMocks
    private CourseQuizService courseQuizService;

    private CourseQuiz courseQuiz;
    private CourseQuizDTO courseQuizDTO;
    private CourseDTO courseDTO;

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

        // Configurar mock del CourseDTO para validaciones
        courseDTO = new CourseDTO();
        courseDTO.setId(1);
        courseDTO.setTitle("Curso de Prueba");
        courseDTO.setDescription("Descripción del curso de prueba");
    }

    @Test
    void deberiaObtenerTodosLosQuizzesDeCurso() {
        // Dado - Preparación de datos de prueba
        List<CourseQuiz> quizzesEsperados = Arrays.asList(courseQuiz);
        List<CourseQuizDTO> dtoEsperados = Arrays.asList(courseQuizDTO);

        when(courseQuizRepository.findAll()).thenReturn(quizzesEsperados);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Cuando - Ejecución del método a probar
        List<CourseQuizDTO> resultado = courseQuizService.findAll();

        // Entonces - Verificación de resultados
        assertNotNull(resultado, "La lista de quizzes no debería ser null");
        assertEquals(1, resultado.size(), "Debería retornar exactamente un quiz");
        assertEquals(dtoEsperados.get(0).getTitle(), resultado.get(0).getTitle(), 
                    "El título del quiz debería coincidir");
        verify(courseQuizRepository).findAll();
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void deberiaObtenerQuizPorIdExistente() {
        // Dado - Configuración de mocks para ID existente
        when(courseQuizRepository.findById(1)).thenReturn(Optional.of(courseQuiz));
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Cuando - Búsqueda por ID existente
        CourseQuizDTO resultado = courseQuizService.findById(1);

        // Entonces - Verificación de datos obtenidos
        assertNotNull(resultado, "El quiz obtenido no debería ser null");
        assertEquals(courseQuizDTO.getTitle(), resultado.getTitle(), 
                    "El título del quiz debería coincidir");
        assertEquals(courseQuizDTO.getCourseId(), resultado.getCourseId(), 
                    "El ID del curso debería coincidir");
        verify(courseQuizRepository).findById(1);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void deberiaLanzarExcepcionParaQuizInexistente() {
        // Dado - Configuración para ID inexistente
        when(courseQuizRepository.findById(1)).thenReturn(Optional.empty());

        // Cuando y Entonces - Verificación de excepción esperada
        ResourceNotFoundException excepcion = assertThrows(ResourceNotFoundException.class, 
                () -> courseQuizService.findById(1), 
                "Debería lanzar ResourceNotFoundException para quiz inexistente");
        
        assertNotNull(excepcion, "La excepción lanzada no debería ser null");
        assertTrue(excepcion.getMessage().contains("Course quiz not found"), 
                  "El mensaje de excepción debería indicar que el quiz no fue encontrado");
        verify(courseQuizRepository).findById(1);
    }

    @Test
    void testFindByCourseId() {
        // Arrange
        List<CourseQuiz> courseQuizzes = Arrays.asList(courseQuiz);
        List<CourseQuizDTO> expectedDTOs = Arrays.asList(courseQuizDTO);

        when(courseQuizRepository.findByCourseIdOrderByCreatedAtDesc(1)).thenReturn(courseQuizzes);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        List<CourseQuizDTO> result = courseQuizService.findByCourseId(1);

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
        List<CourseQuizDTO> expectedDTOs = Arrays.asList(courseQuizDTO);

        when(courseQuizRepository.findByQuizTypeOrderByCreatedAtDesc(quizType)).thenReturn(courseQuizzes);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        List<CourseQuizDTO> result = courseQuizService.findByQuizType(quizType);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDTOs.get(0).getQuizType(), result.get(0).getQuizType());
        verify(courseQuizRepository).findByQuizTypeOrderByCreatedAtDesc(quizType);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testCreate() {
        // Arrange - Dado: Configurar mocks necesarios
        when(courseClient.findById(any(Integer.class))).thenReturn(courseDTO);
        when(courseQuizMapper.toEntity(courseQuizDTO)).thenReturn(courseQuiz);
        when(courseQuizRepository.save(courseQuiz)).thenReturn(courseQuiz);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act - Cuando: Se crea el quiz
        CourseQuizDTO result = courseQuizService.create(courseQuizDTO);

        // Assert - Entonces: Se valida el resultado
        assertNotNull(result);
        assertEquals(courseQuizDTO.getTitle(), result.getTitle());
        verify(courseClient).findById(eq(1));
        verify(courseQuizRepository).save(courseQuiz);
        verify(courseQuizMapper).toEntity(courseQuizDTO);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testUpdate() {
        // Arrange
        when(courseQuizRepository.existsById(1)).thenReturn(true);
        when(courseQuizMapper.toEntity(courseQuizDTO)).thenReturn(courseQuiz);
        when(courseQuizRepository.save(courseQuiz)).thenReturn(courseQuiz);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        CourseQuizDTO result = courseQuizService.update(1, courseQuizDTO);

        // Assert
        assertNotNull(result);
        assertEquals(courseQuizDTO.getTitle(), result.getTitle());
        verify(courseQuizRepository).existsById(1);
        verify(courseQuizRepository).save(courseQuiz);
        verify(courseQuizMapper).toEntity(courseQuizDTO);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }

    @Test
    void testUpdate_NotFound() {
        // Arrange
        when(courseQuizRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> courseQuizService.update(1, courseQuizDTO));
        assertNotNull(exception);
        verify(courseQuizRepository).existsById(1);
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

    @Test
    void testFindByCourseIdAndQuizType() {
        // Arrange
        Integer courseId = 1;
        String quizType = "Multiple Choice";
        List<CourseQuiz> courseQuizzes = Arrays.asList(courseQuiz);
        List<CourseQuizDTO> expectedDTOs = Arrays.asList(courseQuizDTO);

        when(courseQuizRepository.findByCourseIdAndQuizTypeOrderByCreatedAtDesc(courseId, quizType)).thenReturn(courseQuizzes);
        when(courseQuizMapper.toDTO(courseQuiz)).thenReturn(courseQuizDTO);

        // Act
        List<CourseQuizDTO> result = courseQuizService.findByCourseIdAndQuizType(courseId, quizType);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDTOs.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(expectedDTOs.get(0).getQuizType(), result.get(0).getQuizType());
        verify(courseQuizRepository).findByCourseIdAndQuizTypeOrderByCreatedAtDesc(courseId, quizType);
        verify(courseQuizMapper).toDTO(courseQuiz);
    }
}
