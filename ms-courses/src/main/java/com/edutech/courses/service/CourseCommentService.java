package com.edutech.courses.service;

import com.edutech.common.dto.CourseCommentDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.courses.entity.CourseComment;
import com.edutech.courses.mapper.CourseCommentMapperManual;
import com.edutech.courses.repository.CourseCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing course comments
 */
@Service
public class CourseCommentService {

    @Autowired
    private CourseCommentRepository courseCommentRepository;

    @Autowired
    private CourseCommentMapperManual courseCommentMapper;

    /**
     * Get all course comments
     */
    public List<CourseCommentDTO> findAll() {
        return courseCommentRepository.findAll()
                .stream()
                .map(courseCommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get course comment by ID
     */
    public CourseCommentDTO findById(Integer id) {
        CourseComment courseComment = courseCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course comment not found with id: " + id));
        return courseCommentMapper.toDTO(courseComment);
    }

    /**
     * Get comments by course ID
     */
    public List<CourseCommentDTO> findByCourseId(Integer courseId) {
        return courseCommentRepository.findByCourseIdOrderByCreatedAtDesc(courseId)
                .stream()
                .map(courseCommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get comments by user ID
     */
    public List<CourseCommentDTO> findByUserId(Integer userId) {
        return courseCommentRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(courseCommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create new course comment
     */
    public CourseCommentDTO create(CourseCommentDTO courseCommentDTO) {
        CourseComment courseComment = courseCommentMapper.toEntity(courseCommentDTO);
        
        // Set creation timestamp if not provided
        if (courseComment.getCreatedAt() == null) {
            courseComment.setCreatedAt(Instant.now());
        }
        
        CourseComment savedCourseComment = courseCommentRepository.save(courseComment);
        return courseCommentMapper.toDTO(savedCourseComment);
    }

    /**
     * Update existing course comment
     */
    public CourseCommentDTO update(Integer id, CourseCommentDTO courseCommentDTO) {
        // Verify that the course comment exists
        if (!courseCommentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course comment not found with id: " + id);
        }

        CourseComment updatedCourseComment = courseCommentMapper.toEntity(courseCommentDTO);
        updatedCourseComment.setId(id);
        
        CourseComment savedCourseComment = courseCommentRepository.save(updatedCourseComment);
        return courseCommentMapper.toDTO(savedCourseComment);
    }

    /**
     * Delete course comment
     */
    public void delete(Integer id) {
        CourseComment courseComment = courseCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course comment not found with id: " + id));
        courseCommentRepository.delete(courseComment);
    }
}
