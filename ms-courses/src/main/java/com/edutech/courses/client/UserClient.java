package com.edutech.courses.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edutech.common.dto.UserDTO;

// Configuración temporal para desarrollo sin Eureka - conexión directa a ms-users
// Cuando Eureka esté disponible, cambiar a: @FeignClient(name = "ms-users") 
@FeignClient(name = "ms-users", url = "http://localhost:9001")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserDTO findById(@PathVariable("id") Integer id);
}
