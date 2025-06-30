package com.edutech.courses.controller;

import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.courses.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Inscripciones", description = "API para gestión de inscripciones de estudiantes a cursos")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las inscripciones", description = "Retorna una lista de todas las inscripciones")
    public ResponseEntity<CollectionModel<EntityModel<EnrollmentDTO>>> findAll() {
        List<EntityModel<EnrollmentDTO>> enrollments = enrollmentService.findAll().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<EnrollmentDTO>> collectionModel = CollectionModel.of(enrollments)
                .add(linkTo(methodOn(EnrollmentController.class).findAll()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener inscripción por ID", description = "Retorna una inscripción específica por su ID")
    public ResponseEntity<EntityModel<EnrollmentDTO>> findById(@PathVariable Integer id) {
        EnrollmentDTO enrollment = enrollmentService.findById(id);
        return ResponseEntity.ok(toEntityModel(enrollment));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Obtener inscripciones por estudiante", description = "Retorna todas las inscripciones de un estudiante específico")
    public ResponseEntity<CollectionModel<EntityModel<EnrollmentDTO>>> findByStudentId(@PathVariable Integer studentId) {
        List<EntityModel<EnrollmentDTO>> enrollments = enrollmentService.findByStudentId(studentId).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<EnrollmentDTO>> collectionModel = CollectionModel.of(enrollments)
                .add(linkTo(methodOn(EnrollmentController.class).findByStudentId(studentId)).withSelfRel())
                .add(linkTo(methodOn(EnrollmentController.class).findAll()).withRel("all-enrollments"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Obtener inscripciones por curso", description = "Retorna todas las inscripciones de un curso específico")
    public ResponseEntity<CollectionModel<EntityModel<EnrollmentDTO>>> findByCourseId(@PathVariable Integer courseId) {
        List<EntityModel<EnrollmentDTO>> enrollments = enrollmentService.findByCourseId(courseId).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<EnrollmentDTO>> collectionModel = CollectionModel.of(enrollments)
                .add(linkTo(methodOn(EnrollmentController.class).findByCourseId(courseId)).withSelfRel())
                .add(linkTo(methodOn(EnrollmentController.class).findAll()).withRel("all-enrollments"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear nueva inscripción", description = "Crea una nueva inscripción de estudiante a curso")
    public ResponseEntity<EntityModel<EnrollmentDTO>> create(@RequestBody EnrollmentDTO dto) {
        EnrollmentDTO created = enrollmentService.create(dto);
        return ResponseEntity.ok(toEntityModel(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inscripción", description = "Actualiza una inscripción existente")
    public ResponseEntity<EntityModel<EnrollmentDTO>> update(@PathVariable Integer id, @RequestBody EnrollmentDTO dto) {
        EnrollmentDTO updated = enrollmentService.update(id, dto);
        return ResponseEntity.ok(toEntityModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inscripción", description = "Elimina una inscripción por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private EntityModel<EnrollmentDTO> toEntityModel(EnrollmentDTO enrollment) {
        return EntityModel.of(enrollment)
                .add(linkTo(methodOn(EnrollmentController.class).findById(enrollment.getId())).withSelfRel())
                .add(linkTo(methodOn(EnrollmentController.class).findByStudentId(enrollment.getStudentId())).withRel("student-enrollments"))
                .add(linkTo(methodOn(EnrollmentController.class).findByCourseId(enrollment.getCourseId())).withRel("course-enrollments"))
                .add(linkTo(methodOn(EnrollmentController.class).findAll()).withRel("all-enrollments"));
    }
}
