@echo off
echo Running tests for all microservices...

echo.
echo === Testing ms-users ===
cd /d "c:\Users\sebas\Desktop\second wind\ms-users"
mvn test

echo.
echo === Testing ms-courses ===
cd /d "c:\Users\sebas\Desktop\second wind\ms-courses"
mvn test

echo.
echo === Testing ms-grades ===
cd /d "c:\Users\sebas\Desktop\second wind\ms-grades"
mvn test

echo.
echo === Testing ms-payments ===
cd /d "c:\Users\sebas\Desktop\second wind\ms-payments"
mvn test

echo.
echo === Testing ms-support ===
cd /d "c:\Users\sebas\Desktop\second wind\ms-support"
mvn test

echo.
echo === Tests completed ===
pause
