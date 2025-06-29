package com.edutech.common.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CourseDTO {

    private Integer id;

    @NotBlank(message = "El título del curso es obligatorio.")
    @Size(max = 200, message = "El título no puede exceder los 200 caracteres.")
    private String title;

    @NotBlank(message = "La descripción del curso es obligatoria.")
    @Size(max = 800, message = "La descripción no puede exceder los 800 caracteres.")
    private String description;

    @NotNull(message = "Debe especificar el ID de la categoría.")
    private Integer categoryId;

    @NotNull(message = "Debe especificar el ID del encargado del curso.")
    private Integer managerId;

    @NotNull(message = "Debe especificar el ID del instructor del curso.")
    private Integer instructorId;

    @NotNull(message = "La fecha de publicación es obligatoria.")
    private LocalDate publishDate;

    @NotNull(message = "El precio del curso es obligatorio.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0.")
    private BigDecimal price;

    @NotBlank(message = "La imagen del curso es obligatoria.")
    private String image;

    @NotBlank(message = "El estado del curso es obligatorio.")
    @Size(max = 50, message = "El estado no puede exceder los 50 caracteres.")
    private String status;

    // Constructor por defecto
    public CourseDTO() {}

    // Getters
    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getCategoryId() { return categoryId; }
    public Integer getManagerId() { return managerId; }
    public Integer getInstructorId() { return instructorId; }
    public LocalDate getPublishDate() { return publishDate; }
    public BigDecimal getPrice() { return price; }
    public String getImage() { return image; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
    public void setInstructorId(Integer instructorId) { this.instructorId = instructorId; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setImage(String image) { this.image = image; }
    public void setStatus(String status) { this.status = status; }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return java.util.Objects.equals(id, courseDTO.id) &&
               java.util.Objects.equals(title, courseDTO.title);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", managerId=" + managerId +
                ", instructorId=" + instructorId +
                ", publishDate=" + publishDate +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
