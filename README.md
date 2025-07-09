# EduTech Classroom - Arquitectura de Microservicios

## 🎯 Descripción del Proyecto

**EduTech Classroom** es una plataforma educativa moderna basada en **arquitectura de microservicios**, desarrollada por Chiikawa SPA. El proyecto implementa un sistema educativo completo con separación de responsabilidades, comunicación entre servicios mediante **FeignClients**, validaciones cruzadas, **HATEOAS para navegabilidad hipermedia** y documentación interactiva con **Swagger/OpenAPI**.

## 📋 Estado Actual del Proyecto

- **✅ Arquitectura de microservicios** con 5 servicios principales + Eureka
- **✅ FeignClients implementados** para comunicación inter-servicios
- **✅ Validaciones cruzadas** en tiempo real entre microservicios
- **✅ HATEOAS completo** con navegabilidad hipermedia (JSON HAL)
- **✅ Swagger/OpenAPI 3.0** documentación interactiva en todos los servicios
- **✅ Spring Boot 3.2.0** con Java 21 compatible
- **✅ Compilación exitosa** del proyecto completo y pruebas
- **✅ Base de datos MySQL** configurada y funcional
- **✅ Scripts de automatización** para ejecución

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
- ✅ **FeignClient** para comunicación entre servicios
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
Edutech-Classrom-Chiikawa/
├── common/                # Módulo común (DTOs, excepciones, utilidades)
├── eureka/                    # Servidor Eureka
├── ms-users/                # Microservicio de usuarios y roles
├── ms-courses/            # Microservicio de cursos y categorías  
├── ms-grades/              # Microservicio de inscripciones y calificaciones
├── ms-payments/         # Microservicio de pagos y cupones
├── ms-support/             # Microservicio de soporte técnico
├──compile-bat               # Compilar todos los servicios
├──compile-bat               # Compilar todos los servicios
├──Guia_de_Navegacion # Navegación interactiva del proyecto via página HTML
├──install-bat                  # Instalar dependencias del proyecto
├── run-all.bat                 # Ejecutar todos los servicios en consolas independientes.
├── run-*.bat                   # Scripts individuales por servicio
├── create-db.sql             # Script de base de datos
├── pom.xml                    # POM padre con configuración compartida
└── README.md              # Este archivo
```
### Endpoints Principales

#### ms-users (Puerto 9001)
```
User
GET    /api/users              # Listar usuarios
POST   /api/users              # Crear usuario
GET    /api/users/{id}         # Obtener usuario
PUT    /api/users/{id}         # Actualizar usuario
DELETE /api/users/{id}       # Eliminar usuario

Role
GET    /api/roles               # Listar roles
POST   /api/roles              # Crear rol
GET    /api/roles/{id}         # Obtener rol
PUT    /api/roles/{id}         # Actualizar rol
DELETE /api/roles/{id}       # Eliminar rol
```
#### ms-courses (Puerto 9002)
```
Course
GET    /api/courses               # Listar cursos
POST   /api/courses              # Crear curso
GET    /api/courses/{id}         # Obtener curso
PUT    /api/courses/{id}         # Actualizar curso
DELETE /api/courses/{id}       # Eliminar curso

CourseCategory
GET    /api/course-categories           # Listar categorías de cursos
POST   /api/course-categories          # Crear categoría de curso
GET    /api/course-categories/{id}     # Obtener categoría de curso
PUT    /api/course-categories/{id}     # Actualizar categoría de curso
DELETE /api/course-categories/{id}   # Eliminar categoría de curso

CourseComment
GET    /api/course-comment               # Listar comentarios de cursos
POST   /api/course-comment               # Crear comentario en curso
GET    /api/course-comment/{id}          # Obtener comentario de curso
PUT    /api/course-comment/{id}          # Actualizar comentario
DELETE /api/course-comment/{id}          # Eliminar comentario

CourseContent
GET    /api/course-content               # Listar contenidos
POST   /api/course-content               # Crear contenido
GET    /api/course-content/{id}          # Obtener contenido
PUT    /api/course-content/{id}          # Actualizar contenido
DELETE /api/course-content/{id}          # Eliminar contenido

Enrollment
GET    /api/enrollments                                  # Listar inscripciones
POST   /api/enrollments                                 # Crear inscripción
GET    /api/enrollments/{id}                            # Obtener inscripción
PUT    /api/enrollments/{id}                            # Actualizar inscripción
DELETE /api/enrollments/{id}                          # Eliminar inscripción
GET    /api/enrollments/student/{studentId}   # Inscripciones por estudiante
GET    /api/enrollments/course/{courseId}      # Inscripciones por curso
```

#### ms-grades (Puerto 9003)
```
CourseQuiz
GET    /api/course-quiz                                  # Listar pruebas de cursos
POST   /api/course-quiz                                 # Crear pruebas para cursos
GET    /api/course-quiz/{id}                            # Obtener pruebas de un curso
PUT    /api/course-quiz/{id}                            # Actualizar pruebas de un curso
DELETE /api/course-quiz/{id}                          # Eliminar pruebas de un curso

CourseQuizQuestion
GET    /api/course-quiz-question                                  # Listar preguntas de pruebas
POST   /api/course-quiz-question                                 # Crear preguntas para pruebas
GET    /api/course-quiz-question/{id}                            # Obtener preguntas de una prueba
PUT    /api/course-quiz-question/{id}                            # Actualizar preguntas de una prueba
DELETE /api/course-quiz-question/{id}                          # Eliminar preguntas de una prueba

QuizResponse
GET    /api/quiz-response                                # Listar respuestas de pruebas
POST   /api/quiz-response                               # Crear respuestas para pruebas
GET    /api/quiz-response/{id}                          # Obtener respuestas de una prueba
PUT    /api/quiz-response/{id}                          # Actualizar respuestas de una prueba
DELETE /api/quiz-response/{id}                        # Eliminar respuestas de una prueba

StudentMark
GET    /api/student-marks                            # Listar calificaciones de estudiantes
POST   /api/student-marks                           # Crear calificaciones para estudiantes
GET    /api/student-marks/{id}                      # Obtener calificaciones de un estudiante
PUT    /api/student-marks/{id}                      # Actualizar calificaciones de un estudiante
DELETE /api/student-marks/{id}                    # Eliminar calificaciones de un estudiante

```

#### ms-payments (Puerto 9004)
```
Payment
GET    /api/payments                           # Listar pagos
POST   /api/payments                          # Crear pago (valida usuario)
GET    /api/payments/{id}                     # Obtener pago
PUT    /api/payments/{id}                     # Actualizar pago
DELETE /api/payments/{id}                   # Eliminar pago
GET    /api/payments/user/{userId}       # Pagos por usuario

DiscountCoupon
GET    /api/discount-coupons                # Listar cupones
POST   /api/discount-coupons               # Crear cupón
GET    /api/discount-coupons/{id}          # Obtener cupón
PUT    /api/discount-coupons/{id}          # Actualizar cupón
DELETE /api/discount-coupons/{id}        # Eliminar cupón
```

#### ms-support (Puerto 9005)
```
SupportTicket
GET    /api/support-tickets                                    # Listar tickets
POST   /api/support-tickets                                   # Crear ticket 
GET    /api/support-tickets/{id}                              # Obtener ticket
PUT    /api/support-tickets/{id}                              # Actualizar ticket
DELETE /api/support-tickets/{id}                            # Eliminar ticket
GET    /api/support-tickets/user/{userId}                # Tickets por usuario
GET    /api/support-tickets/support-user/{userId}  # Tickets por agente
GET    /api/support-tickets/status/{status}              # Tickets por estado
```


## 🧪 Testing y Calidad de Código

### Framework de Testing
- ✅ **JUnit 5 (Jupiter)** - Framework principal de testing
- ✅ **Mockito** - Para mocking y testing unitario
- ✅ **Spring Boot Test** - Integración con Spring Boot
- ✅ **MockMvc** - Para testing de controladores REST
- ✅ **AssertJ** - Assertions fluidas

### Tipos de Pruebas Implementadas

#### Pruebas Unitarias
- **Servicios**: Lógica de negocio con mocks
- **Controladores**: Endpoints REST con MockMvc
- **Mappers**: Conversiones Entity ↔ DTO
- **Nomenclatura**: Métodos con nombres descriptivos
- **Comentarios**: Given/Dado, When/Cuando, Then/Entonces


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

# Pruebas de un módulo específico
cd ms-users && mvn test
cd ms-courses && mvn test
cd ms-grades && mvn test
cd ms-payments && mvn test
cd ms-support && mvn test

# Ejecutar solo pruebas unitarias de servicios
mvn test -Dtest="*ServiceTest"

# Ejecutar solo pruebas de integración
mvn test -Dtest="*IntegrationTest"
```
### Casos de Prueba Importantes

- **Mappers manuales**: Conversión bidireccional Entity ↔ DTO
- **Servicios**: Lógica de negocio y validaciones
- **Repositorios**: Operaciones CRUD en base de datos  
- **Controladores**: Endpoints REST y respuestas HATEOAS
- **Clientes Feign**: Comunicación entre microservicios (integración real)
- **Validaciones cruzadas**: Integridad referencial distribuida
- **Performance**: Tiempos de respuesta aceptables
## ⚙️ Configuración de Base de Datos

> **Nota:** El formato de fecha esperado en los JSON es `YYYY-MM-DD` (por ejemplo, `"2025-07-07"`).

### Configuración Real del Proyecto

El proyecto utiliza **MySQL** como base de datos principal con la siguiente configuración:

#### Base de Datos
- **Nombre de BD:** `edutech`
- **Puerto:** `3306` (MySQL estándar)
- **Charset:** `utf8mb4` con collation `utf8mb4_0900_ai_ci`

#### Configuración en `application.yml` (ejemplo real del proyecto):

```yaml
spring:
  application:
    name: ms-courses  # Varía según el microservicio
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/edutech
    username: root
    password: ""  # Sin contraseña en desarrollo local
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQLDialect
  cloud:
    discovery:
      enabled: true
server:
  port: 9002  # Puerto específico por microservicio

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    fetch-registry: true
    register-with-eureka: true
  instance:
    prefer-ip-address: true

logging:
  level:
    com.edutech: DEBUG
    org.springframework.cloud.openfeign: DEBUG
```

#### Puertos por Microservicio
- **ms-users**: 9001
- **ms-courses**: 9002  
- **ms-grades**: 9003
- **ms-payments**: 9004
- **ms-support**: 9005
- **eureka**: 8761

#### Configuración de Eureka (Discovery Service)
```yaml
# eureka/src/main/resources/application.yml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://localhost:8761/eureka/
  server:
    enable-self-preservation: false
```

#### Script de Base de Datos
El proyecto incluye `create-db.sql` que crea:
- Base de datos `edutech` con charset UTF-8
- Tablas principales: `role`, `user`, `course`, `course_category`, etc.
- Datos de prueba iniciales

**Nota:** Todos los microservicios comparten la misma base de datos `edutech` en el entorno de desarrollo para simplificar la configuración local.


## 📖 Documentación API con Swagger/OpenAPI

### SpringDoc OpenAPI 3
Todos los microservicios implementan documentación automática con **SpringDoc OpenAPI**:

#### URLs de Swagger UI

- **ms-users**: http://localhost:9001/swagger-ui/index.html
- **ms-courses**: http://localhost:9002/swagger-ui/index.html  
- **ms-grades**: http://localhost:9003/swagger-ui/index.html
- **ms-payments**: http://localhost:9004/swagger-ui/index.html
- **ms-support**: http://localhost:9005/swagger-ui/index.html
- **eureka**: http://localhost:8761

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
curl -X PUT http://localhost:9002/api/courses/1 -H "Content-Type: application/json" -d '{...}'
```

**El proyecto está operacional con arquitectura de microservicios completa y listo para desarrollo continuo.**