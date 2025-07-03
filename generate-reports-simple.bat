@echo off
chcp 65001 > nul
echo ================================================================================
echo GENERADOR SUREFIRE REPORTS - EduTech Classroom
echo ================================================================================
echo.

cd /d "%~dp0"

echo Verificando Maven...
mvn --version > nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: Maven no encontrado
    pause
    exit /b 1
)

echo Maven OK. Compilando proyecto...
mvn clean compile -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Fallo en compilacion
    pause
    exit /b 1
)

echo Ejecutando tests y generando reportes...
mvn test surefire-report:report -q
if %ERRORLEVEL% equ 0 (
    echo SUCCESS: Tests y reportes generados
) else (
    echo WARNING: Algunos tests fallaron pero continuando...
)

echo Generando sitio web...
mvn site:site -q
if %ERRORLEVEL% equ 0 (
    echo SUCCESS: Sitio generado
) else (
    echo ERROR: Fallo generacion de sitio
)

echo.
echo COMPLETADO - Revisar:
echo   target\site\surefire-report.html
echo   test-reports-index.html
echo.
pause
