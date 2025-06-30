package com.edutech.courses.mapper;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.courses.entity.Enrollment;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Mapper manual para conversión entre Enrollment entity y DTO
 */
@Component("enrollmentMapperManual")
public class EnrollmentMapperManual {
    
    public EnrollmentDTO toDTO(Enrollment entity) {
        if (entity == null) {
            return null;
        }
        
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudentId());
        dto.setCourseId(entity.getCourseId());
        dto.setEnrolledAt(entity.getEnrolledAt());
        dto.setStatus(entity.getStatus());
        
        return dto;
    }

    public Enrollment toEntity(EnrollmentDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Enrollment entity = new Enrollment();
        entity.setId(dto.getId());
        entity.setStudentId(dto.getStudentId());
        entity.setCourseId(dto.getCourseId());
        entity.setEnrolledAt(dto.getEnrolledAt() != null ? dto.getEnrolledAt() : Instant.now());
        entity.setStatus(dto.getStatus());
        
        return entity;
    }
}
