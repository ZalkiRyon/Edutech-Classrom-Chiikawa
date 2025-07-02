package com.edutech.courses.controller;

import com.edutech.common.dto.CourseContentDTO;
import com.edutech.courses.service.CourseContentService;
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
    @Operation(summary = "Obtener todo el contenido", description = "Retorna una lista de todo el contenido de cursos con enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CourseContentDTO>>> getAllCourseContents() {
        List<CourseContentDTO> courseContents = courseContentService.findAll();
        
        List<EntityModel<CourseContentDTO>> contentModels = courseContents.stream()
                .map(this::addLinksToDto)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<CourseContentDTO>> collectionModel = CollectionModel.of(contentModels);
        collectionModel.add(linkTo(CourseContentController.class).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Get course content by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener contenido por ID", description = "Retorna contenido específico de curso por su ID con enlaces HATEOAS")
    public ResponseEntity<EntityModel<CourseContentDTO>> getCourseContentById(
            @Parameter(description = "ID del contenido a obtener") @PathVariable Integer id) {
        CourseContentDTO courseContent = courseContentService.findById(id);
        EntityModel<CourseContentDTO> contentModel = addLinksToDto(courseContent);
        return ResponseEntity.ok(contentModel);
    }

    /**
     * Get course contents by course ID
     */
    @GetMapping("/course/{courseId}")
    @Operation(summary = "Obtener contenido por curso", description = "Retorna todo el contenido de un curso específico con enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CourseContentDTO>>> getCourseContentsByCourseId(
            @Parameter(description = "ID del curso") @PathVariable Integer courseId) {
        List<CourseContentDTO> courseContents = courseContentService.findByCourseId(courseId);
        
        List<EntityModel<CourseContentDTO>> contentModels = courseContents.stream()
                .map(this::addLinksToDto)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<CourseContentDTO>> collectionModel = CollectionModel.of(contentModels);
        collectionModel.add(linkTo(methodOn(CourseContentController.class).getCourseContentsByCourseId(courseId)).withSelfRel());
        collectionModel.add(linkTo(CourseContentController.class).withRel("course-contents"));
        
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Create new course content
     */
    @PostMapping
    @Operation(summary = "Crear nuevo contenido", description = "Crea un nuevo contenido de curso y lo retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<CourseContentDTO>> createCourseContent(
            @Parameter(description = "Datos del nuevo contenido") @Valid @RequestBody CourseContentDTO courseContentDTO) {
        CourseContentDTO createdCourseContent = courseContentService.create(courseContentDTO);
        EntityModel<CourseContentDTO> contentModel = addLinksToDto(createdCourseContent);
        return new ResponseEntity<>(contentModel, HttpStatus.CREATED);
    }

    /**
     * Update existing course content
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar contenido", description = "Actualiza contenido existente de curso y lo retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<CourseContentDTO>> updateCourseContent(
            @Parameter(description = "ID del contenido a actualizar") @PathVariable Integer id, 
            @Parameter(description = "Nuevos datos del contenido") @Valid @RequestBody CourseContentDTO courseContentDTO) {
        CourseContentDTO updatedCourseContent = courseContentService.update(id, courseContentDTO);
        EntityModel<CourseContentDTO> contentModel = addLinksToDto(updatedCourseContent);
        return ResponseEntity.ok(contentModel);
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

    private EntityModel<CourseContentDTO> addLinksToDto(CourseContentDTO content) {
        EntityModel<CourseContentDTO> contentModel = EntityModel.of(content);
        
        contentModel.add(linkTo(methodOn(CourseContentController.class).getCourseContentById(content.getId())).withSelfRel());
        contentModel.add(linkTo(CourseContentController.class).withRel("all-contents"));
        contentModel.add(linkTo(methodOn(CourseContentController.class).getCourseContentsByCourseId(content.getCourseId())).withRel("course-contents"));
        contentModel.add(linkTo(methodOn(CourseContentController.class).updateCourseContent(content.getId(), content)).withRel("update"));
        contentModel.add(linkTo(methodOn(CourseContentController.class).deleteCourseContent(content.getId())).withRel("delete"));
        
        return contentModel;
    }
}
