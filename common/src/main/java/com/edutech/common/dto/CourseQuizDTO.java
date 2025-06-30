package com.edutech.common.dto;

/**
 * Alias class for QuizDTO to provide better semantic naming
 * that matches the database table name course_quiz and business context
 */
public class CourseQuizDTO extends QuizDTO {
    
    // This class inherits all functionality from QuizDTO
    // but provides a more explicit name that matches the database table and controller naming
    
    public CourseQuizDTO() {
        super();
    }
    
    public CourseQuizDTO(Integer id, Integer courseId, String title, String description, 
                         String quizType, java.time.Instant createdAt) {
        super(id, courseId, title, description, quizType, createdAt);
    }
}
