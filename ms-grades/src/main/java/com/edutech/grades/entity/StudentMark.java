package com.edutech.grades.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "student_mark")
public class StudentMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "quiz_id", nullable = false)
    private Integer quizId;

    @NotNull
    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @NotNull
    @Column(name = "mark", nullable = false, precision = 5, scale = 2)
    private BigDecimal mark;

    @Size(max = 800)
    @Column(name = "comments", length = 800)
    private String comments;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "graded_at", nullable = false)
    private Instant gradedAt;

    // Constructors
    public StudentMark() {}

    public StudentMark(Integer id, Integer quizId, Integer studentId, BigDecimal mark, String comments, Instant gradedAt) {
        this.id = id;
        this.quizId = quizId;
        this.studentId = studentId;
        this.mark = mark;
        this.comments = comments;
        this.gradedAt = gradedAt;
    }

    // Getters and Setters
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

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Instant getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(Instant gradedAt) {
        this.gradedAt = gradedAt;
    }
}
