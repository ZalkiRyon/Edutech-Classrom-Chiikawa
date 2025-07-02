# Pruebas de Integración FeignClient - EduTech Classroom

## 📋 Descripción General

Las **pruebas de integración FeignClient** verifican la comunicación real entre microservicios en el proyecto EduTech Classroom. Estas pruebas validan que los FeignClients funcionen correctamente al conectarse con servicios reales ejecutándose en sus puertos correspondientes.

## 🎯 Objetivo

- ✅ **Verificar comunicación**: Los FeignClients pueden conectarse exitosamente a otros microservicios
- ✅ **Validar datos**: Las respuestas contienen la estructura de datos esperada
- ✅ **Probar rendimiento**: Los tiempos de respuesta son aceptables (< 5 segundos)
- ✅ **Manejar errores**: Las excepciones FeignException se manejan correctamente
- ✅ **Validar textos en español**: Todos los mensajes de prueba están en español

## 📂 Estructura de Pruebas

### ms-grades - FeignClientIntegrationTest
**Ubicación**: `ms-grades/src/test/java/com/edutech/grades/integration/FeignClientIntegrationTest.java`

**FeignClients probados**:
- `UserClient` → ms-users (puerto 9001)
- `CourseClient` → ms-courses (puerto 9002)

**Casos de prueba**:
```java
✅ deberiaObtenerUsuarioExistentePorId()
✅ deberiaLanzarExcepcionParaUsuarioInexistente()
✅ deberiaObtenerCursoExistentePorId()
✅ deberiaFallarParaCursoInexistente()
✅ deberiaValidarRespuestaCompletaDelUsuario()
✅ deberiaValidarRespuestaCompletaDelCurso()
✅ deberiaResponderEnTiempoAceptable()
```

### ms-courses - CoursesFeignClientIntegrationTest
**Ubicación**: `ms-courses/src/test/java/com/edutech/courses/integration/CoursesFeignClientIntegrationTest.java`

**FeignClients probados**:
- `UserClient` → ms-users (puerto 9001)

**Casos de prueba**:
```java
✅ deberiaValidarInstructorParaCreacionCurso()
✅ deberiaValidarDatosComplementariosInstructor()
✅ deberiaFallarParaInstructorInexistente()
✅ deberiaProbarVariosInstructoresParaCursos()
✅ deberiaValidarInstructorConRolEspecifico()
✅ deberiaValidarCamposRequeridosParaInstructor()
✅ deberiaValidarRendimientoConsultaInstructor()
```

### ms-payments - PaymentsFeignClientIntegrationTest
**Ubicación**: `ms-payments/src/test/java/com/edutech/payments/integration/PaymentsFeignClientIntegrationTest.java`

**FeignClients probados**:
- `UserClient` → ms-users (puerto 9001)
- `CourseClient` → ms-courses (puerto 9002)
- `EnrollmentClient` → ms-courses (puerto 9002)

**Casos de prueba**:
```java
✅ deberiaValidarUsuarioParaProcesoPago()
✅ deberiaValidarCursoParaProcesoPago()
✅ deberiaValidarInscripcionParaProcesoPago()
✅ deberiaFallarParaUsuarioInexistenteEnPago()
✅ deberiaFallarParaCursoInexistenteEnPago()
✅ deberiaValidarFlujoCompletoPago()
✅ deberiaValidarRendimientoConsultasPago()
```

### ms-support - SupportFeignClientIntegrationTest
**Ubicación**: `ms-support/src/test/java/com/edutech/support/integration/SupportFeignClientIntegrationTest.java`

**FeignClients probados**:
- `UserClient` → ms-users (puerto 9001)
- `CourseClient` → ms-courses (puerto 9002)
- `GradeClient` → ms-grades (puerto 9003)

**Casos de prueba**:
```java
✅ deberiaValidarUsuarioParaSoporte()
✅ deberiaValidarCursoParaConsultaSoporte()
✅ deberiaObtenerCalificacionParaSoporteAcademico()
✅ deberiaFallarParaUsuarioInexistenteEnSoporte()
✅ deberiaValidarInformacionComplementariaParaSoporte()
✅ deberiaValidarRendimientoParaConsultasSoporte()
✅ deberiaProbarEscalarTicketConDatosCurso()
```

## 🚀 Ejecución de Pruebas

### Prerequisitos

**⚠️ IMPORTANTE**: Los microservicios de destino deben estar ejecutándose antes de ejecutar las pruebas de integración.

```bash
# 1. Iniciar todos los servicios
run-all.bat

# 2. Verificar que estén activos
netstat -an | findstr :900

# Debe mostrar:
#   :9001 - ms-users
#   :9002 - ms-courses  
#   :9003 - ms-grades
#   :9004 - ms-payments
#   :9005 - ms-support
```

### Métodos de Ejecución

#### 1. Script Automatizado (Recomendado)
```bash
# Ejecutar todas las pruebas de integración FeignClient
test-feign-integration.bat
```

#### 2. Por Microservicio Individual
```bash
# ms-grades
cd ms-grades && mvn test -Dtest="*FeignClient*"

# ms-courses
cd ms-courses && mvn test -Dtest="*FeignClient*"

# ms-payments
cd ms-payments && mvn test -Dtest="*FeignClient*"

# ms-support
cd ms-support && mvn test -Dtest="*FeignClient*"
```

#### 3. Prueba Específica
```bash
# Ejemplo: Probar solo UserClient en ms-grades
cd ms-grades
mvn test -Dtest=FeignClientIntegrationTest#deberiaObtenerUsuarioExistentePorId
```

## 📊 Resultados Esperados

### ✅ Ejecución Exitosa
Cuando todos los servicios están activos y funcionando:
```
✅ ms-grades FeignClient: PASS (7 pruebas)
✅ ms-courses FeignClient: PASS (7 pruebas)
✅ ms-payments FeignClient: PASS (7 pruebas)
✅ ms-support FeignClient: PASS (7 pruebas)

🎯 Total: 28 pruebas de integración PASS
🌟 9 FeignClients verificados exitosamente
```

### ⚠️ Servicios No Disponibles
Si los microservicios no están ejecutándose:
```
❌ Connection refused: ms-users (puerto 9001)
❌ FeignException: Load balancer does not have available server

💡 Solución: Ejecutar run-all.bat antes de las pruebas
```

## 🔧 Características de las Pruebas

### Textos en Español
Todos los textos de prueba están en español:
```java
@Test
void deberiaObtenerUsuarioExistentePorId() {
    // Dado - Given: ID de usuario que debería existir
    Integer usuarioIdExistente = 1;
    
    // Cuando - When: Se invoca el FeignClient
    UserDTO usuarioObtenido = userClient.findById(usuarioIdExistente);
    
    // Entonces - Then: Se valida la respuesta
    assertNotNull(usuarioObtenido, "El usuario obtenido no debería ser null");
}
```

### Validación de Performance
```java
@Test
void deberiaResponderEnTiempoAceptable() {
    long inicioTiempo = System.currentTimeMillis();
    
    UserDTO usuario = userClient.findById(1);
    
    long tiempoTranscurrido = System.currentTimeMillis() - inicioTiempo;
    assertTrue(tiempoTranscurrido < 5000, 
              "La respuesta debe ser menor a 5 segundos");
}
```

### Manejo de Errores
```java
@Test
void deberiaLanzarExcepcionParaUsuarioInexistente() {
    Integer usuarioIdInexistente = 999999;
    
    FeignException exception = assertThrows(FeignException.class, () -> {
        userClient.findById(usuarioIdInexistente);
    });
    
    assertEquals(404, exception.status(), 
                "Debe retornar 404 para usuario inexistente");
}
```

## 📈 Beneficios

1. **🔍 Detección Temprana**: Identifica problemas de comunicación entre servicios
2. **📊 Validación Real**: Prueba con datos reales de microservicios activos
3. **⚡ Performance**: Verifica tiempos de respuesta aceptables
4. **🛡️ Robustez**: Confirma manejo correcto de errores y excepciones
5. **🌐 Integración**: Valida el ecosistema completo de microservicios
6. **🇪🇸 Localización**: Todos los mensajes y validaciones en español

## 🎯 Próximos Pasos

- [ ] Agregar pruebas de carga para FeignClients
- [ ] Implementar circuit breaker testing
- [ ] Pruebas con Eureka discovery activo
- [ ] Métricas de latencia y throughput
- [ ] Pruebas de failover y recuperación

---

**📝 Nota**: Estas pruebas complementan las pruebas unitarias existentes y proporcionan confianza adicional en la comunicación entre microservicios del proyecto EduTech Classroom.
