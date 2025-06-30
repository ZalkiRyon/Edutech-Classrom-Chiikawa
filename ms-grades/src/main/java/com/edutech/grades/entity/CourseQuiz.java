package com.edutech.grades.entity;

/**
 * Alias class for Quiz to match the database table name course_quiz
 * This provides better semantic naming that matches the database schema and controller naming
 */
public class CourseQuiz extends Quiz {
    
    // This class inherits all functionality from Quiz
    // but provides a more explicit name that matches the database table and business context
    
    public CourseQuiz() {
        super();
    }
    
    public CourseQuiz(Integer id, Integer courseId, String title, String description, 
                      String quizType, java.time.Instant createdAt) {
        super(id, courseId, title, description, quizType, createdAt);
    }
}
