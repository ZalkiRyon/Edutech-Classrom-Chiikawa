package com.edutech.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for CourseContent entity - Manual POJO implementation
 */
public class CourseContentDTO {

    @NotNull(message = "El ID del contenido es obligatorio.")
    private Integer id;

    @NotNull(message = "Debe especificar el ID del curso al que pertenece el contenido.")
    private Integer courseId;

    @NotBlank(message = "El título del contenido es obligatorio.")
    @Size(max = 200, message = "El título no puede superar los 200 caracteres.")
    private String title;

    @NotBlank(message = "El tipo de contenido es obligatorio.")
    @Size(max = 50, message = "El tipo de contenido no puede superar los 50 caracteres.")
    private String contentType;

    @NotBlank(message = "La URL del contenido es obligatoria.")
    @Size(max = 800, message = "La URL no puede superar los 800 caracteres.")
    private String url;

    @NotNull(message = "Debe especificar el orden del contenido.")
    @Min(value = 1, message = "El orden debe ser un número positivo mayor a 0.")
    private Integer orderIndex;

    // Constructors
    public CourseContentDTO() {}

    public CourseContentDTO(Integer id, Integer courseId, String title, String contentType, String url, Integer orderIndex) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.contentType = contentType;
        this.url = url;
        this.orderIndex = orderIndex;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}
