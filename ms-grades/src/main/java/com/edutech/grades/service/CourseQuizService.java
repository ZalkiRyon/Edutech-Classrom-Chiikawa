package com.edutech.grades.service;

import com.edutech.common.dto.QuizDTO;
import com.edutech.common.exception.ResourceNotFoundException;
import com.edutech.grades.entity.CourseQuiz;
import com.edutech.grades.mapper.CourseQuizMapperManual;
import com.edutech.grades.repository.CourseQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Get all course quizzes
     */
    public List<QuizDTO> findAll() {
        return courseQuizRepository.findAll()
                .stream()
                .map(courseQuizMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get course quiz by ID
     */
    public QuizDTO findById(Integer id) {
        CourseQuiz courseQuiz = courseQuizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course quiz not found with id: " + id));
        return courseQuizMapper.toDTO(courseQuiz);
    }

    /**
     * Get quizzes by course ID
     */
    public List<QuizDTO> findByCourseId(Integer courseId) {
        return courseQuizRepository.findByCourseIdOrderByCreatedAtDesc(courseId)
                .stream()
                .map(courseQuizMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get quizzes by quiz type
     */
    public List<QuizDTO> findByQuizType(String quizType) {
        return courseQuizRepository.findByQuizTypeOrderByCreatedAtDesc(quizType)
                .stream()
                .map(courseQuizMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get quizzes by course ID and quiz type
     */
    public List<QuizDTO> findByCourseIdAndQuizType(Integer courseId, String quizType) {
        return courseQuizRepository.findByCourseIdAndQuizTypeOrderByCreatedAtDesc(courseId, quizType)
                .stream()
                .map(courseQuizMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create new course quiz
     */
    public QuizDTO create(QuizDTO courseQuizDTO) {
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
    public QuizDTO update(Integer id, QuizDTO courseQuizDTO) {
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
}
