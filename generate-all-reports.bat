@echo off
chcp 65001 > nul
echo ================================================================
echo GENERADOR DE REPORTES SUREFIRE - EduTech Classroom
echo ================================================================
echo.

cd /d "%~dp0"

:: Verificar Maven
echo Verificando Maven...
mvn --version > nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: Maven no esta disponible
    echo Instala Maven y agregalo al PATH
    pause
    exit /b 1
)
echo Maven OK
echo.

:: Función para generar reportes de un módulo
:generate_module_reports
set module=%1
echo [%module%] Generando reportes...
cd %module%
mvn clean test surefire-report:report site:site -q
if %ERRORLEVEL% equ 0 (
    echo [%module%] SUCCESS - Reportes generados en %module%\target\site\
) else (
    echo [%module%] WARNING - Algunos tests fallaron, pero reportes generados
)
cd ..
echo.
goto :eof

:: Generar reportes para cada módulo
echo Generando reportes para todos los módulos...
echo.

call :generate_module_reports common
call :generate_module_reports ms-courses  
call :generate_module_reports ms-grades
call :generate_module_reports ms-payments
call :generate_module_reports ms-support
call :generate_module_reports ms-users

:: Generar reporte agregado
echo [AGREGADO] Generando reporte del proyecto completo...
mvn surefire-report:report site:site -q
if %ERRORLEVEL% equ 0 (
    echo [AGREGADO] SUCCESS - Reporte agregado generado en target\site\
) else (
    echo [AGREGADO] WARNING - Error en reporte agregado
)

echo.
echo ================================================================
echo REPORTES GENERADOS EXITOSAMENTE
echo ================================================================
echo.
echo REPORTES INDIVIDUALES:
echo   common\target\site\surefire-report.html
echo   ms-courses\target\site\surefire-report.html  
echo   ms-grades\target\site\surefire-report.html
echo   ms-payments\target\site\surefire-report.html
echo   ms-support\target\site\surefire-report.html
echo   ms-users\target\site\surefire-report.html
echo.
echo REPORTE AGREGADO:
echo   target\site\surefire-report.html
echo.
echo DASHBOARD:
echo   test-reports-index.html
echo.
echo Presiona cualquier tecla para continuar...
pause > nul
