package com.edutech.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.courses.entity.Course;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    
    /**
     * Find all courses by category ID
     * @param categoryId the category ID
     * @return List of courses in the specified category
     */
    List<Course> findByCategoryId(Integer categoryId);
}
