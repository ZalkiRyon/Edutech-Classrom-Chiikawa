# Pruebas de Integración de FeignClients - EduTech Classroom

## 📋 Descripción

Este documento describe las pruebas de integración implementadas para validar el funcionamiento de los FeignClients en la arquitectura de microservicios de EduTech Classroom.

## 🎯 Objetivo de las Pruebas

Las pruebas de integración de FeignClients validan:

- ✅ **Comunicación real** entre microservicios
- ✅ **Manejo de errores** en llamadas entre servicios
- ✅ **Tiempo de respuesta** aceptable
- ✅ **Integridad de datos** en las respuestas
- ✅ **Disponibilidad de servicios** de destino

## 🏗️ Estructura de Pruebas por Microservicio

### 1. MS-Grades - FeignClient Integration Test
**Archivo**: `ms-grades/src/test/java/com/edutech/grades/integration/FeignClientIntegrationTest.java`

**FeignClients probados**:
- `UserClient` → ms-users (puerto 9001)
- `CourseClient` → ms-courses (puerto 9002)

**Casos de prueba**:
- ✅ `deberiaObtenerUsuarioExistentePorId()` - Validación de estudiantes
- ✅ `deberiaLanzarExcepcionParaUsuarioInexistente()` - Manejo de errores
- ✅ `deberiaObtenerCursoExistentePorId()` - Validación de cursos
- ✅ `deberiaLanzarExcepcionParaCursoInexistente()` - Manejo de errores
- ✅ `deberiaValidarRespuestaCompletaDelUsuario()` - Integridad de datos
- ✅ `deberiaValidarRespuestaCompletaDelCurso()` - Integridad de datos
- ✅ `deberiaValidarTiempoDeRespuestaRazonable()` - Performance

### 2. MS-Payments - FeignClient Integration Test
**Archivo**: `ms-payments/src/test/java/com/edutech/payments/integration/PaymentsFeignClientIntegrationTest.java`

**FeignClients probados**:
- `UserClient` → ms-users (puerto 9001)
- `CourseClient` → ms-courses (puerto 9002)
- `EnrollmentClient` → ms-grades (puerto 9003)

**Casos de prueba**:
- ✅ `deberiaValidarUsuarioParaProcesoPago()` - Validación previa al pago
- ✅ `deberiaValidarCursoParaProcesoPago()` - Validación del curso a pagar
- ✅ `deberiaObtenerInscripcionesDelUsuario()` - Verificación de inscripciones
- ✅ `deberiaFallarParaUsuarioInexistenteEnPago()` - Control de errores
- ✅ `deberiaFallarParaCursoInexistenteEnPago()` - Control de errores
- ✅ `deberiaValidarFlujoPagoCompleto()` - Flujo integral
- ✅ `deberiaValidarTiemposDeRespuestaParaPagos()` - Performance

### 3. MS-Support - FeignClient Integration Test
**Archivo**: `ms-support/src/test/java/com/edutech/support/integration/SupportFeignClientIntegrationTest.java`

**FeignClients probados**:
- `UserClient` → ms-users (puerto 9001)
- `CourseClient` → ms-courses (puerto 9002)
- `GradeClient` → ms-grades (puerto 9003)

**Casos de prueba**:
- ✅ `deberiaValidarUsuarioParaTicketSoporte()` - Validación para tickets
- ✅ `deberiaValidarCursoParaConsultaSoporte()` - Contexto de soporte
- ✅ `deberiaValidarInformacionDeCalificacionesParaSoporte()` - Datos complementarios
- ✅ `deberiaFallarParaUsuarioInexistenteEnSoporte()` - Control de errores
- ✅ `deberiaFallarParaCursoInexistenteEnSoporte()` - Control de errores
- ✅ `deberiaValidarFlujoCreaciónTicketSoporte()` - Flujo completo
- ✅ `deberiaValidarInformacionComplementariaParaSoporte()` - Datos detallados
- ✅ `deberiaValidarRendimientoParaConsultasSoporte()` - Performance
- ✅ `deberiaValidarDisponibilidadServiciosParaSoporte()` - Disponibilidad

### 4. MS-Courses - FeignClient Integration Test
**Archivo**: `ms-courses/src/test/java/com/edutech/courses/integration/CoursesFeignClientIntegrationTest.java`

**FeignClients probados**:
- `UserClient` → ms-users (puerto 9001)

**Casos de prueba**:
- ✅ `deberiaValidarInstructorParaCreacionCurso()` - Validación de instructores
- ✅ `deberiaValidarDatosComplementariosInstructor()` - Datos completos
- ✅ `deberiaFallarParaInstructorInexistente()` - Control de errores
- ✅ `deberiaValidarMultiplesInstructores()` - Validación múltiple
- ✅ `deberiaValidarTiempoRespuestaInstructor()` - Performance
- ✅ `deberiaValidarDisponibilidadServicioUsuarios()` - Disponibilidad
- ✅ `deberiaValidarRolDelInstructor()` - Validación específica de rol
- ✅ `deberiaValidarCamposRequeridosParaInstructor()` - Campos obligatorios
- ✅ `deberiaValidarComunicacionEstableConServicioUsuarios()` - Estabilidad

## 🚀 Ejecución de Pruebas

### Comando Manual
```bash
# Ejecutar todas las pruebas de integración de FeignClient
test-feign-integration.bat

# Ejecutar pruebas específicas por microservicio
cd ms-grades && mvn test -Dtest=FeignClientIntegrationTest
cd ms-payments && mvn test -Dtest=PaymentsFeignClientIntegrationTest
cd ms-support && mvn test -Dtest=SupportFeignClientIntegrationTest
cd ms-courses && mvn test -Dtest=CoursesFeignClientIntegrationTest
```

### Pre-requisitos para Ejecución
```bash
# 1. Compilar proyecto completo
mvn clean compile test-compile

# 2. Ejecutar servicios de destino (REQUERIDO)
run-all.bat

# 3. Verificar que los servicios estén disponibles
curl http://localhost:9001/api/users/1    # ms-users
curl http://localhost:9002/api/courses/1  # ms-courses
curl http://localhost:9003/api/enrollments # ms-grades
```

## 📊 Métricas de Rendimiento

### Tiempos de Respuesta Esperados
| FeignClient | Servicio Destino | Tiempo Máximo | Propósito |
|-------------|------------------|---------------|-----------|
| UserClient | ms-users (9001) | 3000ms | Validaciones rápidas |
| CourseClient | ms-courses (9002) | 3000ms | Consultas de cursos |
| EnrollmentClient | ms-grades (9003) | 4000ms | Operaciones complejas |
| GradeClient | ms-grades (9003) | 4000ms | Consultas de soporte |

### Validaciones de Datos
- ✅ **Campos obligatorios** no nulos
- ✅ **IDs** mayor que 0
- ✅ **Emails** con formato válido (@)
- ✅ **Usernames** con longitud mínima
- ✅ **Precios** mayor o igual a 0

## 🔧 Configuración de Pruebas

### Profile de Pruebas
Las pruebas utilizan `@ActiveProfiles("test")` para:
- Configuración específica de test
- Base de datos de pruebas (H2 o MySQL test)
- Logging detallado
- Timeouts reducidos

### Anotaciones Utilizadas
```java
@SpringBootTest              // Contexto completo de Spring
@ActiveProfiles("test")      // Profile específico
@Autowired                   // Inyección de FeignClients
@Test                        // Método de prueba JUnit 5
```

## ⚠️ Consideraciones Importantes

### Dependencias entre Servicios
- Las pruebas **requieren** que los servicios de destino estén ejecutándose
- Fallarán con `FeignException` si los servicios no están disponibles
- Se recomienda ejecutar `run-all.bat` antes de las pruebas

### Datos de Prueba
- Las pruebas asumen IDs `1, 2, 3` como existentes
- IDs grandes (`999999, 888888, 777777`) como inexistentes
- Los datos deben existir en la base de datos para pruebas exitosas

### Manejo de Errores
- `FeignException.NotFound` (404) para recursos inexistentes
- `FeignException.InternalServerError` (500) para errores del servidor
- Timeouts configurados por FeignClient

## 📈 Beneficios de las Pruebas de Integración

### Validación Real
- ✅ Comunicación real entre microservicios
- ✅ Serialización/deserialización JSON
- ✅ Configuración de red y puertos
- ✅ Manejo de errores HTTP

### Detección Temprana
- ✅ Cambios en APIs entre servicios
- ✅ Problemas de conectividad
- ✅ Incompatibilidades de versiones
- ✅ Fallos de configuración

### Confianza en Producción
- ✅ Validación de contratos entre servicios
- ✅ Verificación de resilencia
- ✅ Medición de performance
- ✅ Estabilidad de comunicación

## 🎯 Mejoras Textuales en Español

### Nomenclatura de Métodos
Todos los métodos de prueba utilizan nombres descriptivos en español:
- `deberiaObtenerUsuarioExistentePorId()` - Casos exitosos
- `deberiaLanzarExcepcionParaUsuarioInexistente()` - Casos de error
- `deberiaValidarRespuestaCompleta()` - Validaciones integrales

### Comentarios y Aserciones
```java
// Given - Dado: Descripción de la configuración inicial
// When - Cuando: Descripción de la acción a ejecutar  
// Then - Entonces: Descripción de la verificación esperada

assertNotNull(usuario, "El usuario obtenido no debería ser null");
assertEquals(expected, actual, "El valor debería coincidir con el esperado");
assertTrue(condition, "La condición debería ser verdadera");
```

### Mensajes de Error Descriptivos
Los mensajes de error están en español y son descriptivos para facilitar el debugging y mantenimiento del código.

---

**📝 Nota**: Las pruebas de integración de FeignClients son fundamentales para garantizar la comunicación confiable entre microservicios en la arquitectura de EduTech Classroom.
