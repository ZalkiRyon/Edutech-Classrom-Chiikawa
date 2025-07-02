package com.edutech.grades.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.common.dto.CourseQuizDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.grades.client.CourseClient;
import com.edutech.grades.entity.CourseQuiz;
import com.edutech.grades.mapper.CourseQuizMapperManual;
import com.edutech.grades.repository.CourseQuizRepository;

import static com.edutech.common.exception.ExceptionUtils.orThrowFeign;

/**
 * Service for managing course quizzes
 * Handles business logic for quiz operations
 */
@Service
public class CourseQuizService {

    @Autowired
    private CourseQuizRepository courseQuizRepository;

    @Autowired
    private CourseQuizMapperManual courseQuizMapper;
    
    @Autowired
    private CourseClient courseClient;

    /**
     * Get all course quizzes
     */
    public List<CourseQuizDTO> findAll() {
        return courseQuizRepository.findAll()
                .stream()
                .map(courseQuizMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get course quiz by ID
     */
    public CourseQuizDTO findById(Integer id) {
        CourseQuiz courseQuiz = courseQuizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course quiz not found with id: " + id));
        return courseQuizMapper.toDTO(courseQuiz);
    }

    /**
     * Get quizzes by course ID
     */
    public List<CourseQuizDTO> findByCourseId(Integer courseId) {
        return courseQuizRepository.findByCourseIdOrderByCreatedAtDesc(courseId)
                .stream()
                .map(courseQuizMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get quizzes by quiz type
     */
    public List<CourseQuizDTO> findByQuizType(String quizType) {
        return courseQuizRepository.findByQuizTypeOrderByCreatedAtDesc(quizType)
                .stream()
                .map(courseQuizMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get quizzes by course ID and quiz type
     */
    public List<CourseQuizDTO> findByCourseIdAndQuizType(Integer courseId, String quizType) {
        return courseQuizRepository.findByCourseIdAndQuizTypeOrderByCreatedAtDesc(courseId, quizType)
                .stream()
                .map(courseQuizMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create new course quiz
     */
    public CourseQuizDTO create(CourseQuizDTO courseQuizDTO) {
        // Validar que el curso exista usando FeignClient
        orThrowFeign(courseQuizDTO.getCourseId(), courseClient::findById, "Curso");
        
        CourseQuiz courseQuiz = courseQuizMapper.toEntity(courseQuizDTO);
        
        // Set creation timestamp if not provided
        if (courseQuiz.getCreatedAt() == null) {
            courseQuiz.setCreatedAt(Instant.now());
        }
        
        CourseQuiz savedCourseQuiz = courseQuizRepository.save(courseQuiz);
        return courseQuizMapper.toDTO(savedCourseQuiz);
    }

    /**
     * Update existing course quiz
     */
    public CourseQuizDTO update(Integer id, CourseQuizDTO courseQuizDTO) {
        // Verify that the course quiz exists
        if (!courseQuizRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course quiz not found with id: " + id);
        }

        CourseQuiz updatedCourseQuiz = courseQuizMapper.toEntity(courseQuizDTO);
        updatedCourseQuiz.setId(id);
        
        CourseQuiz savedCourseQuiz = courseQuizRepository.save(updatedCourseQuiz);
        return courseQuizMapper.toDTO(savedCourseQuiz);
    }

    /**
     * Delete course quiz
     */
    public void delete(Integer id) {
        CourseQuiz courseQuiz = courseQuizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course quiz not found with id: " + id));
        courseQuizRepository.delete(courseQuiz);
    }

    /**
     * Check if course quiz exists by ID
     */
    public boolean existsById(Integer id) {
        if (id == null) {
            return false;
        }
        return courseQuizRepository.existsById(id);
    }

    /**
     * Count course quizzes by course ID
     */
    public long countByCourseId(Integer courseId) {
        if (courseId == null) {
            return 0L;
        }
        return courseQuizRepository.countByCourseId(courseId);
    }

    /**
     * Check if course has any quizzes
     */
    public boolean existsByCourseId(Integer courseId) {
        if (courseId == null) {
            return false;
        }
        return courseQuizRepository.existsByCourseId(courseId);
    }
}
