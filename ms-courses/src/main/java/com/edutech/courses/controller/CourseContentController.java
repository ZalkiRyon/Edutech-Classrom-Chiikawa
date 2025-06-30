package com.edutech.courses.controller;

import com.edutech.common.dto.CourseContentDTO;
import com.edutech.courses.service.CourseContentService;
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
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    /**
     * Get all course contents
     */
    @GetMapping
    public ResponseEntity<List<CourseContentDTO>> getAllCourseContents() {
        List<CourseContentDTO> courseContents = courseContentService.findAll();
        return ResponseEntity.ok(courseContents);
    }

    /**
     * Get course content by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CourseContentDTO> getCourseContentById(@PathVariable Integer id) {
        CourseContentDTO courseContent = courseContentService.findById(id);
        return ResponseEntity.ok(courseContent);
    }

    /**
     * Get course contents by course ID
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseContentDTO>> getCourseContentsByCourseId(@PathVariable Integer courseId) {
        List<CourseContentDTO> courseContents = courseContentService.findByCourseId(courseId);
        return ResponseEntity.ok(courseContents);
    }

    /**
     * Create new course content
     */
    @PostMapping
    public ResponseEntity<CourseContentDTO> createCourseContent(@Valid @RequestBody CourseContentDTO courseContentDTO) {
        CourseContentDTO createdCourseContent = courseContentService.create(courseContentDTO);
        return new ResponseEntity<>(createdCourseContent, HttpStatus.CREATED);
    }

    /**
     * Update existing course content
     */
    @PutMapping("/{id}")
    public ResponseEntity<CourseContentDTO> updateCourseContent(
            @PathVariable Integer id, 
            @Valid @RequestBody CourseContentDTO courseContentDTO) {
        CourseContentDTO updatedCourseContent = courseContentService.update(id, courseContentDTO);
        return ResponseEntity.ok(updatedCourseContent);
    }

    /**
     * Delete course content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseContent(@PathVariable Integer id) {
        courseContentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
