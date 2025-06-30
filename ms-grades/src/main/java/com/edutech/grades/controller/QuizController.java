package com.edutech.grades.controller;

import com.edutech.common.dto.QuizDTO;
import com.edutech.grades.service.QuizService;
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
@RequestMapping("/api/quizzes")
@Tag(name = "Quizzes", description = "API para gestión de quizzes y evaluaciones")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los quizzes", description = "Retorna una lista de todos los quizzes")
    public ResponseEntity<CollectionModel<EntityModel<QuizDTO>>> findAll() {
        List<EntityModel<QuizDTO>> quizzes = quizService.findAll().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<QuizDTO>> collectionModel = CollectionModel.of(quizzes)
                .add(linkTo(methodOn(QuizController.class).findAll()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener quiz por ID", description = "Retorna un quiz específico por su ID")
    public ResponseEntity<EntityModel<QuizDTO>> findById(@PathVariable Integer id) {
        QuizDTO quiz = quizService.findById(id);
        return ResponseEntity.ok(toEntityModel(quiz));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Obtener quizzes por curso", description = "Retorna todos los quizzes de un curso específico")
    public ResponseEntity<CollectionModel<EntityModel<QuizDTO>>> findByCourseId(@PathVariable Integer courseId) {
        List<EntityModel<QuizDTO>> quizzes = quizService.findByCourseId(courseId).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<QuizDTO>> collectionModel = CollectionModel.of(quizzes)
                .add(linkTo(methodOn(QuizController.class).findByCourseId(courseId)).withSelfRel())
                .add(linkTo(methodOn(QuizController.class).findAll()).withRel("all-quizzes"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/type/{quizType}")
    @Operation(summary = "Obtener quizzes por tipo", description = "Retorna todos los quizzes de un tipo específico")
    public ResponseEntity<CollectionModel<EntityModel<QuizDTO>>> findByQuizType(@PathVariable String quizType) {
        List<EntityModel<QuizDTO>> quizzes = quizService.findByQuizType(quizType).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<QuizDTO>> collectionModel = CollectionModel.of(quizzes)
                .add(linkTo(methodOn(QuizController.class).findByQuizType(quizType)).withSelfRel())
                .add(linkTo(methodOn(QuizController.class).findAll()).withRel("all-quizzes"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo quiz", description = "Crea un nuevo quiz")
    public ResponseEntity<EntityModel<QuizDTO>> create(@RequestBody QuizDTO dto) {
        QuizDTO created = quizService.create(dto);
        return ResponseEntity.ok(toEntityModel(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar quiz", description = "Actualiza un quiz existente")
    public ResponseEntity<EntityModel<QuizDTO>> update(@PathVariable Integer id, @RequestBody QuizDTO dto) {
        QuizDTO updated = quizService.update(id, dto);
        return ResponseEntity.ok(toEntityModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar quiz", description = "Elimina un quiz por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        quizService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private EntityModel<QuizDTO> toEntityModel(QuizDTO quiz) {
        return EntityModel.of(quiz)
                .add(linkTo(methodOn(QuizController.class).findById(quiz.getId())).withSelfRel())
                .add(linkTo(methodOn(QuizController.class).findByCourseId(quiz.getCourseId())).withRel("course-quizzes"))
                .add(linkTo(methodOn(QuizController.class).findByQuizType(quiz.getQuizType())).withRel("type-quizzes"))
                .add(linkTo(methodOn(QuizController.class).findAll()).withRel("all-quizzes"));
    }
}
