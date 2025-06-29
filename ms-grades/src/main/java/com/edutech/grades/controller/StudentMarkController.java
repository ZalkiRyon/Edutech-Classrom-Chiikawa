package com.edutech.grades.controller;

import com.edutech.common.dto.StudentMarkDTO;
import com.edutech.grades.service.StudentMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-marks")
@RequiredArgsConstructor
public class StudentMarkController {

    private final StudentMarkService studentMarkService;

    @GetMapping
    public ResponseEntity<List<StudentMarkDTO>> findAll() {
        return ResponseEntity.ok(studentMarkService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentMarkDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(studentMarkService.findById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentMarkDTO>> findByStudentId(@PathVariable Integer studentId) {
        return ResponseEntity.ok(studentMarkService.findByStudentId(studentId));
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<StudentMarkDTO>> findByQuizId(@PathVariable Integer quizId) {
        return ResponseEntity.ok(studentMarkService.findByQuizId(quizId));
    }

    @PostMapping
    public ResponseEntity<StudentMarkDTO> create(@RequestBody StudentMarkDTO dto) {
        return ResponseEntity.ok(studentMarkService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentMarkDTO> update(@PathVariable Integer id, @RequestBody StudentMarkDTO dto) {
        return ResponseEntity.ok(studentMarkService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        studentMarkService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
