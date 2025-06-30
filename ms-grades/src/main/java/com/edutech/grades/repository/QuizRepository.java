package com.edutech.grades.repository;

import com.edutech.grades.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Quiz entity operations
 * Provides data access methods for the course_quiz table
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    
    /**
     * Find all quizzes for a specific course
     */
    List<Quiz> findByCourseId(Integer courseId);
    
    /**
     * Find quizzes by type
     */
    List<Quiz> findByQuizType(String quizType);
    
    /**
     * Find quizzes by course ID and type
     */
    List<Quiz> findByCourseIdAndQuizType(Integer courseId, String quizType);
    
    /**
     * Find quizzes by title containing keyword (case insensitive)
     */
    @Query("SELECT q FROM Quiz q WHERE LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Quiz> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);
    
    /**
     * Count quizzes for a specific course
     */
    long countByCourseId(Integer courseId);
    
    /**
     * Check if a quiz exists for a specific course
     */
    boolean existsByCourseId(Integer courseId);
}
