package com.edutech.grades.mapper;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.grades.entity.Enrollment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    EnrollmentDTO toDTO(Enrollment entity);
    Enrollment toEntity(EnrollmentDTO dto);
}
