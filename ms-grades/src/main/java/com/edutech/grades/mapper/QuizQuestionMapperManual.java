package com.edutech.grades.mapper;

import com.edutech.common.dto.QuizQuestionDTO;
import com.edutech.grades.entity.CourseQuizQuestion;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Mapper manual para conversión entre CourseQuizQuestion entity y DTO
 */
@Component("quizQuestionMapperManual")
public class QuizQuestionMapperManual {
    
    public QuizQuestionDTO toDTO(CourseQuizQuestion entity) {
        if (entity == null) {
            return null;
        }
        
        QuizQuestionDTO dto = new QuizQuestionDTO();
        dto.setId(entity.getId());
        dto.setQuizId(entity.getQuizId());
        dto.setQuestionText(entity.getQuestionText());
        dto.setOptionA(entity.getOptionA());
        dto.setOptionB(entity.getOptionB());
        dto.setOptionC(entity.getOptionC());
        dto.setOptionD(entity.getOptionD());
        dto.setOptionE(entity.getOptionE());
        dto.setCorrectAnswer(entity.getCorrectAnswer());
        dto.setCorrectOption(entity.getCorrectOption());
        dto.setOrderIndex(entity.getOrderIndex());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }

    public CourseQuizQuestion toEntity(QuizQuestionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        CourseQuizQuestion entity = new CourseQuizQuestion();
        entity.setId(dto.getId());
        entity.setQuizId(dto.getQuizId());
        entity.setQuestionText(dto.getQuestionText());
        entity.setOptionA(dto.getOptionA());
        entity.setOptionB(dto.getOptionB());
        entity.setOptionC(dto.getOptionC());
        entity.setOptionD(dto.getOptionD());
        entity.setOptionE(dto.getOptionE());
        entity.setCorrectAnswer(dto.getCorrectAnswer());
        entity.setCorrectOption(dto.getCorrectOption());
        entity.setOrderIndex(dto.getOrderIndex());
        entity.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : Instant.now());
        
        return entity;
    }
}
