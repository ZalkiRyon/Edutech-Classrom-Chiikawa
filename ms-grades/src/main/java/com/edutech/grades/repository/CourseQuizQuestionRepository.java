package com.edutech.grades.repository;

import com.edutech.grades.entity.CourseQuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseQuizQuestionRepository extends JpaRepository<CourseQuizQuestion, Integer> {
    
    /**
     * Find all questions for a specific quiz
     */
    List<CourseQuizQuestion> findByQuizId(Integer quizId);
    
    /**
     * Find all questions for a specific quiz ordered by order_index
     */
    List<CourseQuizQuestion> findByQuizIdOrderByOrderIndex(Integer quizId);
    
    /**
     * Find all questions for a specific quiz ordered by order_index in ascending order
     */
    List<CourseQuizQuestion> findByQuizIdOrderByOrderIndexAsc(Integer quizId);
    
    /**
     * Find all questions for a specific quiz ordered by order_index in descending order  
     */
    List<CourseQuizQuestion> findByQuizIdOrderByOrderIndexDesc(Integer quizId);
    
    /**
     * Count questions for a specific quiz
     */
    long countByQuizId(Integer quizId);
    
    /**
     * Check if any questions exist for a quiz
     */
    boolean existsByQuizId(Integer quizId);
    
    /**
     * Delete all questions for a specific quiz
     */
    void deleteByQuizId(Integer quizId);
}
