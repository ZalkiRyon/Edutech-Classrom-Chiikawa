package com.edutech.grades.mapper;

import com.edutech.common.dto.CourseQuizQuestionDTO;
import com.edutech.grades.entity.CourseQuizQuestion;
import org.springframework.stereotype.Component;

/**
 * Manual mapper for CourseQuizQuestion entity and DTO
 * Replaces MapStruct with manual implementation
 */
@Component
public class CourseQuizQuestionMapperManual {

    /**
     * Convert CourseQuizQuestion entity to CourseQuizQuestionDTO
     */
    public CourseQuizQuestionDTO toDTO(CourseQuizQuestion entity) {
        if (entity == null) {
            return null;
        }
        
        return new CourseQuizQuestionDTO(
            entity.getId(),
            entity.getQuizId(),
            entity.getQuestionText(),
            entity.getOptionA(),
            entity.getOptionB(),
            entity.getOptionC(),
            entity.getOptionD(),
            entity.getOptionE(),
            entity.getCorrectAnswer(),
            entity.getCorrectOption(),
            entity.getOrderIndex(),
            entity.getCreatedAt()
        );
    }

    /**
     * Convert CourseQuizQuestionDTO to CourseQuizQuestion entity
     */
    public CourseQuizQuestion toEntity(CourseQuizQuestionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new CourseQuizQuestion(
            dto.getId(),
            dto.getQuizId(),
            dto.getQuestionText(),
            dto.getOptionA(),
            dto.getOptionB(),
            dto.getOptionC(),
            dto.getOptionD(),
            dto.getOptionE(),
            dto.getCorrectAnswer(),
            dto.getCorrectOption(),
            dto.getOrderIndex(),
            dto.getCreatedAt()
        );
    }
}
