# 🐳 Docker Deployment Guide

## Quick Start with Docker

### **Option 1: Complete Stack (Recommended)**
```bash
# Build and start everything (app + MongoDB)
docker-compose up -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f app
```

### **Option 2: Build and Run Manually**
```bash
# Build the Docker image
docker build -t lokinious/graphql-api:latest .

# Run with external MongoDB
docker run -p 8080:8080 \
  -e MONGODB_URI=mongodb://host.docker.internal:27017/generic-graphql-api \
  lokinious/graphql-api:latest
```

### **Option 3: Use Build Scripts**
```bash
# On Linux/macOS
./scripts/build-docker.sh

# On Windows
scripts\build-docker.bat
```

## 🔧 Configuration

### **Environment Variables**
| Variable | Default | Description |
|----------|---------|-------------|
| `PORT` | `8080` | Application port |
| `MONGODB_URI` | `mongodb://mongodb:27017/generic-graphql-api` | MongoDB connection |
| `JWT_SECRET` | `pleaseChangeThisSecretForProduction` | JWT signing secret |
| `GRAPHQL_ENABLED` | `true` | Enable GraphQL endpoints |
| `GRAPHIQL_ENABLED` | `true` | Enable GraphiQL interface |
| `LOG_LEVEL` | `INFO` | Application log level |

### **Docker Compose Override**
Create `docker-compose.override.yml` for local customizations:
```yaml
version: '3.8'
services:
  app:
    environment:
      - LOG_LEVEL=DEBUG
      - JWT_SECRET=myCustomSecret
    ports:
      - "8081:8080"  # Custom port mapping
```

## 🚀 Deployment Commands

### **Development**
```bash
# Start with live logs
docker-compose up

# Start in background
docker-compose up -d

# Restart just the app
docker-compose restart app

# Rebuild and restart
docker-compose up -d --build app
```

### **Production**
```bash
# Use production environment
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d

# Or with environment variables
MICRONAUT_ENVIRONMENTS=production docker-compose up -d
```

## 🔍 Health Checks & Monitoring

### **Health Endpoints**
- **Application Health**: http://localhost:8080/health
- **Application Info**: http://localhost:8080/info
- **GraphiQL**: http://localhost:8080/graphiql

### **Docker Health Checks**
```bash
# Check container health
docker inspect --format='{{.State.Health.Status}}' generic-graphql-api-app

# View health check logs
docker inspect --format='{{range .State.Health.Log}}{{.Output}}{{end}}' generic-graphql-api-app
```

## 🛠️ Troubleshooting

### **Common Issues**

**1. Application won't start**
```bash
# Check logs
docker-compose logs app

# Check MongoDB connectivity
docker-compose exec app curl -f http://mongodb:27017
```

**2. Port conflicts**
```bash
# Use different port
docker run -p 8081:8080 lokinious/graphql-api:latest
```

**3. MongoDB connection issues**
```bash
# Check MongoDB is running
docker-compose logs mongodb

# Test MongoDB connection
docker-compose exec mongodb mongosh --eval "db.adminCommand('ping')"
```

### **Debug Mode**
```bash
# Run with debug logging
docker run -p 8080:8080 \
  -e LOG_LEVEL=DEBUG \
  -e MICRONAUT_ENVIRONMENTS=docker \
  lokinious/graphql-api:latest
```

## 📊 Image Information

- **Base Image**: OpenJDK 21 Slim
- **Final Image Size**: ~200-300MB (optimized)
- **Security**: Runs as non-root user
- **Health Checks**: Built-in health monitoring
- **Multi-stage Build**: Optimized for production

## 🔐 Security Considerations

### **Production Checklist**
- [ ] Change default JWT secret (`JWT_SECRET`)
- [ ] Use strong MongoDB credentials
- [ ] Disable GraphiQL in production (`GRAPHIQL_ENABLED=false`)
- [ ] Use HTTPS/TLS termination
- [ ] Configure proper logging levels
- [ ] Set up monitoring and alerting

### **Network Security**
```yaml
# Example production docker-compose
services:
  app:
    environment:
      - GRAPHIQL_ENABLED=false
      - LOG_LEVEL=WARN
    networks:
      - internal
  mongodb:
    networks:
      - internal
networks:
  internal:
    internal: true  # No external access
```

## 🚀 CI/CD Integration

### **GitHub Actions Example**
```yaml
- name: Build Docker Image
  run: docker build -t lokinious/graphql-api:${{ github.sha }} .

- name: Run Tests in Docker
  run: |
    docker run --rm \
      -e MICRONAUT_ENVIRONMENTS=test \
      lokinious/graphql-api:${{ github.sha }} \
      ./gradlew test
```
