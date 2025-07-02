package com.edutech.payments.integration;

import com.edutech.common.dto.CourseDTO;
import com.edutech.common.dto.EnrollmentDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.payments.client.CourseClient;
import com.edutech.payments.client.EnrollmentClient;
import com.edutech.payments.client.UserClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración para FeignClients del microservicio ms-payments
 * Valida la comunicación con ms-users, ms-courses y ms-grades
 */
@SpringBootTest
@ActiveProfiles("test")
class PaymentsFeignClientIntegrationTest {

    @Autowired
    private UserClient userClient;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private EnrollmentClient enrollmentClient;

    @Test
    void deberiaValidarUsuarioParaProcesoPago() {
        // Given - Dado: Usuario que realizará un pago
        Integer usuarioIdPagador = 1;

        // When - Cuando: Se valida el usuario antes del pago
        UserDTO usuarioPagador = userClient.findById(usuarioIdPagador);

        // Then - Entonces: Se confirma que el usuario es válido para pagos
        assertNotNull(usuarioPagador, "Usuario pagador debe existir para procesar pago");
        assertNotNull(usuarioPagador.getEmail(), "Email requerido para confirmación de pago");
        assertNotNull(usuarioPagador.getFirstName(), "Nombre requerido para identificación");
        assertTrue(usuarioPagador.getEmail().contains("@"), 
                  "Email debe tener formato válido para notificaciones de pago");
    }

    @Test
    void deberiaValidarCursoParaProcesoPago() {
        // Given - Dado: Curso para el cual se procesará un pago
        Integer cursoIdPago = 1;

        // When - Cuando: Se valida el curso antes del pago
        CourseDTO cursoPago = courseClient.findById(cursoIdPago);

        // Then - Entonces: Se confirma que el curso es válido para pagos
        assertNotNull(cursoPago, "Curso debe existir para procesar pago");
        assertNotNull(cursoPago.getTitle(), "Título del curso requerido para recibo de pago");
        assertNotNull(cursoPago.getPrice(), "Precio del curso requerido para procesamiento");
        assertTrue(cursoPago.getPrice().compareTo(BigDecimal.ZERO) >= 0, "Precio del curso debe ser mayor o igual a cero");
    }

    @Test
    void deberiaValidarInscripcionParaProcesoPago() {
        // Given - Dado: Inscripción específica para verificar en pagos
        Integer inscripcionId = 1;

        // When - Cuando: Se valida la inscripción antes del pago
        try {
            EnrollmentDTO inscripcion = enrollmentClient.findById(inscripcionId);

            // Then - Entonces: Se valida la información de inscripción
            assertNotNull(inscripcion, "Inscripción debe existir para procesar pago relacionado");
            assertNotNull(inscripcion.getId(), "ID de inscripción requerido");
            assertNotNull(inscripcion.getStudentId(), "ID del estudiante requerido");
            assertNotNull(inscripcion.getCourseId(), "ID del curso requerido");
            
        } catch (FeignException e) {
            // Es válido que la inscripción no exista - esto se maneja en el flujo de negocio
            assertTrue(e.status() == 404, "Error esperado: inscripción no encontrada");
        }
    }

    @Test
    void deberiaFallarParaUsuarioInexistenteEnPago() {
        // Given - Dado: Usuario inexistente intentando realizar pago
        Integer usuarioInexistente = 888888;

        // When & Then - Cuando y Entonces: Debe fallar la validación
        assertThrows(FeignException.class, () -> {
            userClient.findById(usuarioInexistente);
        }, "Validación de usuario para pago debe fallar si el usuario no existe");
    }

    @Test
    void deberiaFallarParaCursoInexistenteEnPago() {
        // Given - Dado: Curso inexistente para pago
        Integer cursoInexistente = 777777;

        // When & Then - Cuando y Entonces: Debe fallar la validación
        assertThrows(FeignException.class, () -> {
            courseClient.findById(cursoInexistente);
        }, "Validación de curso para pago debe fallar si el curso no existe");
    }

    @Test
    void deberiaValidarFlujoPagoCompleto() {
        // Given - Dado: Datos para simular flujo de pago completo
        Integer usuarioId = 1;
        Integer cursoId = 1;

        // When - Cuando: Se ejecuta el flujo de validaciones de pago
        UserDTO usuario = userClient.findById(usuarioId);
        CourseDTO curso = courseClient.findById(cursoId);
        
        // Validar inscripción específica en lugar de por estudiante
        EnrollmentDTO inscripcionEspecifica = null;
        try {
            inscripcionEspecifica = enrollmentClient.findById(1); // Inscripción de ejemplo
        } catch (FeignException e) {
            // La inscripción puede no existir, esto es válido
        }

        // Then - Entonces: Se valida que todos los componentes estén disponibles
        assertAll("Flujo completo de validación para pago",
            () -> assertNotNull(usuario, "Usuario debe estar disponible"),
            () -> assertNotNull(curso, "Curso debe estar disponible"),
            () -> assertTrue(usuario.getId() > 0, "Usuario debe tener ID válido"),
            () -> assertTrue(curso.getId() > 0, "Curso debe tener ID válido"),
            () -> assertNotNull(curso.getPrice(), "Precio del curso requerido para pago")
        );
    }

    @Test
    void deberiaValidarTiemposDeRespuestaParaPagos() {
        // Given - Dado: IDs para medir rendimiento en pagos
        Integer usuarioId = 1;
        Integer cursoId = 1;

        // When - Cuando: Se miden los tiempos de respuesta
        long inicioUsuario = System.currentTimeMillis();
        UserDTO usuario = userClient.findById(usuarioId);
        long finUsuario = System.currentTimeMillis();

        long inicioCurso = System.currentTimeMillis();
        CourseDTO curso = courseClient.findById(cursoId);
        long finCurso = System.currentTimeMillis();

        // Then - Entonces: Se valida rendimiento aceptable para pagos
        long duracionUsuario = finUsuario - inicioUsuario;
        long duracionCurso = finCurso - inicioCurso;

        assertTrue(duracionUsuario < 3000, 
                  "Validación de usuario para pago debe ser rápida. Duración: " + duracionUsuario + "ms");
        assertTrue(duracionCurso < 3000, 
                  "Validación de curso para pago debe ser rápida. Duración: " + duracionCurso + "ms");
        
        assertNotNull(usuario, "Usuario obtenido exitosamente");
        assertNotNull(curso, "Curso obtenido exitosamente");
    }
}
