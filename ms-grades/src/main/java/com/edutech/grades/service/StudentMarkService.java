package com.edutech.grades.service;

import com.edutech.common.dto.StudentMarkDTO;
import com.edutech.grades.client.UserClient;
import com.edutech.grades.entity.StudentMark;
import com.edutech.grades.mapper.StudentMarkMapperManual;
import com.edutech.grades.repository.StudentMarkRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;
import static com.edutech.common.exception.ExceptionUtils.orThrowFeign;

@Service
public class StudentMarkService {

    private final StudentMarkRepository studentMarkRepository;
    private final StudentMarkMapperManual studentMarkMapper;
    private final UserClient userClient;

    public StudentMarkService(StudentMarkRepository studentMarkRepository, 
                             StudentMarkMapperManual studentMarkMapper,
                             UserClient userClient) {
        this.studentMarkRepository = studentMarkRepository;
        this.studentMarkMapper = studentMarkMapper;
        this.userClient = userClient;
    }

    public List<StudentMarkDTO> findAll() {
        return studentMarkRepository.findAll().stream().map(studentMarkMapper::toDTO).toList();
    }

    public StudentMarkDTO findById(Integer id) {
        return studentMarkMapper.toDTO(orThrow(studentMarkRepository.findById(id), "Calificación"));
    }

    public List<StudentMarkDTO> findByStudentId(Integer studentId) {
        return studentMarkRepository.findByStudentId(studentId).stream().map(studentMarkMapper::toDTO).toList();
    }

    public List<StudentMarkDTO> findByQuizId(Integer quizId) {
        return studentMarkRepository.findByQuizId(quizId).stream().map(studentMarkMapper::toDTO).toList();
    }

    public StudentMarkDTO create(StudentMarkDTO dto) {
        // Validar que el estudiante exista
        orThrowFeign(dto.getStudentId(), userClient::findById, "Estudiante");

        // Crear nueva calificación
        return saveDTO(dto, null);
    }

    public StudentMarkDTO update(Integer id, StudentMarkDTO dto) {
        orThrow(studentMarkRepository.findById(id), "Calificación");
        
        // Validar que el estudiante exista
        orThrowFeign(dto.getStudentId(), userClient::findById, "Estudiante");
        
        return saveDTO(dto, id);
    }

    public void delete(Integer id) {
        studentMarkRepository.delete(orThrow(studentMarkRepository.findById(id), "Calificación"));
    }

    private StudentMarkDTO saveDTO(StudentMarkDTO dto, Integer id) {
        StudentMark entity = studentMarkMapper.toEntity(dto);
        if (id != null) entity.setId(id);
        return studentMarkMapper.toDTO(studentMarkRepository.save(entity));
    }
}
