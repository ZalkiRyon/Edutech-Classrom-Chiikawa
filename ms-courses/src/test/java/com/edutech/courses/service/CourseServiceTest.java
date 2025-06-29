package com.edutech.courses.service;

import com.edutech.common.dto.CourseDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.courses.entity.Course;
import com.edutech.courses.entity.CourseCategory;
import com.edutech.courses.mapper.CourseMapper;
import com.edutech.courses.repository.CourseRepository;
import com.edutech.courses.repository.CourseCategoryRepository;
import com.edutech.courses.client.UserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    private UserDTO testUserDTO;
    private CourseCategory testCategory;

    @BeforeEach
    void setUp() {
        testCourse = new Course();
        testCourse.setId(1);
        testCourse.setTitle("Java Programming");
        testCourse.setDescription("Learn Java from scratch");
        testCourse.setInstructorId(1);
        testCourse.setCategoryId(1);
        testCourse.setManagerId(1);
        testCourse.setPublishDate(LocalDate.now());
        testCourse.setPrice(new BigDecimal("99.99"));
        testCourse.setImage("java-course.jpg");
        testCourse.setStatus("ACTIVE");

        testCourseDTO = new CourseDTO();
        testCourseDTO.setId(1);
        testCourseDTO.setTitle("Java Programming");
        testCourseDTO.setDescription("Learn Java from scratch");
        testCourseDTO.setInstructorId(1);
        testCourseDTO.setCategoryId(1);
        testCourseDTO.setManagerId(1);
        testCourseDTO.setPublishDate(LocalDate.now());
        testCourseDTO.setPrice(new BigDecimal("99.99"));
        testCourseDTO.setImage("java-course.jpg");
        testCourseDTO.setStatus("ACTIVE");

        testUserDTO = new UserDTO();
        testUserDTO.setId(1);
        testUserDTO.setFirstName("John");
        testUserDTO.setLastName("Doe");
        testUserDTO.setEmail("john@edutech.com");

        testCategory = new CourseCategory();
        testCategory.setId(1);
        testCategory.setName("Programming");
        testCategory.setDescription("Programming courses");
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
        assertEquals(testCourseDTO.getTitle(), result.get(0).getTitle());
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
        assertEquals(testCourseDTO.getTitle(), result.getTitle());
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
        when(userClient.findById(1)).thenReturn(testUserDTO); // manager
        when(userClient.findById(1)).thenReturn(testUserDTO); // instructor
        when(courseMapper.toEntity(testCourseDTO)).thenReturn(testCourse);
        when(courseRepo.save(testCourse)).thenReturn(testCourse);
        when(courseMapper.toDTO(testCourse)).thenReturn(testCourseDTO);

        // When
        CourseDTO result = courseService.create(testCourseDTO);

        // Then
        assertNotNull(result);
        assertEquals(testCourseDTO.getTitle(), result.getTitle());
        verify(categRepo).findById(1);
        verify(userClient, times(2)).findById(1); // manager + instructor
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
        CourseDTO updateDTO = new CourseDTO();
        updateDTO.setTitle("Advanced Java Programming");
        updateDTO.setDescription("Advanced Java concepts");
        updateDTO.setInstructorId(1);
        updateDTO.setCategoryId(1);
        updateDTO.setManagerId(1);
        updateDTO.setPublishDate(LocalDate.now());
        updateDTO.setPrice(new BigDecimal("149.99"));
        updateDTO.setImage("advanced-java.jpg");
        updateDTO.setStatus("ACTIVE");

        Course existingCourse = new Course();
        existingCourse.setId(1);
        existingCourse.setTitle("Java Programming");

        Course updatedCourseEntity = new Course();
        updatedCourseEntity.setId(1);
        updatedCourseEntity.setTitle("Advanced Java Programming");

        CourseDTO expectedDTO = new CourseDTO();
        expectedDTO.setId(1);
        expectedDTO.setTitle("Advanced Java Programming");

        when(courseRepo.findById(1)).thenReturn(Optional.of(existingCourse));
        when(categRepo.findById(1)).thenReturn(Optional.of(testCategory));
        when(userClient.findById(1)).thenReturn(testUserDTO); // manager
        when(userClient.findById(1)).thenReturn(testUserDTO); // instructor
        when(courseMapper.toEntity(updateDTO)).thenReturn(updatedCourseEntity);
        when(courseRepo.save(any(Course.class))).thenReturn(updatedCourseEntity);
        when(courseMapper.toDTO(updatedCourseEntity)).thenReturn(expectedDTO);

        // When
        CourseDTO result = courseService.update(1, updateDTO);

        // Then
        assertNotNull(result);
        assertEquals("Advanced Java Programming", result.getTitle());
        verify(courseRepo).findById(1);
        verify(categRepo).findById(1);
        verify(userClient, times(2)).findById(1);
        verify(courseMapper).toEntity(updateDTO);
        verify(courseRepo).save(any(Course.class));
        verify(courseMapper).toDTO(updatedCourseEntity);
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
