package com.edutech.common.dto;

import jakarta.validation.constraints.*;

import java.time.Instant;

public class UserDTO {

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(max = 100, message = "El apellido no puede superar los 100 caracteres.")
    private String lastName;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe ingresar un correo electrónico válido.")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String passwordHash;

    @NotNull(message = "Debe especificar el rol del usuario.")
    private Integer roleId;

    @NotNull(message = "Debe indicar si el usuario está activo.")
    private Boolean isActive;

    @NotNull(message = "La fecha de creación es obligatoria.")
    private Instant createdAt;

    @NotNull(message = "La fecha de última actualización es obligatoria.")
    private Instant updatedAt;

    // Constructor por defecto
    public UserDTO() {}

    // Getters
    public Integer getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Integer getRoleId() { return roleId; }
    public Boolean getIsActive() { return isActive; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return java.util.Objects.equals(id, userDTO.id) &&
               java.util.Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
