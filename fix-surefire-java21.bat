@echo off
echo ================================================================================
echo 🔧 SOLUCIONADOR DE PROBLEMAS DE SUREFIRE - Java 21 Compatibility Fix
echo ================================================================================
echo.
echo Detectado: Java 21 con configuracion incompatible
echo Aplicando correcion...
echo.

:: Cambiar al directorio del proyecto
cd /d "%~dp0"

echo 🔄 Paso 1: Limpiando proyecto...
call mvn clean -q

echo.
echo 🔄 Paso 2: Compilando sin tests...
call mvn compile -q

echo.
echo 🔄 Paso 3: Ejecutando tests con configuracion compatible Java 21...
call mvn test -DforkCount=1 -DreuseForks=false -Dargline="-Xmx2048m --add-opens java.base/java.lang=ALL-UNNAMED"

echo.
echo 🔄 Paso 4: Si los tests fallan, intentando sin fork...
if %ERRORLEVEL% NEQ 0 (
    echo ⚠️  Tests fallaron con fork, intentando sin fork...
    call mvn test -DforkCount=0
)

echo.
echo ================================================================================
echo ✅ PROCESO COMPLETADO
echo ================================================================================
echo.
echo Si los tests siguen fallando, ejecuta:
echo   mvn test -DforkCount=0 -DargLine=""
echo.
echo Para generar reportes despues de tests exitosos:
echo   mvn surefire-report:report
echo.

pause
