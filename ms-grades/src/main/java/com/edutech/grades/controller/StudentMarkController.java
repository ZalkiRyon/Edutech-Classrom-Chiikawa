package com.edutech.grades.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.common.dto.StudentMarkDTO;
import com.edutech.grades.service.StudentMarkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/student-marks")
@Tag(name = "Calificaciones", description = "API para gestión de calificaciones de estudiantes")
public class StudentMarkController {

    private final StudentMarkService studentMarkService;

    public StudentMarkController(StudentMarkService studentMarkService) {
        this.studentMarkService = studentMarkService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las calificaciones", description = "Retorna una lista de todas las calificaciones")
    public ResponseEntity<CollectionModel<EntityModel<StudentMarkDTO>>> findAll() {
        List<EntityModel<StudentMarkDTO>> marks = studentMarkService.findAll().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<StudentMarkDTO>> collectionModel = CollectionModel.of(marks)
                .add(linkTo(methodOn(StudentMarkController.class).findAll()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener calificación por ID", description = "Retorna una calificación específica por su ID")
    public ResponseEntity<EntityModel<StudentMarkDTO>> findById(@PathVariable Integer id) {
        StudentMarkDTO mark = studentMarkService.findById(id);
        return ResponseEntity.ok(toEntityModel(mark));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Obtener calificaciones por estudiante", description = "Retorna todas las calificaciones de un estudiante específico")
    public ResponseEntity<CollectionModel<EntityModel<StudentMarkDTO>>> findByStudentId(@PathVariable Integer studentId) {
        List<EntityModel<StudentMarkDTO>> marks = studentMarkService.findByStudentId(studentId).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<StudentMarkDTO>> collectionModel = CollectionModel.of(marks)
                .add(linkTo(methodOn(StudentMarkController.class).findByStudentId(studentId)).withSelfRel())
                .add(linkTo(methodOn(StudentMarkController.class).findAll()).withRel("all-marks"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/quiz/{quizId}")
    @Operation(summary = "Obtener calificaciones por quiz", description = "Retorna todas las calificaciones de un quiz específico")
    public ResponseEntity<CollectionModel<EntityModel<StudentMarkDTO>>> findByQuizId(@PathVariable Integer quizId) {
        List<EntityModel<StudentMarkDTO>> marks = studentMarkService.findByQuizId(quizId).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<StudentMarkDTO>> collectionModel = CollectionModel.of(marks)
                .add(linkTo(methodOn(StudentMarkController.class).findByQuizId(quizId)).withSelfRel())
                .add(linkTo(methodOn(StudentMarkController.class).findAll()).withRel("all-marks"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear nueva calificación", description = "Crea una nueva calificación para un estudiante")
    public ResponseEntity<EntityModel<StudentMarkDTO>> create(@RequestBody StudentMarkDTO dto) {
        StudentMarkDTO created = studentMarkService.create(dto);
        return ResponseEntity.ok(toEntityModel(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar calificación", description = "Actualiza una calificación existente")
    public ResponseEntity<EntityModel<StudentMarkDTO>> update(@PathVariable Integer id, @RequestBody StudentMarkDTO dto) {
        StudentMarkDTO updated = studentMarkService.update(id, dto);
        return ResponseEntity.ok(toEntityModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar calificación", description = "Elimina una calificación por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        studentMarkService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private EntityModel<StudentMarkDTO> toEntityModel(StudentMarkDTO mark) {
        return EntityModel.of(mark)
                .add(linkTo(methodOn(StudentMarkController.class).findById(mark.getId())).withSelfRel())
                .add(linkTo(methodOn(StudentMarkController.class).findByStudentId(mark.getStudentId())).withRel("student-marks"))
                .add(linkTo(methodOn(StudentMarkController.class).findByQuizId(mark.getQuizId())).withRel("quiz-marks"))
                .add(linkTo(methodOn(StudentMarkController.class).findAll()).withRel("all-marks"));
    }
}
