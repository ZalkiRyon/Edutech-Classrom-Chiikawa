package com.edutech.grades.service;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.common.dto.CourseDTO;
import com.edutech.grades.entity.Enrollment;
import com.edutech.grades.mapper.EnrollmentMapper;
import com.edutech.grades.repository.EnrollmentRepository;
import com.edutech.grades.client.UserClient;
import com.edutech.grades.client.CourseClient;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EnrollmentMapper enrollmentMapper;

    @Mock
    private UserClient userClient;

    @Mock
    private CourseClient courseClient;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Enrollment testEnrollment;
    private EnrollmentDTO testEnrollmentDTO;
    private UserDTO testUserDTO;
    private CourseDTO testCourseDTO;

    @BeforeEach
    void setUp() {
        testEnrollment = new Enrollment();
        testEnrollment.setId(1);
        testEnrollment.setStudentId(1);
        testEnrollment.setCourseId(1);
        testEnrollment.setEnrollmentDate(Instant.now());
        testEnrollment.setStatus("ACTIVE");

        testEnrollmentDTO = new EnrollmentDTO();
        testEnrollmentDTO.setId(1);
        testEnrollmentDTO.setStudentId(1);
        testEnrollmentDTO.setCourseId(1);
        testEnrollmentDTO.setEnrollmentDate(Instant.now());
        testEnrollmentDTO.setStatus("ACTIVE");

        testUserDTO = new UserDTO();
        testUserDTO.setId(1);
        testUserDTO.setFirstName("John");
        testUserDTO.setLastName("Doe");
        testUserDTO.setEmail("john@edutech.com");

        testCourseDTO = new CourseDTO();
        testCourseDTO.setId(1);
        testCourseDTO.setTitle("Java Programming");
        testCourseDTO.setDescription("Learn Java from scratch");
    }

    @Test
    void findAll_ShouldReturnAllEnrollments() {
        // Given
        List<Enrollment> enrollments = Arrays.asList(testEnrollment);
        
        when(enrollmentRepository.findAll()).thenReturn(enrollments);
        when(enrollmentMapper.toDTO(testEnrollment)).thenReturn(testEnrollmentDTO);

        // When
        List<EnrollmentDTO> result = enrollmentService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(testEnrollmentDTO.getStudentId(), result.get(0).getStudentId());
        verify(enrollmentRepository).findAll();
        verify(enrollmentMapper).toDTO(testEnrollment);
    }

    @Test
    void findById_WhenEnrollmentExists_ShouldReturnEnrollment() {
        // Given
        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(testEnrollment));
        when(enrollmentMapper.toDTO(testEnrollment)).thenReturn(testEnrollmentDTO);

        // When
        EnrollmentDTO result = enrollmentService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(testEnrollmentDTO.getStudentId(), result.getStudentId());
        verify(enrollmentRepository).findById(1);
        verify(enrollmentMapper).toDTO(testEnrollment);
    }

    @Test
    void findById_WhenEnrollmentNotExists_ShouldThrowException() {
        // Given
        when(enrollmentRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> enrollmentService.findById(999));
        verify(enrollmentRepository).findById(999);
        verify(enrollmentMapper, never()).toDTO(any(Enrollment.class));
    }

    @Test
    void create_WithValidStudentAndCourse_ShouldCreateAndReturnEnrollment() {
        // Given
        when(userClient.findById(1)).thenReturn(testUserDTO);
        when(courseClient.findById(1)).thenReturn(testCourseDTO);
        when(enrollmentMapper.toEntity(testEnrollmentDTO)).thenReturn(testEnrollment);
        when(enrollmentRepository.save(testEnrollment)).thenReturn(testEnrollment);
        when(enrollmentMapper.toDTO(testEnrollment)).thenReturn(testEnrollmentDTO);

        // When
        EnrollmentDTO result = enrollmentService.create(testEnrollmentDTO);

        // Then
        assertNotNull(result);
        assertEquals(testEnrollmentDTO.getStudentId(), result.getStudentId());
        verify(userClient).findById(1);
        verify(courseClient).findById(1);
        verify(enrollmentMapper).toEntity(testEnrollmentDTO);
        verify(enrollmentRepository).save(testEnrollment);
        verify(enrollmentMapper).toDTO(testEnrollment);
    }

    @Test
    void create_WithInvalidStudent_ShouldThrowException() {
        // Given
        when(userClient.findById(1)).thenThrow(new RuntimeException("User not found"));

        // When & Then
        assertThrows(RuntimeException.class, () -> enrollmentService.create(testEnrollmentDTO));
        verify(userClient).findById(1);
        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    void create_WithInvalidCourse_ShouldThrowException() {
        // Given
        when(userClient.findById(1)).thenReturn(testUserDTO);
        when(courseClient.findById(1)).thenThrow(new RuntimeException("Course not found"));

        // When & Then
        assertThrows(RuntimeException.class, () -> enrollmentService.create(testEnrollmentDTO));
        verify(userClient).findById(1);
        verify(courseClient).findById(1);
        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    void update_WhenEnrollmentExists_ShouldUpdateAndReturnEnrollment() {
        // Given
        EnrollmentDTO updateDTO = new EnrollmentDTO();
        updateDTO.setStudentId(1);
        updateDTO.setCourseId(1);
        updateDTO.setEnrollmentDate(Instant.now());
        updateDTO.setStatus("COMPLETED");

        Enrollment existingEnrollment = new Enrollment();
        existingEnrollment.setId(1);
        existingEnrollment.setStatus("ACTIVE");

        Enrollment updatedEnrollmentEntity = new Enrollment();
        updatedEnrollmentEntity.setId(1);
        updatedEnrollmentEntity.setStatus("COMPLETED");

        EnrollmentDTO expectedDTO = new EnrollmentDTO();
        expectedDTO.setId(1);
        expectedDTO.setStatus("COMPLETED");

        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(existingEnrollment));
        when(userClient.findById(1)).thenReturn(testUserDTO);
        when(courseClient.findById(1)).thenReturn(testCourseDTO);
        when(enrollmentMapper.toEntity(updateDTO)).thenReturn(updatedEnrollmentEntity);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(updatedEnrollmentEntity);
        when(enrollmentMapper.toDTO(updatedEnrollmentEntity)).thenReturn(expectedDTO);

        // When
        EnrollmentDTO result = enrollmentService.update(1, updateDTO);

        // Then
        assertNotNull(result);
        assertEquals("COMPLETED", result.getStatus());
        verify(enrollmentRepository).findById(1);
        verify(userClient).findById(1);
        verify(courseClient).findById(1);
        verify(enrollmentMapper).toEntity(updateDTO);
        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(enrollmentMapper).toDTO(updatedEnrollmentEntity);
    }

    @Test
    void update_WhenEnrollmentNotExists_ShouldThrowException() {
        // Given
        when(enrollmentRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> enrollmentService.update(999, testEnrollmentDTO));
        verify(enrollmentRepository).findById(999);
        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    void delete_WhenEnrollmentExists_ShouldDeleteEnrollment() {
        // Given
        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(testEnrollment));

        // When
        enrollmentService.delete(1);

        // Then
        verify(enrollmentRepository).findById(1);
        verify(enrollmentRepository).delete(testEnrollment);
    }

    @Test
    void delete_WhenEnrollmentNotExists_ShouldThrowException() {
        // Given
        when(enrollmentRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> enrollmentService.delete(999));
        verify(enrollmentRepository).findById(999);
        verify(enrollmentRepository, never()).delete(any(Enrollment.class));
    }

    @Test
    void findByStudentId_ShouldReturnStudentEnrollments() {
        // Given
        List<Enrollment> enrollments = Arrays.asList(testEnrollment);
        when(enrollmentRepository.findByStudentId(1)).thenReturn(enrollments);
        when(enrollmentMapper.toDTO(testEnrollment)).thenReturn(testEnrollmentDTO);

        // When
        List<EnrollmentDTO> result = enrollmentService.findByStudentId(1);

        // Then
        assertEquals(1, result.size());
        assertEquals(testEnrollmentDTO.getStudentId(), result.get(0).getStudentId());
        verify(enrollmentRepository).findByStudentId(1);
        verify(enrollmentMapper).toDTO(testEnrollment);
    }

    @Test
    void findByCourseId_ShouldReturnCourseEnrollments() {
        // Given
        List<Enrollment> enrollments = Arrays.asList(testEnrollment);
        when(enrollmentRepository.findByCourseId(1)).thenReturn(enrollments);
        when(enrollmentMapper.toDTO(testEnrollment)).thenReturn(testEnrollmentDTO);

        // When
        List<EnrollmentDTO> result = enrollmentService.findByCourseId(1);

        // Then
        assertEquals(1, result.size());
        assertEquals(testEnrollmentDTO.getCourseId(), result.get(0).getCourseId());
        verify(enrollmentRepository).findByCourseId(1);
        verify(enrollmentMapper).toDTO(testEnrollment);
    }
}
