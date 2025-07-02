package com.edutech.courses.service;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.courses.client.UserClient;
import com.edutech.courses.entity.Course;
import com.edutech.courses.entity.Enrollment;
import com.edutech.courses.mapper.EnrollmentMapperManual;
import com.edutech.courses.repository.CourseRepository;
import com.edutech.courses.repository.EnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EnrollmentMapperManual enrollmentMapper;

    @Mock
    private UserClient userClient;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Enrollment enrollment;
    private EnrollmentDTO enrollmentDTO;
    private UserDTO userDTO;
    private Course course;

    @BeforeEach
    void setUp() {
        enrollment = new Enrollment();
        enrollment.setId(1);
        enrollment.setStudentId(15);
        enrollment.setCourseId(1);
        enrollment.setEnrolledAt(Instant.now());
        enrollment.setStatus("Enabled");

        enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setId(1);
        enrollmentDTO.setStudentId(15);
        enrollmentDTO.setCourseId(1);
        enrollmentDTO.setEnrolledAt(Instant.now());
        enrollmentDTO.setStatus("Enabled");

        userDTO = new UserDTO();
        userDTO.setId(15);
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");

        course = new Course();
        course.setId(1);
        course.setTitle("Test Course");
        course.setDescription("Test Description");
        course.setInstructorId(1);
        course.setCategoryId(1);
        course.setManagerId(1);
        course.setPublishDate(LocalDate.now());
        course.setPrice(new BigDecimal("99.99"));
        course.setImage("test.jpg");
        course.setStatus("ACTIVE");
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        List<Enrollment> enrollments = Arrays.asList(enrollment);
        when(enrollmentRepository.findAll()).thenReturn(enrollments);
        when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        // Act
        List<EnrollmentDTO> result = enrollmentService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(15, result.get(0).getStudentId());
        assertEquals(1, result.get(0).getCourseId());
        assertEquals("Enabled", result.get(0).getStatus());
        verify(enrollmentRepository).findAll();
        verify(enrollmentMapper).toDTO(enrollment);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(enrollment));
        when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        // Act
        EnrollmentDTO result = enrollmentService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(15, result.getStudentId());
        assertEquals(1, result.getCourseId());
        assertEquals("Enabled", result.getStatus());
        verify(enrollmentRepository).findById(1);
        verify(enrollmentMapper).toDTO(enrollment);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(enrollmentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> enrollmentService.findById(999));
        verify(enrollmentRepository).findById(999);
        verify(enrollmentMapper, never()).toDTO(any());
    }

    @Test
    void testFindByStudentId_Success() {
        // Arrange
        List<Enrollment> enrollments = Arrays.asList(enrollment);
        when(enrollmentRepository.findByStudentId(15)).thenReturn(enrollments);
        when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        // Act
        List<EnrollmentDTO> result = enrollmentService.findByStudentId(15);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(15, result.get(0).getStudentId());
        verify(enrollmentRepository).findByStudentId(15);
        verify(enrollmentMapper).toDTO(enrollment);
    }

    @Test
    void testFindByCourseId_Success() {
        // Arrange
        List<Enrollment> enrollments = Arrays.asList(enrollment);
        when(enrollmentRepository.findByCourseId(1)).thenReturn(enrollments);
        when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        // Act
        List<EnrollmentDTO> result = enrollmentService.findByCourseId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getCourseId());
        verify(enrollmentRepository).findByCourseId(1);
        verify(enrollmentMapper).toDTO(enrollment);
    }

    @Test
    void testCreate_Success() {
        // Arrange
        when(userClient.findById(15)).thenReturn(userDTO);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentMapper.toEntity(enrollmentDTO)).thenReturn(enrollment);
        when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);
        when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        // Act
        EnrollmentDTO result = enrollmentService.create(enrollmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(15, result.getStudentId());
        assertEquals(1, result.getCourseId());
        assertEquals("Enabled", result.getStatus());
        verify(enrollmentMapper).toEntity(enrollmentDTO);
        verify(enrollmentRepository).save(enrollment);
        verify(enrollmentMapper).toDTO(enrollment);
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        EnrollmentDTO updateDTO = new EnrollmentDTO();
        updateDTO.setStudentId(15);
        updateDTO.setCourseId(1);
        updateDTO.setStatus("Completed");

        Enrollment updatedEntity = new Enrollment();
        updatedEntity.setId(1);
        updatedEntity.setStudentId(15);
        updatedEntity.setCourseId(1);
        updatedEntity.setStatus("Completed");

        EnrollmentDTO updatedDTO = new EnrollmentDTO();
        updatedDTO.setId(1);
        updatedDTO.setStudentId(15);
        updatedDTO.setCourseId(1);
        updatedDTO.setStatus("Completed");

        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(enrollment));
        when(userClient.findById(15)).thenReturn(userDTO);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentMapper.toEntity(updateDTO)).thenReturn(enrollment);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(updatedEntity);
        when(enrollmentMapper.toDTO(updatedEntity)).thenReturn(updatedDTO);

        // Act
        EnrollmentDTO result = enrollmentService.update(1, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Completed", result.getStatus());
        verify(enrollmentRepository).findById(1);
        verify(userClient).findById(15);
        verify(courseRepository).findById(1);
        verify(enrollmentMapper).toEntity(updateDTO);
        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(enrollmentMapper).toDTO(updatedEntity);
    }

    @Test
    void testUpdate_NotFound() {
        // Arrange
        when(enrollmentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> enrollmentService.update(999, enrollmentDTO));
        verify(enrollmentRepository).findById(999);
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void testDelete_Success() {
        // Arrange
        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(enrollment));

        // Act
        enrollmentService.delete(1);

        // Assert
        verify(enrollmentRepository).findById(1);
        verify(enrollmentRepository).delete(enrollment);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(enrollmentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
                    () -> enrollmentService.delete(999));
        verify(enrollmentRepository).findById(999);
        verify(enrollmentRepository, never()).delete(any());
    }
}
