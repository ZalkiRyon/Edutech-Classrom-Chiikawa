@echo off
echo === PRUEBAS COMPLETAS DE COURSE CONTENT ENDPOINTS ===
echo.
echo Esperando que ms-courses este ejecutandose en puerto 9002...
timeout /t 5 /nobreak
echo.

echo === 1. Obtener todos los contenidos ===
echo GET /api/course-contents
curl -X GET "http://localhost:9002/api/course-contents" -H "Accept: application/json" -s | jq . || echo "Error: Verificar que el servicio este ejecutandose"
echo.

echo === 2. Obtener contenido especifico por ID ===
echo GET /api/course-contents/1
curl -X GET "http://localhost:9002/api/course-contents/1" -H "Accept: application/json" -s | jq . || echo "Error: Verificar que el servicio este ejecutandose"
echo.

echo === 3. Obtener contenidos por curso ID ===
echo GET /api/course-contents/course/1
curl -X GET "http://localhost:9002/api/course-contents/course/1" -H "Accept: application/json" -s | jq . || echo "Error: Verificar que el servicio este ejecutandose"
echo.

echo === PRUEBAS DE COURSE CONTENT COMPLETADAS ===
pause
