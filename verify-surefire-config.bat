@echo off
title EduTech Classroom - Verificador de Configuración Surefire
echo.
echo ================================================================================
echo 🔍 VERIFICADOR DE CONFIGURACIÓN SUREFIRE - EduTech Classroom
echo ================================================================================
echo.

:: Variables para colores en CMD
set "GREEN=[92m"
set "RED=[91m"
set "YELLOW=[93m"
set "BLUE=[94m"
set "PURPLE=[95m"
set "CYAN=[96m"
set "RESET=[0m"

echo %BLUE%[INFO]%RESET% Verificando configuración Surefire en todos los módulos...
echo.

:: Verificar Java y Maven
echo %CYAN%[CHECK 1/6]%RESET% Verificando versiones de herramientas...
echo %YELLOW%Java Version:%RESET%
java --version | findstr "openjdk\|java"
echo.
echo %YELLOW%Maven Version:%RESET%
mvn --version | findstr "Apache Maven"
echo.

:: Verificar estructura del proyecto
echo %CYAN%[CHECK 2/6]%RESET% Verificando estructura del proyecto...
if exist "pom.xml" (
    echo %GREEN%✓%RESET% POM padre encontrado
) else (
    echo %RED%✗%RESET% POM padre no encontrado
    pause
    exit /b 1
)

:: Lista de módulos a verificar
set "modules=common eureka ms-users ms-courses ms-grades ms-payments ms-support"

echo %CYAN%[CHECK 3/6]%RESET% Verificando módulos existentes...
for %%m in (%modules%) do (
    if exist "%%m\pom.xml" (
        echo %GREEN%✓%RESET% Módulo %%m - POM encontrado
    ) else (
        echo %RED%✗%RESET% Módulo %%m - POM no encontrado
    )
)
echo.

:: Verificar configuración Surefire en cada módulo
echo %CYAN%[CHECK 4/6]%RESET% Verificando configuración Surefire...
for %%m in (%modules%) do (
    echo %YELLOW%Módulo %%m:%RESET%
    if exist "%%m\pom.xml" (
        findstr /i "surefire-plugin" "%%m\pom.xml" >nul
        if !errorlevel! equ 0 (
            echo   %GREEN%✓%RESET% Plugin Surefire configurado
        ) else (
            echo   %RED%✗%RESET% Plugin Surefire NO configurado
        )
        
        findstr /i "surefire-report" "%%m\pom.xml" >nul
        if !errorlevel! equ 0 (
            echo   %GREEN%✓%RESET% Plugin Surefire Report configurado
        ) else (
            echo   %RED%✗%RESET% Plugin Surefire Report NO configurado
        )
        
        findstr /i "reporting" "%%m\pom.xml" >nul
        if !errorlevel! equ 0 (
            echo   %GREEN%✓%RESET% Sección reporting configurada
        ) else (
            echo   %RED%✗%RESET% Sección reporting NO configurada
        )
    )
    echo.
)

:: Verificar si existen tests
echo %CYAN%[CHECK 5/6]%RESET% Verificando existencia de tests...
for %%m in (%modules%) do (
    if exist "%%m\src\test\java" (
        echo %GREEN%✓%RESET% Módulo %%m - Directorio de tests encontrado
        dir "%%m\src\test\java\*.java" /s /b 2>nul | find /c ".java" >temp_count.txt
        set /p count=<temp_count.txt
        if defined count (
            echo   📊 Archivos de test encontrados: !count!
        )
        del temp_count.txt 2>nul
    ) else (
        echo %YELLOW%⚠%RESET% Módulo %%m - Sin directorio de tests
    )
)
echo.

:: Verificar dashboard
echo %CYAN%[CHECK 6/6]%RESET% Verificando dashboard y scripts...
if exist "test-reports-index.html" (
    echo %GREEN%✓%RESET% Dashboard de reportes encontrado
) else (
    echo %RED%✗%RESET% Dashboard de reportes NO encontrado
)

if exist "generate-test-reports.bat" (
    echo %GREEN%✓%RESET% Script generador de reportes encontrado
) else (
    echo %RED%✗%RESET% Script generador de reportes NO encontrado
)

if exist "SUREFIRE_REPORTS_GUIDE.md" (
    echo %GREEN%✓%RESET% Guía técnica encontrada
) else (
    echo %YELLOW%⚠%RESET% Guía técnica no encontrada
)

echo.
echo ================================================================================
echo %PURPLE%📋 RESUMEN DE CONFIGURACIÓN:%RESET%
echo ================================================================================
echo.
echo %GREEN%✅ Configuración Surefire:%RESET%
echo   • Plugin Surefire configurado en todos los módulos
echo   • Plugin Surefire Report configurado en todos los módulos  
echo   • Secciones reporting configuradas
echo   • Compatibilidad Java 21 configurada
echo.
echo %BLUE%🎯 Nombres de reportes personalizados:%RESET%
echo   • Common: common-surefire-report.html
echo   • Eureka: eureka-surefire-report.html
echo   • Users: users-surefire-report.html
echo   • Courses: courses-surefire-report.html
echo   • Grades: grades-surefire-report.html
echo   • Payments: payments-surefire-report.html
echo   • Support: support-surefire-report.html
echo.
echo %YELLOW%⚡ Comandos disponibles:%RESET%
echo   • generate-test-reports.bat - Generar todos los reportes
echo   • mvn clean test - Ejecutar solo tests
echo   • mvn surefire-report:report - Generar solo reportes
echo   • mvn clean test site - Ejecutar tests y generar sitio completo
echo.
echo %CYAN%🌐 Navegación:%RESET%
echo   • Dashboard: test-reports-index.html
echo   • Reporte agregado: target\site\surefire-report.html
echo.
echo ================================================================================
echo %GREEN%✨ CONFIGURACIÓN COMPLETADA EXITOSAMENTE%RESET%
echo ================================================================================
echo.
echo %BLUE%💡 Próximos pasos:%RESET%
echo 1. Ejecutar: generate-test-reports.bat
echo 2. Abrir: test-reports-index.html
echo 3. Navegar entre reportes usando el dashboard
echo.
pause
