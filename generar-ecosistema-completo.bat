@echo off
echo ================================================================
echo          🏗️ GENERACION COMPLETA DE SITIOS - EDUTECH CLASSROOM
echo ================================================================
echo.

echo 🔨 Paso 1: Compilando todo el proyecto...
call mvn clean compile -q
if %errorlevel% neq 0 (
    echo ❌ Error en compilación
    pause
    exit /b 1
)
echo ✅ Compilación exitosa

echo.
echo 🧪 Paso 2: Ejecutando pruebas...
call mvn test -q
if %errorlevel% neq 0 (
    echo ❌ Error en pruebas
    pause
    exit /b 1
)
echo ✅ Pruebas exitosas

echo.
echo 🏠 Paso 3: Generando sitio del proyecto padre...
call mvn site -q -N
if %errorlevel% neq 0 (
    echo ❌ Error generando sitio padre
    pause
    exit /b 1
)
echo ✅ Sitio padre generado

echo.
echo 🏗️ Paso 4: Generando sitios de módulos...

for %%m in (eureka common ms-users ms-courses ms-grades ms-payments ms-support) do (
    echo   Generando sitio para %%m...
    cd %%m
    call mvn site -q
    if %errorlevel% neq 0 (
        echo   ❌ Error en %%m
        cd ..
        pause
        exit /b 1
    )
    echo   ✅ %%m completado
    cd ..
)

echo.
echo 📊 Paso 5: Generando reportes Surefire...
for %%m in (ms-users ms-courses ms-grades ms-payments ms-support) do (
    echo   Generando reporte para %%m...
    cd %%m
    call mvn surefire-report:report -q
    if %errorlevel% neq 0 (
        echo   ❌ Error en reporte de %%m
        cd ..
        pause
        exit /b 1
    )
    echo   ✅ Reporte %%m completado
    cd ..
)

echo.
echo ================================================================
echo ✅ GENERACION COMPLETA EXITOSA
echo 🌟 Todos los sitios y reportes han sido generados
echo ================================================================
echo.

echo 🚀 ¿Deseas abrir el sitio principal?
echo [S] Sí, abrir sitio principal
echo [N] No, solo mostrar enlaces
echo.

set /p choice="Selecciona (S/N): "

if /i "%choice%"=="S" (
    echo Abriendo sitio principal...
    start "" "target\site\index.html"
) else (
    echo.
    echo 🔗 Sitio principal disponible en:
    echo file:///C:/Users/sebas/Desktop/second wind/target/site/index.html
)

echo.
pause
