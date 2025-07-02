@echo off
echo ================================================================================
echo 🧪 GENERADOR DE INFORMES DE PRUEBAS UNITARIAS - EduTech Classroom
echo ================================================================================
echo.
echo Generando informes completos de tests para todos los módulos...
echo.

:: Establecer el directorio del proyecto
cd /d "%~dp0"

:: Limpiar reportes anteriores
echo 📁 Limpiando reportes anteriores...
rmdir /s /q target\site 2>nul
for /d %%i in (*/target/site) do rmdir /s /q "%%i" 2>nul

echo.
echo ⚡ Ejecutando tests y generando reportes...
echo.

:: Ejecutar tests y generar reportes para todo el proyecto
echo 🔄 Paso 1: Ejecutando tests en todos los módulos...
call mvn clean test -T 4 -Dmaven.test.failure.ignore=true

echo.
echo 🔄 Paso 2: Generando reportes HTML individuales...
call mvn surefire-report:report -T 4

echo.
echo 🔄 Paso 3: Generando reporte agregado del proyecto...
call mvn surefire-report:report-only -Daggregate=true

echo.
echo 🔄 Paso 4: Generando sitio web con navegación...
call mvn site -DgenerateReports=true

echo.
echo ================================================================================
echo ✅ REPORTES GENERADOS EXITOSAMENTE
echo ================================================================================
echo.

:: Verificar y mostrar estadísticas
echo 📊 ESTADÍSTICAS DE TESTS:
echo.

:: Función para contar tests en cada módulo
for /d %%i in (ms-* common eureka) do (
    if exist "%%i\target\surefire-reports" (
        echo 📁 Módulo: %%i
        for /f %%j in ('dir "%%i\target\surefire-reports\TEST-*.xml" 2^>nul ^| find /c "TEST-"') do echo    - Archivos de test: %%j
        echo.
    )
)

echo ================================================================================
echo 🌐 NAVEGACIÓN DE REPORTES:
echo ================================================================================
echo.
echo 📋 Índice principal: test-reports-index.html
echo 📈 Reporte agregado: target\site\surefire-report.html
echo.
echo 📁 Reportes por módulo:
echo    - Common:      common\target\site\common-surefire-report.html
echo    - Eureka:      eureka\target\site\eureka-surefire-report.html  
echo    - MS-Users:    ms-users\target\site\users-surefire-report.html
echo    - MS-Courses:  ms-courses\target\site\courses-surefire-report.html
echo    - MS-Grades:   ms-grades\target\site\grades-surefire-report.html
echo    - MS-Payments: ms-payments\target\site\payments-surefire-report.html
echo    - MS-Support:  ms-support\target\site\support-surefire-report.html
echo.

:: Abrir el índice principal en el navegador
echo 🚀 Abriendo índice de navegación...
start "" "test-reports-index.html"

echo.
echo ================================================================================
echo 💡 COMANDOS ÚTILES:
echo ================================================================================
echo.
echo Para regenerar solo tests:        mvn clean test
echo Para regenerar solo reportes:     mvn surefire-report:report  
echo Para regenerar sitio completo:    mvn clean test site
echo Para un módulo específico:        cd ms-courses ^&^& mvn test surefire-report:report
echo.
echo ================================================================================
echo ✨ PROCESO COMPLETADO - ¡Tests y reportes listos para navegación!
echo ================================================================================

pause
