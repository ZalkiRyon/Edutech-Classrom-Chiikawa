package com.edutech.courses.mapper;

import org.springframework.stereotype.Component;

import com.edutech.common.dto.CourseCategoryDTO;
import com.edutech.courses.entity.CourseCategory;

@Component
public class CourseCategoryMapper {
    
    public CourseCategoryDTO toDTO(CourseCategory entity) {
        if (entity == null) {
            return null;
        }
        
        CourseCategoryDTO dto = new CourseCategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        
        return dto;
    }

    public CourseCategory toEntity(CourseCategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        CourseCategory entity = new CourseCategory();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        
        return entity;
    }
}
