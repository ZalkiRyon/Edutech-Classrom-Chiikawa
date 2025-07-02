@echo off
cd /d "C:\Edutech-Classrom-Chiikawa\ms-users"
echo Compiling ms-users...
mvn clean compile
echo.
echo Testing ms-users...
mvn test
pause
