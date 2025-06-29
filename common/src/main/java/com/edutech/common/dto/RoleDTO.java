package com.edutech.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleDTO {

    private Integer id;

    @NotBlank(message = "El nombre del rol es obligatorio.")
    @Size(max = 50, message = "El nombre del rol no puede exceder los 50 caracteres.")
    private String name;

    @NotBlank(message = "La descripción del rol es obligatoria.")
    @Size(max = 800, message = "La descripción del rol no puede exceder los 800 caracteres.")
    private String description;

    // Constructor por defecto
    public RoleDTO() {}

    // Getters
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return java.util.Objects.equals(id, roleDTO.id) &&
               java.util.Objects.equals(name, roleDTO.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
