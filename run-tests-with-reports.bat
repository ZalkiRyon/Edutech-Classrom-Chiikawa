@echo off
echo ========================================================
echo EJECUTANDO PRUEBAS UNITARIAS CON REPORTES HTML DETALLADOS
echo ========================================================
echo.

echo [1/6] Ejecutando pruebas en ms-courses...
cd ms-courses
call mvn clean test surefire-report:report
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la ejecución de pruebas en ms-courses
    cd ..
    exit /b 1
)
cd ..

echo.
echo [2/6] Ejecutando pruebas en ms-grades...
cd ms-grades
call mvn clean test surefire-report:report
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la ejecución de pruebas en ms-grades
    cd ..
    exit /b 1
)
cd ..

echo.
echo [3/6] Ejecutando pruebas en ms-users...
cd ms-users
call mvn clean test surefire-report:report
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la ejecución de pruebas en ms-users
    cd ..
    exit /b 1
)
cd ..

echo.
echo [4/6] Ejecutando pruebas en ms-payments...
cd ms-payments
call mvn clean test surefire-report:report
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la ejecución de pruebas en ms-payments
    cd ..
    exit /b 1
)
cd ..

echo.
echo [5/6] Ejecutando pruebas en ms-support...
cd ms-support
call mvn clean test surefire-report:report
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la ejecución de pruebas en ms-support
    cd ..
    exit /b 1
)
cd ..

echo.
echo [6/6] Ejecutando pruebas en common...
cd common
call mvn clean test surefire-report:report
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la ejecución de pruebas en common
    cd ..
    exit /b 1
)
cd ..

echo.
echo ========================================================
echo TODAS LAS PRUEBAS COMPLETADAS EXITOSAMENTE
echo ========================================================
echo.
echo REPORTES HTML GENERADOS EN:
echo - ms-courses/target/site/surefire-report.html
echo - ms-grades/target/site/surefire-report.html
echo - ms-users/target/site/surefire-report.html
echo - ms-payments/target/site/surefire-report.html
echo - ms-support/target/site/surefire-report.html
echo - common/target/site/surefire-report.html
echo.
echo REPORTES XML GENERADOS EN:
echo - */target/surefire-reports/TEST-*.xml
echo.
echo Para ver los reportes HTML, abre los archivos con tu navegador web.
echo ========================================================

pause
