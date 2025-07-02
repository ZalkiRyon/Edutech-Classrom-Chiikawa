package com.edutech.support.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edutech.common.dto.CourseDTO;

/**
 * Feign Client para comunicación con el microservicio de cursos (ms-courses)
 * Permite validar cursos al gestionar tickets de soporte relacionados
 */
@FeignClient(name = "ms-courses", url = "http://localhost:9002") 
public interface CourseClient {
    
    /**
     * Busca un curso por su ID
     * @param id ID del curso
     * @return Datos del curso
     */
    @GetMapping("/api/courses/{id}")
    CourseDTO findById(@PathVariable("id") Integer id);
}
