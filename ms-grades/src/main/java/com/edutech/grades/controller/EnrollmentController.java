package com.edutech.grades.controller;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.grades.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Inscripciones", description = "API para gestión de inscripciones de estudiantes a cursos")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    @Operation(summary = "Obtener todas las inscripciones", description = "Retorna una lista de todas las inscripciones")
    public ResponseEntity<List<EnrollmentDTO>> findAll() {
        return ResponseEntity.ok(enrollmentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener inscripción por ID", description = "Retorna una inscripción específica por su ID")
    public ResponseEntity<EnrollmentDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(enrollmentService.findById(id));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Obtener inscripciones por estudiante", description = "Retorna todas las inscripciones de un estudiante específico")
    public ResponseEntity<List<EnrollmentDTO>> findByStudentId(@PathVariable Integer studentId) {
        return ResponseEntity.ok(enrollmentService.findByStudentId(studentId));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Obtener inscripciones por curso", description = "Retorna todas las inscripciones de un curso específico")
    public ResponseEntity<List<EnrollmentDTO>> findByCourseId(@PathVariable Integer courseId) {
        return ResponseEntity.ok(enrollmentService.findByCourseId(courseId));
    }

    @PostMapping
    @Operation(summary = "Crear nueva inscripción", description = "Crea una nueva inscripción de estudiante a curso")
    public ResponseEntity<EnrollmentDTO> create(@RequestBody EnrollmentDTO dto) {
        return ResponseEntity.ok(enrollmentService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inscripción", description = "Actualiza una inscripción existente")
    public ResponseEntity<EnrollmentDTO> update(@PathVariable Integer id, @RequestBody EnrollmentDTO dto) {
        return ResponseEntity.ok(enrollmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inscripción", description = "Elimina una inscripción por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
