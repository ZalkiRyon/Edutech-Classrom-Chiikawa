package com.edutech.grades.service;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.common.dto.CourseDTO;
import com.edutech.common.exception.ResourceNotFoundException;
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
        // Crear entidad Enrollment básica
        testEnrollment = new Enrollment();
        testEnrollment.setId(1);
        testEnrollment.setStudentId(1);
        testEnrollment.setCourseId(1);
        testEnrollment.setEnrolledAt(Instant.now());
        testEnrollment.setStatus("ACTIVE");

        // Crear DTO Enrollment básico
        testEnrollmentDTO = new EnrollmentDTO();
        testEnrollmentDTO.setId(1);
        testEnrollmentDTO.setStudentId(1);
        testEnrollmentDTO.setCourseId(1);
        testEnrollmentDTO.setEnrolledAt(Instant.now());
        testEnrollmentDTO.setStatus("ACTIVE");

        // Crear DTO User básico
        testUserDTO = new UserDTO();
        testUserDTO.setId(1);
        testUserDTO.setFirstName("John");
        testUserDTO.setLastName("Doe");

        // Crear DTO Course básico
        testCourseDTO = new CourseDTO();
        testCourseDTO.setId(1);
        testCourseDTO.setTitle("Java Programming");
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
        assertEquals(1, result.get(0).getStudentId());
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
        assertEquals(1, result.getStudentId());
        verify(enrollmentRepository).findById(1);
        verify(enrollmentMapper).toDTO(testEnrollment);
    }

    @Test
    void findById_WhenEnrollmentNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(enrollmentRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.findById(999));
        verify(enrollmentRepository).findById(999);
        verify(enrollmentMapper, never()).toDTO(any(Enrollment.class));
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
        assertEquals(1, result.get(0).getStudentId());
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
        assertEquals(1, result.get(0).getCourseId());
        verify(enrollmentRepository).findByCourseId(1);
        verify(enrollmentMapper).toDTO(testEnrollment);
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
        assertEquals(1, result.getStudentId());
        verify(userClient).findById(1);
        verify(courseClient).findById(1);
        verify(enrollmentMapper).toEntity(testEnrollmentDTO);
        verify(enrollmentRepository).save(testEnrollment);
        verify(enrollmentMapper).toDTO(testEnrollment);
    }

    @Test
    void update_WhenEnrollmentExists_ShouldUpdateAndReturnEnrollment() {
        // Given
        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(testEnrollment));
        when(userClient.findById(1)).thenReturn(testUserDTO);
        when(courseClient.findById(1)).thenReturn(testCourseDTO);
        when(enrollmentMapper.toEntity(testEnrollmentDTO)).thenReturn(testEnrollment);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(testEnrollment);
        when(enrollmentMapper.toDTO(testEnrollment)).thenReturn(testEnrollmentDTO);

        // When
        EnrollmentDTO result = enrollmentService.update(1, testEnrollmentDTO);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getStudentId());
        verify(enrollmentRepository).findById(1);
        verify(userClient).findById(1);
        verify(courseClient).findById(1);
        verify(enrollmentMapper).toEntity(testEnrollmentDTO);
        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(enrollmentMapper).toDTO(testEnrollment);
    }

    @Test
    void update_WhenEnrollmentNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(enrollmentRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.update(999, testEnrollmentDTO));
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
    void delete_WhenEnrollmentNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(enrollmentRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.delete(999));
        verify(enrollmentRepository).findById(999);
        verify(enrollmentRepository, never()).delete(any(Enrollment.class));
    }
}
