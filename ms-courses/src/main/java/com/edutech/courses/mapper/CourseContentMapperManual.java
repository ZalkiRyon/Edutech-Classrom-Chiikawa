package com.edutech.courses.mapper;

import com.edutech.common.dto.CourseContentDTO;
import com.edutech.courses.entity.CourseContent;
import org.springframework.stereotype.Component;

/**
 * Manual mapper for CourseContent entity and DTO conversion
 * Replaces MapStruct implementation for better control and no dependency issues
 */
@Component
public class CourseContentMapperManual {

    public CourseContentDTO toDTO(CourseContent entity) {
        if (entity == null) {
            return null;
        }

        CourseContentDTO dto = new CourseContentDTO();
        dto.setId(entity.getId());
        dto.setCourseId(entity.getCourseId());
        dto.setTitle(entity.getTitle());
        dto.setContentType(entity.getContentType());
        dto.setUrl(entity.getUrl());
        dto.setOrderIndex(entity.getOrderIndex());

        return dto;
    }

    public CourseContent toEntity(CourseContentDTO dto) {
        if (dto == null) {
            return null;
        }

        CourseContent entity = new CourseContent();
        entity.setId(dto.getId());
        entity.setCourseId(dto.getCourseId());
        entity.setTitle(dto.getTitle());
        entity.setContentType(dto.getContentType());
        entity.setUrl(dto.getUrl());
        entity.setOrderIndex(dto.getOrderIndex());

        return entity;
    }
}
