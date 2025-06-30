package com.edutech.courses.controller;

import com.edutech.common.dto.CourseDTO;
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
@RequestMapping("/api/courses")
@Tag(name = "Cursos", description = "API para gestión de cursos de la plataforma")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los cursos", description = "Retorna una lista de todos los cursos disponibles")
    public ResponseEntity<CollectionModel<EntityModel<CourseDTO>>> findAll() {
        List<CourseDTO> courses = courseService.findAll();
        
        List<EntityModel<CourseDTO>> courseModels = courses.stream()
                .map(this::addLinksToDto)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<CourseDTO>> collectionModel = CollectionModel.of(courseModels);
        collectionModel.add(linkTo(CourseController.class).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener curso por ID", description = "Retorna un curso específico por su ID")
    public ResponseEntity<EntityModel<CourseDTO>> findById(@PathVariable Integer id) {
        CourseDTO course = courseService.findById(id);
        EntityModel<CourseDTO> courseModel = addLinksToDto(course);
        return ResponseEntity.ok(courseModel);
    }

    @PostMapping
    @Operation(summary = "Create course", description = "Create a new course and return it with HATEOAS links")
    public ResponseEntity<EntityModel<CourseDTO>> create(@RequestBody @Valid CourseDTO dto) {
        CourseDTO createdCourse = courseService.create(dto);
        EntityModel<CourseDTO> courseModel = addLinksToDto(createdCourse);
        return ResponseEntity.ok(courseModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Update an existing course and return it with HATEOAS links")
    public ResponseEntity<EntityModel<CourseDTO>> update(@PathVariable Integer id, @RequestBody @Valid CourseDTO dto) {
        CourseDTO updatedCourse = courseService.update(id, dto);
        EntityModel<CourseDTO> courseModel = addLinksToDto(updatedCourse);
        return ResponseEntity.ok(courseModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Delete a course by its ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<CourseDTO> addLinksToDto(CourseDTO course) {
        EntityModel<CourseDTO> courseModel = EntityModel.of(course);
        
        courseModel.add(linkTo(methodOn(CourseController.class).findById(course.getId())).withSelfRel());
        courseModel.add(linkTo(CourseController.class).withRel("courses"));
        courseModel.add(linkTo(methodOn(CourseController.class).update(course.getId(), course)).withRel("update"));
        courseModel.add(linkTo(methodOn(CourseController.class).delete(course.getId())).withRel("delete"));
        
        return courseModel;
    }
}
