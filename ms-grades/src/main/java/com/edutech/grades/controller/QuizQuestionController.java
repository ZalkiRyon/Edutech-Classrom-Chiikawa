package com.edutech.grades.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.edutech.common.dto.QuizQuestionDTO;
import com.edutech.grades.service.QuizQuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quiz-questions")
@Tag(name = "Preguntas de Evaluaciones", description = "API para gestión de preguntas de evaluaciones y quizzes")
public class QuizQuestionController {

    @Autowired
    private QuizQuestionService quizQuestionService;

    @GetMapping
    @Operation(summary = "Obtener todas las preguntas", description = "Retorna una lista de todas las preguntas de evaluaciones")
    public ResponseEntity<List<QuizQuestionDTO>> getAllQuizQuestions() {
        List<QuizQuestionDTO> quizQuestions = quizQuestionService.findAll();
        return ResponseEntity.ok(quizQuestions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pregunta por ID", description = "Retorna una pregunta específica por su ID")
    public ResponseEntity<QuizQuestionDTO> getQuizQuestionById(
            @Parameter(description = "ID de la pregunta a obtener") @PathVariable Integer id) {
        QuizQuestionDTO quizQuestion = quizQuestionService.findById(id);
        if (quizQuestion != null) {
            return ResponseEntity.ok(quizQuestion);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/quiz/{quizId}")
    @Operation(summary = "Obtener preguntas por evaluación", description = "Retorna todas las preguntas de una evaluación específica")
    public ResponseEntity<List<QuizQuestionDTO>> getQuizQuestionsByQuizId(
            @Parameter(description = "ID de la evaluación") @PathVariable Integer quizId) {
        List<QuizQuestionDTO> quizQuestions = quizQuestionService.findByQuizId(quizId);
        return ResponseEntity.ok(quizQuestions);
    }

    @GetMapping("/quiz/{quizId}/ordered")
    @Operation(summary = "Obtener preguntas ordenadas por evaluación", description = "Retorna las preguntas de una evaluación ordenadas por índice")
    public ResponseEntity<List<QuizQuestionDTO>> getQuizQuestionsByQuizIdOrdered(
            @Parameter(description = "ID de la evaluación") @PathVariable Integer quizId) {
        List<QuizQuestionDTO> quizQuestions = quizQuestionService.findByQuizIdOrderByOrderIndex(quizId);
        return ResponseEntity.ok(quizQuestions);
    }

    @PostMapping
    @Operation(summary = "Crear nueva pregunta", description = "Crea una nueva pregunta para una evaluación")
    public ResponseEntity<QuizQuestionDTO> createQuizQuestion(
            @Parameter(description = "Datos de la nueva pregunta") @Valid @RequestBody QuizQuestionDTO quizQuestionDTO) {
        QuizQuestionDTO createdQuizQuestion = quizQuestionService.create(quizQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuizQuestion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pregunta", description = "Actualiza una pregunta existente")
    public ResponseEntity<QuizQuestionDTO> updateQuizQuestion(
            @Parameter(description = "ID de la pregunta a actualizar") @PathVariable Integer id, 
            @Parameter(description = "Nuevos datos de la pregunta") @Valid @RequestBody QuizQuestionDTO quizQuestionDTO) {
        QuizQuestionDTO updatedQuizQuestion = quizQuestionService.update(id, quizQuestionDTO);
        if (updatedQuizQuestion != null) {
            return ResponseEntity.ok(updatedQuizQuestion);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pregunta", description = "Elimina una pregunta por su ID")
    public ResponseEntity<Void> deleteQuizQuestion(
            @Parameter(description = "ID de la pregunta a eliminar") @PathVariable Integer id) {
        quizQuestionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
