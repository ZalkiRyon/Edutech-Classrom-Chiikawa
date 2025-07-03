@echo off
chcp 65001 > nul
echo ==========================================
echo COMANDO SIMPLE PARA INFORMES SUREFIRE
echo ==========================================
echo.

cd /d "%~dp0"

echo Ejecutando tests y generando reportes (sin fork de JVM)...
echo.

REM Limpiar primero
mvn clean

REM Ejecutar tests con configuracion sin fork
mvn test -Dmaven.test.failure.ignore=true

REM Generar reportes Surefire
mvn surefire-report:report

echo.
echo ==========================================
echo COMPLETADO
echo ==========================================
echo.
echo Revisar reportes en:
echo - target\site\surefire-report.html
echo - [modulo]\target\site\surefire-report.html
echo.
pause
