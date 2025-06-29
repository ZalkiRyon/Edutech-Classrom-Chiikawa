@echo off
echo Corrigiendo configuraciones de annotation processors...

echo.
echo === Corrigiendo ms-grades ===
cd "c:\Users\sebas\Desktop\second wind\ms-grades"
powershell -Command "(Get-Content pom.xml) -replace '<!-- Plugin de compilacion para procesadores de anotaciones \(MapStruct \+ Lombok\) -->', '<!-- Plugin de compilacion - SIMPLIFICADO TEMPORALMENTE -->' | Set-Content pom.xml"
powershell -Command "(Get-Content pom.xml) -replace '<plugin>\s*<groupId>org.apache.maven.plugins</groupId>\s*<artifactId>maven-compiler-plugin</artifactId>\s*<configuration>\s*<parameters>true</parameters>\s*<annotationProcessorPaths>.*?</annotationProcessorPaths>\s*</configuration>\s*</plugin>', '<!-- PLUGIN COMENTADO TEMPORALMENTE -->' | Set-Content pom.xml"

echo.
echo === Corrigiendo ms-payments ===
cd "c:\Users\sebas\Desktop\second wind\ms-payments"
powershell -Command "(Get-Content pom.xml) -replace '<!-- Plugin de compilacion para procesadores de anotaciones \(MapStruct \+ Lombok\) -->', '<!-- Plugin de compilacion - SIMPLIFICADO TEMPORALMENTE -->' | Set-Content pom.xml"

echo.
echo === Corrigiendo ms-support ===
cd "c:\Users\sebas\Desktop\second wind\ms-support"
powershell -Command "(Get-Content pom.xml) -replace '<!-- Plugin de compilacion para procesadores de anotaciones \(MapStruct \+ Lombok\) -->', '<!-- Plugin de compilacion - SIMPLIFICADO TEMPORALMENTE -->' | Set-Content pom.xml"

echo.
echo === Correccion completada ===
echo Verificando compilacion...

cd "c:\Users\sebas\Desktop\second wind\ms-users"
mvn clean compile

echo.
pause
