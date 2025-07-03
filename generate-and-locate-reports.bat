@echo off
chcp 65001 > nul
echo ================================================================
echo GENERADOR Y LOCALIZADOR DE REPORTES SUREFIRE
echo ================================================================
echo.

cd /d "%~dp0"

:: Función para generar reporte y localizar archivo
:generate_and_locate
set module=%1
echo [%module%] Generando reporte...

if not exist "%module%" (
    echo [%module%] SKIP - Modulo no existe
    goto :eof
)

cd %module%

:: Generar reporte
mvn test surefire-report:report -q

:: Buscar archivos de reporte generados
echo [%module%] Buscando archivos de reporte...

:: Posibles ubicaciones de reportes
for %%f in (
    "target\site\surefire-report.html"
    "target\site\%module%-surefire-report.html"
    "target\site\%module:~3%-surefire-report.html"
    "target\reports\surefire.html"
    "target\surefire-reports\index.html"
) do (
    if exist "%%f" (
        echo [%module%] ✓ ENCONTRADO: %%f
        echo     URL: file:///%CD%\%%f
    )
)

cd ..
echo.
goto :eof

:: Generar reportes para todos los módulos
echo Generando reportes para todos los módulos...
echo.

call :generate_and_locate common
call :generate_and_locate ms-courses
call :generate_and_locate ms-grades
call :generate_and_locate ms-payments
call :generate_and_locate ms-support
call :generate_and_locate ms-users

:: Generar reporte agregado
echo [AGREGADO] Generando reporte del proyecto...
mvn surefire-report:report -q

if exist "target\site\surefire-report.html" (
    echo [AGREGADO] ✓ ENCONTRADO: target\site\surefire-report.html
    echo     URL: file:///%CD%\target\site\surefire-report.html
)

echo.
echo ================================================================
echo RESUMEN DE REPORTES GENERADOS
echo ================================================================
echo.
echo Para abrir los reportes, usa las URLs mostradas arriba
echo O abre el dashboard: test-reports-index.html
echo.
pause
