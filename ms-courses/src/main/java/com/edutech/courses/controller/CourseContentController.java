package com.edutech.courses.controller;

import com.edutech.common.dto.CourseContentDTO;
import com.edutech.courses.service.CourseContentService;
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
 * REST Controller for managing course content
 */
@RestController
@RequestMapping("/api/course-contents")
@Tag(name = "Contenido de Cursos", description = "API para gestión de contenido de cursos")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    /**
     * Get all course contents
     */
    @GetMapping
    @Operation(summary = "Obtener todo el contenido", description = "Retorna una lista de todo el contenido de cursos")
    public ResponseEntity<List<CourseContentDTO>> getAllCourseContents() {
        List<CourseContentDTO> courseContents = courseContentService.findAll();
        return ResponseEntity.ok(courseContents);
    }

    /**
     * Get course content by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener contenido por ID", description = "Retorna contenido específico de curso por su ID")
    public ResponseEntity<CourseContentDTO> getCourseContentById(
            @Parameter(description = "ID del contenido a obtener") @PathVariable Integer id) {
        CourseContentDTO courseContent = courseContentService.findById(id);
        return ResponseEntity.ok(courseContent);
    }

    /**
     * Get course contents by course ID
     */
    @GetMapping("/course/{courseId}")
    @Operation(summary = "Obtener contenido por curso", description = "Retorna todo el contenido de un curso específico")
    public ResponseEntity<List<CourseContentDTO>> getCourseContentsByCourseId(
            @Parameter(description = "ID del curso") @PathVariable Integer courseId) {
        List<CourseContentDTO> courseContents = courseContentService.findByCourseId(courseId);
        return ResponseEntity.ok(courseContents);
    }

    /**
     * Create new course content
     */
    @PostMapping
    @Operation(summary = "Crear nuevo contenido", description = "Crea un nuevo contenido de curso")
    public ResponseEntity<CourseContentDTO> createCourseContent(
            @Parameter(description = "Datos del nuevo contenido") @Valid @RequestBody CourseContentDTO courseContentDTO) {
        CourseContentDTO createdCourseContent = courseContentService.create(courseContentDTO);
        return new ResponseEntity<>(createdCourseContent, HttpStatus.CREATED);
    }

    /**
     * Update existing course content
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar contenido", description = "Actualiza contenido existente de curso")
    public ResponseEntity<CourseContentDTO> updateCourseContent(
            @Parameter(description = "ID del contenido a actualizar") @PathVariable Integer id, 
            @Parameter(description = "Nuevos datos del contenido") @Valid @RequestBody CourseContentDTO courseContentDTO) {
        CourseContentDTO updatedCourseContent = courseContentService.update(id, courseContentDTO);
        return ResponseEntity.ok(updatedCourseContent);
    }

    /**
     * Delete course content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar contenido", description = "Elimina contenido de curso por su ID")
    public ResponseEntity<Void> deleteCourseContent(
            @Parameter(description = "ID del contenido a eliminar") @PathVariable Integer id) {
        courseContentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
