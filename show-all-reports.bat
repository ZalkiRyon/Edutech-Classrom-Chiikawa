@echo off
echo ========================================================
echo VISUALIZACIÓN DE REPORTES - MS-COURSES
echo ========================================================
echo.
echo Abriendo todos los reportes HTML generados en el navegador...
echo.

echo 🌐 [1/3] Abriendo Reporte Principal de Pruebas...
start "" "ms-courses\target\site\surefire-report.html"
timeout /t 2 > nul

echo 🌐 [2/3] Abriendo Sitio Completo del Proyecto...  
start "" "ms-courses\target\site\index.html"
timeout /t 2 > nul

echo 🌐 [3/3] Mostrando Reportes XML Disponibles...
echo.
echo ========================================================
echo 📁 REPORTES XML INDIVIDUALES:
echo ========================================================
if exist ms-courses\target\surefire-reports\ (
    dir ms-courses\target\surefire-reports\*.xml /b
) else (
    echo ❌ No se encontraron reportes XML. Ejecute primero las pruebas.
)

echo.
echo ========================================================
echo 📊 ESTADÍSTICAS DE ARCHIVOS:
echo ========================================================
if exist ms-courses\target\surefire-reports\ (
    echo 📄 Reportes XML: 
    for /f %%a in ('dir ms-courses\target\surefire-reports\*.xml /b ^| find /c /v ""') do echo    %%a archivos XML encontrados
    
    echo.
    echo 📄 Tamaños de archivo:
    for %%f in (ms-courses\target\surefire-reports\*.xml) do (
        echo    %%~nxf: %%~zf bytes
    )
)

echo.
echo ========================================================
echo 📋 DOCUMENTACIÓN TÉCNICA DISPONIBLE:
echo ========================================================
echo.
if exist EVIDENCIAS_PRUEBAS_COURSE.md (
    echo ✅ EVIDENCIAS_PRUEBAS_COURSE.md
) else (
    echo ❌ EVIDENCIAS_PRUEBAS_COURSE.md - No encontrado
)

if exist RESUMEN_REPORTES_COMPLETO.md (
    echo ✅ RESUMEN_REPORTES_COMPLETO.md  
) else (
    echo ❌ RESUMEN_REPORTES_COMPLETO.md - No encontrado
)

if exist ANALISIS_DETALLADO_ENTIDADES.md (
    echo ✅ ANALISIS_DETALLADO_ENTIDADES.md
) else (
    echo ❌ ANALISIS_DETALLADO_ENTIDADES.md - No encontrado
)

echo.
echo ========================================================
echo 🔗 ENLACES DIRECTOS:
echo ========================================================
echo.
echo Reporte Principal:
echo file:///c:/Users/sebas/Desktop/second%%20wind/ms-courses/target/site/surefire-report.html
echo.
echo Sitio del Proyecto:
echo file:///c:/Users/sebas/Desktop/second%%20wind/ms-courses/target/site/index.html
echo.
echo ========================================================
echo ✨ Reportes abiertos en el navegador
echo ========================================================

pause
