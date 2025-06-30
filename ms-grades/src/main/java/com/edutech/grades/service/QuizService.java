package com.edutech.grades.service;

import com.edutech.common.dto.QuizDTO;
import com.edutech.grades.entity.Quiz;
import com.edutech.grades.mapper.QuizMapperManual;
import com.edutech.grades.repository.QuizRepository;
import com.edutech.grades.client.CourseClient;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;
import static com.edutech.common.exception.ExceptionUtils.orThrowFeign;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapperManual quizMapper;
    private final CourseClient courseClient;

    public QuizService(QuizRepository quizRepository, QuizMapperManual quizMapper, CourseClient courseClient) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
        this.courseClient = courseClient;
    }

    public List<QuizDTO> findAll() {
        return quizRepository.findAll().stream().map(quizMapper::toDTO).toList();
    }

    public QuizDTO findById(Integer id) {
        return quizMapper.toDTO(orThrow(quizRepository.findById(id), "Quiz"));
    }

    public List<QuizDTO> findByCourseId(Integer courseId) {
        return quizRepository.findByCourseId(courseId).stream().map(quizMapper::toDTO).toList();
    }

    public List<QuizDTO> findByQuizType(String quizType) {
        return quizRepository.findByQuizType(quizType).stream().map(quizMapper::toDTO).toList();
    }

    public QuizDTO create(QuizDTO dto) {
        // Validar que el curso exista
        orThrowFeign(dto.getCourseId(), courseClient::findById, "Curso");

        return saveDTO(dto, null);
    }

    public QuizDTO update(Integer id, QuizDTO dto) {
        orThrow(quizRepository.findById(id), "Quiz");
        
        // Validar que el curso exista
        orThrowFeign(dto.getCourseId(), courseClient::findById, "Curso");
        
        return saveDTO(dto, id);
    }

    public void delete(Integer id) {
        quizRepository.delete(orThrow(quizRepository.findById(id), "Quiz"));
    }

    private QuizDTO saveDTO(QuizDTO dto, Integer id) {
        Quiz entity = quizMapper.toEntity(dto);
        if (id != null) entity.setId(id);
        return quizMapper.toDTO(quizRepository.save(entity));
    }
}
