package com.edutech.courses.controller;

import com.edutech.common.dto.CourseCommentDTO;
import com.edutech.courses.service.CourseCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
    @Operation(summary = "Obtener todos los comentarios", description = "Retorna una lista de todos los comentarios de cursos con enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CourseCommentDTO>>> getAllCourseComments() {
        List<CourseCommentDTO> courseComments = courseCommentService.findAll();
        
        List<EntityModel<CourseCommentDTO>> commentModels = courseComments.stream()
                .map(this::addLinksToDto)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<CourseCommentDTO>> collectionModel = CollectionModel.of(commentModels);
        collectionModel.add(linkTo(CourseCommentController.class).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Get course comment by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener comentario por ID", description = "Retorna un comentario específico por su ID con enlaces HATEOAS")
    public ResponseEntity<EntityModel<CourseCommentDTO>> getCourseCommentById(
            @Parameter(description = "ID del comentario a obtener") @PathVariable Integer id) {
        CourseCommentDTO courseComment = courseCommentService.findById(id);
        EntityModel<CourseCommentDTO> commentModel = addLinksToDto(courseComment);
        return ResponseEntity.ok(commentModel);
    }

    /**
     * Get comments by course ID
     */
    @GetMapping("/course/{courseId}")
    @Operation(summary = "Obtener comentarios por curso", description = "Retorna todos los comentarios de un curso específico con enlaces HATEOAS")
    public ResponseEntity<List<EntityModel<CourseCommentDTO>>> getCommentsByCourseId(
            @Parameter(description = "ID del curso") @PathVariable Integer courseId) {
        List<CourseCommentDTO> courseComments = courseCommentService.findByCourseId(courseId);
        
        List<EntityModel<CourseCommentDTO>> commentModels = courseComments.stream()
                .map(this::addLinksToDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(commentModels);
    }

    /**
     * Get comments by user ID
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener comentarios por usuario", description = "Retorna todos los comentarios de un usuario específico con enlaces HATEOAS")
    public ResponseEntity<List<EntityModel<CourseCommentDTO>>> getCommentsByUserId(
            @Parameter(description = "ID del usuario") @PathVariable Integer userId) {
        List<CourseCommentDTO> courseComments = courseCommentService.findByUserId(userId);
        
        List<EntityModel<CourseCommentDTO>> commentModels = courseComments.stream()
                .map(this::addLinksToDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(commentModels);
    }

    /**
     * Create new course comment
     */
    @PostMapping
    @Operation(summary = "Crear nuevo comentario", description = "Crea un nuevo comentario en un curso y lo retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<CourseCommentDTO>> createCourseComment(@Valid @RequestBody CourseCommentDTO courseCommentDTO) {
        CourseCommentDTO createdCourseComment = courseCommentService.create(courseCommentDTO);
        EntityModel<CourseCommentDTO> commentModel = addLinksToDto(createdCourseComment);
        return new ResponseEntity<>(commentModel, HttpStatus.CREATED);
    }

    /**
     * Update existing course comment
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar comentario", description = "Actualiza un comentario existente y lo retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<CourseCommentDTO>> updateCourseComment(
            @Parameter(description = "ID del comentario a actualizar") @PathVariable Integer id, 
            @Valid @RequestBody CourseCommentDTO courseCommentDTO) {
        CourseCommentDTO updatedCourseComment = courseCommentService.update(id, courseCommentDTO);
        EntityModel<CourseCommentDTO> commentModel = addLinksToDto(updatedCourseComment);
        return ResponseEntity.ok(commentModel);
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

    private EntityModel<CourseCommentDTO> addLinksToDto(CourseCommentDTO comment) {
        EntityModel<CourseCommentDTO> commentModel = EntityModel.of(comment);
        
        commentModel.add(linkTo(methodOn(CourseCommentController.class).getCourseCommentById(comment.getId())).withSelfRel());
        commentModel.add(linkTo(CourseCommentController.class).withRel("all-comments"));
        commentModel.add(linkTo(methodOn(CourseCommentController.class).getCommentsByCourseId(comment.getCourseId())).withRel("course-comments"));
        commentModel.add(linkTo(methodOn(CourseCommentController.class).getCommentsByUserId(comment.getUserId())).withRel("user-comments"));
        commentModel.add(linkTo(methodOn(CourseCommentController.class).updateCourseComment(comment.getId(), comment)).withRel("update"));
        commentModel.add(linkTo(methodOn(CourseCommentController.class).deleteCourseComment(comment.getId())).withRel("delete"));
        
        return commentModel;
    }
}
