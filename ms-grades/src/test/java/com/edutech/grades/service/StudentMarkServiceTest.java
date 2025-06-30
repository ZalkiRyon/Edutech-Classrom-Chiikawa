package com.edutech.grades.service;

import com.edutech.common.dto.StudentMarkDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.grades.client.UserClient;
import com.edutech.grades.entity.StudentMark;
import com.edutech.grades.mapper.StudentMarkMapperManual;
import com.edutech.grades.repository.StudentMarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentMarkServiceTest {

    @Mock
    private StudentMarkRepository studentMarkRepository;

    @Mock
    private StudentMarkMapperManual studentMarkMapper;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private StudentMarkService studentMarkService;

    private StudentMark studentMark;
    private StudentMarkDTO studentMarkDTO;

    @BeforeEach
    void setUp() {
        studentMark = new StudentMark();
        studentMark.setId(1);
        studentMark.setQuizId(1);
        studentMark.setStudentId(1);
        studentMark.setMark(new BigDecimal("85.50"));
        studentMark.setComments("Buen trabajo");
        studentMark.setGradedAt(Instant.now());

        studentMarkDTO = new StudentMarkDTO();
        studentMarkDTO.setId(1);
        studentMarkDTO.setQuizId(1);
        studentMarkDTO.setStudentId(1);
        studentMarkDTO.setMark(new BigDecimal("85.50"));
        studentMarkDTO.setComments("Buen trabajo");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<StudentMark> studentMarks = Arrays.asList(studentMark);
        when(studentMarkRepository.findAll()).thenReturn(studentMarks);
        when(studentMarkMapper.toDTO(studentMark)).thenReturn(studentMarkDTO);

        // Act
        List<StudentMarkDTO> result = studentMarkService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(studentMarkDTO.getMark(), result.get(0).getMark());
        verify(studentMarkRepository).findAll();
        verify(studentMarkMapper).toDTO(studentMark);
    }

    @Test
    void testFindById() {
        // Arrange
        when(studentMarkRepository.findById(1)).thenReturn(Optional.of(studentMark));
        when(studentMarkMapper.toDTO(studentMark)).thenReturn(studentMarkDTO);

        // Act
        StudentMarkDTO result = studentMarkService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(studentMarkDTO.getMark(), result.getMark());
        assertEquals(studentMarkDTO.getStudentId(), result.getStudentId());
        verify(studentMarkRepository).findById(1);
        verify(studentMarkMapper).toDTO(studentMark);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(studentMarkRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> studentMarkService.findById(1));
        verify(studentMarkRepository).findById(1);
        verify(studentMarkMapper, never()).toDTO(any());
    }

    @Test
    void testFindByQuizId() {
        // Arrange
        List<StudentMark> studentMarks = Arrays.asList(studentMark);
        when(studentMarkRepository.findByQuizId(1)).thenReturn(studentMarks);
        when(studentMarkMapper.toDTO(studentMark)).thenReturn(studentMarkDTO);

        // Act
        List<StudentMarkDTO> result = studentMarkService.findByQuizId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(studentMarkDTO.getQuizId(), result.get(0).getQuizId());
        verify(studentMarkRepository).findByQuizId(1);
        verify(studentMarkMapper).toDTO(studentMark);
    }

    @Test
    void testFindByStudentId() {
        // Arrange
        List<StudentMark> studentMarks = Arrays.asList(studentMark);
        when(studentMarkRepository.findByStudentId(1)).thenReturn(studentMarks);
        when(studentMarkMapper.toDTO(studentMark)).thenReturn(studentMarkDTO);

        // Act
        List<StudentMarkDTO> result = studentMarkService.findByStudentId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(studentMarkDTO.getStudentId(), result.get(0).getStudentId());
        verify(studentMarkRepository).findByStudentId(1);
        verify(studentMarkMapper).toDTO(studentMark);
    }

    @Test
    void testCreate() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        
        when(userClient.findById(1)).thenReturn(userDTO);
        when(studentMarkMapper.toEntity(studentMarkDTO)).thenReturn(studentMark);
        when(studentMarkRepository.save(studentMark)).thenReturn(studentMark);
        when(studentMarkMapper.toDTO(studentMark)).thenReturn(studentMarkDTO);

        // Act
        StudentMarkDTO result = studentMarkService.create(studentMarkDTO);

        // Assert
        assertNotNull(result);
        assertEquals(studentMarkDTO.getMark(), result.getMark());
        verify(userClient).findById(1);
        verify(studentMarkMapper).toEntity(studentMarkDTO);
        verify(studentMarkRepository).save(studentMark);
        verify(studentMarkMapper).toDTO(studentMark);
    }

    @Test
    void testUpdate() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        
        when(studentMarkRepository.findById(1)).thenReturn(Optional.of(studentMark));
        when(userClient.findById(1)).thenReturn(userDTO);
        when(studentMarkMapper.toEntity(studentMarkDTO)).thenReturn(studentMark);
        when(studentMarkRepository.save(any(StudentMark.class))).thenReturn(studentMark);
        when(studentMarkMapper.toDTO(studentMark)).thenReturn(studentMarkDTO);

        // Act
        StudentMarkDTO result = studentMarkService.update(1, studentMarkDTO);

        // Assert
        assertNotNull(result);
        assertEquals(studentMarkDTO.getMark(), result.getMark());
        verify(studentMarkRepository).findById(1);
        verify(userClient).findById(1);
        verify(studentMarkMapper).toEntity(studentMarkDTO);
        verify(studentMarkRepository).save(any(StudentMark.class));
        verify(studentMarkMapper).toDTO(studentMark);
    }

    @Test
    void testDelete() {
        // Arrange
        when(studentMarkRepository.findById(1)).thenReturn(Optional.of(studentMark));

        // Act
        studentMarkService.delete(1);

        // Assert
        verify(studentMarkRepository).findById(1);
        verify(studentMarkRepository).delete(studentMark);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        when(studentMarkRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> studentMarkService.delete(1));
        verify(studentMarkRepository).findById(1);
        verify(studentMarkRepository, never()).delete(any());
    }
}
