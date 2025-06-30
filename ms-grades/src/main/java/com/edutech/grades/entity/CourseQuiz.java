package com.edutech.grades.entity;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * JPA Entity for Quiz to match the database table name course_quiz
 * This provides better semantic naming that matches the database schema and controller naming
 */
@Entity
@Table(name = "course_quiz")
public class CourseQuiz {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "course_id", nullable = false)
    private Integer courseId;
    
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "quiz_type", nullable = false, length = 50)
    private String quizType;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    
    // Default constructor
    public CourseQuiz() {
    }
    
    // Parameterized constructor
    public CourseQuiz(Integer id, Integer courseId, String title, String description, 
                      String quizType, Instant createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.quizType = quizType;
        this.createdAt = createdAt;
    }
    
    // Getters and setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getCourseId() {
        return courseId;
    }
    
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getQuizType() {
        return quizType;
    }
    
    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
