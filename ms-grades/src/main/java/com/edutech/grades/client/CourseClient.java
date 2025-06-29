package com.edutech.grades.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edutech.common.dto.CourseDTO;

@FeignClient(name = "ms-courses") 
public interface CourseClient {
    @GetMapping("/api/courses/{id}")
    CourseDTO findById(@PathVariable("id") Integer id);
}
