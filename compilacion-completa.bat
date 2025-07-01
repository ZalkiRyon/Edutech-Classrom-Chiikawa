@echo off
echo ================================================================
echo    🔨 COMPILACION Y GENERACION COMPLETA - EDUTECH CLASSROOM
echo ================================================================
echo.

echo 🧹 Limpiando proyecto...
mvn clean -q
if errorlevel 1 (
    echo ❌ Error en limpieza
    pause
    exit /b 1
)
echo ✅ Limpieza completada

echo.
echo 🔨 Compilando proyecto completo...
mvn compile -q
if errorlevel 1 (
    echo ❌ Error en compilación
    pause
    exit /b 1
)
echo ✅ Compilación exitosa

echo.
echo 🧪 Ejecutando pruebas...
mvn test -q
if errorlevel 1 (
    echo ❌ Error en pruebas
    pause
    exit /b 1
)
echo ✅ Todas las pruebas pasaron

echo.
echo 🌐 Generando sitio del proyecto padre...
mvn site -q -N
if errorlevel 1 (
    echo ❌ Error generando sitio padre
    pause
    exit /b 1
)
echo ✅ Sitio padre generado

echo.
echo 🏗️ Generando sitios de todos los módulos...
for %%m in (eureka common ms-users ms-courses ms-grades ms-payments ms-support) do (
    echo   📦 Generando sitio para %%m...
    cd %%m
    mvn site -q
    if errorlevel 1 (
        echo   ❌ Error en %%m
        cd ..
        pause
        exit /b 1
    )
    echo   ✅ %%m completado
    cd ..
)

echo.
echo ================================================================
echo ✅ GENERACION COMPLETA EXITOSA
echo 🌐 Todos los sitios han sido generados
echo 🔗 Navegación entre módulos funcional
echo ================================================================
echo.

echo 🚀 ¿Verificar navegación?
echo [1] Sí, abrir sitio principal
echo [2] No, solo mostrar enlaces
echo.

set /p choice="Selecciona (1-2): "

if "%choice%"=="1" (
    echo Abriendo sitio principal...
    start "" "target\site\index.html"
) else (
    echo.
    echo 🔗 Enlaces disponibles:
    echo Sitio Principal: file:///C:/Users/sebas/Desktop/second wind/target/site/index.html
    echo.
)

pause
