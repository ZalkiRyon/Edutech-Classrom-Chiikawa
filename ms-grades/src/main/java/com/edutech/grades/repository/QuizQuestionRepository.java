package com.edutech.grades.repository;

import com.edutech.grades.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {
    List<QuizQuestion> findByQuizId(Integer quizId);
    List<QuizQuestion> findByQuizIdOrderByOrderIndex(Integer quizId);
}
