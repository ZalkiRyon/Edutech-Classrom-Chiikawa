package com.edutech.courses.controller;

import com.edutech.common.dto.CourseCategoryDTO;
import com.edutech.courses.service.CourseCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/course-categories")
@RequiredArgsConstructor
@Tag(name = "Course Categories", description = "Course category management endpoints")
public class CourseCategoryController {

    private final CourseCategoryService categService;

    @GetMapping
    @Operation(summary = "Get all course categories", description = "Retrieve all course categories with HATEOAS links")
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
    @Operation(summary = "Get course category by ID", description = "Retrieve a specific course category by its ID with HATEOAS links")
    public ResponseEntity<EntityModel<CourseCategoryDTO>> findById(@PathVariable Integer id) {
        CourseCategoryDTO category = categService.findById(id);
        EntityModel<CourseCategoryDTO> categoryModel = addLinksToDto(category);
        return ResponseEntity.ok(categoryModel);
    }

    @PostMapping
    @Operation(summary = "Create course category", description = "Create a new course category and return it with HATEOAS links")
    public ResponseEntity<EntityModel<CourseCategoryDTO>> create(@RequestBody @Valid CourseCategoryDTO dto) {
        CourseCategoryDTO createdCategory = categService.create(dto);
        EntityModel<CourseCategoryDTO> categoryModel = addLinksToDto(createdCategory);
        return ResponseEntity.ok(categoryModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course category", description = "Update an existing course category and return it with HATEOAS links")
    public ResponseEntity<EntityModel<CourseCategoryDTO>> update(@PathVariable Integer id, @RequestBody @Valid CourseCategoryDTO dto) {
        CourseCategoryDTO updatedCategory = categService.update(id, dto);
        EntityModel<CourseCategoryDTO> categoryModel = addLinksToDto(updatedCategory);
        return ResponseEntity.ok(categoryModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course category", description = "Delete a course category by its ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<CourseCategoryDTO> addLinksToDto(CourseCategoryDTO category) {
        EntityModel<CourseCategoryDTO> categoryModel = EntityModel.of(category);
        
        categoryModel.add(linkTo(methodOn(CourseCategoryController.class).findById(category.getId())).withSelfRel());
        categoryModel.add(linkTo(CourseCategoryController.class).withRel("course-categories"));
        categoryModel.add(linkTo(methodOn(CourseCategoryController.class).update(category.getId(), category)).withRel("update"));
        categoryModel.add(linkTo(methodOn(CourseCategoryController.class).delete(category.getId())).withRel("delete"));
        
        return categoryModel;
    }
}
