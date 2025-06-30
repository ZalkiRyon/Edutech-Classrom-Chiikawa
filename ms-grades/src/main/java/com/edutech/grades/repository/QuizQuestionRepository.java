package com.edutech.grades.repository;

import com.edutech.grades.entity.CourseQuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<CourseQuizQuestion, Integer> {
    List<CourseQuizQuestion> findByQuizId(Integer quizId);
    List<CourseQuizQuestion> findByQuizIdOrderByOrderIndex(Integer quizId);
}
