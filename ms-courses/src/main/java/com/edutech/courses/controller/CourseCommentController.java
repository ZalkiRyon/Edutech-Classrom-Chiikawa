package com.edutech.courses.controller;

import com.edutech.common.dto.CourseCommentDTO;
import com.edutech.courses.service.CourseCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing course comments
 */
@RestController
@RequestMapping("/api/course-comments")
@Tag(name = "Comentarios de Cursos", description = "API para gestión de comentarios de cursos")
public class CourseCommentController {

    @Autowired
    private CourseCommentService courseCommentService;

    /**
     * Get all course comments
     */
    @GetMapping
    @Operation(summary = "Obtener todos los comentarios", description = "Retorna una lista de todos los comentarios de cursos")
    public ResponseEntity<List<CourseCommentDTO>> getAllCourseComments() {
        List<CourseCommentDTO> courseComments = courseCommentService.findAll();
        return ResponseEntity.ok(courseComments);
    }

    /**
     * Get course comment by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener comentario por ID", description = "Retorna un comentario específico por su ID")
    public ResponseEntity<CourseCommentDTO> getCourseCommentById(
            @Parameter(description = "ID del comentario a obtener") @PathVariable Integer id) {
        CourseCommentDTO courseComment = courseCommentService.findById(id);
        return ResponseEntity.ok(courseComment);
    }

    /**
     * Get comments by course ID
     */
    @GetMapping("/course/{courseId}")
    @Operation(summary = "Obtener comentarios por curso", description = "Retorna todos los comentarios de un curso específico")
    public ResponseEntity<List<CourseCommentDTO>> getCommentsByCourseId(
            @Parameter(description = "ID del curso") @PathVariable Integer courseId) {
        List<CourseCommentDTO> courseComments = courseCommentService.findByCourseId(courseId);
        return ResponseEntity.ok(courseComments);
    }

    /**
     * Get comments by user ID
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener comentarios por usuario", description = "Retorna todos los comentarios de un usuario específico")
    public ResponseEntity<List<CourseCommentDTO>> getCommentsByUserId(
            @Parameter(description = "ID del usuario") @PathVariable Integer userId) {
        List<CourseCommentDTO> courseComments = courseCommentService.findByUserId(userId);
        return ResponseEntity.ok(courseComments);
    }

    /**
     * Create new course comment
     */
    @PostMapping
    @Operation(summary = "Crear nuevo comentario", description = "Crea un nuevo comentario en un curso")
    public ResponseEntity<CourseCommentDTO> createCourseComment(@Valid @RequestBody CourseCommentDTO courseCommentDTO) {
        CourseCommentDTO createdCourseComment = courseCommentService.create(courseCommentDTO);
        return new ResponseEntity<>(createdCourseComment, HttpStatus.CREATED);
    }

    /**
     * Update existing course comment
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar comentario", description = "Actualiza un comentario existente")
    public ResponseEntity<CourseCommentDTO> updateCourseComment(
            @Parameter(description = "ID del comentario a actualizar") @PathVariable Integer id, 
            @Valid @RequestBody CourseCommentDTO courseCommentDTO) {
        CourseCommentDTO updatedCourseComment = courseCommentService.update(id, courseCommentDTO);
        return ResponseEntity.ok(updatedCourseComment);
    }

    /**
     * Delete course comment
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar comentario", description = "Elimina un comentario por su ID")
    public ResponseEntity<Void> deleteCourseComment(
            @Parameter(description = "ID del comentario a eliminar") @PathVariable Integer id) {
        courseCommentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
