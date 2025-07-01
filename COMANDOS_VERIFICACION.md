# 🎯 COMANDOS DE VERIFICACIÓN - PROYECTO EDUTECH

## Verificación Completa Automática

```bash
# Comando principal para verificar TODO el proyecto
cd "c:\Users\sebas\Desktop\second wind"
verificacion-final.bat
```

## Verificación Manual por Módulos

### 1. Verificar ms-users
```bash
cd "c:\Users\sebas\Desktop\second wind\ms-users"
mvn clean test -q
```

### 2. Verificar ms-grades  
```bash
cd "c:\Users\sebas\Desktop\second wind\ms-grades"
mvn clean test -q
```

### 3. Verificar ms-courses
```bash
cd "c:\Users\sebas\Desktop\second wind\ms-courses"
mvn clean test -q
```

### 4. Verificar ms-payments
```bash
cd "c:\Users\sebas\Desktop\second wind\ms-payments"
mvn clean test -q
```

### 5. Verificar ms-support
```bash
cd "c:\Users\sebas\Desktop\second wind\ms-support"
mvn clean test -q
```

### 6. Verificar common
```bash
cd "c:\Users\sebas\Desktop\second wind\common"
mvn clean compile -q
```

## Scripts Adicionales Disponibles

### Verificación Rápida de Contextos
```bash
cd "c:\Users\sebas\Desktop\second wind"
quick-test.bat
```

### Suite Completa con Reportes
```bash
cd "c:\Users\sebas\Desktop\second wind"
run-all-tests.bat
```

### Pruebas Específicas
```bash
# Solo ms-users
cd "c:\Users\sebas\Desktop\second wind"
test-users.bat

# Solo un test específico
cd "c:\Users\sebas\Desktop\second wind\ms-grades"
mvn test -Dtest=StudentMarkServiceTest

# Solo controladores
cd "c:\Users\sebas\Desktop\second wind\ms-courses"
mvn test -Dtest=*ControllerTest
```

## Comandos de Ejecución de Aplicaciones

### Ejecutar Eureka (Primero)
```bash
cd "c:\Users\sebas\Desktop\second wind\eureka"
mvn spring-boot:run
```

### Ejecutar Microservicios Individuales
```bash
# ms-users (Puerto 9001)
cd "c:\Users\sebas\Desktop\second wind\ms-users"
mvn spring-boot:run

# ms-courses (Puerto 9002)
cd "c:\Users\sebas\Desktop\second wind\ms-courses"
mvn spring-boot:run

# ms-grades (Puerto 9003)
cd "c:\Users\sebas\Desktop\second wind\ms-grades"
mvn spring-boot:run

# ms-payments (Puerto 9004)
cd "c:\Users\sebas\Desktop\second wind\ms-payments"
mvn spring-boot:run

# ms-support (Puerto 9005)
cd "c:\Users\sebas\Desktop\second wind\ms-support"
mvn spring-boot:run
```

### Ejecutar Todos los Servicios (Scripts Automatizados)
```bash
cd "c:\Users\sebas\Desktop\second wind"

# Ejecutar eureka
run-eureka.bat

# Ejecutar todos los microservicios
run-all.bat
```

## URLs de Verificación (Cuando los servicios estén activos)

### Swagger UI
- ms-users: http://localhost:9001/swagger-ui/index.html
- ms-courses: http://localhost:9002/swagger-ui/index.html
- ms-grades: http://localhost:9003/swagger-ui/index.html
- ms-payments: http://localhost:9004/swagger-ui/index.html
- ms-support: http://localhost:9005/swagger-ui/index.html

### Eureka Dashboard
- http://localhost:8761

### Health Checks
```bash
curl http://localhost:9001/actuator/health
curl http://localhost:9002/actuator/health
curl http://localhost:9003/actuator/health
curl http://localhost:9004/actuator/health
curl http://localhost:9005/actuator/health
```

## Comandos de Troubleshooting

### Verificar Puertos Activos
```bash
netstat -an | findstr :900
netstat -an | findstr :8761
```

### Limpiar y Reinstalar
```bash
cd "c:\Users\sebas\Desktop\second wind"
mvn clean install -N
mvn clean compile
```

### Ver Logs Detallados
```bash
cd "c:\Users\sebas\Desktop\second wind\ms-grades"
mvn spring-boot:run --debug
```

---
**Nota**: Todos estos comandos están probados y verificados para funcionar correctamente en el proyecto EduTech.
