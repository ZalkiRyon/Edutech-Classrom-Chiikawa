package com.edutech.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * DTO for CourseComment entity - Manual POJO implementation
 */
public class CourseCommentDTO {

    @NotNull(message = "El ID del comentario es obligatorio.")
    private Integer id;

    @NotNull(message = "Debe especificar el ID del curso.")
    private Integer courseId;

    @NotNull(message = "Debe especificar el ID del usuario.")
    private Integer userId;

    @NotBlank(message = "El texto del comentario es obligatorio.")
    @Size(max = 800, message = "El comentario no puede exceder los 800 caracteres.")
    private String commentText;

    @NotNull(message = "Debe especificar una calificación.")
    @Min(value = 0, message = "La calificación mínima es 0.")
    @Max(value = 5, message = "La calificación máxima es 5.")
    private Integer rating;

    @NotNull(message = "La fecha de creación del comentario es obligatoria.")
    private Instant createdAt;

    // Constructors
    public CourseCommentDTO() {}

    public CourseCommentDTO(Integer id, Integer courseId, Integer userId, String commentText, Integer rating, Instant createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.commentText = commentText;
        this.rating = rating;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}