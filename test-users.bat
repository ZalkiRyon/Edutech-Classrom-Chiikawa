@echo off
cd /d "c:\Users\sebas\Desktop\second wind\ms-users"
echo Compiling ms-users...
mvn clean compile
echo.
echo Testing ms-users...
mvn test
pause
