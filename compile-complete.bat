@echo off
echo ============================================================
echo COMPILACION COMPLETA - EDUTECH CLASSROOM
echo ============================================================
echo.

echo 📦 Compilando proyecto padre...
call mvn clean compile
if %errorlevel% neq 0 (
    echo ❌ Error en compilacion del proyecto padre
    pause
    exit /b 1
)

echo.
echo 🔧 Compilando Common Library...
cd common
call mvn clean compile install
cd ..

echo.
echo 🏗️ Compilando microservicios...
for %%m in (eureka ms-users ms-courses ms-grades ms-payments ms-support) do (
    echo.
    echo 📂 Compilando %%m...
    cd %%m
    call mvn clean compile
    if %errorlevel% neq 0 (
        echo ❌ Error en %%m
        cd ..
        pause
        exit /b 1
    )
    cd ..
)

echo.
echo ✅ COMPILACION COMPLETA EXITOSA
echo ============================================================
pause
