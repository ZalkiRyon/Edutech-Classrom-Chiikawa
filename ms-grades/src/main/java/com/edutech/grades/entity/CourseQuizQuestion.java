package com.edutech.grades.entity;

/**
 * Alias class for QuizQuestion to match the database table name course_quiz_question
 * This provides better semantic naming that matches the database schema
 */
public class CourseQuizQuestion extends QuizQuestion {
    
    // This class inherits all functionality from QuizQuestion
    // but provides a more explicit name that matches the database table
    
    public CourseQuizQuestion() {
        super();
    }
    
    public CourseQuizQuestion(Integer id, Integer quizId, String questionText, String optionA, 
                             String optionB, String optionC, String optionD, String optionE, 
                             String correctAnswer, String correctOption, Integer orderIndex, 
                             java.time.Instant createdAt) {
        super(id, quizId, questionText, optionA, optionB, optionC, optionD, optionE, 
              correctAnswer, correctOption, orderIndex, createdAt);
    }
}
