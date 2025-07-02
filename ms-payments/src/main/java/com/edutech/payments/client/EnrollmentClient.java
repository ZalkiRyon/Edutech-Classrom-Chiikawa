package com.edutech.payments.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edutech.common.dto.EnrollmentDTO;

/**
 * Feign Client para comunicación con el microservicio de cursos (ms-courses)
 * Permite validar inscripciones al procesar pagos relacionados
 */
@FeignClient(name = "ms-courses-enrollment", url = "http://localhost:9002") 
public interface EnrollmentClient {
    
    /**
     * Busca una inscripción por su ID
     * @param id ID de la inscripción
     * @return Datos de la inscripción
     */
    @GetMapping("/api/enrollments/{id}")
    EnrollmentDTO findById(@PathVariable("id") Integer id);
}
