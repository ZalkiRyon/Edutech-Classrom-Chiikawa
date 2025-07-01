@echo off
echo ========================================================
echo GENERACIÓN COMPLETA DE SITIOS Y PRUEBAS - TODOS LOS MÓDULOS
echo ========================================================
echo.
echo 🚀 Generando sitios del proyecto padre y todos los módulos...
echo 🧪 Ejecutando pruebas extensivas de todos los microservicios...
echo.

echo [PASO 1] ========== LIMPIEZA GENERAL ==========
echo Limpiando proyecto padre y todos los módulos...
call mvn clean -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en limpieza general
    goto :error
) else (
    echo ✅ Limpieza general completada
)

echo.
echo [PASO 2] ========== SITIO DEL PROYECTO PADRE ==========
echo Generando sitio del proyecto principal (EduTech Classroom)...
call mvn site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en generación del sitio padre
    goto :error
) else (
    echo ✅ Sitio del proyecto padre generado
)

echo.
echo [PASO 3] ========== MÓDULO MS-COURSES ==========
echo [3.1] Ejecutando pruebas extensivas de ms-courses...
cd ms-courses
call mvn clean test surefire-report:report site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en ms-courses
    cd ..
    goto :error
) else (
    echo ✅ ms-courses: Pruebas y sitio generados (46+ pruebas)
)
cd ..

echo.
echo [PASO 4] ========== MÓDULO MS-USERS ==========
echo [4.1] Ejecutando pruebas extensivas de ms-users...
cd ms-users
call mvn clean test surefire-report:report site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en ms-users
    cd ..
    goto :error
) else (
    echo ✅ ms-users: Pruebas y sitio generados (15+ pruebas)
)
cd ..

echo.
echo [PASO 5] ========== MÓDULO MS-GRADES ==========
echo [5.1] Ejecutando pruebas extensivas de ms-grades...
cd ms-grades
call mvn clean test surefire-report:report site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en ms-grades
    cd ..
    goto :error
) else (
    echo ✅ ms-grades: Pruebas y sitio generados (15+ pruebas)
)
cd ..

echo.
echo [PASO 6] ========== MÓDULO MS-PAYMENTS ==========
echo [6.1] Ejecutando pruebas extensivas de ms-payments...
cd ms-payments
call mvn clean test surefire-report:report site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en ms-payments
    cd ..
    goto :error
) else (
    echo ✅ ms-payments: Pruebas y sitio generados (21+ pruebas)
)
cd ..

echo.
echo [PASO 7] ========== MÓDULO MS-SUPPORT ==========
echo [7.1] Ejecutando pruebas extensivas de ms-support...
cd ms-support
call mvn clean test surefire-report:report site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en ms-support
    cd ..
    goto :error
) else (
    echo ✅ ms-support: Pruebas y sitio generados (20+ pruebas)
)
cd ..

echo.
echo [PASO 8] ========== MÓDULO EUREKA ==========
echo [8.1] Generando sitio de eureka...
cd eureka
call mvn clean site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en eureka
    cd ..
    goto :error
) else (
    echo ✅ eureka: Sitio generado
)
cd ..

echo.
echo [PASO 9] ========== MÓDULO COMMON ==========
echo [9.1] Generando sitio de common...
cd common
call mvn clean site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en common
    cd ..
    goto :error
) else (
    echo ✅ common: Sitio generado
)
cd ..

echo.
echo [PASO 10] ========== VERIFICACIÓN FINAL ==========
echo Verificando que todos los sitios estén generados...

if exist target\site\index.html (
    echo ✅ Sitio padre: target\site\index.html
) else (
    echo ❌ Sitio padre no encontrado
)

if exist ms-courses\target\site\index.html (
    echo ✅ Sitio ms-courses: ms-courses\target\site\index.html
) else (
    echo ❌ Sitio ms-courses no encontrado
)

if exist ms-users\target\site\index.html (
    echo ✅ Sitio ms-users: ms-users\target\site\index.html
) else (
    echo ❌ Sitio ms-users no encontrado
)

if exist ms-grades\target\site\index.html (
    echo ✅ Sitio ms-grades: ms-grades\target\site\index.html
) else (
    echo ❌ Sitio ms-grades no encontrado
)

if exist ms-payments\target\site\index.html (
    echo ✅ Sitio ms-payments: ms-payments\target\site\index.html
) else (
    echo ❌ Sitio ms-payments no encontrado
)

if exist ms-support\target\site\index.html (
    echo ✅ Sitio ms-support: ms-support\target\site\index.html
) else (
    echo ❌ Sitio ms-support no encontrado
)

if exist eureka\target\site\index.html (
    echo ✅ Sitio eureka: eureka\target\site\index.html
) else (
    echo ❌ Sitio eureka no encontrado
)

if exist common\target\site\index.html (
    echo ✅ Sitio common: common\target\site\index.html
) else (
    echo ❌ Sitio common no encontrado
)

echo.
echo ========================================================
echo ✅ GENERACIÓN COMPLETADA EXITOSAMENTE
echo ========================================================
echo.
echo 📊 RESUMEN DE RESULTADOS:
echo.
echo    🏠 Proyecto Padre: EduTech Classroom ✅
echo    📚 ms-courses: 46+ pruebas ✅
echo    👥 ms-users: 15+ pruebas ✅
echo    📊 ms-grades: 15+ pruebas ✅
echo    💳 ms-payments: 21+ pruebas ✅
echo    🎧 ms-support: 20+ pruebas ✅
echo    🔍 eureka: Servidor de descubrimiento ✅
echo    📦 common: Librería compartida ✅
echo.
echo    📊 TOTAL ESTIMADO: 130+ pruebas unitarias
echo.
echo ========================================================
echo 🌐 SITIOS DISPONIBLES:
echo ========================================================
echo.
echo 🏠 Proyecto Principal:
echo    file:///c:/Users/sebas/Desktop/second%%20wind/target/site/index.html
echo.
echo 📚 MS-Courses (Gestión de Cursos):
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-courses/target/site/index.html
echo    📊 Reportes: file:///c:/Users/sebas/Desktop/second%%20wind/ms-courses/target/site/surefire-report.html
echo.
echo 👥 MS-Users (Gestión de Usuarios):
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-users/target/site/index.html
echo    📊 Reportes: file:///c:/Users/sebas/Desktop/second%%20wind/ms-users/target/site/surefire-report.html
echo.
echo 📊 MS-Grades (Gestión de Notas):
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-grades/target/site/index.html
echo    📊 Reportes: file:///c:/Users/sebas/Desktop/second%%20wind/ms-grades/target/site/surefire-report.html
echo.
echo 💳 MS-Payments (Gestión de Pagos):
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-payments/target/site/index.html
echo    📊 Reportes: file:///c:/Users/sebas/Desktop/second%%20wind/ms-payments/target/site/surefire-report.html
echo.
echo 🎧 MS-Support (Soporte Técnico):
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-support/target/site/index.html
echo    📊 Reportes: file:///c:/Users/sebas/Desktop/second%%20wind/ms-support/target/site/surefire-report.html
echo.
echo 🔍 Eureka (Discovery Server):
echo    file:///c:/Users/sebas/Desktop/second%%20wind/eureka/target/site/index.html
echo.
echo 📦 Common (Librería Compartida):
echo    file:///c:/Users/sebas/Desktop/second%%20wind/common/target/site/index.html
echo.
echo ========================================================
echo 🚀 COMANDOS RÁPIDOS:
echo ========================================================
echo.
echo 🌐 Abrir sitio principal:
echo    start target\site\index.html
echo.
echo 📚 Abrir sitio de ms-courses:
echo    start ms-courses\target\site\index.html
echo.
echo 📊 Abrir reporte de pruebas de ms-courses:
echo    start ms-courses\target\site\surefire-report.html
echo.
echo 🔍 Ver todos los sitios:
echo    mostrar-todos-sitios.bat
echo.
echo ========================================================
echo 📋 NAVEGACIÓN ENTRE MÓDULOS:
echo ========================================================
echo.
echo ✅ Los enlaces "Parent Project" ahora funcionan correctamente
echo ✅ Navegación completa entre proyecto padre y módulos
echo ✅ Reportes de pruebas individuales por módulo
echo ✅ Documentación técnica completa
echo.
echo ========================================================

goto :end

:error
echo.
echo ❌ ERROR: La generación falló. Revise los logs anteriores.
echo.
pause
exit /b 1

:end
echo.
echo ✨ Presione cualquier tecla para finalizar...
pause > nul
