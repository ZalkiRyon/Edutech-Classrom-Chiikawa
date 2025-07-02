package com.edutech.courses.controller;

import com.edutech.common.dto.CourseCategoryDTO;
import com.edutech.common.dto.CourseDTO;
import com.edutech.courses.service.CourseCategoryService;
import com.edutech.courses.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/course-categories")
@Tag(name = "Categorías de Cursos", description = "API para gestión de categorías de cursos")
public class CourseCategoryController {

    private final CourseCategoryService categService;
    private final CourseService courseService;

    public CourseCategoryController(CourseCategoryService categService, CourseService courseService) {
        this.categService = categService;
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Retorna una lista de todas las categorías de cursos con enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CourseCategoryDTO>>> findAll() {
        List<CourseCategoryDTO> categories = categService.findAll();
        
        List<EntityModel<CourseCategoryDTO>> categoryModels = categories.stream()
                .map(this::addLinksToDto)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<CourseCategoryDTO>> collectionModel = CollectionModel.of(categoryModels);
        collectionModel.add(linkTo(CourseCategoryController.class).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Retorna una categoría específica por su ID con enlaces HATEOAS")
    public ResponseEntity<EntityModel<CourseCategoryDTO>> findById(@PathVariable Integer id) {
        CourseCategoryDTO category = categService.findById(id);
        EntityModel<CourseCategoryDTO> categoryModel = addLinksToDto(category);
        return ResponseEntity.ok(categoryModel);
    }

    @PostMapping
    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría de curso y la retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<CourseCategoryDTO>> create(@RequestBody @Valid CourseCategoryDTO dto) {
        CourseCategoryDTO createdCategory = categService.create(dto);
        EntityModel<CourseCategoryDTO> categoryModel = addLinksToDto(createdCategory);
        return ResponseEntity.ok(categoryModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría existente y la retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<CourseCategoryDTO>> update(@PathVariable Integer id, @RequestBody @Valid CourseCategoryDTO dto) {
        CourseCategoryDTO updatedCategory = categService.update(id, dto);
        EntityModel<CourseCategoryDTO> categoryModel = addLinksToDto(updatedCategory);
        return ResponseEntity.ok(categoryModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría de curso por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{categoryId}/courses")
    @Operation(summary = "Obtener cursos por categoría", description = "Retorna todos los cursos de una categoría específica con enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CourseDTO>>> getCoursesByCategory(@PathVariable Integer categoryId) {
        List<CourseDTO> courses = courseService.findByCategoryId(categoryId);
        
        List<EntityModel<CourseDTO>> courseModels = courses.stream()
                .map(this::addLinksToCourseDto)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<CourseDTO>> collectionModel = CollectionModel.of(courseModels);
        collectionModel.add(linkTo(methodOn(CourseCategoryController.class).getCoursesByCategory(categoryId)).withSelfRel());
        collectionModel.add(linkTo(methodOn(CourseCategoryController.class).findById(categoryId)).withRel("category"));
        collectionModel.add(linkTo(CourseCategoryController.class).withRel("course-categories"));
        
        return ResponseEntity.ok(collectionModel);
    }

    private EntityModel<CourseCategoryDTO> addLinksToDto(CourseCategoryDTO category) {
        EntityModel<CourseCategoryDTO> categoryModel = EntityModel.of(category);
        
        categoryModel.add(linkTo(methodOn(CourseCategoryController.class).findById(category.getId())).withSelfRel());
        categoryModel.add(linkTo(CourseCategoryController.class).withRel("course-categories"));
        categoryModel.add(linkTo(methodOn(CourseCategoryController.class).update(category.getId(), category)).withRel("update"));
        categoryModel.add(linkTo(methodOn(CourseCategoryController.class).delete(category.getId())).withRel("delete"));
        
        return categoryModel;
    }

    private EntityModel<CourseDTO> addLinksToCourseDto(CourseDTO course) {
        EntityModel<CourseDTO> courseModel = EntityModel.of(course);
        
        // Agregar enlaces básicos para el curso (necesitaremos importar CourseController si existe)
        // courseModel.add(linkTo(methodOn(CourseController.class).findById(course.getId())).withSelfRel());
        // courseModel.add(linkTo(CourseController.class).withRel("courses"));
        
        // Por ahora, agregamos enlaces básicos
        courseModel.add(linkTo(methodOn(CourseCategoryController.class).getCoursesByCategory(course.getCategoryId())).withRel("category-courses"));
        courseModel.add(linkTo(methodOn(CourseCategoryController.class).findById(course.getCategoryId())).withRel("category"));
        
        return courseModel;
    }
}
