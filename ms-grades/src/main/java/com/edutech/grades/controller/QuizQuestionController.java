package com.edutech.grades.controller;

import com.edutech.common.dto.QuizQuestionDTO;
import com.edutech.grades.service.QuizQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-questions")
public class QuizQuestionController {

    @Autowired
    private QuizQuestionService quizQuestionService;

    @GetMapping
    public ResponseEntity<List<QuizQuestionDTO>> getAllQuizQuestions() {
        List<QuizQuestionDTO> quizQuestions = quizQuestionService.findAll();
        return ResponseEntity.ok(quizQuestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizQuestionDTO> getQuizQuestionById(@PathVariable Integer id) {
        QuizQuestionDTO quizQuestion = quizQuestionService.findById(id);
        if (quizQuestion != null) {
            return ResponseEntity.ok(quizQuestion);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuizQuestionDTO>> getQuizQuestionsByQuizId(@PathVariable Integer quizId) {
        List<QuizQuestionDTO> quizQuestions = quizQuestionService.findByQuizId(quizId);
        return ResponseEntity.ok(quizQuestions);
    }

    @GetMapping("/quiz/{quizId}/ordered")
    public ResponseEntity<List<QuizQuestionDTO>> getQuizQuestionsByQuizIdOrdered(@PathVariable Integer quizId) {
        List<QuizQuestionDTO> quizQuestions = quizQuestionService.findByQuizIdOrderByOrderIndex(quizId);
        return ResponseEntity.ok(quizQuestions);
    }

    @PostMapping
    public ResponseEntity<QuizQuestionDTO> createQuizQuestion(@RequestBody QuizQuestionDTO quizQuestionDTO) {
        QuizQuestionDTO createdQuizQuestion = quizQuestionService.create(quizQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuizQuestion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizQuestionDTO> updateQuizQuestion(@PathVariable Integer id, @RequestBody QuizQuestionDTO quizQuestionDTO) {
        QuizQuestionDTO updatedQuizQuestion = quizQuestionService.update(id, quizQuestionDTO);
        if (updatedQuizQuestion != null) {
            return ResponseEntity.ok(updatedQuizQuestion);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizQuestion(@PathVariable Integer id) {
        quizQuestionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
