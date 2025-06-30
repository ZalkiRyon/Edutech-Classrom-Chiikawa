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
    public ResponseEntity<CourseCommentDTO> getCourseCommentById(@PathVariable Integer id) {
        CourseCommentDTO courseComment = courseCommentService.findById(id);
        return ResponseEntity.ok(courseComment);
    }

    /**
     * Get comments by course ID
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseCommentDTO>> getCommentsByCourseId(@PathVariable Integer courseId) {
        List<CourseCommentDTO> courseComments = courseCommentService.findByCourseId(courseId);
        return ResponseEntity.ok(courseComments);
    }

    /**
     * Get comments by user ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CourseCommentDTO>> getCommentsByUserId(@PathVariable Integer userId) {
        List<CourseCommentDTO> courseComments = courseCommentService.findByUserId(userId);
        return ResponseEntity.ok(courseComments);
    }

    /**
     * Create new course comment
     */
    @PostMapping
    public ResponseEntity<CourseCommentDTO> createCourseComment(@Valid @RequestBody CourseCommentDTO courseCommentDTO) {
        CourseCommentDTO createdCourseComment = courseCommentService.create(courseCommentDTO);
        return new ResponseEntity<>(createdCourseComment, HttpStatus.CREATED);
    }

    /**
     * Update existing course comment
     */
    @PutMapping("/{id}")
    public ResponseEntity<CourseCommentDTO> updateCourseComment(
            @PathVariable Integer id, 
            @Valid @RequestBody CourseCommentDTO courseCommentDTO) {
        CourseCommentDTO updatedCourseComment = courseCommentService.update(id, courseCommentDTO);
        return ResponseEntity.ok(updatedCourseComment);
    }

    /**
     * Delete course comment
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseComment(@PathVariable Integer id) {
        courseCommentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
