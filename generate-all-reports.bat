@echo off
echo ========================================================
echo GENERANDO REPORTES HTML PARA TODOS LOS MICROSERVICIOS
echo ========================================================
echo.

echo [USERS] Ejecutando pruebas en ms-users...
cd ms-users
call mvn clean test surefire-report:report site -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la generación de reportes en ms-users
    cd ..
    goto :support
)
echo ✅ ms-users: Reportes generados exitosamente
cd ..

:support
echo.
echo [SUPPORT] Ejecutando pruebas en ms-support...
cd ms-support
call mvn clean test surefire-report:report site -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la generación de reportes en ms-support
    cd ..
    goto :common
)
echo ✅ ms-support: Reportes generados exitosamente
cd ..

:common
echo.
echo [COMMON] Ejecutando pruebas en common...
cd common
call mvn clean test surefire-report:report site -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la generación de reportes en common
    cd ..
    goto :results
)
echo ✅ common: Reportes generados exitosamente
cd ..

:results
echo.
echo ========================================================
echo RESUMEN DE REPORTES GENERADOS
echo ========================================================
echo.
if exist "ms-courses\target\site\surefire-report.html" (
    echo ✅ ms-courses: ms-courses\target\site\surefire-report.html
) else (
    echo ❌ ms-courses: Reporte no encontrado
)

if exist "ms-grades\target\site\surefire-report.html" (
    echo ✅ ms-grades: ms-grades\target\site\surefire-report.html
) else (
    echo ❌ ms-grades: Reporte no encontrado
)

if exist "ms-users\target\site\surefire-report.html" (
    echo ✅ ms-users: ms-users\target\site\surefire-report.html
) else (
    echo ❌ ms-users: Reporte no encontrado
)

if exist "ms-payments\target\site\surefire-report.html" (
    echo ✅ ms-payments: ms-payments\target\site\surefire-report.html
) else (
    echo ❌ ms-payments: Reporte no encontrado
)

if exist "ms-support\target\site\surefire-report.html" (
    echo ✅ ms-support: ms-support\target\site\surefire-report.html
) else (
    echo ❌ ms-support: Reporte no encontrado
)

if exist "common\target\site\surefire-report.html" (
    echo ✅ common: common\target\site\surefire-report.html
) else (
    echo ❌ common: Reporte no encontrado
)

echo.
echo ========================================================
echo COMANDOS PARA ABRIR REPORTES:
echo ========================================================
echo start ms-courses\target\site\surefire-report.html
echo start ms-grades\target\site\surefire-report.html
echo start ms-users\target\site\surefire-report.html
echo start ms-payments\target\site\surefire-report.html
echo start ms-support\target\site\surefire-report.html
echo start common\target\site\surefire-report.html
echo ========================================================

pause
