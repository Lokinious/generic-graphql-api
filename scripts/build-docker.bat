@echo off
REM Docker build script for Windows

echo 🐳 Building Docker image for Micronaut GraphQL API...

REM Build the Docker image
docker build -t lokinious/graphql-api:latest .

if %ERRORLEVEL% EQU 0 (
    echo ✅ Docker image built successfully!
    echo 📦 Image: lokinious/graphql-api:latest
    echo.
    echo 🚀 To run the complete stack:
    echo    docker-compose up -d
    echo.
    echo 🚀 To run just the app ^(if MongoDB is running separately^):
    echo    docker run -p 8080:8080 -e MONGODB_URI=mongodb://host.docker.internal:27017/generic-graphql-api lokinious/graphql-api:latest
    echo.
    echo 🔍 To check the image size:
    echo    docker images lokinious/graphql-api:latest
) else (
    echo ❌ Docker build failed!
    exit /b 1
)
