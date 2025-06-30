package com.edutech.grades.mapper;

import com.edutech.common.dto.QuizDTO;
import com.edutech.grades.entity.Quiz;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Mapper manual para conversión entre Quiz entity y DTO
 */
@Component("quizMapperManual")
public class QuizMapperManual {
    
    public QuizDTO toDTO(Quiz entity) {
        if (entity == null) {
            return null;
        }
        
        QuizDTO dto = new QuizDTO();
        dto.setId(entity.getId());
        dto.setCourseId(entity.getCourseId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setQuizType(entity.getQuizType());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }

    public Quiz toEntity(QuizDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Quiz entity = new Quiz();
        entity.setId(dto.getId());
        entity.setCourseId(dto.getCourseId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setQuizType(dto.getQuizType());
        entity.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : Instant.now());
        
        return entity;
    }
}
