# Second Wind - Microservicios EduTech

## 🎯 Descripción del Proyecto

**Second Wind** es la migración exitosa del monolito "Monolitico" hacia una arquitectura de microservicios moderna, escalable y mantenible. El proyecto implementa un sistema educativo completo con separación de responsabilidades, comunicación entre servicios, validaciones cruzadas y documentación interactiva.

## 🏗️ Arquitectura de Microservicios

### Servicios Implementados

| Microservicio | Puerto | Responsabilidad | Validaciones Cruzadas |
|---------------|--------|-----------------|----------------------|
| **ms-users**    | 9001   | Gestión de usuarios y roles | - |
| **ms-courses**  | 9002   | Gestión de cursos y categorías | ✅ Usuarios |
| **ms-grades**   | 9003   | Gestión de inscripciones y calificaciones | ✅ Usuarios + Cursos |
| **ms-payments** | 9004   | Gestión de pagos y cupones de descuento | ✅ Usuarios |
| **ms-support**  | 9005   | Gestión de tickets de soporte técnico | ✅ Usuarios |

### Características Técnicas

- ✅ **Arquitectura de Microservicios** independientes
- ✅ **Spring Boot 3.x** con Java 17+
- ✅ **MySQL** como base de datos compartida
- ✅ **Feign Client** para comunicación entre servicios (sin Eureka)
- ✅ **Validaciones cruzadas** en tiempo real
- ✅ **HATEOAS** para navegabilidad de APIs
- ✅ **Swagger/OpenAPI** para documentación interactiva
- ✅ **POJOs manuales** para máxima compatibilidad
- ✅ **Mappers manuales** sin dependencias externas
- ✅ **Pruebas unitarias** con JUnit y Mockito

## 🚀 Ejecución del Proyecto

### Ejecución Completa con Scripts

```bash
# Ejecutar todos los microservicios de una vez
run-all.bat

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

### Endpoints Principales

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

## 🧪 Pruebas

### Ejecutar Pruebas Unitarias

```bash
# Pruebas de todos los módulos
mvn test

# Pruebas de un módulo específico
cd ms-users && mvn test
cd ms-courses && mvn test
cd ms-grades && mvn test
cd ms-payments && mvn test
cd ms-support && mvn test

# Ejecutar script de pruebas de integración
test-integration.bat
```

### Casos de Prueba Importantes

- **Mappers manuales**: Conversión bidireccional Entity ↔ DTO
- **Servicios**: Lógica de negocio y validaciones
- **Repositorios**: Operaciones CRUD en base de datos
- **Controladores**: Endpoints REST y respuestas HATEOAS
- **Clientes Feign**: Comunicación entre microservicios
- **Validaciones cruzadas**: Integridad referencial distribuida

## 🛠️ Comandos de Mantenimiento

### Compilación y Limpieza

```bash
# Compilar sólo el POM del padre
mvn install -N

# Limpiar carpetas target
mvn clean

# Eliminar caché de Maven
rmdir /S /Q %USERPROFILE%\.m2

# Compilar proyecto completo
mvn clean install

# Compilar sin pruebas
mvn clean install -DskipTests
```

### Base de Datos

```bash
# Crear base de datos (ejecutar create-db.sql)
mysql -u root -p < create-db.sql

# Las tablas se crean automáticamente con ddl-auto: update
# Los datos de prueba se insertan automáticamente
```

## 📁 Estructura del Proyecto

```
second-wind/
├── common/                 # Módulo común (DTOs, excepciones, utilidades)
├── ms-users/              # Microservicio de usuarios y roles
├── ms-courses/            # Microservicio de cursos y categorías  
├── ms-grades/             # Microservicio de inscripciones y calificaciones
├── ms-payments/           # Microservicio de pagos y cupones
├── ms-support/            # Microservicio de soporte técnico
├── eureka/                # Servidor Eureka (no utilizado)
├── Monolitico/           # Monolito original (migrado)
├── run-all.bat           # Ejecutar todos los servicios
├── run-*.bat             # Scripts individuales por servicio
├── test-integration.bat  # Pruebas de integración
├── create-db.sql         # Script de base de datos
├── pom.xml               # POM padre con configuración compartida
└── README.md             # Este archivo
```

## 🔧 Configuración Técnica

### Puertos de Servicio

- **ms-users**: 9001
- **ms-courses**: 9002
- **ms-grades**: 9003
- **ms-payments**: 9004
- **ms-support**: 9005
- **eureka**: 8761 (deshabilitado)

### Base de Datos

- **Motor**: MySQL 8.0+
- **Base de datos**: `edutech`
- **Usuario**: `root`
- **Contraseña**: (vacía por defecto)
- **URL**: `jdbc:mysql://localhost:3306/edutech`
- **DDL**: `update` (creación automática de tablas)

### Dependencias Principales

- **Spring Boot**: 3.2.x
- **Spring Data JPA**: Para persistencia
- **Spring Web**: Para APIs REST
- **Spring HATEOAS**: Para navegabilidad
- **Spring Cloud OpenFeign**: Para comunicación entre servicios
- **SpringDoc OpenAPI**: Para documentación Swagger
- **MySQL Connector**: Para base de datos
- **JUnit 5**: Para pruebas unitarias
- **Mockito**: Para mocking en pruebas

## 🎯 Logros de la Migración

### ✅ Migración Completada

- **Monolito dividido** en 5 microservicios independientes
- **Arquitectura limpia** con separación de responsabilidades
- **Comunicación distribuida** vía Feign Client
- **Validaciones cruzadas** funcionando en tiempo real
- **Documentación interactiva** con Swagger
- **Navegabilidad HATEOAS** en todos los endpoints
- **Compatibilidad máxima** sin dependencias conflictivas

### ✅ Problemas Resueltos

- **Conflictos de Lombok/MapStruct**: Eliminados con POJOs y mappers manuales
- **Errores de annotation processors**: Resueltos completamente
- **Dependencias circulares**: Eliminadas con arquitectura modular
- **Problemas de compilación**: Solucionados al 100%
- **Configuración de Eureka**: Simplificado con URLs directas

### ✅ Calidad y Robustez

- **Código limpio** sin anotaciones problemáticas
- **Mappers explícitos** y mantenibles
- **Configuración clara** y documentada
- **Pruebas comprehensivas** en todos los niveles
- **Documentación completa** para mantenimiento futuro

## 🚀 Siguiente Fase (Opcional)

Para futuras mejoras, se puede considerar:

1. **Containerización** con Docker
2. **Orchestración** con Kubernetes
3. **API Gateway** con Spring Cloud Gateway
4. **Circuit Breakers** con Resilience4j
5. **Monitoreo** con Actuator y Prometheus
6. **Logging centralizado** con ELK Stack
7. **Configuración centralizada** con Spring Cloud Config
8. **Seguridad** con Spring Security y JWT

---

## 📞 Contacto y Soporte

Para consultas sobre la implementación o mejoras futuras, consultar la documentación técnica incluida en cada módulo y los comentarios en el código fuente.

**¡Migración exitosa completada! 🎉**

