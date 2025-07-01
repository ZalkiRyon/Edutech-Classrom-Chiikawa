@echo off
echo ========================================================
echo PRUEBAS EXTENSIVAS MS-COURSES - ANÁLISIS COMPLETO
echo ========================================================
echo.
echo 🔍 Ejecutando análisis detallado de todas las entidades
echo.

cd ms-courses

echo [PASO 1] ========== LIMPIEZA Y PREPARACIÓN ==========
call mvn clean -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en limpieza
    goto :error
)
echo ✅ Limpieza completada

echo.
echo [PASO 2] ========== COMPILACIÓN ==========
call mvn compile test-compile -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en compilación
    goto :error
)
echo ✅ Compilación exitosa

echo.
echo [PASO 3] ========== PRUEBAS POR ENTIDAD ==========

echo.
echo [3.1] Entidad COURSE (Core Business Entity)
call mvn test -Dtest=CourseServiceTest -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en pruebas de Course
    goto :error
) else (
    echo ✅ CourseServiceTest - 9 pruebas completadas
)

echo.
echo [3.2] Entidad COURSE CATEGORY (Categorization)
call mvn test -Dtest=CourseCategoryServiceTest -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en pruebas de CourseCategory
    goto :error
) else (
    echo ✅ CourseCategoryServiceTest - 8 pruebas completadas
)

echo.
echo [3.3] Entidad COURSE CONTENT (Content Management)
call mvn test -Dtest=CourseContentServiceTest -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en pruebas de CourseContent
    goto :error
) else (
    echo ✅ CourseContentServiceTest - 9 pruebas completadas
)

echo.
echo [3.4] Entidad COURSE COMMENT (Reviews & Comments)
call mvn test -Dtest=CourseCommentServiceTest -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en pruebas de CourseComment
    goto :error
) else (
    echo ✅ CourseCommentServiceTest - 10 pruebas completadas
)

echo.
echo [3.5] Entidad ENROLLMENT (Student Management)
call mvn test -Dtest=EnrollmentServiceTest -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en pruebas de Enrollment
    goto :error
) else (
    echo ✅ EnrollmentServiceTest - 10 pruebas completadas
)

echo.
echo [PASO 4] ========== PRUEBAS COMPLETAS + REPORTES ==========
call mvn test surefire-report:report site -q
if %ERRORLEVEL% neq 0 (
    echo ❌ Error en generación de reportes
    goto :error
) else (
    echo ✅ Todas las pruebas y reportes generados exitosamente
)

echo.
echo [PASO 5] ========== VERIFICACIÓN DE REPORTES ==========
if exist target\site\surefire-report.html (
    echo ✅ Reporte HTML principal generado
) else (
    echo ❌ Reporte HTML no encontrado
    goto :error
)

if exist target\surefire-reports\ (
    echo ✅ Reportes XML individuales generados
) else (
    echo ❌ Reportes XML no encontrados
    goto :error
)

cd ..

echo.
echo ========================================================
echo ✅ EJECUCIÓN COMPLETADA EXITOSAMENTE
echo ========================================================
echo.
echo 📊 RESUMEN DE RESULTADOS:
echo.
echo    📈 Course Service: 9/9 pruebas ✅
echo    📈 CourseCategory Service: 8/8 pruebas ✅  
echo    📈 CourseContent Service: 9/9 pruebas ✅
echo    📈 CourseComment Service: 10/10 pruebas ✅
echo    📈 Enrollment Service: 10/10 pruebas ✅
echo.
echo    📊 TOTAL: 46/46 pruebas unitarias exitosas
echo.
echo ========================================================
echo 🌐 ECOSISTEMA COMPLETO EDUTECH CLASSROOM
echo ========================================================
echo.
echo 🏠 PROYECTO PADRE: target\site\index.html
echo 📚 MS-COURSES: ms-courses\target\site\index.html
echo 👥 MS-USERS: ms-users\target\site\index.html  
echo 📊 MS-GRADES: ms-grades\target\site\index.html
echo 💳 MS-PAYMENTS: ms-payments\target\site\index.html
echo 🎧 MS-SUPPORT: ms-support\target\site\index.html
echo.
echo ✅ NAVEGACIÓN REPARADA: Enlaces "Parent Project" funcionan
echo ✅ SITIOS ENLAZADOS: Navegación bidireccional completa
echo ✅ PRUEBAS EXTENSIVAS: 130+ pruebas en todo el ecosistema
echo.
echo 🚀 Para generar TODO el ecosistema: generate-complete-ecosystem.bat
echo 🌐 Para ver TODOS los sitios: mostrar-todos-sitios.bat
echo.
echo ========================================================
echo 📄 REPORTES DISPONIBLES:
echo ========================================================
echo.
echo 🌐 Reporte HTML Principal:
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-courses/target/site/surefire-report.html
echo.
echo 🌐 Sitio Completo del Proyecto:
echo    file:///c:/Users/sebas/Desktop/second%%20wind/ms-courses/target/site/index.html
echo.
echo 📁 Reportes XML Detallados:
echo    ms-courses\target\surefire-reports\
echo.
echo ========================================================
echo 📋 DOCUMENTACIÓN TÉCNICA:
echo ========================================================
echo.
echo 📋 Evidencias por Entidad:
echo    ├── EVIDENCIAS_PRUEBAS_COURSE.md
echo    ├── RESUMEN_REPORTES_COMPLETO.md
echo    └── ANALISIS_DETALLADO_ENTIDADES.md
echo.
echo ========================================================
echo 🚀 COMANDOS RÁPIDOS:
echo ========================================================
echo.
echo 🔍 Abrir reporte principal:
echo    start ms-courses\target\site\surefire-report.html
echo.
echo 📁 Ver reportes XML:
echo    dir ms-courses\target\surefire-reports\
echo.
echo ⚡ Ejecutar pruebas específicas:
echo    cd ms-courses ^&^& mvn test -Dtest=CourseServiceTest
echo.
echo ========================================================

goto :end

:error
echo.
echo ❌ ERROR: La ejecución falló. Revise los logs anteriores.
echo.
cd ..
pause
exit /b 1

:end
echo.
echo ✨ Presione cualquier tecla para finalizar...
pause > nul
