package com.edutech.grades.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edutech.common.dto.CourseDTO;

// Configuración temporal para desarrollo sin Eureka - conexión directa a ms-courses
@FeignClient(name = "ms-courses", url = "http://localhost:9002") 
public interface CourseClient {
    @GetMapping("/api/courses/{id}")
    CourseDTO findById(@PathVariable("id") Integer id);
}
