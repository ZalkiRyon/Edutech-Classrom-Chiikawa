package com.edutech.grades.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edutech.common.dto.CourseContentDTO;
import com.edutech.common.dto.CourseDTO;

// Configuración temporal para desarrollo sin Eureka - conexión directa a ms-courses
@FeignClient(name = "ms-courses", url = "http://localhost:9002") 
public interface CourseClient {
    
    @GetMapping("/api/courses/{id}")
    CourseDTO findById(@PathVariable("id") Integer id);
    
    /**
     * Obtiene todos los cursos de una categoría específica
     */
    @GetMapping("/api/course-categories/{categoryId}/courses")
    CollectionModel<EntityModel<CourseDTO>> getCoursesByCategory(@PathVariable("categoryId") Integer categoryId);
    
    /**
     * Obtiene todos los contenidos de un curso específico
     */
    @GetMapping("/api/course-contents/course/{courseId}")
    CollectionModel<EntityModel<CourseContentDTO>> getCourseContentsByCourseId(@PathVariable("courseId") Integer courseId);
    
    /**
     * Obtiene un contenido específico por su ID
     */
    @GetMapping("/api/course-contents/{id}")
    EntityModel<CourseContentDTO> getCourseContentById(@PathVariable("id") Integer id);
}
