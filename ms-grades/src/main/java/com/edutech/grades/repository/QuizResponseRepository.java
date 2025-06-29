package com.edutech.grades.repository;

import com.edutech.grades.entity.QuizResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResponseRepository extends JpaRepository<QuizResponse, Integer> {
    List<QuizResponse> findByStudentId(Integer studentId);
    List<QuizResponse> findByQuizId(Integer quizId);
    List<QuizResponse> findByStudentIdAndQuizId(Integer studentId, Integer quizId);
}
