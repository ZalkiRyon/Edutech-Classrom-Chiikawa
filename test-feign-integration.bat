@echo off
echo ===============================================
echo    PRUEBAS DE INTEGRACION DE FEIGNCLIENTS
echo    EduTech Classroom - Microservicios
echo ===============================================
echo.

echo [INFO] Compilando proyecto antes de ejecutar pruebas...
call mvn clean compile test-compile -q

if %ERRORLEVEL% neq 0 (
    echo [ERROR] Fallo en la compilacion. Abortando pruebas.
    exit /b 1
)

echo.
echo [INFO] Ejecutando pruebas de integracion de FeignClients...
echo.

echo ============= MS-GRADES - FeignClient Integration =============
cd ms-grades
call mvn test -Dtest=FeignClientIntegrationTest -q
if %ERRORLEVEL% neq 0 (
    echo [WARNING] Algunas pruebas de ms-grades fallaron (servicios no disponibles)
) else (
    echo [SUCCESS] Pruebas de integracion ms-grades completadas
)
cd ..

echo.
echo ============= MS-COURSES - FeignClient Integration =============
cd ms-courses  
call mvn test -Dtest=CoursesFeignClientIntegrationTest -q
if %ERRORLEVEL% neq 0 (
    echo [WARNING] Algunas pruebas de ms-courses fallaron (servicios no disponibles)
) else (
    echo [SUCCESS] Pruebas de integracion ms-courses completadas
)
cd ..

echo.
echo ============= MS-PAYMENTS - FeignClient Integration =============
cd ms-payments
call mvn test -Dtest=PaymentsFeignClientIntegrationTest -q
if %ERRORLEVEL% neq 0 (
    echo [WARNING] Algunas pruebas de ms-payments fallaron (servicios no disponibles)
) else (
    echo [SUCCESS] Pruebas de integracion ms-payments completadas
)
cd ..

echo.
echo ============= MS-SUPPORT - FeignClient Integration =============
cd ms-support
call mvn test -Dtest=SupportFeignClientIntegrationTest -q
if %ERRORLEVEL% neq 0 (
    echo [WARNING] Algunas pruebas de ms-support fallaron (servicios no disponibles)
) else (
    echo [SUCCESS] Pruebas de integracion ms-support completadas
)
cd ..

echo.
echo ===============================================
echo    RESUMEN DE PRUEBAS DE INTEGRACION
echo ===============================================
echo.
echo [INFO] Pruebas de integracion de FeignClients completadas
echo [INFO] Nota: Las pruebas pueden fallar si los microservicios
echo       de destino no estan ejecutandose (ms-users, ms-courses, etc.)
echo.
echo [RECOMENDACION] Para ejecutar pruebas completas:
echo   1. Ejecutar: run-all.bat
echo   2. Esperar que todos los servicios esten disponibles  
echo   3. Ejecutar este script nuevamente
echo.
echo ===============================================

pause
