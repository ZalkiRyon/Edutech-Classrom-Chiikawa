@echo off
echo.
echo === VERIFICACION RAPIDA DE PRUEBAS EDUTECH ===
echo.

echo === MS-GRADES ===
cd "C:\Edutech-Classrom-Chiikawa\ms-grades"
mvn test -q -Dtest=*ApplicationTests 2>nul
if %ERRORLEVEL% == 0 (
    echo [OK] ms-grades: Contexto funcionando
) else (
    echo [ERROR] ms-grades: Problemas de contexto
)

echo === MS-COURSES ===
cd "C:\Edutech-Classrom-Chiikawa\ms-courses"
mvn test -q -Dtest=*ApplicationTests 2>nul
if %ERRORLEVEL% == 0 (
    echo [OK] ms-courses: Contexto funcionando
) else (
    echo [ERROR] ms-courses: Problemas de contexto
)

echo === MS-PAYMENTS ===
cd "C:\Edutech-Classrom-Chiikawa\ms-payments"
mvn test -q -Dtest=*ApplicationTests 2>nul
if %ERRORLEVEL% == 0 (
    echo [OK] ms-payments: Contexto funcionando
) else (
    echo [ERROR] ms-payments: Problemas de contexto
)

echo === MS-SUPPORT ===
cd "C:\Edutech-Classrom-Chiikawa\ms-support"
mvn test -q -Dtest=*ApplicationTests 2>nul
if %ERRORLEVEL% == 0 (
    echo [OK] ms-support: Contexto funcionando
) else (
    echo [ERROR] ms-support: Problemas de contexto
)

echo === MS-USERS ===
cd "C:\Edutech-Classrom-Chiikawa\ms-users"
mvn test -q -Dtest=*ApplicationTests 2>nul
if %ERRORLEVEL% == 0 (
    echo [OK] ms-users: Contexto funcionando
) else (
    echo [ERROR] ms-users: Problemas de contexto
)

echo.
echo === RESUMEN ===
echo Para ejecutar todas las pruebas completas, usa: run-all-tests.bat
echo Para ver errores detallados, navega a cada modulo y ejecuta: mvn test
echo.
cd "C:\Edutech-Classrom-Chiikawa"
pause
