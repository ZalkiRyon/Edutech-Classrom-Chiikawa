package com.edutech.grades.repository;

import com.edutech.grades.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    List<Quiz> findByCourseId(Integer courseId);
    List<Quiz> findByQuizType(String quizType);
}
