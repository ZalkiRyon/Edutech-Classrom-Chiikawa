package com.edutech.grades.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.edutech.common.dto.QuizResponseDTO;
import com.edutech.grades.service.QuizResponseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/quiz-response")
@Tag(name = "Respuestas de Evaluaciones", description = "API para gestión de respuestas de estudiantes a evaluaciones")
public class QuizResponseController {

    @Autowired
    private QuizResponseService quizResponseService;

    @GetMapping
    @Operation(summary = "Obtener todas las respuestas", description = "Retorna una lista de todas las respuestas a evaluaciones")
    public ResponseEntity<CollectionModel<EntityModel<QuizResponseDTO>>> getAllQuizResponses() {
        List<QuizResponseDTO> quizResponses = quizResponseService.findAll();
        
        List<EntityModel<QuizResponseDTO>> responseModels = quizResponses.stream()
            .map(response -> EntityModel.of(response)
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponseById(response.getId())).withSelfRel())
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByQuizId(response.getQuizId())).withRel("quiz-responses"))
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByStudentId(response.getStudentId())).withRel("student-responses")))
            .toList();
        
        CollectionModel<EntityModel<QuizResponseDTO>> collectionModel = CollectionModel.of(responseModels)
            .add(linkTo(methodOn(QuizResponseController.class).getAllQuizResponses()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener respuesta por ID", description = "Retorna una respuesta específica por su ID")
    public ResponseEntity<EntityModel<QuizResponseDTO>> getQuizResponseById(
            @Parameter(description = "ID de la respuesta a obtener") @PathVariable Integer id) {
        QuizResponseDTO quizResponse = quizResponseService.findById(id);
        if (quizResponse != null) {
            EntityModel<QuizResponseDTO> responseModel = EntityModel.of(quizResponse)
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponseById(id)).withSelfRel())
                .add(linkTo(methodOn(QuizResponseController.class).getAllQuizResponses()).withRel("all-responses"))
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByQuizId(quizResponse.getQuizId())).withRel("quiz-responses"));
            return ResponseEntity.ok(responseModel);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/quiz/{quizId}")
    @Operation(summary = "Obtener respuestas por evaluación", description = "Retorna todas las respuestas de una evaluación específica")
    public ResponseEntity<CollectionModel<EntityModel<QuizResponseDTO>>> getQuizResponsesByQuizId(
            @Parameter(description = "ID de la evaluación") @PathVariable Integer quizId) {
        List<QuizResponseDTO> quizResponses = quizResponseService.findByQuizId(quizId);
        
        List<EntityModel<QuizResponseDTO>> responseModels = quizResponses.stream()
            .map(response -> EntityModel.of(response)
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponseById(response.getId())).withSelfRel())
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByStudentId(response.getStudentId())).withRel("student-responses")))
            .toList();
        
        CollectionModel<EntityModel<QuizResponseDTO>> collectionModel = CollectionModel.of(responseModels)
            .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByQuizId(quizId)).withSelfRel())
            .add(linkTo(methodOn(QuizResponseController.class).getAllQuizResponses()).withRel("all-responses"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Obtener respuestas por estudiante", description = "Retorna todas las respuestas de un estudiante específico")
    public ResponseEntity<CollectionModel<EntityModel<QuizResponseDTO>>> getQuizResponsesByStudentId(
            @Parameter(description = "ID del estudiante") @PathVariable Integer studentId) {
        List<QuizResponseDTO> quizResponses = quizResponseService.findByStudentId(studentId);
        
        List<EntityModel<QuizResponseDTO>> responseModels = quizResponses.stream()
            .map(response -> EntityModel.of(response)
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponseById(response.getId())).withSelfRel())
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByQuizId(response.getQuizId())).withRel("quiz-responses")))
            .toList();
        
        CollectionModel<EntityModel<QuizResponseDTO>> collectionModel = CollectionModel.of(responseModels)
            .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByStudentId(studentId)).withSelfRel())
            .add(linkTo(methodOn(QuizResponseController.class).getAllQuizResponses()).withRel("all-responses"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/quiz/{quizId}/student/{studentId}")
    @Operation(summary = "Obtener respuestas por evaluación y estudiante", description = "Retorna las respuestas de un estudiante específico a una evaluación")
    public ResponseEntity<CollectionModel<EntityModel<QuizResponseDTO>>> getQuizResponsesByQuizIdAndStudentId(
            @Parameter(description = "ID de la evaluación") @PathVariable Integer quizId, 
            @Parameter(description = "ID del estudiante") @PathVariable Integer studentId) {
        List<QuizResponseDTO> quizResponses = quizResponseService.findByQuizIdAndStudentId(quizId, studentId);
        
        List<EntityModel<QuizResponseDTO>> responseModels = quizResponses.stream()
            .map(response -> EntityModel.of(response)
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponseById(response.getId())).withSelfRel())
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByQuizId(quizId)).withRel("quiz-responses"))
                .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByStudentId(studentId)).withRel("student-responses")))
            .toList();
        
        CollectionModel<EntityModel<QuizResponseDTO>> collectionModel = CollectionModel.of(responseModels)
            .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByQuizIdAndStudentId(quizId, studentId)).withSelfRel())
            .add(linkTo(methodOn(QuizResponseController.class).getAllQuizResponses()).withRel("all-responses"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear nueva respuesta", description = "Crea una nueva respuesta a una evaluación")
    public ResponseEntity<EntityModel<QuizResponseDTO>> createQuizResponse(
            @Parameter(description = "Datos de la nueva respuesta") @Valid @RequestBody QuizResponseDTO quizResponseDTO) {
        QuizResponseDTO createdQuizResponse = quizResponseService.create(quizResponseDTO);
        
        EntityModel<QuizResponseDTO> responseModel = EntityModel.of(createdQuizResponse)
            .add(linkTo(methodOn(QuizResponseController.class).getQuizResponseById(createdQuizResponse.getId())).withSelfRel())
            .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByQuizId(createdQuizResponse.getQuizId())).withRel("quiz-responses"))
            .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByStudentId(createdQuizResponse.getStudentId())).withRel("student-responses"))
            .add(linkTo(methodOn(QuizResponseController.class).getAllQuizResponses()).withRel("all-responses"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar respuesta", description = "Actualiza una respuesta existente")
    public ResponseEntity<EntityModel<QuizResponseDTO>> updateQuizResponse(
            @Parameter(description = "ID de la respuesta a actualizar") @PathVariable Integer id, 
            @Parameter(description = "Nuevos datos de la respuesta") @Valid @RequestBody QuizResponseDTO quizResponseDTO) {
        QuizResponseDTO updatedQuizResponse = quizResponseService.update(id, quizResponseDTO);
        
        EntityModel<QuizResponseDTO> responseModel = EntityModel.of(updatedQuizResponse)
            .add(linkTo(methodOn(QuizResponseController.class).getQuizResponseById(id)).withSelfRel())
            .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByQuizId(updatedQuizResponse.getQuizId())).withRel("quiz-responses"))
            .add(linkTo(methodOn(QuizResponseController.class).getQuizResponsesByStudentId(updatedQuizResponse.getStudentId())).withRel("student-responses"))
            .add(linkTo(methodOn(QuizResponseController.class).getAllQuizResponses()).withRel("all-responses"));
        
        return ResponseEntity.ok(responseModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar respuesta", description = "Elimina una respuesta por su ID")
    public ResponseEntity<Void> deleteQuizResponse(
            @Parameter(description = "ID de la respuesta a eliminar") @PathVariable Integer id) {
        quizResponseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
