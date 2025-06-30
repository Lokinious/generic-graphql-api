# 🛠️ Custom Gradle Docker Tasks

## ✅ Available Docker Tasks

Your project now includes custom Gradle tasks for Docker operations:

### **`buildDockerImage`** - Main Docker Build Task
```bash
./gradlew buildDockerImage
```

**What it does:**
- ✅ Builds the shadow JAR (depends on `shadowJar` task)
- ✅ Creates Docker image named `generic-graphql-api:0.1`
- ✅ Also tags as `generic-graphql-api:latest`
- ✅ Keeps image **locally only** (no push to registry)
- ✅ Shows helpful output with run commands

### **`dockerRun`** - Build and Run
```bash
./gradlew dockerRun
```

**What it does:**
- ✅ Builds the Docker image (depends on `buildDockerImage`)
- ✅ Runs the container with correct port mapping
- ✅ Sets up MongoDB connection for external database
- ✅ Uses `--rm` flag for automatic cleanup

## 🎯 Usage Examples

### **Build Image Only:**
```bash
# Build the Docker image locally
./gradlew buildDockerImage
```

**Output:**
```
🐳 Building Docker image: generic-graphql-api:0.1
📦 Also tagging as: generic-graphql-api:latest
✅ Docker image built successfully!
🚀 Run with: docker run -p 8080:8080 generic-graphql-api:latest
🔍 Image size: docker images generic-graphql-api
```

### **Build and Run:**
```bash
# Build and immediately run the container
./gradlew dockerRun
```

### **Run Manually After Build:**
```bash
# After building, run manually with custom options
docker run -p 8080:8080 generic-graphql-api:latest
```

## 🔧 Task Configuration

The tasks are configured to:
- **Image Name**: `generic-graphql-api` (matches your repository name)
- **Version Tag**: Uses the `version` from [`build.gradle`](build.gradle ) (currently `0.1`)
- **Latest Tag**: Always creates a `latest` tag for convenience
- **Local Only**: No automatic pushing to any registry
- **Dependencies**: Automatically builds the shadow JAR first

## 📋 Task Dependencies

```
buildDockerImage
    └── shadowJar
            └── classes
                    ├── compileJava
                    └── processResources

dockerRun
    └── buildDockerImage
            └── shadowJar
                    └── (continues as above)
```

## 🎯 Why This Setup?

1. **Repository Name**: Image is named `generic-graphql-api` to match your repo
2. **Local Development**: No accidental pushes to registries
3. **Gradle Integration**: Seamlessly integrates with your build process
4. **Version Management**: Uses your project version automatically
5. **Convenience**: Easy-to-remember task names in the `docker` group

## 🚀 Quick Commands Summary

```bash
# List all Docker tasks
./gradlew tasks --group=docker

# Build Docker image
./gradlew buildDockerImage

# Build and run container
./gradlew dockerRun

# Check what would be executed (dry run)
./gradlew buildDockerImage --dry-run
```

Your Docker workflow is now fully integrated with Gradle! 🎉
