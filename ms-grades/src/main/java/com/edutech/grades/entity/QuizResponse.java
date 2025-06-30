package com.edutech.grades.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "quiz_response")
public class QuizResponse {
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

    @Size(max = 1)
    @Column(name = "selected_option", length = 1)
    private String selectedOption;

    @Size(max = 800)
    @Column(name = "response_content", length = 800)
    private String responseContent;

    @Size(max = 800)
    @Column(name = "assignment_url", length = 800)
    private String assignmentUrl;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "submitted_at", nullable = false)
    private Instant submittedAt;

    // Constructors
    public QuizResponse() {}

    public QuizResponse(Integer id, Integer quizId, Integer studentId, String selectedOption, 
                       String responseContent, String assignmentUrl, Instant submittedAt) {
        this.id = id;
        this.quizId = quizId;
        this.studentId = studentId;
        this.selectedOption = selectedOption;
        this.responseContent = responseContent;
        this.assignmentUrl = assignmentUrl;
        this.submittedAt = submittedAt;
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

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public String getAssignmentUrl() {
        return assignmentUrl;
    }

    public void setAssignmentUrl(String assignmentUrl) {
        this.assignmentUrl = assignmentUrl;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }
}
