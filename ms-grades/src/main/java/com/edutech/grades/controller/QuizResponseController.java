package com.edutech.grades.controller;

import com.edutech.common.dto.QuizResponseDTO;
import com.edutech.grades.service.QuizResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-responses")
public class QuizResponseController {

    @Autowired
    private QuizResponseService quizResponseService;

    @GetMapping
    public ResponseEntity<List<QuizResponseDTO>> getAllQuizResponses() {
        List<QuizResponseDTO> quizResponses = quizResponseService.findAll();
        return ResponseEntity.ok(quizResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponseDTO> getQuizResponseById(@PathVariable Integer id) {
        QuizResponseDTO quizResponse = quizResponseService.findById(id);
        if (quizResponse != null) {
            return ResponseEntity.ok(quizResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuizResponseDTO>> getQuizResponsesByQuizId(@PathVariable Integer quizId) {
        List<QuizResponseDTO> quizResponses = quizResponseService.findByQuizId(quizId);
        return ResponseEntity.ok(quizResponses);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<QuizResponseDTO>> getQuizResponsesByStudentId(@PathVariable Integer studentId) {
        List<QuizResponseDTO> quizResponses = quizResponseService.findByStudentId(studentId);
        return ResponseEntity.ok(quizResponses);
    }

    @GetMapping("/quiz/{quizId}/student/{studentId}")
    public ResponseEntity<List<QuizResponseDTO>> getQuizResponsesByQuizIdAndStudentId(
            @PathVariable Integer quizId, @PathVariable Integer studentId) {
        List<QuizResponseDTO> quizResponses = quizResponseService.findByQuizIdAndStudentId(quizId, studentId);
        return ResponseEntity.ok(quizResponses);
    }

    @PostMapping
    public ResponseEntity<QuizResponseDTO> createQuizResponse(@RequestBody QuizResponseDTO quizResponseDTO) {
        QuizResponseDTO createdQuizResponse = quizResponseService.create(quizResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuizResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizResponseDTO> updateQuizResponse(@PathVariable Integer id, @RequestBody QuizResponseDTO quizResponseDTO) {
        QuizResponseDTO updatedQuizResponse = quizResponseService.update(id, quizResponseDTO);
        if (updatedQuizResponse != null) {
            return ResponseEntity.ok(updatedQuizResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizResponse(@PathVariable Integer id) {
        quizResponseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
