@echo off
echo ================================================================
echo      🔨 COMPILACION Y GENERACION COMPLETA - EDUTECH CLASSROOM
echo ================================================================
echo.

echo 🧹 Paso 1: Limpiando compilaciones anteriores...
mvn clean -q
if errorlevel 1 (
    echo ❌ Error en clean
    pause
    exit /b 1
)
echo ✅ Limpieza completada
echo.

echo 🔨 Paso 2: Compilando todo el proyecto...
mvn compile -q
if errorlevel 1 (
    echo ❌ Error en compilación
    pause
    exit /b 1
)
echo ✅ Compilación completada
echo.

echo 🧪 Paso 3: Ejecutando pruebas unitarias...
mvn test -q
if errorlevel 1 (
    echo ❌ Error en pruebas
    pause
    exit /b 1
)
echo ✅ Pruebas completadas
echo.

echo 🏗️ Paso 4: Generando sitio base del proyecto padre...
mvn site -q -N
if errorlevel 1 (
    echo ❌ Error generando sitio padre
    pause
    exit /b 1
)
echo ✅ Sitio padre generado
echo.

echo 📊 Paso 5: Generando reportes de pruebas...
mvn surefire-report:report -q -N
if errorlevel 1 (
    echo ❌ Error generando reportes
    pause
    exit /b 1
)
echo ✅ Reportes de pruebas generados
echo.

echo 🏗️ Paso 6: Generando sitios de todos los módulos...
mvn site -q
if errorlevel 1 (
    echo ❌ Error generando sitios de módulos
    pause
    exit /b 1
)
echo ✅ Sitios de módulos generados
echo.

echo 🔗 Paso 7: Restaurando página de navegación personalizada...
if exist "custom-index.html" (
    copy "custom-index.html" "target\site\index.html" >nul
    echo ✅ Navegación personalizada restaurada
) else (
    echo ⚠️  custom-index.html no encontrado, usando sitio Maven por defecto
)
echo.

echo ================================================================
echo ✅ COMPILACION Y GENERACION COMPLETADA EXITOSAMENTE
echo ================================================================
echo.

echo 🔍 Verificando estructura generada...
echo.

echo 📂 Sitios generados:
if exist "target\site\index.html" echo ✅ Proyecto padre: target\site\index.html
if exist "ms-users\target\site\index.html" echo ✅ MS-Users: ms-users\target\site\index.html
if exist "ms-courses\target\site\index.html" echo ✅ MS-Courses: ms-courses\target\site\index.html
if exist "ms-grades\target\site\index.html" echo ✅ MS-Grades: ms-grades\target\site\index.html
if exist "ms-payments\target\site\index.html" echo ✅ MS-Payments: ms-payments\target\site\index.html
if exist "ms-support\target\site\index.html" echo ✅ MS-Support: ms-support\target\site\index.html
if exist "eureka\target\site\index.html" echo ✅ Eureka: eureka\target\site\index.html
if exist "common\target\site\index.html" echo ✅ Common: common\target\site\index.html

echo.
echo 📊 Reportes de pruebas:
if exist "ms-users\target\site\surefire-report.html" echo ✅ MS-Users Tests
if exist "ms-courses\target\site\surefire-report.html" echo ✅ MS-Courses Tests
if exist "ms-grades\target\site\surefire-report.html" echo ✅ MS-Grades Tests
if exist "ms-payments\target\site\surefire-report.html" echo ✅ MS-Payments Tests
if exist "ms-support\target\site\surefire-report.html" echo ✅ MS-Support Tests

echo.
echo 🚀 ¿Abrir sitio principal para verificar navegación?
echo [S/N]:
set /p open_site=""
if /i "%open_site%"=="S" (
    start "" "target\site\index.html"
)

echo.
echo 🎉 ¡Proceso completado!
pause
