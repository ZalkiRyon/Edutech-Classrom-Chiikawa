@echo off
echo ========================================================
echo GENERACIÓN DE SITIO COMPLETO - PROYECTO PADRE + MÓDULOS
echo ========================================================
echo.
echo 🔧 Generando sitios para proyecto padre y todos los módulos...
echo.

echo [PASO 1] ========== SITIO DEL PROYECTO PADRE ==========
echo Generando sitio del proyecto principal (classroom)...
call mvn site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en generación del sitio padre
    goto :error
) else (
    echo ✅ Sitio del proyecto padre generado
)

echo.
echo [PASO 2] ========== SITIOS DE SUBMÓDULOS ==========

echo.
echo [2.1] Generando sitio de ms-courses...
cd ms-courses
call mvn site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en sitio de ms-courses
    cd ..
    goto :error
) else (
    echo ✅ Sitio de ms-courses generado
)
cd ..

echo.
echo [2.2] Generando sitio de ms-grades...
cd ms-grades
call mvn site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en sitio de ms-grades
    cd ..
    goto :error
) else (
    echo ✅ Sitio de ms-grades generado
)
cd ..

echo.
echo [2.3] Generando sitio de ms-users...
cd ms-users
call mvn site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en sitio de ms-users
    cd ..
    goto :error
) else (
    echo ✅ Sitio de ms-users generado
)
cd ..

echo.
echo [PASO 3] ========== VERIFICACIÓN ==========
if exist target\site\index.html (
    echo ✅ Sitio padre disponible en: target\site\index.html
) else (
    echo ❌ Sitio padre no generado
)

if exist ms-courses\target\site\index.html (
    echo ✅ Sitio ms-courses disponible
) else (
    echo ❌ Sitio ms-courses no disponible
)

echo.
echo ========================================================
echo ✅ SITIOS GENERADOS EXITOSAMENTE
echo ========================================================
echo.
echo 🌐 ENLACES DISPONIBLES:
echo.
echo 📋 Proyecto Principal:
echo    file:///c:/Users/sebas/Desktop/second%%20wind/target/site/index.html
echo.
echo 📋 Módulo Courses:
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-courses/target/site/index.html
echo.
echo 📋 Módulo Grades:
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-grades/target/site/index.html
echo.
echo 📋 Módulo Users:
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-users/target/site/index.html
echo.
echo ========================================================

goto :end

:error
echo.
echo ❌ ERROR: La generación de sitios falló
echo.
pause
exit /b 1

:end
echo.
echo ✨ Presione cualquier tecla para finalizar...
pause > nul
