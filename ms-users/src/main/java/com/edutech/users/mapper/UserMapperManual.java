package com.edutech.users.mapper;

import com.edutech.common.dto.UserDTO;
import com.edutech.users.entity.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Implementación manual del UserMapper como respaldo
 * En caso de que MapStruct tenga problemas de compatibilidad
 */
@Component("userMapperManual")
public class UserMapperManual {

    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPasswordHash(entity.getPasswordHash());
        dto.setRoleId(entity.getRoleId());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPasswordHash(dto.getPasswordHash());
        entity.setRoleId(dto.getRoleId());
        entity.setIsActive(dto.getIsActive());
        entity.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : Instant.now());
        entity.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : Instant.now());

        return entity;
    }
}
