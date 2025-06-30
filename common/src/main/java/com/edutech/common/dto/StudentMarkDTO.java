package com.edutech.common.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public class StudentMarkDTO {

    private Integer id;

    @NotNull(message = "Debe especificar el ID del quiz.")
    private Integer quizId;

    @NotNull(message = "Debe especificar el ID del estudiante.")
    private Integer studentId;

    @NotNull(message = "Debe ingresar la nota del estudiante.")
    @DecimalMin(value = "0.0", inclusive = true, message = "La nota debe ser mayor o igual a 0.")
    @DecimalMax(value = "100.0", inclusive = true, message = "La nota no puede superar 100.")
    private BigDecimal mark;

    @Size(max = 800, message = "Los comentarios no pueden superar los 800 caracteres.")
    private String comments;

    @NotNull(message = "Debe registrar la fecha en que se asignó la nota.")
    private Instant gradedAt;

    // Default constructor
    public StudentMarkDTO() {}

    // Constructor with all fields
    public StudentMarkDTO(Integer id, Integer quizId, Integer studentId, BigDecimal mark, 
                         String comments, Instant gradedAt) {
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

    @Override
    public String toString() {
        return "StudentMarkDTO{" +
                "id=" + id +
                ", quizId=" + quizId +
                ", studentId=" + studentId +
                ", mark=" + mark +
                ", comments='" + comments + '\'' +
                ", gradedAt=" + gradedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentMarkDTO that = (StudentMarkDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (quizId != null ? !quizId.equals(that.quizId) : that.quizId != null) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
        return gradedAt != null ? gradedAt.equals(that.gradedAt) : that.gradedAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quizId != null ? quizId.hashCode() : 0);
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (gradedAt != null ? gradedAt.hashCode() : 0);
        return result;
    }
}
