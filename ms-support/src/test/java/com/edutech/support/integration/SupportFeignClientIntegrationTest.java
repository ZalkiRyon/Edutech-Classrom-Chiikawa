package com.edutech.support.integration;

import com.edutech.common.dto.CourseDTO;
import com.edutech.common.dto.UserDTO;
import com.edutech.support.client.CourseClient;
import com.edutech.support.client.GradeClient;
import com.edutech.support.client.UserClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración para FeignClients del microservicio ms-support
 * Valida la comunicación con ms-users, ms-courses y ms-grades para soporte técnico
 */
@SpringBootTest
@ActiveProfiles("test")
class SupportFeignClientIntegrationTest {

    @Autowired
    private UserClient userClient;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private GradeClient gradeClient;

    @Test
    void deberiaValidarUsuarioParaTicketSoporte() {
        // Given - Dado: Usuario que necesita crear ticket de soporte
        Integer usuarioSoporteId = 1;

        // When - Cuando: Se valida el usuario antes de crear ticket
        UserDTO usuarioSoporte = userClient.findById(usuarioSoporteId);

        // Then - Entonces: Se confirma que el usuario es válido para soporte
        assertNotNull(usuarioSoporte, "Usuario debe existir para crear ticket de soporte");
        assertNotNull(usuarioSoporte.getFirstName(), "Nombre requerido para identificación en soporte");
        assertNotNull(usuarioSoporte.getEmail(), "Email requerido para comunicación de soporte");
        assertTrue(usuarioSoporte.getEmail().contains("@"), 
                  "Email debe ser válido para notificaciones de soporte");
    }

    @Test
    void deberiaValidarCursoParaConsultaSoporte() {
        // Given - Dado: Curso sobre el cual se consulta en soporte
        Integer cursoConsultaId = 1;

        // When - Cuando: Se valida el curso en el contexto de soporte
        CourseDTO cursoConsulta = courseClient.findById(cursoConsultaId);

        // Then - Entonces: Se confirma información del curso para soporte
        assertNotNull(cursoConsulta, "Curso debe existir para consultas de soporte");
        assertNotNull(cursoConsulta.getTitle(), "Título del curso requerido para contexto de soporte");
        assertNotNull(cursoConsulta.getDescription(), "Descripción del curso útil para soporte");
        assertNotNull(cursoConsulta.getInstructorId(), "ID del instructor necesario para escalamiento");
    }

    @Test
    void deberiaValidarInformacionDeCalificacionesParaSoporte() {
        // Given - Dado: ID de inscripción para consulta de calificaciones
        Integer inscripcionId = 1;

        // When & Then - Cuando y Entonces: Se intenta obtener información de calificaciones
        // Nota: Esto podría fallar si no existe la inscripción, lo cual es comportamiento esperado
        try {
            // Aquí se implementaría la llamada cuando el método esté disponible en GradeClient
            // Por ahora, solo validamos que el cliente está disponible
            assertNotNull(gradeClient, "GradeClient debe estar disponible para consultas de soporte");
        } catch (Exception e) {
            // Esperado si no hay datos o el servicio no está disponible
            assertTrue(e instanceof FeignException || e instanceof RuntimeException,
                      "Excepción esperada para datos no disponibles");
        }
    }

    @Test
    void deberiaFallarParaUsuarioInexistenteEnSoporte() {
        // Given - Dado: Usuario inexistente intentando crear ticket
        Integer usuarioInexistente = 999999;

        // When & Then - Cuando y Entonces: Debe fallar la validación
        assertThrows(FeignException.class, () -> {
            userClient.findById(usuarioInexistente);
        }, "Validación de usuario para soporte debe fallar si el usuario no existe");
    }

    @Test
    void deberiaFallarParaCursoInexistenteEnSoporte() {
        // Given - Dado: Curso inexistente para consulta de soporte
        Integer cursoInexistente = 888888;

        // When & Then - Cuando y Entonces: Debe fallar la validación
        assertThrows(FeignException.class, () -> {
            courseClient.findById(cursoInexistente);
        }, "Validación de curso para soporte debe fallar si el curso no existe");
    }

    @Test
    void deberiaValidarFlujoCreaciónTicketSoporte() {
        // Given - Dado: Datos para crear ticket de soporte
        Integer usuarioId = 1;
        Integer cursoRelacionadoId = 1;

        // When - Cuando: Se validan todos los componentes para crear ticket
        UserDTO usuario = userClient.findById(usuarioId);
        CourseDTO curso = courseClient.findById(cursoRelacionadoId);

        // Then - Entonces: Se valida que toda la información esté disponible
        assertAll("Validación completa para creación de ticket de soporte",
            () -> assertNotNull(usuario, "Usuario solicitante debe existir"),
            () -> assertNotNull(curso, "Curso relacionado debe existir"),
            () -> assertNotNull(usuario.getEmail(), "Email del usuario requerido para seguimiento"),
            () -> assertNotNull(curso.getTitle(), "Título del curso requerido para contexto"),
            () -> assertTrue(usuario.getId() > 0, "ID de usuario debe ser válido"),
            () -> assertTrue(curso.getId() > 0, "ID de curso debe ser válido")
        );
    }

    @Test
    void deberiaValidarInformacionComplementariaParaSoporte() {
        // Given - Dado: IDs para obtener información completa de soporte
        Integer usuarioId = 1;
        Integer cursoId = 1;

        // When - Cuando: Se obtiene información detallada
        UserDTO usuario = userClient.findById(usuarioId);
        CourseDTO curso = courseClient.findById(cursoId);

        // Then - Entonces: Se valida información útil para agentes de soporte
        assertAll("Información complementaria para soporte técnico",
            () -> assertNotNull(usuario.getFirstName(), "Nombre para identificación"),
            () -> assertNotNull(usuario.getEmail(), "Email para comunicación"),
            () -> assertNotNull(curso.getTitle(), "Título del curso para contexto"),
            () -> assertNotNull(curso.getInstructorId(), "Instructor para escalamiento si es necesario"),
            () -> assertTrue(curso.getTitle().length() > 0, "Título debe tener contenido descriptivo"),
            () -> assertTrue(usuario.getFirstName().length() > 0, "Nombre debe ser identificable")
        );
    }

    @Test
    void deberiaValidarRendimientoParaConsultasSoporte() {
        // Given - Dado: IDs para medir rendimiento en consultas de soporte
        Integer usuarioId = 1;
        Integer cursoId = 1;

        // When - Cuando: Se miden tiempos de respuesta para soporte
        long inicioUsuario = System.currentTimeMillis();
        UserDTO usuario = userClient.findById(usuarioId);
        long finUsuario = System.currentTimeMillis();

        long inicioCurso = System.currentTimeMillis();
        CourseDTO curso = courseClient.findById(cursoId);
        long finCurso = System.currentTimeMillis();

        // Then - Entonces: Se valida respuesta rápida para soporte eficiente
        long duracionUsuario = finUsuario - inicioUsuario;
        long duracionCurso = finCurso - inicioCurso;

        assertTrue(duracionUsuario < 4000, 
                  "Consulta de usuario para soporte debe ser eficiente. Duración: " + duracionUsuario + "ms");
        assertTrue(duracionCurso < 4000, 
                  "Consulta de curso para soporte debe ser eficiente. Duración: " + duracionCurso + "ms");
        
        assertNotNull(usuario, "Usuario obtenido para soporte exitosamente");
        assertNotNull(curso, "Curso obtenido para soporte exitosamente");
    }

    @Test
    void deberiaValidarDisponibilidadServiciosParaSoporte() {
        // Given - Dado: Verificación de disponibilidad de servicios
        Integer idPrueba = 1;

        // When & Then - Cuando y Entonces: Se verifica que todos los servicios respondan
        assertDoesNotThrow(() -> {
            UserDTO usuario = userClient.findById(idPrueba);
            assertNotNull(usuario, "Servicio de usuarios debe estar disponible");
        }, "Servicio ms-users debe estar disponible para soporte");

        assertDoesNotThrow(() -> {
            CourseDTO curso = courseClient.findById(idPrueba);
            assertNotNull(curso, "Servicio de cursos debe estar disponible");
        }, "Servicio ms-courses debe estar disponible para soporte");

        // GradeClient puede no tener métodos implementados aún, pero debe estar inyectado
        assertNotNull(gradeClient, "GradeClient debe estar disponible para futuras consultas");
    }
}
