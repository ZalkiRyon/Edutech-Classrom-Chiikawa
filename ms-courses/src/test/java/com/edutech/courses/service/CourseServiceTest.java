package com.edutech.courses.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edutech.common.dto.CourseDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.courses.client.UserClient;
import com.edutech.courses.entity.Course;
import com.edutech.courses.entity.CourseCategory;
import com.edutech.courses.mapper.CourseMapper;
import com.edutech.courses.repository.CourseCategoryRepository;
import com.edutech.courses.repository.CourseRepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private CourseCategoryRepository categRepo;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private CourseService courseService;

    private Course testCourse;
    private CourseDTO testCourseDTO;
    private UserDTO testManagerDTO;
    private UserDTO testInstructorDTO;
    private CourseCategory testCategory;

    @BeforeEach
    void setUp() {
        // Crear entidad Course básica
        testCourse = new Course();
        testCourse.setId(1);
        testCourse.setTitle("Programacion en Java");
        testCourse.setDescription("Aprende Java desde 0");
        testCourse.setInstructorId(1);
        testCourse.setCategoryId(1);
        testCourse.setManagerId(2);
        testCourse.setPublishDate(LocalDate.now());
        testCourse.setPrice(new BigDecimal("99.99"));
        testCourse.setImage("java-course.jpg");
        testCourse.setStatus("ACTIVE");

        // Crear DTO Course básico
        testCourseDTO = new CourseDTO();
        testCourseDTO.setId(1);
        testCourseDTO.setTitle("Programacion en Java");
        testCourseDTO.setDescription("Aprende Java desde 0");
        testCourseDTO.setInstructorId(1);
        testCourseDTO.setCategoryId(1);
        testCourseDTO.setManagerId(2);
        testCourseDTO.setPublishDate(LocalDate.now());
        testCourseDTO.setPrice(new BigDecimal("99.99"));
        testCourseDTO.setImage("java-course.jpg");
        testCourseDTO.setStatus("ACTIVE");

        // Crear DTOs de usuario
        testManagerDTO = new UserDTO();
        testManagerDTO.setId(2);
        testManagerDTO.setFirstName("Manager");
        testManagerDTO.setLastName("User");

        testInstructorDTO = new UserDTO();
        testInstructorDTO.setId(1);
        testInstructorDTO.setFirstName("Instructor");
        testInstructorDTO.setLastName("User");

        // Crear categoría
        testCategory = new CourseCategory();
        testCategory.setId(1);
        testCategory.setName("Programacion");
    }

    @Test
    void findAll_ShouldReturnAllCourses() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        
        when(courseRepo.findAll()).thenReturn(courses);
        when(courseMapper.toDTO(testCourse)).thenReturn(testCourseDTO);

        // When
        List<CourseDTO> result = courseService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals("Programacion en Java", result.get(0).getTitle());
        verify(courseRepo).findAll();
        verify(courseMapper).toDTO(testCourse);
    }

    @Test
    void findById_WhenCourseExists_ShouldReturnCourse() {
        // Given
        when(courseRepo.findById(1)).thenReturn(Optional.of(testCourse));
        when(courseMapper.toDTO(testCourse)).thenReturn(testCourseDTO);

        // When
        CourseDTO result = courseService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals("Programacion en Java", result.getTitle());
        verify(courseRepo).findById(1);
        verify(courseMapper).toDTO(testCourse);
    }

    @Test
    void findById_WhenCourseNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(courseRepo.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> courseService.findById(999));
        verify(courseRepo).findById(999);
        verify(courseMapper, never()).toDTO(any(Course.class));
    }

    @Test
    void create_WithValidData_ShouldCreateAndReturnCourse() {
        // Given
        when(categRepo.findById(1)).thenReturn(Optional.of(testCategory));
        when(userClient.findById(2)).thenReturn(testManagerDTO); // manager
        when(userClient.findById(1)).thenReturn(testInstructorDTO); // instructor
        when(courseMapper.toEntity(testCourseDTO)).thenReturn(testCourse);
        when(courseRepo.save(testCourse)).thenReturn(testCourse);
        when(courseMapper.toDTO(testCourse)).thenReturn(testCourseDTO);

        // When
        CourseDTO result = courseService.create(testCourseDTO);

        // Then
        assertNotNull(result);
        assertEquals("Programacion en Java", result.getTitle());
        verify(categRepo).findById(1);
        verify(userClient).findById(2); // manager validation
        verify(userClient).findById(1); // instructor validation
        verify(courseMapper).toEntity(testCourseDTO);
        verify(courseRepo).save(testCourse);
        verify(courseMapper).toDTO(testCourse);
    }

    @Test
    void create_WithInvalidCategory_ShouldThrowResourceNotFoundException() {
        // Given
        when(categRepo.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> courseService.create(testCourseDTO));
        verify(categRepo).findById(1);
        verify(courseRepo, never()).save(any(Course.class));
    }

    @Test
    void update_WhenCourseExists_ShouldUpdateAndReturnCourse() {
        // Given
        when(courseRepo.findById(1)).thenReturn(Optional.of(testCourse));
        when(courseMapper.toEntity(testCourseDTO)).thenReturn(testCourse);
        when(courseRepo.save(any(Course.class))).thenReturn(testCourse);
        when(courseMapper.toDTO(testCourse)).thenReturn(testCourseDTO);

        // When
        CourseDTO result = courseService.update(1, testCourseDTO);

        // Then
        assertNotNull(result);
        assertEquals("Programacion en Java", result.getTitle());
        verify(courseRepo).findById(1);
        verify(courseMapper).toEntity(testCourseDTO);
        verify(courseRepo).save(any(Course.class));
        verify(courseMapper).toDTO(testCourse);
    }

    @Test
    void update_WhenCourseNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(courseRepo.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> courseService.update(999, testCourseDTO));
        verify(courseRepo).findById(999);
        verify(courseRepo, never()).save(any(Course.class));
    }

    @Test
    void delete_WhenCourseExists_ShouldDeleteCourse() {
        // Given
        when(courseRepo.findById(1)).thenReturn(Optional.of(testCourse));

        // When
        courseService.delete(1);

        // Then
        verify(courseRepo).findById(1);
        verify(courseRepo).delete(testCourse);
    }

    @Test
    void delete_WhenCourseNotExists_ShouldThrowResourceNotFoundException() {
        // Given
        when(courseRepo.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> courseService.delete(999));
        verify(courseRepo).findById(999);
        verify(courseRepo, never()).delete(any(Course.class));
    }
}
