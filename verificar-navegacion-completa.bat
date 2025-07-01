@echo off
echo ====================================================
echo VERIFICACION DE NAVEGACION COMPLETA - EDUTECH CLASSROOM
echo ====================================================
echo.

echo Verificando sitios de modulos...
echo.

set "error_count=0"

echo 🔍 Proyecto Principal:
if exist "target\site\index.html" (
    echo   ✓ target\site\index.html [OK]
) else (
    echo   ✗ target\site\index.html [FALTA]
    set /a error_count+=1
)

echo.
echo 🏗️ Microservicios:
for %%m in (ms-users ms-courses ms-grades ms-payments ms-support) do (
    echo %%m:
    if exist "%%m\target\site\index.html" (
        echo   ✓ %%m\target\site\index.html [OK]
    ) else (
        echo   ✗ %%m\target\site\index.html [FALTA]
        set /a error_count+=1
    )
    if exist "%%m\target\site\surefire-report.html" (
        echo   ✓ %%m\target\site\surefire-report.html [OK]
    ) else (
        echo   ✗ %%m\target\site\surefire-report.html [FALTA]
        set /a error_count+=1
    )
    echo.
)

echo 🔧 Infraestructura:
for %%i in (eureka common) do (
    echo %%i:
    if exist "%%i\target\site\index.html" (
        echo   ✓ %%i\target\site\index.html [OK]
    ) else (
        echo   ✗ %%i\target\site\index.html [FALTA]
        set /a error_count+=1
    )
    echo.
)

echo ====================================================
if %error_count%==0 (
    echo ✅ TODAS LAS VERIFICACIONES PASARON
    echo 🌟 El ecosistema esta completo y listo para navegacion
    echo.
    echo 🚀 Abriendo sitio principal...
    start "" "target\site\index.html"
) else (
    echo ❌ SE ENCONTRARON %error_count% ERRORES
    echo ⚠️  Ejecuta 'generate-complete-ecosystem.bat' para generar sitios faltantes
)
echo ====================================================
pause
