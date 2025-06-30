package com.edutech.courses.service;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.courses.client.UserClient;
import com.edutech.courses.entity.Enrollment;
import com.edutech.courses.mapper.EnrollmentMapperManual;
import com.edutech.courses.repository.EnrollmentRepository;
import com.edutech.courses.repository.CourseRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;
import static com.edutech.common.exception.ExceptionUtils.orThrowFeign;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapperManual enrollmentMapper;
    private final UserClient userClient;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, EnrollmentMapperManual enrollmentMapper,
                           UserClient userClient, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentMapper = enrollmentMapper;
        this.userClient = userClient;
        this.courseRepository = courseRepository;
    }

    public List<EnrollmentDTO> findAll() {
        return enrollmentRepository.findAll().stream().map(enrollmentMapper::toDTO).toList();
    }

    public EnrollmentDTO findById(Integer id) {
        return enrollmentMapper.toDTO(orThrow(enrollmentRepository.findById(id), "Inscripción"));
    }

    public List<EnrollmentDTO> findByStudentId(Integer studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream().map(enrollmentMapper::toDTO).toList();
    }

    public List<EnrollmentDTO> findByCourseId(Integer courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream().map(enrollmentMapper::toDTO).toList();
    }

    public EnrollmentDTO create(EnrollmentDTO dto) {
        // Validar que el estudiante exista
        orThrowFeign(dto.getStudentId(), userClient::findById, "Estudiante");

        // Validar que el curso exista
        orThrow(courseRepository.findById(dto.getCourseId()), "Curso");

        // Crear nueva inscripción
        return saveDTO(dto, null);
    }

    public EnrollmentDTO update(Integer id, EnrollmentDTO dto) {
        orThrow(enrollmentRepository.findById(id), "Inscripción");
        
        // Validar que el estudiante exista
        orThrowFeign(dto.getStudentId(), userClient::findById, "Estudiante");

        // Validar que el curso exista
        orThrow(courseRepository.findById(dto.getCourseId()), "Curso");
        
        return saveDTO(dto, id);
    }

    public void delete(Integer id) {
        enrollmentRepository.delete(orThrow(enrollmentRepository.findById(id), "Inscripción"));
    }

    private EnrollmentDTO saveDTO(EnrollmentDTO dto, Integer id) {
        Enrollment entity = enrollmentMapper.toEntity(dto);
        if (id != null) entity.setId(id);
        return enrollmentMapper.toDTO(enrollmentRepository.save(entity));
    }
}
