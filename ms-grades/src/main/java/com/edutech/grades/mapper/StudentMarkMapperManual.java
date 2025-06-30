package com.edutech.grades.mapper;

import com.edutech.common.dto.StudentMarkDTO;
import com.edutech.grades.entity.StudentMark;
import org.springframework.stereotype.Component;

@Component
public class StudentMarkMapperManual {

    public StudentMarkDTO toDTO(StudentMark entity) {
        if (entity == null) {
            return null;
        }

        StudentMarkDTO dto = new StudentMarkDTO();
        dto.setId(entity.getId());
        dto.setQuizId(entity.getQuizId());
        dto.setStudentId(entity.getStudentId());
        dto.setMark(entity.getMark());
        dto.setComments(entity.getComments());
        dto.setGradedAt(entity.getGradedAt());

        return dto;
    }

    public StudentMark toEntity(StudentMarkDTO dto) {
        if (dto == null) {
            return null;
        }

        StudentMark entity = new StudentMark();
        entity.setId(dto.getId());
        entity.setQuizId(dto.getQuizId());
        entity.setStudentId(dto.getStudentId());
        entity.setMark(dto.getMark());
        entity.setComments(dto.getComments());
        entity.setGradedAt(dto.getGradedAt());

        return entity;
    }
}
