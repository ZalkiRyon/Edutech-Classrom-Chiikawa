package com.edutech.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * DTO for Quiz entity
 * Used for compatibility with CourseQuiz mapping
 */
public class QuizDTO {

    private Integer id;

    @NotNull(message = "Debe especificar el ID del curso al que pertenece el quiz.")
    private Integer courseId;

    @NotBlank(message = "El título del quiz es obligatorio.")
    @Size(max = 200, message = "El título no puede exceder los 200 caracteres.")
    private String title;

    @NotBlank(message = "La descripción del quiz es obligatoria.")
    @Size(max = 800, message = "La descripción no puede exceder los 800 caracteres.")
    private String description;

    @NotBlank(message = "El tipo de quiz es obligatorio.")
    @Size(max = 50, message = "El tipo de quiz no puede exceder los 50 caracteres.")
    private String quizType;

    private Instant createdAt;

    // Constructors
    public QuizDTO() {}

    public QuizDTO(Integer id, Integer courseId, String title, String description, String quizType, Instant createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.quizType = quizType;
        this.createdAt = createdAt;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "QuizDTO{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", quizType='" + quizType + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
