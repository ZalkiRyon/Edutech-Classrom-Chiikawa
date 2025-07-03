@echo off
echo ================================================================
echo GENERADOR INDIVIDUAL DE REPORTES SUREFIRE
echo ================================================================
echo.
echo Selecciona el modulo para generar reporte:
echo.
echo 1) Common Library
echo 2) MS-Courses
echo 3) MS-Grades  
echo 4) MS-Payments
echo 5) MS-Support
echo 6) MS-Users
echo 7) Eureka Server
echo 8) Reporte Agregado (todos)
echo 9) Salir
echo.
set /p choice="Ingresa tu opcion (1-9): "

if "%choice%"=="1" goto common
if "%choice%"=="2" goto courses
if "%choice%"=="3" goto grades
if "%choice%"=="4" goto payments
if "%choice%"=="5" goto support
if "%choice%"=="6" goto users
if "%choice%"=="7" goto eureka
if "%choice%"=="8" goto agregado
if "%choice%"=="9" goto exit
goto invalid

:common
echo Generando reporte para Common Library...
cd common && mvn clean test surefire-report:report site:site
echo Reporte generado en: common\target\site\surefire-report.html
goto end

:courses
echo Generando reporte para MS-Courses...
cd ms-courses && mvn clean test surefire-report:report site:site
echo Reporte generado en: ms-courses\target\site\courses-surefire-report.html
goto end

:grades
echo Generando reporte para MS-Grades...
cd ms-grades && mvn clean test surefire-report:report site:site
echo Reporte generado en: ms-grades\target\site\grades-surefire-report.html
goto end

:payments
echo Generando reporte para MS-Payments...
cd ms-payments && mvn clean test surefire-report:report site:site
echo Reporte generado en: ms-payments\target\site\payments-surefire-report.html
goto end

:support
echo Generando reporte para MS-Support...
cd ms-support && mvn clean test surefire-report:report site:site
echo Reporte generado en: ms-support\target\site\support-surefire-report.html
goto end

:users
echo Generando reporte para MS-Users...
cd ms-users && mvn clean test surefire-report:report site:site
echo Reporte generado en: ms-users\target\site\users-surefire-report.html
goto end

:eureka
echo Generando reporte para Eureka Server...
cd eureka && mvn clean test surefire-report:report site:site
echo Reporte generado en: eureka\target\site\surefire-report.html
goto end

:agregado
echo Generando reporte agregado de todo el proyecto...
mvn surefire-report:report site:site
echo Reporte agregado generado en: target\site\surefire-report.html
goto end

:invalid
echo Opcion invalida. Intenta de nuevo.
pause
goto start

:end
echo.
echo ================================================================
echo REPORTE GENERADO EXITOSAMENTE
echo ================================================================
echo.
pause

:exit
echo Saliendo...
