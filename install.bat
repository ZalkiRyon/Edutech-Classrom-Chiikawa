@echo off
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.

REM Paso 1: Eliminar carpeta local de dependencias
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2

REM Paso 2: Eliminar carpetas target de los proyectos
echo Eliminando carpetas target ...
rmdir /s /q "C:\Edutech-Classrom-Chiikawa\common\target"
rmdir /s /q "C:\Edutech-Classrom-Chiikawa\eureka\target"
rmdir /s /q "C:\Edutech-Classrom-Chiikawa\ms-courses\target"
rmdir /s /q "C:\Edutech-Classrom-Chiikawa\ms-grades\target"
rmdir /s /q "C:\Edutech-Classrom-Chiikawa\ms-payments\target"
rmdir /s /q "C:\Edutech-Classrom-Chiikawa\ms-support\target"
rmdir /s /q "C:\Edutech-Classrom-Chiikawa\ms-users\target"

REM Paso 3: Instalar todas las dependencias forzadamente
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests

echo.
echo === PROCESO COMPLETADO ===
pause
