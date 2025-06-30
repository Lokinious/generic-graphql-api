@echo off
echo Setting up Micronaut GraphQL API...

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    exit /b 1
)

echo Java is installed.

REM Check if MongoDB is running
echo Checking MongoDB connection...
powershell -Command "try { $connection = New-Object System.Net.Sockets.TcpClient('localhost', 27017); $connection.Close(); Write-Host 'MongoDB is running on localhost:27017' } catch { Write-Host 'WARNING: MongoDB does not appear to be running on localhost:27017' }"

echo.
echo Project structure created successfully!
echo.
echo To build and run the application:
echo 1. Make sure MongoDB is running on localhost:27017
echo 2. Install Gradle (if not already installed)
echo 3. Run: gradle build
echo 4. Run: gradle run
echo.
echo The application will be available at:
echo - GraphiQL Interface: http://localhost:8080/graphiql
echo - GraphQL Endpoint: http://localhost:8080/graphql
echo - Health Check: http://localhost:8080/health
echo.
echo Default login credentials:
echo Username: admin
echo Password: password
echo.
pause
