package com.edutech.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.courses.entity.CourseComment;

@Repository
public interface CourseCommentRepository extends JpaRepository<CourseComment, Integer> {

}
