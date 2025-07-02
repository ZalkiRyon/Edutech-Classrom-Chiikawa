package com.edutech.support.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edutech.common.dto.StudentMarkDTO;

/**
 * Feign Client para comunicación con el microservicio de calificaciones (ms-grades)
 * Permite consultar calificaciones al gestionar tickets de soporte académico
 */
@FeignClient(name = "ms-grades", url = "http://localhost:9003")
public interface GradeClient {
    
    /**
     * Busca las calificaciones de un estudiante específico
     * @param studentId ID del estudiante
     * @return Lista de calificaciones del estudiante
     */
    @GetMapping("/api/student-marks/student/{studentId}")
    List<StudentMarkDTO> findByStudentId(@PathVariable("studentId") Integer studentId);
}
