package com.edutech.grades.mapper;

import com.edutech.common.dto.QuizResponseDTO;
import com.edutech.grades.entity.QuizResponse;
import org.springframework.stereotype.Component;

@Component
public class QuizResponseMapperManual {

    public QuizResponseDTO toDTO(QuizResponse entity) {
        if (entity == null) {
            return null;
        }

        QuizResponseDTO dto = new QuizResponseDTO();
        dto.setId(entity.getId());
        dto.setQuizId(entity.getQuizId());
        dto.setStudentId(entity.getStudentId());
        dto.setSelectedOption(entity.getSelectedOption());
        dto.setResponseContent(entity.getResponseContent());
        dto.setAssignmentUrl(entity.getAssignmentUrl());
        dto.setSubmittedAt(entity.getSubmittedAt());

        return dto;
    }

    public QuizResponse toEntity(QuizResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        QuizResponse entity = new QuizResponse();
        entity.setId(dto.getId());
        entity.setQuizId(dto.getQuizId());
        entity.setStudentId(dto.getStudentId());
        entity.setSelectedOption(dto.getSelectedOption());
        entity.setResponseContent(dto.getResponseContent());
        entity.setAssignmentUrl(dto.getAssignmentUrl());
        entity.setSubmittedAt(dto.getSubmittedAt());

        return entity;
    }
}
