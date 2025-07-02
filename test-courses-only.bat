@echo off
echo ================================================================================
echo 🧪 TESTING ESPECÍFICO PARA MS-COURSES - EduTech Classroom  
echo ================================================================================
echo.

cd /d "%~dp0"

echo 🔄 Compilando módulo ms-courses...
cd ms-courses
mvn clean compile

echo.
echo 🧪 Compilando tests de ms-courses...
mvn test-compile

echo.
echo 🚀 Ejecutando tests de ms-courses...
mvn test -Dmaven.test.failure.ignore=true

echo.
echo 📊 Generando reporte de ms-courses...
mvn surefire-report:report

echo.
echo ================================================================================
echo ✅ PROCESO COMPLETADO PARA MS-COURSES
echo ================================================================================
echo.

if exist "target\site\courses-surefire-report.html" (
    echo 📄 Reporte generado: target\site\courses-surefire-report.html
    start "" "target\site\courses-surefire-report.html"
) else (
    echo ❌ No se pudo generar el reporte
)

pause
