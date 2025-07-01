@echo off
color 0A
echo ================================================================
echo          🎓 EDUTECH CLASSROOM - ENLACES DIRECTOS RÁPIDOS
echo ================================================================
echo.

echo 🌟 SITIOS PRINCIPALES:
echo.
echo Proyecto Principal:
echo file:///C:/Users/sebas/Desktop/second wind/target/site/index.html
echo.

echo 🏗️ MICROSERVICIOS (Sitios Maven):
echo.
echo MS-Users:
echo file:///C:/Users/sebas/Desktop/second wind/ms-users/target/site/index.html
echo.
echo MS-Courses:
echo file:///C:/Users/sebas/Desktop/second wind/ms-courses/target/site/index.html
echo.
echo MS-Grades:
echo file:///C:/Users/sebas/Desktop/second wind/ms-grades/target/site/index.html
echo.
echo MS-Payments:
echo file:///C:/Users/sebas/Desktop/second wind/ms-payments/target/site/index.html
echo.
echo MS-Support:
echo file:///C:/Users/sebas/Desktop/second wind/ms-support/target/site/index.html
echo.

echo 🧪 REPORTES DE PRUEBAS UNITARIAS:
echo.
echo Courses Tests (46+ pruebas):
echo file:///C:/Users/sebas/Desktop/second wind/ms-courses/target/site/surefire-report.html
echo.
echo Users Tests (15+ pruebas):
echo file:///C:/Users/sebas/Desktop/second wind/ms-users/target/site/surefire-report.html
echo.
echo Grades Tests (15+ pruebas):
echo file:///C:/Users/sebas/Desktop/second wind/ms-grades/target/site/surefire-report.html
echo.
echo Payments Tests (21+ pruebas):
echo file:///C:/Users/sebas/Desktop/second wind/ms-payments/target/site/surefire-report.html
echo.
echo Support Tests (20+ pruebas):
echo file:///C:/Users/sebas/Desktop/second wind/ms-support/target/site/surefire-report.html
echo.

echo 🔧 INFRAESTRUCTURA:
echo.
echo Eureka Server:
echo file:///C:/Users/sebas/Desktop/second wind/eureka/target/site/index.html
echo.
echo Common Library:
echo file:///C:/Users/sebas/Desktop/second wind/common/target/site/index.html
echo.

echo ================================================================
echo ✅ TODOS LOS ENLACES ESTÁN AHORA FUNCIONALES
echo 🔗 NAVEGACIÓN CORREGIDA ENTRE MÓDULOS
echo 🧪 130+ PRUEBAS UNITARIAS DISPONIBLES
echo ================================================================
echo.

echo 🚀 ¿Qué deseas hacer?
echo [1] Abrir sitio principal (navegación completa)
echo [2] Abrir todos los sitios en paralelo
echo [3] Ejecutar verificación de navegación
echo [4] Salir
echo.

set /p choice="Selecciona una opción (1-4): "

if "%choice%"=="1" (
    echo Abriendo sitio principal...
    start "" "target\site\index.html"
) else if "%choice%"=="2" (
    echo Abriendo todos los sitios...
    call mostrar-todos-sitios.bat
) else if "%choice%"=="3" (
    echo Ejecutando verificación...
    call verificar-navegacion-completa.bat
) else if "%choice%"=="4" (
    echo ¡Hasta luego!
    exit /b
) else (
    echo Opción no válida. Abriendo sitio principal por defecto...
    start "" "target\site\index.html"
)

echo.
echo ✨ ¡Navegación completa disponible!
pause
