package com.edutech.grades.repository;

import com.edutech.grades.entity.StudentMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMarkRepository extends JpaRepository<StudentMark, Integer> {
    List<StudentMark> findByStudentId(Integer studentId);
    List<StudentMark> findByQuizId(Integer quizId);
    List<StudentMark> findByStudentIdAndQuizId(Integer studentId, Integer quizId);
}
