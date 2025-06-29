package com.edutech.users.mapper;

import com.edutech.common.dto.RoleDTO;
import com.edutech.users.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Implementación manual del RoleMapper
 * Reemplaza temporalmente el MapStruct mientras resolvemos compatibilidad
 */
@Component
public class RoleMapperManual {

    public RoleDTO toDTO(Role entity) {
        if (entity == null) {
            return null;
        }

        RoleDTO dto = new RoleDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    public Role toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        Role entity = new Role();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }
}
