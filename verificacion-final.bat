@echo off
echo ========================================
echo EDUTECH PROJECT - VERIFICACION FINAL DE PRUEBAS UNITARIAS
echo ========================================
echo.

echo [1/6] Compilando y ejecutando pruebas en ms-users...
cd /d "C:\Edutech-Classrom-Chiikawa\ms-users"
mvn clean test -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Fallos en ms-users
    pause
    exit /b 1
)
echo ✓ ms-users - PRUEBAS EXITOSAS
echo.

echo [2/6] Compilando y ejecutando pruebas en ms-grades...
cd /d "C:\Edutech-Classrom-Chiikawa\ms-grades"
mvn clean test -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Fallos en ms-grades
    pause
    exit /b 1
)
echo ✓ ms-grades - PRUEBAS EXITOSAS
echo.

echo [3/6] Compilando y ejecutando pruebas en ms-courses...
cd /d "C:\Edutech-Classrom-Chiikawa\ms-courses"
mvn clean test -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Fallos en ms-courses
    pause
    exit /b 1
)
echo ✓ ms-courses - PRUEBAS EXITOSAS
echo.

echo [4/6] Compilando y ejecutando pruebas en ms-payments...
cd /d "C:\Edutech-Classrom-Chiikawa\ms-payments"
mvn clean test -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Fallos en ms-payments
    pause
    exit /b 1
)
echo ✓ ms-payments - PRUEBAS EXITOSAS
echo.

echo [5/6] Compilando y ejecutando pruebas en ms-support...
cd /d "C:\Edutech-Classrom-Chiikawa\ms-support"
mvn clean test -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Fallos en ms-support
    pause
    exit /b 1
)
echo ✓ ms-support - PRUEBAS EXITOSAS
echo.

echo [6/6] Compilando common...
cd /d "C:\Edutech-Classrom-Chiikawa\common"
mvn clean compile -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Fallos en common
    pause
    exit /b 1
)
echo ✓ common - COMPILACION EXITOSA
echo.

echo ========================================
echo VERIFICACION FINAL COMPLETADA ✓
echo ========================================
echo TODOS LOS MICROSERVICIOS PASARON LAS PRUEBAS UNITARIAS
echo - ms-users: OK
echo - ms-grades: OK  
echo - ms-courses: OK
echo - ms-payments: OK
echo - ms-support: OK
echo - common: OK
echo.
echo El proyecto EduTech está listo para producción.
echo ========================================
pause
