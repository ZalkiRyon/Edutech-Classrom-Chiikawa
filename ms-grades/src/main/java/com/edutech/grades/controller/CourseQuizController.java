package com.edutech.grades.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.common.dto.CourseQuizDTO;
import com.edutech.grades.service.CourseQuizService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/course-quiz")
@Tag(name = "Evaluaciones de Curso", description = "API para gestión de evaluaciones y quizzes de cursos")
public class CourseQuizController {

    private final CourseQuizService courseQuizService;

    public CourseQuizController(CourseQuizService courseQuizService) {
        this.courseQuizService = courseQuizService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las evaluaciones", description = "Retorna una lista de todas las evaluaciones de cursos")
    public ResponseEntity<CollectionModel<EntityModel<CourseQuizDTO>>> getAllCourseQuizzes() {
        List<CourseQuizDTO> quizzes = courseQuizService.findAll();
        
        List<EntityModel<CourseQuizDTO>> quizModels = quizzes.stream()
            .map(quiz -> EntityModel.of(quiz)
                .add(linkTo(methodOn(CourseQuizController.class).getCourseQuizById(quiz.getId())).withSelfRel())
                .add(linkTo(methodOn(CourseQuizController.class).getCourseQuizzesByCourseId(quiz.getCourseId())).withRel("course-quizzes")))
            .toList();
        
        CollectionModel<EntityModel<CourseQuizDTO>> collectionModel = CollectionModel.of(quizModels)
            .add(linkTo(methodOn(CourseQuizController.class).getAllCourseQuizzes()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evaluación por ID", description = "Retorna una evaluación específica por su ID")
    public ResponseEntity<EntityModel<CourseQuizDTO>> getCourseQuizById(
            @Parameter(description = "ID de la evaluación a obtener") @PathVariable Integer id) {
        CourseQuizDTO quiz = courseQuizService.findById(id);
        
        EntityModel<CourseQuizDTO> quizModel = EntityModel.of(quiz)
            .add(linkTo(methodOn(CourseQuizController.class).getCourseQuizById(id)).withSelfRel())
            .add(linkTo(methodOn(CourseQuizController.class).getAllCourseQuizzes()).withRel("all-quizzes"))
            .add(linkTo(methodOn(CourseQuizController.class).getCourseQuizzesByCourseId(quiz.getCourseId())).withRel("course-quizzes"));
        
        return ResponseEntity.ok(quizModel);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Obtener evaluaciones por curso", description = "Retorna todas las evaluaciones de un curso específico")
    public ResponseEntity<List<CourseQuizDTO>> getCourseQuizzesByCourseId(
            @Parameter(description = "ID del curso") @PathVariable Integer courseId) {
        List<CourseQuizDTO> quizzes = courseQuizService.findByCourseId(courseId);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/type/{quizType}")
    @Operation(summary = "Obtener evaluaciones por tipo", description = "Retorna todas las evaluaciones de un tipo específico")
    public ResponseEntity<List<CourseQuizDTO>> getCourseQuizzesByType(
            @Parameter(description = "Tipo de evaluación") @PathVariable String quizType) {
        List<CourseQuizDTO> quizzes = courseQuizService.findByQuizType(quizType);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/course/{courseId}/type/{quizType}")
    @Operation(summary = "Obtener evaluaciones por curso y tipo", description = "Retorna evaluaciones filtradas por curso y tipo")
    public ResponseEntity<List<CourseQuizDTO>> getCourseQuizzesByCourseIdAndType(
            @Parameter(description = "ID del curso") @PathVariable Integer courseId,
            @Parameter(description = "Tipo de evaluación") @PathVariable String quizType) {
        List<CourseQuizDTO> quizzes = courseQuizService.findByCourseIdAndQuizType(courseId, quizType);
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping
    @Operation(summary = "Crear nueva evaluación", description = "Crea una nueva evaluación para un curso")
    public ResponseEntity<CourseQuizDTO> createCourseQuiz(
            @Parameter(description = "Datos de la nueva evaluación") @Valid @RequestBody CourseQuizDTO courseQuizDTO) {
        CourseQuizDTO createdQuiz = courseQuizService.create(courseQuizDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar evaluación", description = "Actualiza una evaluación existente")
    public ResponseEntity<CourseQuizDTO> updateCourseQuiz(
            @Parameter(description = "ID de la evaluación a actualizar") @PathVariable Integer id,
            @Parameter(description = "Nuevos datos de la evaluación") @Valid @RequestBody CourseQuizDTO courseQuizDTO) {
        CourseQuizDTO updatedQuiz = courseQuizService.update(id, courseQuizDTO);
        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evaluación", description = "Elimina una evaluación por su ID")
    public ResponseEntity<Void> deleteCourseQuiz(
            @Parameter(description = "ID de la evaluación a eliminar") @PathVariable Integer id) {
        courseQuizService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar existencia de evaluación", description = "Verifica si existe una evaluación con el ID dado")
    public ResponseEntity<Boolean> courseQuizExists(
            @Parameter(description = "ID de la evaluación a verificar") @PathVariable Integer id) {
        boolean exists = courseQuizService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/course/{courseId}/count")
    @Operation(summary = "Contar evaluaciones por curso", description = "Retorna el número de evaluaciones en un curso específico")
    public ResponseEntity<Long> countCourseQuizzesByCourseId(
            @Parameter(description = "ID del curso") @PathVariable Integer courseId) {
        long count = courseQuizService.countByCourseId(courseId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/course/{courseId}/exists")
    @Operation(summary = "Verificar evaluaciones en curso", description = "Verifica si un curso tiene evaluaciones")
    public ResponseEntity<Boolean> courseQuizzesExistByCourseId(
            @Parameter(description = "ID del curso") @PathVariable Integer courseId) {
        boolean exists = courseQuizService.existsByCourseId(courseId);
        return ResponseEntity.ok(exists);
    }
}
