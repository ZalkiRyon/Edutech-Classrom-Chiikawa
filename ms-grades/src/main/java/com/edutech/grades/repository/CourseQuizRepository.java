package com.edutech.grades.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.grades.entity.CourseQuiz;

/**
 * Repository for CourseQuiz entity
 * Provides data access methods for course quiz management
 */
@Repository
public interface CourseQuizRepository extends JpaRepository<CourseQuiz, Integer> {
    
    /**
     * Find all quizzes for a specific course
     */
    List<CourseQuiz> findByCourseId(Integer courseId);
    List<CourseQuiz> findByCourseIdOrderByCreatedAtAsc(Integer courseId);
    List<CourseQuiz> findByCourseIdOrderByCreatedAtDesc(Integer courseId);
    
    /**
     * Find quizzes by type
     */
    List<CourseQuiz> findByQuizType(String quizType);
    List<CourseQuiz> findByQuizTypeOrderByCreatedAtAsc(String quizType);
    List<CourseQuiz> findByQuizTypeOrderByCreatedAtDesc(String quizType);
    
    /**
     * Find quizzes by course and type
     */
    List<CourseQuiz> findByCourseIdAndQuizType(Integer courseId, String quizType);
    List<CourseQuiz> findByCourseIdAndQuizTypeOrderByCreatedAtAsc(Integer courseId, String quizType);
    List<CourseQuiz> findByCourseIdAndQuizTypeOrderByCreatedAtDesc(Integer courseId, String quizType);
    
    /**
     * Count methods
     */
    long countByCourseId(Integer courseId);
    
    /**
     * Existence check methods
     */
    boolean existsByCourseId(Integer courseId);
}
