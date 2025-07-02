@echo off
echo Running tests for all microservices...

echo.
echo === Testing ms-users ===
cd /d "C:\Edutech-Classrom-Chiikawa\ms-users"
mvn test

echo.
echo === Testing ms-courses ===
cd /d "C:\Edutech-Classrom-Chiikawa\ms-courses"
mvn test

echo.
echo === Testing ms-grades ===
cd /d "C:\Edutech-Classrom-Chiikawa\ms-grades"
mvn test

echo.
echo === Testing ms-payments ===
cd /d "C:\Edutech-Classrom-Chiikawa\ms-payments"
mvn test

echo.
echo === Testing ms-support ===
cd /d "C:\Edutech-Classrom-Chiikawa\ms-support"
mvn test

echo.
echo === Tests completed ===
pause
