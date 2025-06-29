package com.edutech.courses.mapper;

import com.edutech.common.dto.CourseDTO;
import com.edutech.courses.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    
    public CourseDTO toDTO(Course entity) {
        if (entity == null) {
            return null;
        }
        
        CourseDTO dto = new CourseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCategoryId(entity.getCategoryId());
        dto.setManagerId(entity.getManagerId());
        dto.setInstructorId(entity.getInstructorId());
        dto.setPublishDate(entity.getPublishDate());
        dto.setPrice(entity.getPrice());
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus());
        
        return dto;
    }

    public Course toEntity(CourseDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Course entity = new Course();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCategoryId(dto.getCategoryId());
        entity.setManagerId(dto.getManagerId());
        entity.setInstructorId(dto.getInstructorId());
        entity.setPublishDate(dto.getPublishDate());
        entity.setPrice(dto.getPrice());
        entity.setImage(dto.getImage());
        entity.setStatus(dto.getStatus());
        
        return entity;
    }
}
