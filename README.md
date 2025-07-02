# EduTech Classroom - Arquitectura de Microservicios

## 🎯 Descripción del Proyecto

**EduTech Classroom** es una plataforma educativa moderna basada en **arquitectura de microservicios**, desarrollada por Chiikawa SPA. El proyecto implementa un sistema educativo completo con separación de responsabilidades, comunicación entre servicios mediante **FeignClients**, validaciones cruzadas, **HATEOAS para navegabilidad hipermedia** y documentación interactiva con **Swagger/OpenAPI**.

## 📋 Estado Actual del Proyecto (Diciembre 2024)

### ✅ Completado y Funcional

- **✅ Arquitectura de microservicios** con 5 servicios principales + Eureka
- **✅ FeignClients implementados** para comunicación inter-servicios (9 clientes totales)
- **✅ Validaciones cruzadas** en tiempo real entre microservicios
- **✅ HATEOAS completo** con navegabilidad hipermedia (JSON HAL)
- **✅ Swagger/OpenAPI 3.0** documentación interactiva en todos los servicios
- **✅ Spring Boot 3.2.0** con Java 21 compatible
- **✅ Compilación exitosa** del proyecto completo y pruebas
- **✅ Base de datos MySQL** configurada y funcional
- **✅ Scripts de automatización** para ejecución y testing

### Servicios Implementados

| Microservicio | Puerto | Responsabilidad | Validaciones Cruzadas | FeignClients |
|---------------|--------|-----------------|----------------------|--------------|
| **ms-users**    | 9001   | Gestión de usuarios y roles | - | - |
| **ms-courses**  | 9002   | Gestión de cursos, categorías, contenidos e inscripciones | ✅ Usuarios | UserClient |
| **ms-grades**   | 9003   | Gestión de calificaciones, quizzes y respuestas | ✅ Usuarios + Cursos | UserClient, CourseClient |
| **ms-payments** | 9004   | Gestión de pagos y cupones de descuento | ✅ Usuarios + Cursos | UserClient, CourseClient, EnrollmentClient |
| **ms-support**  | 9005   | Gestión de tickets de soporte técnico | ✅ Usuarios + Cursos + Calificaciones | UserClient, CourseClient, GradeClient |
| **eureka**       | 8761   | Servidor de descubrimiento de servicios | - | - |

## 🏗️ Arquitectura de Microservicios

### Características Técnicas

- ✅ **Arquitectura de Microservicios** independientes y escalables
- ✅ **Spring Boot 3.2.0** con **Java 21** compatible
- ✅ **MySQL** como base de datos (compartida para desarrollo)
- ✅ **FeignClient** para comunicación entre servicios (9 clientes totales)
- ✅ **Validaciones cruzadas** en tiempo real entre microservicios
- ✅ **HATEOAS completo** para navegabilidad hipermedia (JSON HAL)
- ✅ **Swagger/OpenAPI 3.0** para documentación interactiva
- ✅ **Pruebas unitarias completas** con JUnit 5 y Mockito
- ✅ **Manejo de excepciones** centralizado

## 🚀 Ejecución del Proyecto

### Ejecución Completa con Scripts

```bash
# Ejecutar todos los microservicios de una vez
run-all.bat

# Ejecutar el servidor Eureka
run-eureka.bat

# Ejecutar servicios individuales
run-users.bat      # Puerto 9001
run-courses.bat    # Puerto 9002  
run-grades.bat     # Puerto 9003
run-payments.bat   # Puerto 9004
run-support.bat    # Puerto 9005
```

### Ejecución Manual

```bash
# Compilar proyecto padre
mvn install -N

# Compilar todos los módulos
mvn clean compile

# Ejecutar cada microservicio (en terminales separadas)
cd ms-users && mvn spring-boot:run
cd ms-courses && mvn spring-boot:run  
cd ms-grades && mvn spring-boot:run
cd ms-payments && mvn spring-boot:run
cd ms-support && mvn spring-boot:run
cd eureka && mvn spring-boot:run
```

### Verificar Servicios Activos

```bash
# Verificar todos los puertos activos
netstat -an | findstr :900

# Verificar conectividad
curl http://localhost:9001/api/users
curl http://localhost:9002/api/courses
curl http://localhost:9003/api/enrollments
curl http://localhost:9004/api/payments
curl http://localhost:9005/api/support-tickets
```

## 📚 Documentación API (Swagger)

### URLs de Swagger UI

- **ms-users**: http://localhost:9001/swagger-ui/index.html
- **ms-courses**: http://localhost:9002/swagger-ui/index.html
- **ms-grades**: http://localhost:9003/swagger-ui/index.html
- **ms-payments**: http://localhost:9004/swagger-ui/index.html
- **ms-support**: http://localhost:9005/swagger-ui/index.html
- **eureka**: http://localhost:8761

---

## 📁 Estructura del Proyecto

```
second-wind/
├── common/                 # Módulo común (DTOs, excepciones, utilidades)
├── ms-users/              # Microservicio de usuarios y roles
├── ms-courses/            # Microservicio de cursos y categorías  
├── ms-grades/             # Microservicio de inscripciones y calificaciones
├── ms-payments/           # Microservicio de pagos y cupones
├── ms-support/            # Microservicio de soporte técnico
├── eureka/                # Servidor Eureka
├── Monolitico/           # Monolito original (migrado)
├── run-all.bat           # Ejecutar todos los servicios
├── run-*.bat             # Scripts individuales por servicio
├── test-integration.bat  # Pruebas de integración
├── create-db.sql         # Script de base de datos
├── pom.xml               # POM padre con configuración compartida
└── README.md             # Este archivo
```
### Endpoints PrincipalesAdd commentMore actions

#### ms-users (Puerto 9001)
```
GET    /api/users              # Listar usuarios
POST   /api/users              # Crear usuario
GET    /api/users/{id}         # Obtener usuario
PUT    /api/users/{id}         # Actualizar usuario
DELETE /api/users/{id}         # Eliminar usuario

GET    /api/roles              # Listar roles
POST   /api/roles              # Crear rol
```

#### ms-courses (Puerto 9002)
```
GET    /api/courses            # Listar cursos
POST   /api/courses            # Crear curso (valida instructor)
GET    /api/courses/{id}       # Obtener curso
PUT    /api/courses/{id}       # Actualizar curso
DELETE /api/courses/{id}       # Eliminar curso

GET    /api/course-categories  # Listar categorías
POST   /api/course-categories  # Crear categoría
```

#### ms-grades (Puerto 9003)
```
GET    /api/enrollments                    # Listar inscripciones
POST   /api/enrollments                    # Crear inscripción (valida student+course)
GET    /api/enrollments/{id}               # Obtener inscripción
PUT    /api/enrollments/{id}               # Actualizar inscripción
DELETE /api/enrollments/{id}               # Eliminar inscripción
GET    /api/enrollments/student/{studentId} # Inscripciones por estudiante
GET    /api/enrollments/course/{courseId}   # Inscripciones por curso
```

#### ms-payments (Puerto 9004)
```
GET    /api/payments                # Listar pagos
POST   /api/payments                # Crear pago (valida usuario)
GET    /api/payments/{id}           # Obtener pago
PUT    /api/payments/{id}           # Actualizar pago
DELETE /api/payments/{id}           # Eliminar pago
GET    /api/payments/user/{userId}  # Pagos por usuario

GET    /api/discount-coupons        # Listar cupones
POST   /api/discount-coupons        # Crear cupón
GET    /api/discount-coupons/{id}   # Obtener cupón
```

#### ms-support (Puerto 9005)
```
GET    /api/support-tickets                      # Listar tickets
POST   /api/support-tickets                      # Crear ticket (valida usuario)
GET    /api/support-tickets/{id}                 # Obtener ticket
PUT    /api/support-tickets/{id}                 # Actualizar ticket
DELETE /api/support-tickets/{id}                 # Eliminar ticket
GET    /api/support-tickets/user/{userId}        # Tickets por usuario
GET    /api/support-tickets/support-user/{userId} # Tickets por agente
GET    /api/support-tickets/status/{status}      # Tickets por estado
```

## 🔗 Validaciones Cruzadas

### Casos de Prueba de Validación

#### ms-grades ↔ ms-users + ms-courses
```bash
# ❌ Student ID inválido
curl -X POST http://localhost:9003/api/enrollments \
  -H "Content-Type: application/json" \
  -d '{"studentId": 999, "courseId": 1, "enrollmentDate": "2024-01-15T10:00:00", "status": "ACTIVE"}'

# ❌ Course ID inválido  
curl -X POST http://localhost:9003/api/enrollments \
  -H "Content-Type: application/json" \
  -d '{"studentId": 1, "courseId": 999, "enrollmentDate": "2024-01-15T10:00:00", "status": "ACTIVE"}'

# ✅ Ambos IDs válidos
curl -X POST http://localhost:9003/api/enrollments \
  -H "Content-Type: application/json" \
  -d '{"studentId": 1, "courseId": 1, "enrollmentDate": "2024-01-15T10:00:00", "status": "ACTIVE"}'
```

#### ms-payments ↔ ms-users
```bash
# ❌ User ID inválido
curl -X POST http://localhost:9004/api/payments \
  -H "Content-Type: application/json" \
  -d '{"userId": 999, "amount": 100.50, "paymentDate": "2024-01-15T10:00:00Z", "paymentMethod": "Credit Card", "paymentInstitution": "TestBank", "transactionId": "TEST001", "status": "Pending"}'

# ✅ User ID válido
curl -X POST http://localhost:9004/api/payments \
  -H "Content-Type: application/json" \
  -d '{"userId": 15, "amount": 100.50, "paymentDate": "2024-01-15T10:00:00Z", "paymentMethod": "Credit Card", "paymentInstitution": "TestBank", "transactionId": "TEST002", "status": "Completed"}'
```

#### ms-support ↔ ms-users
```bash
# ❌ User ID inválido
curl -X POST http://localhost:9005/api/support-tickets \
  -H "Content-Type: application/json" \
  -d '{"userId": 888, "supportUserId": 16, "subject": "Test Ticket", "description": "Test description", "status": "Open", "createdAt": "2024-01-15T10:00:00Z"}'

# ✅ User ID válido
curl -X POST http://localhost:9005/api/support-tickets \
  -H "Content-Type: application/json" \
  -d '{"userId": 20, "supportUserId": 16, "subject": "Test Ticket", "description": "Test description", "status": "Open", "createdAt": "2024-01-15T10:00:00Z"}'
```

## 🧪 Testing y Calidad de Código

### Framework de Testing
- ✅ **JUnit 5 (Jupiter)** - Framework principal de testing
- ✅ **Mockito** - Para mocking y testing unitario
- ✅ **Spring Boot Test** - Integración con Spring Boot
- ✅ **MockMvc** - Para testing de controladores REST
- ✅ **AssertJ** - Assertions fluidas

### Tipos de Pruebas Implementadas

#### 1. Pruebas Unitarias (Español)
- **Servicios**: Lógica de negocio con mocks
- **Controladores**: Endpoints REST con MockMvc
- **Mappers**: Conversiones Entity ↔ DTO
- **Nomenclatura**: Métodos con nombres descriptivos en español
- **Comentarios**: Given/Dado, When/Cuando, Then/Entonces

#### 2. Pruebas de Integración FeignClient (NUEVO) ✨
- **ms-grades**: `FeignClientIntegrationTest` - UserClient, CourseClient (7 pruebas)
- **ms-payments**: `PaymentsFeignClientIntegrationTest` - UserClient, CourseClient, EnrollmentClient (7 pruebas)  
- **ms-support**: `SupportFeignClientIntegrationTest` - UserClient, CourseClient, GradeClient (7 pruebas)
- **ms-courses**: `CoursesFeignClientIntegrationTest` - UserClient (7 pruebas)
- **Total**: 28 pruebas de integración con servicios reales
- **Requisito**: Microservicios ejecutándose en puertos 9001-9005
- **Validaciones**: Comunicación real, performance < 5s, manejo de errores
- **Textos**: Completamente en español (Given/Dado, When/Cuando, Then/Entonces)

### Ejecutar Pruebas

```bash
# Compilar proyecto completo
mvn clean compile -T 4

# Compilar pruebas
mvn test-compile -T 4

# Ejecutar todas las pruebas unitarias
mvn test

# Levantar servicios
run-all.bat

# Ejecutar pruebas de integración FeignClient (requiere servicios activos)
test-feign-integration.bat

# Verificar que servicios estén activos antes de integración
netstat -an | findstr :900

# Pruebas de un módulo específico
cd ms-users && mvn test
cd ms-courses && mvn test
cd ms-grades && mvn test
cd ms-payments && mvn test
cd ms-support && mvn test

# Scripts de pruebas automatizadas
test-runner.bat
test-integration.bat
```

### Casos de Prueba Importantes

- **Mappers manuales**: Conversión bidireccional Entity ↔ DTO
- **Servicios**: Lógica de negocio y validaciones
- **Repositorios**: Operaciones CRUD en base de datos  
- **Controladores**: Endpoints REST y respuestas HATEOAS
- **Clientes Feign**: Comunicación entre microservicios (integración real)
- **Validaciones cruzadas**: Integridad referencial distribuida
- **Performance**: Tiempos de respuesta aceptables

## 📖 Documentación API con Swagger/OpenAPI

### SpringDoc OpenAPI 3
Todos los microservicios implementan documentación automática con **SpringDoc OpenAPI**:

#### URLs de Swagger UI
- **ms-users**: http://localhost:9001/swagger-ui/index.html
- **ms-courses**: http://localhost:9002/swagger-ui/index.html  
- **ms-grades**: http://localhost:9003/swagger-ui/index.html
- **ms-payments**: http://localhost:9004/swagger-ui/index.html
- **ms-support**: http://localhost:9005/swagger-ui/index.html

#### Características de la Documentación
- ✅ **Documentación en Español** - Todas las descripciones en español
- ✅ **Ejemplos de Request/Response** - Para cada endpoint
- ✅ **Validaciones Documentadas** - Constraints y reglas de negocio
- ✅ **Esquemas de DTOs** - Estructuras de datos completas
- ✅ **Códigos de Respuesta** - HTTP status codes documentados

#### Configuración OpenAPI
```java
@Tag(name = "Cursos", description = "API para gestión de cursos de la plataforma")
@Operation(summary = "Obtener todos los cursos", 
          description = "Retorna una lista de todos los cursos disponibles")
```

### URLs de OpenAPI JSON
- **ms-users**: http://localhost:9001/v3/api-docs
- **ms-courses**: http://localhost:9002/v3/api-docs
- **ms-grades**: http://localhost:9003/v3/api-docs
- **ms-payments**: http://localhost:9004/v3/api-docs  
- **ms-support**: http://localhost:9005/v3/api-docs

## 🔗 HATEOAS (Hypermedia as the Engine of Application State)

### Implementación con Spring HATEOAS
Todos los controladores REST implementan **HATEOAS** para navegabilidad de APIs:

#### Características HATEOAS
- ✅ **EntityModel<T>** - Wrapper para recursos individuales
- ✅ **CollectionModel<T>** - Wrapper para colecciones
- ✅ **Enlaces de navegación** - Self, related, CRUD operations
- ✅ **Hipermedia automática** - Links generados dinámicamente

#### Ejemplo de Respuesta HATEOAS
```json
{
  "id": 1,
  "title": "Introducción a Java",
  "description": "Curso básico de Java",
  "_links": {
    "self": {
      "href": "http://localhost:9002/api/courses/1"
    },
    "courses": {
      "href": "http://localhost:9002/api/courses"
    },
    "update": {
      "href": "http://localhost:9002/api/courses/1"
    },
    "delete": {
      "href": "http://localhost:9002/api/courses/1"
    }
  }
}
```

#### Controllers con HATEOAS Implementado
- ✅ **ms-grades**: Todos los controladores (CourseQuiz, StudentMark, QuizResponse, etc.)
- ✅ **ms-courses**: Todos los controladores (Course, CourseCategory, CourseContent, etc.)
- ✅ **ms-users**: UserController, RoleController
- ✅ **ms-payments**: PaymentController, DiscountCouponController
- ✅ **ms-support**: SupportTicketController

#### Navegación por Enlaces
```bash
# Obtener recurso con enlaces
curl http://localhost:9002/api/courses/1

# Seguir enlace "courses" para obtener todos los cursos
curl http://localhost:9002/api/courses

# Seguir enlace "update" para actualizar
curl -X PUT http://localhost:9002/api/courses/1 -d '{...}'
```

**� El proyecto está operacional con arquitectura de microservicios completa y listo para desarrollo continuo.**