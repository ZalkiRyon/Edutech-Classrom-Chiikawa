@echo off
echo.
echo === EJECUCION COMPLETA DE PRUEBAS UNITARIAS EDUTECH ===
echo.

echo Ejecutando pruebas de ms-payments...
cd "C:\Edutech-Classrom-Chiikawa\ms-payments"
mvn test
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallos en ms-payments
) else (
    echo [OK] ms-payments - Todas las pruebas exitosas
)
echo.

echo Ejecutando pruebas de ms-support...
cd "C:\Edutech-Classrom-Chiikawa\ms-support"
mvn test
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallos en ms-support
) else (
    echo [OK] ms-support - Todas las pruebas exitosas
)
echo.

echo Ejecutando pruebas de ms-grades...
cd "C:\Edutech-Classrom-Chiikawa\ms-grades"
mvn test
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallos en ms-grades
) else (
    echo [OK] ms-grades - Todas las pruebas exitosas
)
echo.

echo Ejecutando pruebas de ms-courses...
cd "C:\Edutech-Classrom-Chiikawa\ms-courses"
mvn test
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallos en ms-courses
) else (
    echo [OK] ms-courses - Todas las pruebas exitosas
)
echo.

echo Ejecutando pruebas de ms-users...
cd "C:\Edutech-Classrom-Chiikawa\ms-users"
mvn test
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallos en ms-users
) else (
    echo [OK] ms-users - Todas las pruebas exitosas
)
echo.

echo === RESUMEN DE EJECUCION COMPLETA ===
echo.
echo Si todos los modulos muestran [OK], entonces todas las pruebas
echo unitarias del proyecto EduTech estan funcionando correctamente.
echo.
pause
