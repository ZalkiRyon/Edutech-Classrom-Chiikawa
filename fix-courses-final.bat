@echo off
echo ================================================================================
echo 🔧 CORRECCIÓN FINAL PARA MS-COURSES - EduTech Classroom
echo ================================================================================
echo.

cd /d "%~dp0"

echo 🏁 Paso 1: Finalizando tests de ms-courses...
cd ms-courses
echo Ejecutando: mvn clean test
mvn clean test -Dmaven.test.failure.ignore=true

echo.
echo 📊 Paso 2: Generando reporte individual de ms-courses...
echo Ejecutando: mvn surefire-report:report  
mvn surefire-report:report

echo.
echo 🔄 Paso 3: Regenerando reporte agregado desde raíz...
cd ..
echo Ejecutando: mvn surefire-report:report (agregado)
mvn surefire-report:report -Daggregate=true

echo.
echo ✅ Paso 4: Verificando inclusión de courses...
for /d %%i in (ms-*) do (
    if exist "%%i\target\surefire-reports" (
        echo 📁 Módulo %%i:
        for /f %%j in ('dir "%%i\target\surefire-reports\TEST-*.xml" 2^>nul ^| find /c "TEST-"') do echo    - Archivos XML: %%j
    )
)

echo.
echo ================================================================================
echo 🎯 VERIFICACIÓN FINAL
echo ================================================================================

if exist "target\site\surefire-report.html" (
    echo ✅ Reporte agregado disponible: target\site\surefire-report.html
    echo 🌐 Abriendo reporte para verificar inclusión de courses...
    start "" "target\site\surefire-report.html"
) else (
    echo ❌ Error: No se pudo generar el reporte agregado
)

if exist "ms-courses\target\site\courses-surefire-report.html" (
    echo ✅ Reporte individual courses disponible
) else (
    echo ⚠️  Reporte individual courses no disponible
)

echo.
echo ================================================================================
echo 📋 COMANDOS DE VERIFICACIÓN MANUAL:
echo ================================================================================
echo.
echo findstr "com.edutech.courses" target\site\surefire-report.html
echo dir ms-courses\target\surefire-reports\TEST-*.xml
echo.

pause
