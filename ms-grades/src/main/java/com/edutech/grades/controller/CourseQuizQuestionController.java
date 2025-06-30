package com.edutech.grades.controller;

import com.edutech.common.dto.CourseQuizQuestionDTO;
import com.edutech.grades.service.CourseQuizQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course-quiz-questions")
@Tag(name = "Course Quiz Questions", description = "API for managing course quiz questions")
public class CourseQuizQuestionController {

    private final CourseQuizQuestionService courseQuizQuestionService;

    public CourseQuizQuestionController(CourseQuizQuestionService courseQuizQuestionService) {
        this.courseQuizQuestionService = courseQuizQuestionService;
    }

    @GetMapping
    @Operation(summary = "Get all course quiz questions", description = "Retrieve a list of all course quiz questions")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getAllCourseQuizQuestions() {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course quiz question by ID", description = "Retrieve a specific course quiz question by its ID")
    public ResponseEntity<CourseQuizQuestionDTO> getCourseQuizQuestionById(
            @Parameter(description = "ID of the course quiz question to retrieve") @PathVariable Integer id) {
        CourseQuizQuestionDTO question = courseQuizQuestionService.findById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/quiz/{quizId}")
    @Operation(summary = "Get questions by quiz ID", description = "Retrieve all questions for a specific quiz")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getQuestionsByQuizId(
            @Parameter(description = "ID of the quiz") @PathVariable Integer quizId) {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/{quizId}/ordered")
    @Operation(summary = "Get questions by quiz ID ordered by index", description = "Retrieve all questions for a specific quiz ordered by order_index")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getQuestionsByQuizIdOrdered(
            @Parameter(description = "ID of the quiz") @PathVariable Integer quizId) {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findByQuizIdOrderByOrderIndex(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/{quizId}/ordered/asc")
    @Operation(summary = "Get questions by quiz ID ordered ascending", description = "Retrieve all questions for a specific quiz ordered by order_index ascending")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getQuestionsByQuizIdOrderedAsc(
            @Parameter(description = "ID of the quiz") @PathVariable Integer quizId) {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findByQuizIdOrderByOrderIndexAsc(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/{quizId}/ordered/desc")
    @Operation(summary = "Get questions by quiz ID ordered descending", description = "Retrieve all questions for a specific quiz ordered by order_index descending")
    public ResponseEntity<List<CourseQuizQuestionDTO>> getQuestionsByQuizIdOrderedDesc(
            @Parameter(description = "ID of the quiz") @PathVariable Integer quizId) {
        List<CourseQuizQuestionDTO> questions = courseQuizQuestionService.findByQuizIdOrderByOrderIndexDesc(quizId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/{quizId}/count")
    @Operation(summary = "Count questions by quiz ID", description = "Get the number of questions for a specific quiz")
    public ResponseEntity<Long> countQuestionsByQuizId(
            @Parameter(description = "ID of the quiz") @PathVariable Integer quizId) {
        long count = courseQuizQuestionService.countByQuizId(quizId);
        return ResponseEntity.ok(count);
    }

    @PostMapping
    @Operation(summary = "Create a new course quiz question", description = "Create a new course quiz question")
    public ResponseEntity<CourseQuizQuestionDTO> createCourseQuizQuestion(
            @Valid @RequestBody CourseQuizQuestionDTO courseQuizQuestionDTO) {
        CourseQuizQuestionDTO createdQuestion = courseQuizQuestionService.create(courseQuizQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course quiz question", description = "Update an existing course quiz question by its ID")
    public ResponseEntity<CourseQuizQuestionDTO> updateCourseQuizQuestion(
            @Parameter(description = "ID of the course quiz question to update") @PathVariable Integer id,
            @Valid @RequestBody CourseQuizQuestionDTO courseQuizQuestionDTO) {
        CourseQuizQuestionDTO updatedQuestion = courseQuizQuestionService.update(id, courseQuizQuestionDTO);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course quiz question", description = "Delete a course quiz question by its ID")
    public ResponseEntity<Void> deleteCourseQuizQuestion(
            @Parameter(description = "ID of the course quiz question to delete") @PathVariable Integer id) {
        courseQuizQuestionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/quiz/{quizId}")
    @Operation(summary = "Delete all questions for a quiz", description = "Delete all questions for a specific quiz")
    public ResponseEntity<Void> deleteQuestionsByQuizId(
            @Parameter(description = "ID of the quiz") @PathVariable Integer quizId) {
        courseQuizQuestionService.deleteByQuizId(quizId);
        return ResponseEntity.noContent().build();
    }
}
