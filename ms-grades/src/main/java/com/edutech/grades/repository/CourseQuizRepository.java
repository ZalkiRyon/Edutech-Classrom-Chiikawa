package com.edutech.grades.repository;

import com.edutech.grades.entity.CourseQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for CourseQuiz entity
 * Provides data access methods for course quiz management
 */
@Repository
public interface CourseQuizRepository extends JpaRepository<CourseQuiz, Integer> {
    
    /**
     * Find all quizzes for a specific course
     */
    List<CourseQuiz> findByCourseIdOrderByCreatedAtAsc(Integer courseId);
    List<CourseQuiz> findByCourseIdOrderByCreatedAtDesc(Integer courseId);
    
    /**
     * Find quizzes by type
     */
    List<CourseQuiz> findByQuizTypeOrderByCreatedAtAsc(String quizType);
    List<CourseQuiz> findByQuizTypeOrderByCreatedAtDesc(String quizType);
    
    /**
     * Find quizzes by course and type
     */
    List<CourseQuiz> findByCourseIdAndQuizTypeOrderByCreatedAtAsc(Integer courseId, String quizType);
    List<CourseQuiz> findByCourseIdAndQuizTypeOrderByCreatedAtDesc(Integer courseId, String quizType);
}
