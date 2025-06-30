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

import com.edutech.common.dto.QuizResponseDTO;
import com.edutech.grades.service.QuizResponseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quiz-response")
@Tag(name = "Respuestas de Evaluaciones", description = "API para gestión de respuestas de estudiantes a evaluaciones")
public class QuizResponseController {

    @Autowired
    private QuizResponseService quizResponseService;

    @GetMapping
    @Operation(summary = "Obtener todas las respuestas", description = "Retorna una lista de todas las respuestas a evaluaciones")
    public ResponseEntity<List<QuizResponseDTO>> getAllQuizResponses() {
        List<QuizResponseDTO> quizResponses = quizResponseService.findAll();
        return ResponseEntity.ok(quizResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener respuesta por ID", description = "Retorna una respuesta específica por su ID")
    public ResponseEntity<QuizResponseDTO> getQuizResponseById(
            @Parameter(description = "ID de la respuesta a obtener") @PathVariable Integer id) {
        QuizResponseDTO quizResponse = quizResponseService.findById(id);
        if (quizResponse != null) {
            return ResponseEntity.ok(quizResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/quiz/{quizId}")
    @Operation(summary = "Obtener respuestas por evaluación", description = "Retorna todas las respuestas de una evaluación específica")
    public ResponseEntity<List<QuizResponseDTO>> getQuizResponsesByQuizId(
            @Parameter(description = "ID de la evaluación") @PathVariable Integer quizId) {
        List<QuizResponseDTO> quizResponses = quizResponseService.findByQuizId(quizId);
        return ResponseEntity.ok(quizResponses);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Obtener respuestas por estudiante", description = "Retorna todas las respuestas de un estudiante específico")
    public ResponseEntity<List<QuizResponseDTO>> getQuizResponsesByStudentId(
            @Parameter(description = "ID del estudiante") @PathVariable Integer studentId) {
        List<QuizResponseDTO> quizResponses = quizResponseService.findByStudentId(studentId);
        return ResponseEntity.ok(quizResponses);
    }

    @GetMapping("/quiz/{quizId}/student/{studentId}")
    @Operation(summary = "Obtener respuestas por evaluación y estudiante", description = "Retorna las respuestas de un estudiante específico a una evaluación")
    public ResponseEntity<List<QuizResponseDTO>> getQuizResponsesByQuizIdAndStudentId(
            @Parameter(description = "ID de la evaluación") @PathVariable Integer quizId, 
            @Parameter(description = "ID del estudiante") @PathVariable Integer studentId) {
        List<QuizResponseDTO> quizResponses = quizResponseService.findByQuizIdAndStudentId(quizId, studentId);
        return ResponseEntity.ok(quizResponses);
    }

    @PostMapping
    @Operation(summary = "Crear nueva respuesta", description = "Crea una nueva respuesta a una evaluación")
    public ResponseEntity<QuizResponseDTO> createQuizResponse(
            @Parameter(description = "Datos de la nueva respuesta") @Valid @RequestBody QuizResponseDTO quizResponseDTO) {
        QuizResponseDTO createdQuizResponse = quizResponseService.create(quizResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuizResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar respuesta", description = "Actualiza una respuesta existente")
    public ResponseEntity<QuizResponseDTO> updateQuizResponse(
            @Parameter(description = "ID de la respuesta a actualizar") @PathVariable Integer id, 
            @Parameter(description = "Nuevos datos de la respuesta") @Valid @RequestBody QuizResponseDTO quizResponseDTO) {
        QuizResponseDTO updatedQuizResponse = quizResponseService.update(id, quizResponseDTO);
        if (updatedQuizResponse != null) {
            return ResponseEntity.ok(updatedQuizResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar respuesta", description = "Elimina una respuesta por su ID")
    public ResponseEntity<Void> deleteQuizResponse(
            @Parameter(description = "ID de la respuesta a eliminar") @PathVariable Integer id) {
        quizResponseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
