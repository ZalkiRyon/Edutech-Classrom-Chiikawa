@echo off
echo === PRUEBAS DE ENDPOINTS IMPLEMENTADOS ===
echo.
echo Esperando que ms-courses este ejecutandose en puerto 9002...
timeout /t 10 /nobreak
echo.

echo === ENDPOINT 1: Obtener cursos por categoria ===
echo GET /api/course-categories/1/courses
curl -X GET "http://localhost:9002/api/course-categories/1/courses" -H "Accept: application/json" -s | jq . || echo "Error: Verificar que el servicio este ejecutandose y jq este instalado"
echo.

echo === ENDPOINT 2: Obtener contenidos por curso ===
echo GET /api/course-contents/course/1
curl -X GET "http://localhost:9002/api/course-contents/course/1" -H "Accept: application/json" -s | jq . || echo "Error: Verificar que el servicio este ejecutandose y jq este instalado"
echo.

echo === PRUEBAS COMPLETADAS ===
pause
