package com.edutech.grades.service;

import com.edutech.common.dto.QuizResponseDTO;
import com.edutech.grades.entity.QuizResponse;
import com.edutech.grades.mapper.QuizResponseMapperManual;
import com.edutech.grades.repository.QuizResponseRepository;
import com.edutech.grades.repository.CourseQuizRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;

@Service
public class QuizResponseService {

    private final QuizResponseRepository quizResponseRepository;
    private final QuizResponseMapperManual quizResponseMapper;
    private final CourseQuizRepository courseQuizRepository;

    public QuizResponseService(QuizResponseRepository quizResponseRepository, 
                              QuizResponseMapperManual quizResponseMapper, 
                              CourseQuizRepository courseQuizRepository) {
        this.quizResponseRepository = quizResponseRepository;
        this.quizResponseMapper = quizResponseMapper;
        this.courseQuizRepository = courseQuizRepository;
    }

    public List<QuizResponseDTO> findAll() {
        return quizResponseRepository.findAll().stream().map(quizResponseMapper::toDTO).toList();
    }

    public QuizResponseDTO findById(Integer id) {
        return quizResponseMapper.toDTO(orThrow(quizResponseRepository.findById(id), "Respuesta de Quiz"));
    }

    public List<QuizResponseDTO> findByQuizId(Integer quizId) {
        return quizResponseRepository.findByQuizId(quizId).stream().map(quizResponseMapper::toDTO).toList();
    }

    public List<QuizResponseDTO> findByStudentId(Integer studentId) {
        return quizResponseRepository.findByStudentId(studentId).stream().map(quizResponseMapper::toDTO).toList();
    }

    public List<QuizResponseDTO> findByQuizIdAndStudentId(Integer quizId, Integer studentId) {
        return quizResponseRepository.findByStudentIdAndQuizId(studentId, quizId).stream().map(quizResponseMapper::toDTO).toList();
    }

    public QuizResponseDTO create(QuizResponseDTO dto) {
        // Validar que el quiz exista
        orThrow(courseQuizRepository.findById(dto.getQuizId()), "Quiz");

        return saveDTO(dto, null);
    }

    public QuizResponseDTO update(Integer id, QuizResponseDTO dto) {
        orThrow(quizResponseRepository.findById(id), "Respuesta de Quiz");
        
        // Validar que el quiz exista
        orThrow(courseQuizRepository.findById(dto.getQuizId()), "Quiz");
        
        return saveDTO(dto, id);
    }

    public void delete(Integer id) {
        quizResponseRepository.delete(orThrow(quizResponseRepository.findById(id), "Respuesta de Quiz"));
    }

    private QuizResponseDTO saveDTO(QuizResponseDTO dto, Integer id) {
        QuizResponse entity = quizResponseMapper.toEntity(dto);
        if (id != null) entity.setId(id);
        return quizResponseMapper.toDTO(quizResponseRepository.save(entity));
    }
}
