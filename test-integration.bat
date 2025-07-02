@echo off
echo ======================================
echo    PRUEBAS DE INTEGRACION ENDPOINTS
echo ======================================
echo.

echo Iniciando microservicios...
echo.

echo === Paso 1: Iniciar Eureka Server ===
start "Eureka Server" cmd /k "cd /d 'C:\Edutech-Classrom-Chiikawa\eureka' && mvn spring-boot:run"
echo Esperando 30 segundos para que Eureka inicie completamente...
timeout /t 30 /nobreak > nul

echo.
echo === Paso 2: Iniciar ms-users ===
start "MS Users" cmd /k "cd /d 'C:\Edutech-Classrom-Chiikawa\ms-users' && mvn spring-boot:run"
echo Esperando 20 segundos para que ms-users inicie...
timeout /t 20 /nobreak > nul

echo.
echo === Paso 3: Iniciar ms-courses ===
start "MS Courses" cmd /k "cd /d 'C:\Edutech-Classrom-Chiikawa\ms-courses' && mvn spring-boot:run"
echo Esperando 20 segundos para que ms-courses inicie...
timeout /t 20 /nobreak > nul

echo.
echo === Paso 4: Pruebas de endpoints ===
echo.

echo *** Probando endpoint de usuarios ***
curl -X GET "http://localhost:8081/api/users" -H "Accept: application/json"
echo.

echo *** Probando endpoint de cursos ***
curl -X GET "http://localhost:8082/api/courses" -H "Accept: application/json"
echo.

echo *** Probando documentacion Swagger de usuarios ***
echo Abrir en navegador: http://localhost:8081/swagger-ui.html
echo.

echo *** Probando documentacion Swagger de cursos ***
echo Abrir en navegador: http://localhost:8082/swagger-ui.html
echo.

echo *** Probando descubrimiento de servicios en Eureka ***
echo Abrir en navegador: http://localhost:8761
echo.

echo ======================================
echo    PRUEBAS COMPLETADAS
echo ======================================
echo.
echo NOTA: Los microservicios siguen ejecutandose en ventanas separadas.
echo Cierre las ventanas cuando termine las pruebas.
echo.
pause
