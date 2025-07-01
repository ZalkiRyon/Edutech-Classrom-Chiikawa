@echo off
echo ========================================================
echo EJECUTANDO PRUEBAS UNITARIAS DE LA ENTIDAD COURSE
echo CON REPORTES HTML DETALLADOS
echo ========================================================
echo.

echo Ejecutando pruebas específicas de Course en ms-courses...
cd ms-courses

echo.
echo Ejecutando CourseServiceTest...
call mvn clean test -Dtest=CourseServiceTest surefire-report:report
if %ERRORLEVEL% neq 0 (
    echo ERROR: Falló la ejecución de CourseServiceTest
    cd ..
    exit /b 1
)

echo.
echo Verificando si existe CourseControllerTest...
if exist "src\test\java\com\edutech\courses\controller\CourseControllerTest.java" (
    echo Ejecutando CourseControllerTest...
    call mvn test -Dtest=CourseControllerTest surefire-report:report
    if %ERRORLEVEL% neq 0 (
        echo ERROR: Falló la ejecución de CourseControllerTest
        cd ..
        exit /b 1
    )
) else (
    echo CourseControllerTest no encontrado, continuando...
)

echo.
echo Ejecutando todas las pruebas relacionadas con Course...
call mvn test -Dtest=**/*Course* surefire-report:report

cd ..

echo.
echo ========================================================
echo PRUEBAS DE COURSE COMPLETADAS
echo ========================================================
echo.
echo REPORTES GENERADOS:
echo - Reporte HTML: ms-courses/target/site/surefire-report.html
echo - Reportes XML: ms-courses/target/surefire-reports/TEST-*.xml
echo.
echo DESCRIPCIÓN DE LAS PRUEBAS DE COURSE:
echo.
echo CourseServiceTest incluye las siguientes pruebas:
echo 1. findAll_ShouldReturnAllCourses - Verifica que se retornen todos los cursos
echo 2. findById_WhenCourseExists_ShouldReturnCourse - Buscar curso existente
echo 3. findById_WhenCourseNotExists_ShouldThrowResourceNotFoundException - Manejo de curso inexistente
echo 4. create_WithValidData_ShouldCreateAndReturnCourse - Creación exitosa de curso
echo 5. create_WithInvalidCategory_ShouldThrowResourceNotFoundException - Validación de categoría
echo 6. update_WhenCourseExists_ShouldUpdateAndReturnCourse - Actualización exitosa
echo 7. update_WhenCourseNotExists_ShouldThrowResourceNotFoundException - Actualización de curso inexistente
echo 8. delete_WhenCourseExists_ShouldDeleteCourse - Eliminación exitosa
echo 9. delete_WhenCourseNotExists_ShouldThrowResourceNotFoundException - Eliminación de curso inexistente
echo.
echo Para ver los reportes detallados, abre el archivo HTML con tu navegador web.
echo ========================================================

pause
