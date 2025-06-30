package com.edutech.grades.service;

import com.edutech.common.dto.QuizDTO;
import com.edutech.grades.entity.Quiz;
import com.edutech.grades.mapper.QuizMapperManual;
import com.edutech.grades.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Quiz operations
 * Handles business logic for quiz management
 */
@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapperManual quizMapper;

    public QuizService(QuizRepository quizRepository, QuizMapperManual quizMapper) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
    }

    /**
     * Create a new quiz
     */
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        if (quizDTO == null) {
            throw new IllegalArgumentException("Quiz data cannot be null");
        }
        
        Quiz quiz = quizMapper.toEntity(quizDTO);
        Quiz savedQuiz = quizRepository.save(quiz);
        return quizMapper.toDTO(savedQuiz);
    }

    /**
     * Get quiz by ID
     */
    @Transactional(readOnly = true)
    public Optional<QuizDTO> getQuizById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        
        return quizRepository.findById(id)
                .map(quizMapper::toDTO);
    }

    /**
     * Get all quizzes
     */
    @Transactional(readOnly = true)
    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizMapper.toDTOList(quizzes);
    }

    /**
     * Get quizzes by course ID
     */
    @Transactional(readOnly = true)
    public List<QuizDTO> getQuizzesByCourseId(Integer courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("Course ID cannot be null");
        }
        
        List<Quiz> quizzes = quizRepository.findByCourseId(courseId);
        return quizMapper.toDTOList(quizzes);
    }

    /**
     * Get quizzes by type
     */
    @Transactional(readOnly = true)
    public List<QuizDTO> getQuizzesByType(String quizType) {
        if (quizType == null || quizType.trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz type cannot be null or empty");
        }
        
        List<Quiz> quizzes = quizRepository.findByQuizType(quizType.trim());
        return quizMapper.toDTOList(quizzes);
    }

    /**
     * Get quizzes by course ID and type
     */
    @Transactional(readOnly = true)
    public List<QuizDTO> getQuizzesByCourseIdAndType(Integer courseId, String quizType) {
        if (courseId == null) {
            throw new IllegalArgumentException("Course ID cannot be null");
        }
        if (quizType == null || quizType.trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz type cannot be null or empty");
        }
        
        List<Quiz> quizzes = quizRepository.findByCourseIdAndQuizType(courseId, quizType.trim());
        return quizMapper.toDTOList(quizzes);
    }

    /**
     * Search quizzes by title keyword
     */
    @Transactional(readOnly = true)
    public List<QuizDTO> searchQuizzesByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllQuizzes();
        }
        
        List<Quiz> quizzes = quizRepository.findByTitleContainingIgnoreCase(keyword.trim());
        return quizMapper.toDTOList(quizzes);
    }

    /**
     * Update quiz
     */
    public QuizDTO updateQuiz(Integer id, QuizDTO quizDTO) {
        if (id == null) {
            throw new IllegalArgumentException("Quiz ID cannot be null");
        }
        if (quizDTO == null) {
            throw new IllegalArgumentException("Quiz data cannot be null");
        }
        
        Optional<Quiz> existingQuizOpt = quizRepository.findById(id);
        if (existingQuizOpt.isEmpty()) {
            throw new RuntimeException("Quiz not found with ID: " + id);
        }
        
        Quiz existingQuiz = existingQuizOpt.get();
        quizMapper.updateEntityFromDTO(quizDTO, existingQuiz);
        
        Quiz updatedQuiz = quizRepository.save(existingQuiz);
        return quizMapper.toDTO(updatedQuiz);
    }

    /**
     * Delete quiz by ID
     */
    public boolean deleteQuiz(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Quiz ID cannot be null");
        }
        
        if (!quizRepository.existsById(id)) {
            return false;
        }
        
        quizRepository.deleteById(id);
        return true;
    }

    /**
     * Check if quiz exists
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        if (id == null) {
            return false;
        }
        return quizRepository.existsById(id);
    }

    /**
     * Count quizzes for a specific course
     */
    @Transactional(readOnly = true)
    public long countQuizzesByCourseId(Integer courseId) {
        if (courseId == null) {
            return 0;
        }
        return quizRepository.countByCourseId(courseId);
    }

    /**
     * Check if quizzes exist for a specific course
     */
    @Transactional(readOnly = true)
    public boolean existsByCourseId(Integer courseId) {
        if (courseId == null) {
            return false;
        }
        return quizRepository.existsByCourseId(courseId);
    }
}
