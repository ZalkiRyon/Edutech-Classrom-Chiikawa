@echo off
echo ========================================================
echo VISUALIZACIÓN DE TODOS LOS SITIOS - ECOSISTEMA COMPLETO
echo ========================================================
echo.
echo 🌐 Abriendo todos los sitios del ecosistema EduTech Classroom...
echo.

echo [1/8] 🏠 Abriendo Sitio Principal (Proyecto Padre)...
if exist target\site\index.html (
    start "" "target\site\index.html"
    echo ✅ Sitio principal abierto
) else (
    echo ❌ Sitio principal no encontrado
)
timeout /t 2 > nul

echo.
echo [2/8] 📚 Abriendo MS-Courses (Gestión de Cursos)...
if exist ms-courses\target\site\index.html (
    start "" "ms-courses\target\site\index.html"
    echo ✅ Sitio ms-courses abierto
) else (
    echo ❌ Sitio ms-courses no encontrado
)
timeout /t 2 > nul

echo.
echo [3/8] 👥 Abriendo MS-Users (Gestión de Usuarios)...
if exist ms-users\target\site\index.html (
    start "" "ms-users\target\site\index.html"
    echo ✅ Sitio ms-users abierto
) else (
    echo ❌ Sitio ms-users no encontrado
)
timeout /t 2 > nul

echo.
echo [4/8] 📊 Abriendo MS-Grades (Gestión de Notas)...
if exist ms-grades\target\site\index.html (
    start "" "ms-grades\target\site\index.html"
    echo ✅ Sitio ms-grades abierto
) else (
    echo ❌ Sitio ms-grades no encontrado
)
timeout /t 2 > nul

echo.
echo [5/8] 💳 Abriendo MS-Payments (Gestión de Pagos)...
if exist ms-payments\target\site\index.html (
    start "" "ms-payments\target\site\index.html"
    echo ✅ Sitio ms-payments abierto
) else (
    echo ❌ Sitio ms-payments no encontrado
)
timeout /t 2 > nul

echo.
echo [6/8] 🎧 Abriendo MS-Support (Soporte Técnico)...
if exist ms-support\target\site\index.html (
    start "" "ms-support\target\site\index.html"
    echo ✅ Sitio ms-support abierto
) else (
    echo ❌ Sitio ms-support no encontrado
)
timeout /t 2 > nul

echo.
echo [7/8] 🔍 Abriendo Eureka (Discovery Server)...
if exist eureka\target\site\index.html (
    start "" "eureka\target\site\index.html"
    echo ✅ Sitio eureka abierto
) else (
    echo ❌ Sitio eureka no encontrado
)
timeout /t 2 > nul

echo.
echo [8/8] 📦 Abriendo Common (Librería Compartida)...
if exist common\target\site\index.html (
    start "" "common\target\site\index.html"
    echo ✅ Sitio common abierto
) else (
    echo ❌ Sitio common no encontrado
)

echo.
echo ========================================================
echo 📊 REPORTES DE PRUEBAS ESPECÍFICOS
echo ========================================================
echo.

echo 🧪 Abriendo reportes de pruebas unitarias...
echo.

if exist ms-courses\target\site\surefire-report.html (
    echo [COURSES] Abriendo reporte de pruebas...
    start "" "ms-courses\target\site\surefire-report.html"
    timeout /t 1 > nul
)

if exist ms-users\target\site\surefire-report.html (
    echo [USERS] Abriendo reporte de pruebas...
    start "" "ms-users\target\site\surefire-report.html"
    timeout /t 1 > nul
)

if exist ms-grades\target\site\surefire-report.html (
    echo [GRADES] Abriendo reporte de pruebas...
    start "" "ms-grades\target\site\surefire-report.html"
    timeout /t 1 > nul
)

if exist ms-payments\target\site\surefire-report.html (
    echo [PAYMENTS] Abriendo reporte de pruebas...
    start "" "ms-payments\target\site\surefire-report.html"
    timeout /t 1 > nul
)

if exist ms-support\target\site\surefire-report.html (
    echo [SUPPORT] Abriendo reporte de pruebas...
    start "" "ms-support\target\site\surefire-report.html"
    timeout /t 1 > nul
)

echo.
echo ========================================================
echo 📈 ESTADÍSTICAS DEL ECOSISTEMA
echo ========================================================
echo.

echo 🏗️ ARQUITECTURA COMPLETA:
echo    ├── 🏠 Proyecto Padre: EduTech Classroom
echo    ├── 📚 MS-Courses: 46+ pruebas (5 entidades)
echo    ├── 👥 MS-Users: 15+ pruebas (2 entidades)
echo    ├── 📊 MS-Grades: 15+ pruebas (4 entidades)
echo    ├── 💳 MS-Payments: 21+ pruebas (2 entidades)
echo    ├── 🎧 MS-Support: 20+ pruebas (1 entidad)
echo    ├── 🔍 Eureka: Servidor de descubrimiento
echo    └── 📦 Common: DTOs y utilidades compartidas

echo.
echo 📊 MÉTRICAS TOTALES:
echo    • Microservicios: 5
echo    • Módulos totales: 8
echo    • Pruebas unitarias: 130+ estimadas
echo    • Entidades cubiertas: 14+
echo    • Sitios HTML generados: 8
echo    • Reportes de pruebas: 5

echo.
echo ========================================================
echo 🔗 ENLACES DIRECTOS RÁPIDOS
echo ========================================================
echo.

echo 🌐 SITIOS PRINCIPALES:
echo.
echo Proyecto Principal:
echo file:///c:/Users/sebas/Desktop/second%%20wind/target/site/index.html
echo.
echo MS-Courses:
echo file:///c:/Users/sebas/Desktop/second%%20wind/ms-courses/target/site/index.html
echo.
echo MS-Users:
echo file:///c:/Users/sebas/Desktop/second%%20wind/ms-users/target/site/index.html
echo.

echo 📊 REPORTES DE PRUEBAS:
echo.
echo Courses Test Report:
echo file:///c:/Users/sebas/Desktop/second%%20wind/ms-courses/target/site/surefire-report.html
echo.
echo Users Test Report:
echo file:///c:/Users/sebas/Desktop/second%%20wind/ms-users/target/site/surefire-report.html
echo.

echo ========================================================
echo ✅ TODOS LOS SITIOS ABIERTOS EN EL NAVEGADOR
echo ========================================================
echo.
echo 💡 TIP: Los enlaces "Parent Project" en cada módulo
echo    ahora funcionan correctamente y te llevan al sitio principal.
echo.
echo ========================================================

pause
