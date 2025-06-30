package com.edutech.grades.controller;

import com.edutech.common.dto.CourseQuizQuestionDTO;
import com.edutech.grades.service.CourseQuizQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course-quiz-question")
@Tag(name = "Preguntas de Cuestionarios", description = "API para gestionar las preguntas de los cuestionarios de cursos")
public class CourseQuizQuestionController {

    private final CourseQuizQuestionService courseQuizQuestionService;

    public CourseQuizQuestionController(CourseQuizQuestionService courseQuizQuestionService) {
        this.courseQuizQuestionService = courseQuizQuestionService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las preguntas de cuestionarios", description = "Obtiene una lista de todas las preguntas de cuestionarios")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getAllCourseQuizQuestions() {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pregunta por ID", description = "Obtiene una pregunta específica de cuestionario por su ID")
    public ResponseEntity<CourseQuizQuestionDTO> getCourseQuizQuestionById(
            @Parameter(description = "ID de la pregunta de cuestionario a obtener") @PathVariable Integer id) {
        CourseQuizQuestionDTO question = courseQuizQuestionService.findById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/quiz/{quizId}")
    @Operation(summary = "Obtener preguntas por ID de cuestionario", description = "Obtiene todas las preguntas de un cuestionario específico")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getQuestionsByQuizId(
            @Parameter(description = "ID del cuestionario") @PathVariable Integer quizId) {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/{quizId}/ordered")
    @Operation(summary = "Obtener preguntas ordenadas por índice", description = "Obtiene todas las preguntas de un cuestionario específico ordenadas por índice de orden")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getQuestionsByQuizIdOrdered(
            @Parameter(description = "ID del cuestionario") @PathVariable Integer quizId) {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findByQuizIdOrderByOrderIndex(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/{quizId}/ordered/asc")
    @Operation(summary = "Obtener preguntas ordenadas ascendentemente", description = "Obtiene todas las preguntas de un cuestionario específico ordenadas por índice ascendente")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getQuestionsByQuizIdOrderedAsc(
            @Parameter(description = "ID del cuestionario") @PathVariable Integer quizId) {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findByQuizIdOrderByOrderIndexAsc(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/{quizId}/ordered/desc")
    @Operation(summary = "Obtener preguntas ordenadas descendentemente", description = "Obtiene todas las preguntas de un cuestionario específico ordenadas por índice descendente")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getQuestionsByQuizIdOrderedDesc(
            @Parameter(description = "ID del cuestionario") @PathVariable Integer quizId) {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findByQuizIdOrderByOrderIndexDesc(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/{quizId}/count")
    @Operation(summary = "Contar preguntas por cuestionario", description = "Obtiene el número de preguntas de un cuestionario específico")
    public ResponseEntity<Long> countQuestionsByQuizId(
            @Parameter(description = "ID del cuestionario") @PathVariable Integer quizId) {
        long count = courseQuizQuestionService.countByQuizId(quizId);
        return ResponseEntity.ok(count);
    }

    @PostMapping
    @Operation(summary = "Crear nueva pregunta de cuestionario", description = "Crea una nueva pregunta de cuestionario")
    public ResponseEntity<CourseQuizQuestionDTO> createCourseQuizQuestion(
            @Valid @RequestBody CourseQuizQuestionDTO courseQuizQuestionDTO) {
        CourseQuizQuestionDTO createdQuestion = courseQuizQuestionService.create(courseQuizQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pregunta de cuestionario", description = "Actualiza una pregunta de cuestionario existente por su ID")
    public ResponseEntity<CourseQuizQuestionDTO> updateCourseQuizQuestion(
            @Parameter(description = "ID de la pregunta de cuestionario a actualizar") @PathVariable Integer id,
            @Valid @RequestBody CourseQuizQuestionDTO courseQuizQuestionDTO) {
        CourseQuizQuestionDTO updatedQuestion = courseQuizQuestionService.update(id, courseQuizQuestionDTO);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pregunta de cuestionario", description = "Elimina una pregunta de cuestionario por su ID")
    public ResponseEntity<Void> deleteCourseQuizQuestion(
            @Parameter(description = "ID de la pregunta de cuestionario a eliminar") @PathVariable Integer id) {
        courseQuizQuestionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/quiz/{quizId}")
    @Operation(summary = "Eliminar todas las preguntas de un cuestionario", description = "Elimina todas las preguntas de un cuestionario específico")
    public ResponseEntity<Void> deleteQuestionsByQuizId(
            @Parameter(description = "ID del cuestionario") @PathVariable Integer quizId) {
        courseQuizQuestionService.deleteByQuizId(quizId);
        return ResponseEntity.noContent().build();
    }
}
