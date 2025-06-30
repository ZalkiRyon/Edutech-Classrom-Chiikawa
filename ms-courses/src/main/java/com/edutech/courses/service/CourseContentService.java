package com.edutech.courses.service;

import com.edutech.common.dto.CourseContentDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.courses.entity.CourseContent;
import com.edutech.courses.mapper.CourseContentMapperManual;
import com.edutech.courses.repository.CourseContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing course content
 */
@Service
public class CourseContentService {

    @Autowired
    private CourseContentRepository courseContentRepository;

    @Autowired
    private CourseContentMapperManual courseContentMapper;

    /**
     * Get all course contents
     */
    public List<CourseContentDTO> findAll() {
        return courseContentRepository.findAll()
                .stream()
                .map(courseContentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get course content by ID
     */
    public CourseContentDTO findById(Integer id) {
        CourseContent courseContent = courseContentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course content not found with id: " + id));
        return courseContentMapper.toDTO(courseContent);
    }

    /**
     * Get course contents by course ID
     */
    public List<CourseContentDTO> findByCourseId(Integer courseId) {
        return courseContentRepository.findByCourseIdOrderByOrderIndexAsc(courseId)
                .stream()
                .map(courseContentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create new course content
     */
    public CourseContentDTO create(CourseContentDTO courseContentDTO) {
        CourseContent courseContent = courseContentMapper.toEntity(courseContentDTO);
        CourseContent savedCourseContent = courseContentRepository.save(courseContent);
        return courseContentMapper.toDTO(savedCourseContent);
    }

    /**
     * Update existing course content
     */
    public CourseContentDTO update(Integer id, CourseContentDTO courseContentDTO) {
        // Verify that the course content exists
        if (!courseContentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course content not found with id: " + id);
        }

        CourseContent updatedCourseContent = courseContentMapper.toEntity(courseContentDTO);
        updatedCourseContent.setId(id);
        
        CourseContent savedCourseContent = courseContentRepository.save(updatedCourseContent);
        return courseContentMapper.toDTO(savedCourseContent);
    }

    /**
     * Delete course content
     */
    public void delete(Integer id) {
        CourseContent courseContent = courseContentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course content not found with id: " + id));
        courseContentRepository.delete(courseContent);
    }
}
