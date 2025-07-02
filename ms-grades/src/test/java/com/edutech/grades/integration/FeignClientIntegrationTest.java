package com.edutech.grades.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.edutech.common.dto.CourseDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.grades.client.CourseClient;
import com.edutech.grades.client.UserClient;

import feign.FeignException;

/**
 * Pruebas de integración para FeignClients del microservicio ms-grades
 * Estas pruebas validan la comunicación real entre microservicios
 */
@SpringBootTest
@ActiveProfiles("test")
class FeignClientIntegrationTest {

    @Autowired
    private UserClient userClient;

    @Autowired
    private CourseClient courseClient;

    @Test
    void deberiaObtenerUsuarioExistentePorId() {
        // Given - Dado: ID de usuario que debería existir
        Integer usuarioIdExistente = 1;

        // When - Cuando: Se invoca el FeignClient
        UserDTO usuarioObtenido = userClient.findById(usuarioIdExistente);

        // Then - Entonces: Se valida la respuesta
        assertNotNull(usuarioObtenido, "El usuario obtenido no debería ser null");
        assertEquals(usuarioIdExistente, usuarioObtenido.getId(), 
                    "El ID del usuario obtenido debería coincidir con el solicitado");
        assertNotNull(usuarioObtenido.getFirstName(), 
                     "El nombre del usuario no debería ser null");
        assertNotNull(usuarioObtenido.getEmail(), 
                     "El email del usuario no debería ser null");
    }

    @Test
    void deberiaLanzarExcepcionParaUsuarioInexistente() {
        // Given - Dado: ID de usuario que no existe
        Integer usuarioIdInexistente = 999999;

        // When & Then - Cuando y Entonces: Se espera una excepción
        assertThrows(FeignException.class, () -> {
            userClient.findById(usuarioIdInexistente);
        }, "Debería lanzar FeignException para usuario inexistente");
    }

    @Test
    void deberiaObtenerCursoExistentePorId() {
        // Given - Dado: ID de curso que debería existir
        Integer cursoIdExistente = 1;

        // When - Cuando: Se invoca el FeignClient
        CourseDTO cursoObtenido = courseClient.findById(cursoIdExistente);

        // Then - Entonces: Se valida la respuesta
        assertNotNull(cursoObtenido, "El curso obtenido no debería ser null");
        assertEquals(cursoIdExistente, cursoObtenido.getId(), 
                    "El ID del curso obtenido debería coincidir con el solicitado");
        assertNotNull(cursoObtenido.getTitle(), 
                     "El título del curso no debería ser null");
        assertNotNull(cursoObtenido.getDescription(), 
                     "La descripción del curso no debería ser null");
    }

    @Test
    void deberiaLanzarExcepcionParaCursoInexistente() {
        // Given - Dado: ID de curso que no existe
        Integer cursoIdInexistente = 999999;

        // When & Then - Cuando y Entonces: Se espera una excepción
        assertThrows(FeignException.class, () -> {
            courseClient.findById(cursoIdInexistente);
        }, "Debería lanzar FeignException para curso inexistente");
    }

    @Test
    void deberiaValidarRespuestaCompletaDelUsuario() {
        // Given - Dado: ID de usuario para validación completa
        Integer usuarioId = 1;

        // When - Cuando: Se obtiene el usuario
        UserDTO usuario = userClient.findById(usuarioId);

        // Then - Entonces: Se valida que todos los campos necesarios estén presentes
        assertAll("Validación completa del usuario obtenido via FeignClient",
            () -> assertNotNull(usuario.getId(), "ID del usuario requerido"),
            () -> assertNotNull(usuario.getFirstName(), "Nombre del usuario requerido"),
            () -> assertNotNull(usuario.getEmail(), "Email del usuario requerido"),
            () -> assertTrue(usuario.getFirstName().length() > 0, 
                           "El nombre del usuario debe tener contenido"),
            () -> assertTrue(usuario.getEmail().contains("@"), 
                           "El email debe tener formato válido")
        );
    }

    @Test
    void deberiaValidarRespuestaCompletaDelCurso() {
        // Given - Dado: ID de curso para validación completa
        Integer cursoId = 1;

        // When - Cuando: Se obtiene el curso
        CourseDTO curso = courseClient.findById(cursoId);

        // Then - Entonces: Se valida que todos los campos necesarios estén presentes
        assertAll("Validación completa del curso obtenido via FeignClient",
            () -> assertNotNull(curso.getId(), "ID del curso requerido"),
            () -> assertNotNull(curso.getTitle(), "Título del curso requerido"),
            () -> assertNotNull(curso.getDescription(), "Descripción del curso requerida"),
            () -> assertTrue(curso.getTitle().length() > 0, 
                           "El título del curso debe tener contenido"),
            () -> assertTrue(curso.getDescription().length() > 0, 
                           "La descripción del curso debe tener contenido")
        );
    }

    @Test
    void deberiaValidarTiempoDeRespuestaRazonable() {
        // Given - Dado: ID para medir tiempo de respuesta
        Integer usuarioId = 1;
        long tiempoInicio = System.currentTimeMillis();

        // When - Cuando: Se realiza la llamada
        UserDTO usuario = userClient.findById(usuarioId);
        long tiempoFin = System.currentTimeMillis();

        // Then - Entonces: Se valida tiempo de respuesta
        long duracion = tiempoFin - tiempoInicio;
        assertTrue(duracion < 5000, 
                  "La respuesta del FeignClient debería ser menor a 5 segundos. Duración: " + duracion + "ms");
        assertNotNull(usuario, "El usuario debería haberse obtenido exitosamente");
    }
}
