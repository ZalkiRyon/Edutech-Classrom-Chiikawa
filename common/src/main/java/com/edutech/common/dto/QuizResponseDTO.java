package com.edutech.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public class QuizResponseDTO {

    private Integer id;

    @NotNull(message = "Debe especificar el ID del quiz.")
    private Integer quizId;

    @NotNull(message = "Debe especificar el ID del estudiante.")
    private Integer studentId;

    @Size(max = 1, message = "La opción seleccionada debe ser solo una letra.")
    private String selectedOption;

    @Size(max = 800, message = "El contenido de la respuesta no puede exceder los 800 caracteres.")
    private String responseContent;

    @Size(max = 800, message = "La URL de entrega no puede exceder los 800 caracteres.")
    private String assignmentUrl;

    @NotNull(message = "La fecha de envío de la respuesta es obligatoria.")
    private Instant submittedAt;

    // Default constructor
    public QuizResponseDTO() {}

    // Constructor with all fields
    public QuizResponseDTO(Integer id, Integer quizId, Integer studentId, String selectedOption, 
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

    @Override
    public String toString() {
        return "QuizResponseDTO{" +
                "id=" + id +
                ", quizId=" + quizId +
                ", studentId=" + studentId +
                ", selectedOption='" + selectedOption + '\'' +
                ", responseContent='" + responseContent + '\'' +
                ", assignmentUrl='" + assignmentUrl + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizResponseDTO that = (QuizResponseDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (quizId != null ? !quizId.equals(that.quizId) : that.quizId != null) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (selectedOption != null ? !selectedOption.equals(that.selectedOption) : that.selectedOption != null)
            return false;
        if (responseContent != null ? !responseContent.equals(that.responseContent) : that.responseContent != null)
            return false;
        if (assignmentUrl != null ? !assignmentUrl.equals(that.assignmentUrl) : that.assignmentUrl != null)
            return false;
        return submittedAt != null ? submittedAt.equals(that.submittedAt) : that.submittedAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quizId != null ? quizId.hashCode() : 0);
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (selectedOption != null ? selectedOption.hashCode() : 0);
        result = 31 * result + (responseContent != null ? responseContent.hashCode() : 0);
        result = 31 * result + (assignmentUrl != null ? assignmentUrl.hashCode() : 0);
        result = 31 * result + (submittedAt != null ? submittedAt.hashCode() : 0);
        return result;
    }
}
