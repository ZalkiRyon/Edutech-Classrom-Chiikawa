package com.edutech.grades.service;

import com.edutech.common.dto.CourseQuizQuestionDTO;
import com.edutech.grades.entity.CourseQuizQuestion;
import com.edutech.grades.mapper.CourseQuizQuestionMapperManual;
import com.edutech.grades.repository.CourseQuizQuestionRepository;
import com.edutech.grades.repository.CourseQuizRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;

@Service
@Transactional
public class CourseQuizQuestionService {

    private final CourseQuizQuestionRepository courseQuizQuestionRepository;
    private final CourseQuizQuestionMapperManual courseQuizQuestionMapper;
    private final CourseQuizRepository courseQuizRepository;

    public CourseQuizQuestionService(CourseQuizQuestionRepository courseQuizQuestionRepository, 
                                   CourseQuizQuestionMapperManual courseQuizQuestionMapper,
                                   CourseQuizRepository courseQuizRepository) {
        this.courseQuizQuestionRepository = courseQuizQuestionRepository;
        this.courseQuizQuestionMapper = courseQuizQuestionMapper;
        this.courseQuizRepository = courseQuizRepository;
    }

    @Transactional(readOnly = true)
    public List<CourseQuizQuestionDTO> findAll() {
        return courseQuizQuestionRepository.findAll().stream()
                .map(courseQuizQuestionMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseQuizQuestionDTO findById(Integer id) {
        CourseQuizQuestion question = orThrow(courseQuizQuestionRepository.findById(id), "Pregunta de Quiz");
        return courseQuizQuestionMapper.toDTO(question);
    }

    @Transactional(readOnly = true)
    public List<CourseQuizQuestionDTO> findByQuizId(Integer quizId) {
        return courseQuizQuestionRepository.findByQuizId(quizId).stream()
                .map(courseQuizQuestionMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseQuizQuestionDTO> findByQuizIdOrderByOrderIndex(Integer quizId) {
        return courseQuizQuestionRepository.findByQuizIdOrderByOrderIndex(quizId).stream()
                .map(courseQuizQuestionMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseQuizQuestionDTO> findByQuizIdOrderByOrderIndexAsc(Integer quizId) {
        return courseQuizQuestionRepository.findByQuizIdOrderByOrderIndexAsc(quizId).stream()
                .map(courseQuizQuestionMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseQuizQuestionDTO> findByQuizIdOrderByOrderIndexDesc(Integer quizId) {
        return courseQuizQuestionRepository.findByQuizIdOrderByOrderIndexDesc(quizId).stream()
                .map(courseQuizQuestionMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public long countByQuizId(Integer quizId) {
        return courseQuizQuestionRepository.countByQuizId(quizId);
    }

    public CourseQuizQuestionDTO create(CourseQuizQuestionDTO dto) {
        // Validar que el quiz exista
        orThrow(courseQuizRepository.findById(dto.getQuizId()), "Quiz");

        return saveDTO(dto, null);
    }

    public CourseQuizQuestionDTO update(Integer id, CourseQuizQuestionDTO dto) {
        orThrow(courseQuizQuestionRepository.findById(id), "Pregunta de Quiz");
        
        // Validar que el quiz exista
        orThrow(courseQuizRepository.findById(dto.getQuizId()), "Quiz");
        
        return saveDTO(dto, id);
    }

    public void delete(Integer id) {
        CourseQuizQuestion question = orThrow(courseQuizQuestionRepository.findById(id), "Pregunta de Quiz");
        courseQuizQuestionRepository.delete(question);
    }

    public void deleteByQuizId(Integer quizId) {
        courseQuizQuestionRepository.deleteByQuizId(quizId);
    }

    private CourseQuizQuestionDTO saveDTO(CourseQuizQuestionDTO dto, Integer id) {
        CourseQuizQuestion entity = courseQuizQuestionMapper.toEntity(dto);
        if (id != null) {
            entity.setId(id);
        }
        
        // Set created_at if it's a new entity
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(java.time.Instant.now());
        }
        
        CourseQuizQuestion savedEntity = courseQuizQuestionRepository.save(entity);
        return courseQuizQuestionMapper.toDTO(savedEntity);
    }
}
