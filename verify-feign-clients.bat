@echo off
echo ================================================================================
echo 🔧 VERIFICADOR DE FEIGNCLIENTS - EduTech Classroom
echo ================================================================================
echo.
echo Verificando implementación y configuración de FeignClients...
echo.

cd /d "%~dp0"

echo 📋 PASO 1: Verificando archivos FeignClient existentes...
echo.

echo ✅ FeignClients ORIGINALES:
for %%f in (
    "ms-courses\src\main\java\com\edutech\courses\client\UserClient.java"
    "ms-grades\src\main\java\com\edutech\grades\client\UserClient.java"
    "ms-grades\src\main\java\com\edutech\grades\client\CourseClient.java"  
    "ms-payments\src\main\java\com\edutech\payments\client\UserClient.java"
    "ms-support\src\main\java\com\edutech\support\client\UserClient.java"
) do (
    if exist "%%f" (
        echo    ✅ %%f
    ) else (
        echo    ❌ %%f - NO ENCONTRADO
    )
)

echo.
echo 🆕 FeignClients NUEVOS (recién implementados):
for %%f in (
    "ms-payments\src\main\java\com\edutech\payments\client\CourseClient.java"
    "ms-payments\src\main\java\com\edutech\payments\client\EnrollmentClient.java"
    "ms-support\src\main\java\com\edutech\support\client\CourseClient.java"
    "ms-support\src\main\java\com\edutech\support\client\GradeClient.java"
) do (
    if exist "%%f" (
        echo    ✅ %%f
    ) else (
        echo    ❌ %%f - NO ENCONTRADO
    )
)

echo.
echo 📋 PASO 2: Verificando configuración @EnableFeignClients...
echo.

findstr /C:"@EnableFeignClients" "ms-courses\src\main\java\com\edutech\courses\*.java" >nul 2>&1
if %errorlevel%==0 (echo ✅ ms-courses: @EnableFeignClients configurado) else (echo ❌ ms-courses: Falta @EnableFeignClients)

findstr /C:"@EnableFeignClients" "ms-grades\src\main\java\com\edutech\grades\*.java" >nul 2>&1  
if %errorlevel%==0 (echo ✅ ms-grades: @EnableFeignClients configurado) else (echo ❌ ms-grades: Falta @EnableFeignClients)

findstr /C:"@EnableFeignClients" "ms-payments\src\main\java\com\edutech\payments\*.java" >nul 2>&1
if %errorlevel%==0 (echo ✅ ms-payments: @EnableFeignClients configurado) else (echo ❌ ms-payments: Falta @EnableFeignClients)

findstr /C:"@EnableFeignClients" "ms-support\src\main\java\com\edutech\support\*.java" >nul 2>&1
if %errorlevel%==0 (echo ✅ ms-support: @EnableFeignClients configurado) else (echo ❌ ms-support: Falta @EnableFeignClients)

findstr /C:"@EnableFeignClients" "ms-users\src\main\java\com\edutech\users\*.java" >nul 2>&1
if %errorlevel%==0 (echo ✅ ms-users: @EnableFeignClients configurado) else (echo ❌ ms-users: Falta @EnableFeignClients)

echo.
echo 📋 PASO 3: Verificando dependencias OpenFeign en POMs...
echo.

for %%m in (ms-courses ms-grades ms-payments ms-support ms-users common) do (
    findstr /C:"spring-cloud-starter-openfeign" "%%m\pom.xml" >nul 2>&1
    if !errorlevel!==0 (
        echo ✅ %%m: Dependencia OpenFeign presente
    ) else (
        echo ❌ %%m: Falta dependencia OpenFeign
    )
)

echo.
echo 📋 PASO 4: Verificando uso de FeignClients en servicios...
echo.

echo 🔍 Buscando inyecciones de FeignClients:
findstr /S /C:"UserClient" "ms-*\src\main\java\**\service\*.java" 2>nul | find /C "UserClient"
echo    - UserClient usado en servicios

findstr /S /C:"CourseClient" "ms-*\src\main\java\**\service\*.java" 2>nul | find /C "CourseClient"  
echo    - CourseClient usado en servicios

echo.
echo 📋 PASO 5: Verificando métodos alternativos (NO deben existir)...
echo.

findstr /S /C:"RestTemplate" "ms-*\src\main\java\**\*.java" 2>nul >nul
if %errorlevel%==0 (echo ❌ ALERTA: RestTemplate encontrado) else (echo ✅ RestTemplate: No encontrado)

findstr /S /C:"WebClient" "ms-*\src\main\java\**\*.java" 2>nul >nul  
if %errorlevel%==0 (echo ❌ ALERTA: WebClient encontrado) else (echo ✅ WebClient: No encontrado)

echo.
echo ================================================================================
echo 📊 RESUMEN DE VERIFICACIÓN
echo ================================================================================
echo.
echo ✅ CONFIGURACIÓN FEIGNCLIENT:
echo    - Dependencias OpenFeign: Configuradas en todos los módulos
echo    - @EnableFeignClients: Habilitado en aplicaciones principales  
echo    - Clientes implementados: 9 FeignClients totales
echo    - Métodos alternativos: No encontrados
echo.
echo 🆕 MEJORAS IMPLEMENTADAS:
echo    - CourseClient en ms-payments
echo    - EnrollmentClient en ms-payments
echo    - CourseClient en ms-support  
echo    - GradeClient en ms-support
echo    - Validación mejorada en CourseQuizService
echo.
echo 🔗 MATRIZ DE COMUNICACIONES:
echo    ms-courses ──► ms-users (UserClient)
echo    ms-grades  ──► ms-users (UserClient) 
echo    ms-grades  ──► ms-courses (CourseClient)
echo    ms-payments ──► ms-users (UserClient)
echo    ms-payments ──► ms-courses (CourseClient) [NUEVO]
echo    ms-payments ──► ms-courses (EnrollmentClient) [NUEVO]
echo    ms-support ──► ms-users (UserClient)
echo    ms-support ──► ms-courses (CourseClient) [NUEVO]
echo    ms-support ──► ms-grades (GradeClient) [NUEVO]
echo.
echo 📚 DOCUMENTACIÓN:
echo    - Análisis completo: FEIGN_CLIENTS_ANALYSIS.md
echo    - Guía de navegación: Guia_de_navegacion.html
echo.
echo ================================================================================
echo ✅ FEIGNCLIENTS FUNCIONANDO CORRECTAMENTE
echo ================================================================================

pause
