package com.edutech.grades.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

/**
 * JPA Entity for CourseQuizQuestion to match the database table name course_quiz_question
 * This provides proper entity mapping for the course_quiz_question table
 */
@Entity
@Table(name = "course_quiz_question")
public class CourseQuizQuestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "quiz_id")
    private Integer quizId;

    @Column(name = "question_text", length = 800)
    private String questionText;

    @Column(name = "option_a", length = 800)
    private String optionA;

    @Column(name = "option_b", length = 800)
    private String optionB;

    @Column(name = "option_c", length = 800)
    private String optionC;

    @Column(name = "option_d", length = 800)
    private String optionD;

    @Column(name = "option_e", length = 800)
    private String optionE;

    @Column(name = "correct_answer", length = 800)
    private String correctAnswer;

    @Column(name = "correct_option", length = 1)
    private String correctOption;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    // Default constructor
    public CourseQuizQuestion() {
    }
    
    // Parameterized constructor
    public CourseQuizQuestion(Integer id, Integer quizId, String questionText, String optionA, 
                             String optionB, String optionC, String optionD, String optionE, 
                             String correctAnswer, String correctOption, Integer orderIndex, 
                             Instant createdAt) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.correctAnswer = correctAnswer;
        this.correctOption = correctOption;
        this.orderIndex = orderIndex;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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
