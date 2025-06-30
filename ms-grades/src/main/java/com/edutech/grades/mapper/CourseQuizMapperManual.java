package com.edutech.grades.mapper;

import com.edutech.common.dto.CourseQuizDTO;
import com.edutech.grades.entity.CourseQuiz;
import org.springframework.stereotype.Component;

/**
 * Manual mapper for CourseQuiz entity and DTO conversion
 * Uses CourseQuizDTO for proper semantic naming
 */
@Component
public class CourseQuizMapperManual {

    public CourseQuizDTO toDTO(CourseQuiz entity) {
        if (entity == null) {
            return null;
        }

        CourseQuizDTO dto = new CourseQuizDTO();
        dto.setId(entity.getId());
        dto.setCourseId(entity.getCourseId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setQuizType(entity.getQuizType());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public CourseQuiz toEntity(CourseQuizDTO dto) {
        if (dto == null) {
            return null;
        }

        CourseQuiz entity = new CourseQuiz();
        entity.setId(dto.getId());
        entity.setCourseId(dto.getCourseId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setQuizType(dto.getQuizType());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;
    }
}
