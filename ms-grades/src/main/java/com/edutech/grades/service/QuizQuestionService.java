package com.edutech.grades.service;

import com.edutech.common.dto.QuizQuestionDTO;
import com.edutech.grades.entity.QuizQuestion;
import com.edutech.grades.mapper.QuizQuestionMapperManual;
import com.edutech.grades.repository.QuizQuestionRepository;
import com.edutech.grades.repository.CourseQuizRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;

@Service
public class QuizQuestionService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizQuestionMapperManual quizQuestionMapper;
    private final CourseQuizRepository courseQuizRepository;

    public QuizQuestionService(QuizQuestionRepository quizQuestionRepository, QuizQuestionMapperManual quizQuestionMapper, CourseQuizRepository courseQuizRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizQuestionMapper = quizQuestionMapper;
        this.courseQuizRepository = courseQuizRepository;
    }

    public List<QuizQuestionDTO> findAll() {
        return quizQuestionRepository.findAll().stream().map(quizQuestionMapper::toDTO).toList();
    }

    public QuizQuestionDTO findById(Integer id) {
        return quizQuestionMapper.toDTO(orThrow(quizQuestionRepository.findById(id), "Pregunta de Quiz"));
    }

    public List<QuizQuestionDTO> findByQuizId(Integer quizId) {
        return quizQuestionRepository.findByQuizId(quizId).stream().map(quizQuestionMapper::toDTO).toList();
    }

    public List<QuizQuestionDTO> findByQuizIdOrderByOrderIndex(Integer quizId) {
        return quizQuestionRepository.findByQuizIdOrderByOrderIndex(quizId).stream().map(quizQuestionMapper::toDTO).toList();
    }

    public QuizQuestionDTO create(QuizQuestionDTO dto) {
        // Validar que el quiz exista
        orThrow(courseQuizRepository.findById(dto.getQuizId()), "Quiz");

        return saveDTO(dto, null);
    }

    public QuizQuestionDTO update(Integer id, QuizQuestionDTO dto) {
        orThrow(quizQuestionRepository.findById(id), "Pregunta de Quiz");
        
        // Validar que el quiz exista
        orThrow(courseQuizRepository.findById(dto.getQuizId()), "Quiz");
        
        return saveDTO(dto, id);
    }

    public void delete(Integer id) {
        quizQuestionRepository.delete(orThrow(quizQuestionRepository.findById(id), "Pregunta de Quiz"));
    }

    private QuizQuestionDTO saveDTO(QuizQuestionDTO dto, Integer id) {
        QuizQuestion entity = quizQuestionMapper.toEntity(dto);
        if (id != null) entity.setId(id);
        return quizQuestionMapper.toDTO(quizQuestionRepository.save(entity));
    }
}
