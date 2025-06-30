package com.edutech.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public class QuizQuestionDTO {

    private Integer id;

    private Integer quizId;

    @Size(max = 800, message = "El texto de la pregunta no puede exceder los 800 caracteres.")
    private String questionText;

    @Size(max = 800, message = "La opción A no puede exceder los 800 caracteres.")
    private String optionA;

    @Size(max = 800, message = "La opción B no puede exceder los 800 caracteres.")
    private String optionB;

    @Size(max = 800, message = "La opción C no puede exceder los 800 caracteres.")
    private String optionC;

    @Size(max = 800, message = "La opción D no puede exceder los 800 caracteres.")
    private String optionD;

    @Size(max = 800, message = "La opción E no puede exceder los 800 caracteres.")
    private String optionE;

    @Size(max = 800, message = "La respuesta correcta no puede exceder los 800 caracteres.")
    private String correctAnswer;

    @Size(max = 1, message = "La opción correcta debe ser una sola letra.")
    private String correctOption;

    @NotNull(message = "El índice de orden es obligatorio.")
    private Integer orderIndex;

    private Instant createdAt;

    // Constructors
    public QuizQuestionDTO() {}

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
}
