@echo off
chcp 65001 > nul
echo ================================================================================
echo GENERADOR COMPLETO DE INFORMES SUREFIRE - EduTech Classroom
echo ================================================================================
echo.
echo Generando informes de pruebas unitarias para todos los modulos...
echo.

:: Establecer el directorio del proyecto
cd /d "%~dp0"

:: Verificar que Maven esté disponible
mvn --version >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: Maven no esta disponible en el PATH
    echo    Asegurate de que Maven este instalado y configurado correctamente
    pause
    exit /b 1
)

echo Tiempo estimado: 5-8 minutos para completar todos los modulos
echo.

echo Compilando todos los modulos primero...
mvn clean compile test-compile -q
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la compilación de los módulos
    echo    Revisa los errores de compilación antes de continuar
    pause
    exit /b 1
)
echo Compilacion exitosa, continuando con las pruebas...
echo.

:: Variables para conteo
set "SUCCESS_COUNT=0"
set "ERROR_COUNT=0"

echo [1/6] Generando reportes para Common Library...
cd common
mvn clean test surefire-report:report
if %ERRORLEVEL% equ 0 (
    echo Common - Reportes generados exitosamente
    set /a SUCCESS_COUNT+=1
) else (
    echo Common - Error al generar reportes
    set /a ERROR_COUNT+=1
)
cd ..
echo.

echo [2/6] Generando reportes para MS-Courses...
cd ms-courses
mvn clean test surefire-report:report
if %ERRORLEVEL% equ 0 (
    echo MS-Courses - Reportes generados exitosamente
    set /a SUCCESS_COUNT+=1
) else (
    echo MS-Courses - Error al generar reportes
    set /a ERROR_COUNT+=1
)
cd ..
echo.

echo [3/6] Generando reportes para MS-Grades...
cd ms-grades
mvn clean test surefire-report:report
if %ERRORLEVEL% equ 0 (
    echo MS-Grades - Reportes generados exitosamente
    set /a SUCCESS_COUNT+=1
) else (
    echo MS-Grades - Error al generar reportes
    set /a ERROR_COUNT+=1
)
cd ..
echo.

echo [4/6] Generando reportes para MS-Payments...
cd ms-payments
mvn clean test surefire-report:report
if %ERRORLEVEL% equ 0 (
    echo MS-Payments - Reportes generados exitosamente
    set /a SUCCESS_COUNT+=1
) else (
    echo MS-Payments - Error al generar reportes
    set /a ERROR_COUNT+=1
)
cd ..
echo.

echo [5/6] Generando reportes para MS-Support...
cd ms-support
mvn clean test surefire-report:report
if %ERRORLEVEL% equ 0 (
    echo MS-Support - Reportes generados exitosamente
    set /a SUCCESS_COUNT+=1
) else (
    echo MS-Support - Error al generar reportes
    set /a ERROR_COUNT+=1
)
cd ..
echo.

echo [6/6] Generando reportes para MS-Users...
cd ms-users
mvn clean test surefire-report:report
if %ERRORLEVEL% equ 0 (
    echo MS-Users - Reportes generados exitosamente
    set /a SUCCESS_COUNT+=1
) else (
    echo MS-Users - Error al generar reportes
    set /a ERROR_COUNT+=1
)
cd ..
echo.

echo Generando reporte agregado del proyecto...
mvn surefire-report:report site:site
if %ERRORLEVEL% equ 0 (
    echo Reporte agregado generado exitosamente
) else (
    echo Advertencia: Error al generar reporte agregado
)
echo.

echo ================================================================================
echo RESUMEN DE GENERACION DE REPORTES
echo ================================================================================
echo Modulos exitosos: %SUCCESS_COUNT%/6
echo Modulos con errores: %ERROR_COUNT%/6
echo.

if %ERROR_COUNT% equ 0 (
    echo Todos los reportes se generaron exitosamente!
    echo.
    echo Los reportes estan disponibles en:
    echo    * common/target/site/surefire-report.html
    echo    * ms-courses/target/site/surefire-report.html
    echo    * ms-grades/target/site/surefire-report.html
    echo    * ms-payments/target/site/surefire-report.html
    echo    * ms-support/target/site/surefire-report.html
    echo    * ms-users/target/site/surefire-report.html
    echo    * target/site/surefire-report.html (agregado)
    echo.
    echo Puedes abrir el dashboard en: test-reports-index.html
) else (
    echo Se encontraron errores en %ERROR_COUNT% modulo(s)
    echo    Revisa los logs anteriores para mas detalles
)

echo.
echo Presiona cualquier tecla para salir...
pause >nul
