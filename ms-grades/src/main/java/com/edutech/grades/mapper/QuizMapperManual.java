package com.edutech.grades.mapper;

import com.edutech.common.dto.QuizDTO;
import com.edutech.grades.entity.Quiz;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manual mapper for converting between Quiz entity and QuizDTO
 * Replaces MapStruct with manual implementation
 */
@Component
public class QuizMapperManual {

    /**
     * Convert Quiz entity to QuizDTO
     */
    public QuizDTO toDTO(Quiz quiz) {
        if (quiz == null) {
            return null;
        }
        
        QuizDTO dto = new QuizDTO();
        dto.setId(quiz.getId());
        dto.setCourseId(quiz.getCourseId());
        dto.setTitle(quiz.getTitle());
        dto.setDescription(quiz.getDescription());
        dto.setQuizType(quiz.getQuizType());
        dto.setCreatedAt(quiz.getCreatedAt());
        
        return dto;
    }

    /**
     * Convert QuizDTO to Quiz entity
     */
    public Quiz toEntity(QuizDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Quiz quiz = new Quiz();
        quiz.setId(dto.getId());
        quiz.setCourseId(dto.getCourseId());
        quiz.setTitle(dto.getTitle());
        quiz.setDescription(dto.getDescription());
        quiz.setQuizType(dto.getQuizType());
        quiz.setCreatedAt(dto.getCreatedAt());
        
        return quiz;
    }

    /**
     * Convert list of Quiz entities to list of QuizDTOs
     */
    public List<QuizDTO> toDTOList(List<Quiz> quizzes) {
        if (quizzes == null) {
            return null;
        }
        
        return quizzes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert list of QuizDTOs to list of Quiz entities
     */
    public List<Quiz> toEntityList(List<QuizDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Update existing Quiz entity with data from QuizDTO
     * Preserves the entity's ID and createdAt if not provided in DTO
     */
    public void updateEntityFromDTO(QuizDTO dto, Quiz quiz) {
        if (dto == null || quiz == null) {
            return;
        }
        
        if (dto.getCourseId() != null) {
            quiz.setCourseId(dto.getCourseId());
        }
        if (dto.getTitle() != null) {
            quiz.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            quiz.setDescription(dto.getDescription());
        }
        if (dto.getQuizType() != null) {
            quiz.setQuizType(dto.getQuizType());
        }
        // Note: createdAt is typically not updated after creation
    }
}
