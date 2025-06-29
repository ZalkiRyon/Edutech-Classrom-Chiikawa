# Chiikawa SPA - Microservicios EduTech

## 🎯 Descripción del Proyecto

**Chiikawa SPA** realizó la migración exitosa del monolito "Monolitico" hacia una arquitectura de microservicios moderna, escalable y mantenible. El proyecto implementa un sistema educativo completo con separación de responsabilidades, comunicación entre servicios, validaciones cruzadas y documentación interactiva.

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
- ✅ **Spring Boot 3.2.0** con Java 17+
- ✅ **MySQL** como base de datos compartida (levantada localmente con Laragon y gestionada con HeidiSQL)
- ✅ **Feign Client** para comunicación entre servicios (con Eureka)
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

