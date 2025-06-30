# Generic GraphQL API

A Micronaut-based GraphQL API with MongoDB, Security, and Health monitoring.

## Features

- 🚀 **Micronaut Framework** - Fast, lightweight Java framework
- 📊 **GraphQL API** - Modern API query language with GraphiQL interface
- 🗄️ **MongoDB Integration** - Reactive MongoDB support
- 🔐 **JWT Security** - Token-based authentication
- 💊 **Health Monitoring** - Application health endpoints
- 🧪 **Test Coverage** - JUnit 5 and Testcontainers support

## Dependencies

- **MongoDB** - Document database for data persistence
- **GraphQL** - API query language and runtime
- **Micronaut Health** - Health check endpoints
- **Micronaut Security** - JWT-based authentication

## Quick Start

### Prerequisites

- Java 17 or higher
- MongoDB running on localhost:27017

### Running the Application

```bash
# Clone the repository
git clone <repository-url>
cd generic-graphql-api

# Run the application
./gradlew run
```

### Accessing the API

- **GraphiQL Interface**: http://localhost:8080/graphiql
- **GraphQL Endpoint**: http://localhost:8080/graphql
- **Health Check**: http://localhost:8080/health
- **Application Info**: http://localhost:8080/info

### Authentication

To access protected endpoints, first obtain a JWT token:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password"}'
```

Use the returned token in subsequent requests:

```bash
curl -H "Authorization: Bearer <your-token>" http://localhost:8080/graphql
```

## GraphQL Schema

### Queries

```graphql
# Get all users
query {
  users {
    id
    username
    email
    firstName
    lastName
  }
}

# Get user by ID or username
query {
  user(id: "userId") {
    id
    username
    email
  }
}
```

### Mutations

```graphql
# Create a new user
mutation {
  createUser(input: {
    username: "john_doe"
    email: "john@example.com"
    firstName: "John"
    lastName: "Doe"
  }) {
    id
    username
    email
  }
}
```

## Development

### Build the Application

```bash
./gradlew build
```

### Run Tests

```bash
./gradlew test
```

### Generate Shadow JAR

```bash
./gradlew shadowJar
```

## Configuration

The application can be configured via `src/main/resources/application.yml`:

- MongoDB connection string
- Server port
- Security settings
- GraphQL configuration
- Health endpoint settings

## Project Structure

```
src/
├── main/
│   ├── java/lokinious/graphql_api/
│   │   ├── Application.java           # Main application class
│   │   ├── controller/                # REST controllers
│   │   ├── graphql/                   # GraphQL data fetchers and factory
│   │   ├── health/                    # Health indicators
│   │   ├── model/                     # Domain models
│   │   ├── repository/                # Data repositories
│   │   ├── security/                  # Security configuration
│   │   └── service/                   # Business logic services
│   └── resources/
│       ├── application.yml            # Application configuration
│       └── schema.graphql             # GraphQL schema definition
└── test/
    ├── java/                          # Test classes
    └── resources/
        └── application-test.yml       # Test configuration
```
