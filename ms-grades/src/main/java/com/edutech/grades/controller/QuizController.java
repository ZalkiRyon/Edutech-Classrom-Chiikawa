package com.edutech.grades.controller;

import com.edutech.common.dto.QuizDTO;
import com.edutech.grades.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Quiz operations
 * Provides endpoints for quiz management
 */
@RestController
@RequestMapping("/api/grades/quizzes")
@Tag(name = "Quiz", description = "Quiz management operations")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    @Operation(summary = "Create a new quiz", description = "Creates a new quiz for a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quiz created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quiz data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<QuizDTO> createQuiz(
            @Valid @RequestBody QuizDTO quizDTO) {
        try {
            QuizDTO createdQuiz = quizService.createQuiz(quizDTO);
            return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get quiz by ID", description = "Retrieves a quiz by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz found"),
            @ApiResponse(responseCode = "404", description = "Quiz not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<QuizDTO> getQuizById(
            @Parameter(description = "Quiz ID", required = true)
            @PathVariable Integer id) {
        try {
            Optional<QuizDTO> quiz = quizService.getQuizById(id);
            return quiz.map(dto -> ResponseEntity.ok(dto))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all quizzes", description = "Retrieves all quizzes with optional filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<QuizDTO>> getAllQuizzes(
            @Parameter(description = "Filter by course ID") 
            @RequestParam(required = false) Integer courseId,
            @Parameter(description = "Filter by quiz type") 
            @RequestParam(required = false) String quizType,
            @Parameter(description = "Search by title keyword") 
            @RequestParam(required = false) String keyword) {
        try {
            List<QuizDTO> quizzes;
            
            if (courseId != null && quizType != null) {
                quizzes = quizService.getQuizzesByCourseIdAndType(courseId, quizType);
            } else if (courseId != null) {
                quizzes = quizService.getQuizzesByCourseId(courseId);
            } else if (quizType != null) {
                quizzes = quizService.getQuizzesByType(quizType);
            } else if (keyword != null) {
                quizzes = quizService.searchQuizzesByTitle(keyword);
            } else {
                quizzes = quizService.getAllQuizzes();
            }
            
            return ResponseEntity.ok(quizzes);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get quizzes by course", description = "Retrieves all quizzes for a specific course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid course ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<QuizDTO>> getQuizzesByCourseId(
            @Parameter(description = "Course ID", required = true)
            @PathVariable Integer courseId) {
        try {
            List<QuizDTO> quizzes = quizService.getQuizzesByCourseId(courseId);
            return ResponseEntity.ok(quizzes);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/type/{quizType}")
    @Operation(summary = "Get quizzes by type", description = "Retrieves all quizzes of a specific type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quiz type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<QuizDTO>> getQuizzesByType(
            @Parameter(description = "Quiz type", required = true)
            @PathVariable String quizType) {
        try {
            List<QuizDTO> quizzes = quizService.getQuizzesByType(quizType);
            return ResponseEntity.ok(quizzes);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update quiz", description = "Updates an existing quiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quiz data"),
            @ApiResponse(responseCode = "404", description = "Quiz not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<QuizDTO> updateQuiz(
            @Parameter(description = "Quiz ID", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody QuizDTO quizDTO) {
        try {
            QuizDTO updatedQuiz = quizService.updateQuiz(id, quizDTO);
            return ResponseEntity.ok(updatedQuiz);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete quiz", description = "Deletes a quiz by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Quiz deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Quiz not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteQuiz(
            @Parameter(description = "Quiz ID", required = true)
            @PathVariable Integer id) {
        try {
            boolean deleted = quizService.deleteQuiz(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Check if quiz exists", description = "Checks if a quiz exists by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check completed successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Boolean> existsById(
            @Parameter(description = "Quiz ID", required = true)
            @PathVariable Integer id) {
        try {
            boolean exists = quizService.existsById(id);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/course/{courseId}/count")
    @Operation(summary = "Count quizzes by course", description = "Returns the number of quizzes for a specific course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Long> countQuizzesByCourseId(
            @Parameter(description = "Course ID", required = true)
            @PathVariable Integer courseId) {
        try {
            long count = quizService.countQuizzesByCourseId(courseId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/course/{courseId}/exists")
    @Operation(summary = "Check if course has quizzes", description = "Checks if a course has any quizzes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check completed successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Boolean> existsByCourseId(
            @Parameter(description = "Course ID", required = true)
            @PathVariable Integer courseId) {
        try {
            boolean exists = quizService.existsByCourseId(courseId);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
