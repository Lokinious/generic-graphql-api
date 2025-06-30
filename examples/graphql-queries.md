# Example GraphQL Queries

## Authentication
First, get a JWT token by calling the login endpoint:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password"}'
```

## GraphQL Queries

### Get All Users
```graphql
query GetAllUsers {
  users {
    id
    username
    email
    firstName
    lastName
  }
}
```

### Get User by ID
```graphql
query GetUserById($id: String!) {
  user(id: $id) {
    id
    username
    email
    firstName
    lastName
  }
}
```

### Get User by Username
```graphql
query GetUserByUsername($username: String!) {
  user(username: $username) {
    id
    username
    email
    firstName
    lastName
  }
}
```

### Create User
```graphql
mutation CreateUser($input: CreateUserInput!) {
  createUser(input: $input) {
    id
    username
    email
    firstName
    lastName
  }
}
```

Variables for CreateUser:
```json
{
  "input": {
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }
}
```

## Using with Authorization Header

When making GraphQL requests to protected endpoints, include the JWT token:

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"query": "{ users { id username email } }"}'
```
