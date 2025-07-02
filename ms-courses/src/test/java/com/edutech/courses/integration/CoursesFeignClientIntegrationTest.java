package com.edutech.courses.integration;

import com.edutech.common.dto.UserDTO;
import com.edutech.courses.client.UserClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración para FeignClients del microservicio ms-courses
 * Valida la comunicación con ms-users para validación de instructores
 */
@SpringBootTest
@ActiveProfiles("test")
class CoursesFeignClientIntegrationTest {

    @Autowired
    private UserClient userClient;

    @Test
    void deberiaValidarInstructorParaCreacionCurso() {
        // Given - Dado: ID de instructor que debe crear un curso
        Integer instructorId = 1;

        // When - Cuando: Se valida el instructor antes de crear curso
        UserDTO instructor = userClient.findById(instructorId);

        // Then - Entonces: Se confirma que el instructor es válido
        assertNotNull(instructor, "Instructor debe existir para crear curso");
        assertNotNull(instructor.getFirstName(), "Nombre del instructor requerido");
        assertNotNull(instructor.getEmail(), "Email del instructor requerido para comunicación");
        assertTrue(instructor.getId() > 0, "ID del instructor debe ser válido");
    }

    @Test
    void deberiaValidarDatosComplementariosInstructor() {
        // Given - Dado: Instructor para validación completa
        Integer instructorId = 1;

        // When - Cuando: Se obtienen datos del instructor
        UserDTO instructor = userClient.findById(instructorId);

        // Then - Entonces: Se valida información completa del instructor
        assertAll("Validación completa de instructor para curso",
            () -> assertNotNull(instructor.getId(), "ID del instructor requerido"),
            () -> assertNotNull(instructor.getFirstName(), "Nombre del instructor requerido"),
            () -> assertNotNull(instructor.getEmail(), "Email del instructor requerido"),
            () -> assertTrue(instructor.getFirstName().length() > 0, 
                           "Nombre del instructor debe tener contenido"),
            () -> assertTrue(instructor.getEmail().contains("@"), 
                           "Email del instructor debe ser válido"),
            () -> assertEquals(instructorId, instructor.getId(),
                            "ID debe coincidir con el solicitado")
        );
    }

    @Test
    void deberiaFallarParaInstructorInexistente() {
        // Given - Dado: ID de instructor que no existe
        Integer instructorInexistente = 777777;

        // When & Then - Cuando y Entonces: Debe fallar la validación
        assertThrows(FeignException.class, () -> {
            userClient.findById(instructorInexistente);
        }, "Validación de instructor debe fallar si el instructor no existe");
    }

    @Test
    void deberiaValidarMultiplesInstructores() {
        // Given - Dado: Múltiples IDs de instructores
        Integer[] instructoresIds = {1, 2, 3};

        // When & Then - Cuando y Entonces: Se validan múltiples instructores
        for (Integer instructorId : instructoresIds) {
            try {
                UserDTO instructor = userClient.findById(instructorId);
                if (instructor != null) {
                    // Si el instructor existe, validar datos básicos
                    assertNotNull(instructor.getFirstName(), 
                                 "Instructor " + instructorId + " debe tener nombre");
                    assertNotNull(instructor.getEmail(), 
                                 "Instructor " + instructorId + " debe tener email");
                    assertTrue(instructor.getId() > 0, 
                             "Instructor " + instructorId + " debe tener ID válido");
                }
            } catch (FeignException e) {
                // Es esperado que algunos instructores puedan no existir
                assertTrue(e.status() == 404, 
                          "Error esperado: instructor " + instructorId + " no encontrado");
            }
        }
    }

    @Test
    void deberiaValidarTiempoRespuestaInstructor() {
        // Given - Dado: ID de instructor para medir rendimiento
        Integer instructorId = 1;
        long tiempoInicio = System.currentTimeMillis();

        // When - Cuando: Se valida el instructor
        UserDTO instructor = userClient.findById(instructorId);
        long tiempoFin = System.currentTimeMillis();

        // Then - Entonces: Se valida tiempo de respuesta eficiente
        long duracion = tiempoFin - tiempoInicio;
        assertTrue(duracion < 3000, 
                  "Validación de instructor debe ser rápida para creación de cursos. Duración: " + duracion + "ms");
        assertNotNull(instructor, "Instructor obtenido exitosamente");
    }

    @Test
    void deberiaValidarDisponibilidadServicioUsuarios() {
        // Given - Dado: ID para verificar disponibilidad del servicio
        Integer usuarioId = 1;

        // When & Then - Cuando y Entonces: Se verifica que el servicio esté disponible
        assertDoesNotThrow(() -> {
            UserDTO usuario = userClient.findById(usuarioId);
            assertNotNull(usuario, "Servicio de usuarios debe responder correctamente");
        }, "Servicio ms-users debe estar disponible para validaciones de instructores");
    }

    @Test
    void deberiaValidarRolDelInstructor() {
        // Given - Dado: Instructor para validar su rol
        Integer instructorId = 1;

        // When - Cuando: Se obtiene información del instructor
        UserDTO instructor = userClient.findById(instructorId);

        // Then - Entonces: Se valida que tenga información de rol si está disponible
        assertNotNull(instructor, "Instructor debe existir");
        
        // Validar campos específicos para instructores
        assertAll("Validación específica para rol de instructor",
            () -> assertNotNull(instructor.getFirstName(), "Nombre requerido para identificación"),
            () -> assertNotNull(instructor.getEmail(), "Email requerido para comunicación con estudiantes"),
            () -> assertTrue(instructor.getFirstName().length() >= 2, 
                           "Nombre del instructor debe tener longitud mínima"),
            () -> assertTrue(instructor.getEmail().matches(".*@.*\\..*"), 
                           "Email del instructor debe tener formato válido")
        );
    }

    @Test
    void deberiaValidarCamposRequeridosParaInstructor() {
        // Given - Dado: ID de instructor con datos completos
        Integer instructorId = 1;

        // When - Cuando: Se obtiene el instructor
        UserDTO instructor = userClient.findById(instructorId);

        // Then - Entonces: Se validan todos los campos necesarios para un instructor
        assertNotNull(instructor, "Instructor no puede ser null");
        
        // Validaciones específicas para la funcionalidad de instructor
        assertAll("Campos requeridos para funcionalidad de instructor",
            () -> assertTrue(instructor.getId() != null && instructor.getId() > 0, 
                           "ID del instructor debe ser positivo"),
            () -> assertNotNull(instructor.getFirstName(), 
                              "Nombre requerido para mostrar como autor del curso"),
            () -> assertNotNull(instructor.getEmail(), 
                              "Email requerido para notificaciones de inscripciones"),
            () -> assertFalse(instructor.getFirstName().trim().isEmpty(), 
                             "Nombre no puede estar vacío"),
            () -> assertFalse(instructor.getEmail().trim().isEmpty(), 
                             "Email no puede estar vacío")
        );
    }

    @Test
    void deberiaValidarComunicacionEstableConServicioUsuarios() {
        // Given - Dado: Múltiples intentos para verificar estabilidad
        Integer instructorId = 1;
        int intentos = 3;

        // When & Then - Cuando y Entonces: Se realizan múltiples llamadas
        for (int i = 0; i < intentos; i++) {
            final int intento = i + 1;
            
            assertDoesNotThrow(() -> {
                UserDTO instructor = userClient.findById(instructorId);
                assertNotNull(instructor, 
                             "Intento " + intento + ": Instructor debe obtenerse exitosamente");
                assertEquals(instructorId, instructor.getId(),
                           "Intento " + intento + ": ID debe coincidir");
            }, "Intento " + intento + ": Comunicación con ms-users debe ser estable");

            // Pequeña pausa entre intentos
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
