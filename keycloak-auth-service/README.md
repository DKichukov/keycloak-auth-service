# Spring Boot Keycloak Integration

This project demonstrates how to integrate Keycloak with a Spring Boot application for authentication and authorization. It includes features like user management, role-based access control (RBAC), and group management using Keycloak's REST API.

## Features

* **User Management**
    * Create users
    * Send verification emails
    * Delete users
    * Forgot password functionality

* **Role Management**
    * Assign/unassign roles to users

* **Group Management**
    * Assign/unassign users to groups

* **Role-Based Access Control (RBAC)**
    * Restrict access to endpoints based on user roles (e.g., MERGE role)

* **JWT Authentication**
    * Validate and extract roles from Keycloak-issued JWT tokens

## Prerequisites

Before running the project, ensure you have the following installed:

* Java 17 or higher
* Docker (for running Keycloak and PostgreSQL)
* Postman or any API testing tool (for testing endpoints)

## Setup

### 1. Keycloak and PostgreSQL Setup

Run the following command to start Keycloak and PostgreSQL using Docker Compose:

```bash
docker-compose up -d
```

This will start:
* Keycloak on http://localhost:9082
* PostgreSQL as the database for Keycloak

### Keycloak Configuration

1. Access the Keycloak admin console at http://localhost:9082
2. Log in with the admin credentials (set in .env or docker-compose.yml)
3. Create a realm named `spring`
4. Create a client named `spring-app` with the following settings:
    * Client ID: `spring-app`
    * Client authentication: ON
    * Valid redirect URIs: `http://localhost:8088/*`
    * Web origins: `http://localhost:8088`
5. Create roles (e.g.,ADMIN, MERGE, MEMBER)
6. Create groups (optional)

### 2. Environment Variables

Create a `.env` file in the root directory with the following variables:

```env
# Keycloak Configuration
KEYCLOAK_ADMIN=admin
KEYCLOAK_ADMIN_PASSWORD=admin
KEYCLOAK_SERVER_URL=http://localhost:9082
KEYCLOAK_REALM=spring
KEYCLOAK_ADMIN_CLIENT=spring-app
KEYCLOAK_ADMIN_SECRET=your-client-secret

# PostgreSQL Configuration
POSTGRES_DB=keycloak
POSTGRES_USER=keycloak
POSTGRES_PASSWORD=password
KC_DB=postgres
KC_DB_SCHEMA=public
KC_DB_USERNAME=keycloak
KC_DB_PASSWORD=password
KC_DB_URL=jdbc:postgresql://postgres:5432/keycloak
KC_HOSTNAME=localhost
KC_FEATURES=token-exchange,admin-fine-grained-authz
```

### 3. Running the Spring Boot Application

1. Clone the repository:
```bash
git clone https://github.com/your-repo/spring-boot-keycloak.git
cd spring-boot-keycloak
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

The application will start on http://localhost:8088

## API Endpoints

### User Management

* Create User:
```
POST /auth/api/v1/users
{
  "username": "user@gmail.com",
  "password": "Test@1234",
  "firstname": "user",
  "lastname": "test"
}
```

* Send Verification Email: `PUT /auth/api/v1/users/{id}/send-verification-email`
* Delete User: `DELETE /auth/api/v1/users/{id}`
* Forgot Password: `PUT /auth/api/v1/users/forgot-password?username=user@gmail.com`

### Role Management

* Assign Role: `PUT /auth/api/v1/roles/assign/users/{userId}?roleName=MEMBER`
* Unassign Role: `DELETE /auth/api/v1/roles/remove/users/{userId}?roleName=MEMBER`

### Group Management

* Assign User to Group: `PUT /auth/api/v1/groups/{groupId}/assign/users/{userId}`
* Unassign User from Group: `DELETE /auth/api/v1/groups/{groupId}/remove/users/{userId}`

### RBAC Example

* Test Merge Role Access: `GET /auth/api/v1/rbac/merge-role` (Requires MERGE role)

## Testing with Postman

### Get Access Token

* URL: `http://localhost:9082/realms/spring/protocol/openid-connect/token`
* Method: POST
* Body (x-www-form-urlencoded):
    * client_id: spring-app
    * client_secret: your-client-secret
    * grant_type: password
    * username: user@gmail.com
    * password: Test@1234

### Use Access Token

1. Add the token to the Authorization header as a Bearer token
2. Test the endpoints listed above

## Project Structure

```
src/main/java
├── config/              # Configuration classes (Keycloak, Security)
├── controller/          # REST controllers
├── dto/                 # Data Transfer Objects (e.g., UserRegistrationRequest)
└── service/            # Service layer (UserService, RoleService, GroupService)

```

## Troubleshooting

* **Keycloak Not Starting**: Ensure PostgreSQL is running and the database credentials are correct
* **JWT Validation Issues**: Verify the issuer-uri in application.yml matches your Keycloak server URL
* **Role/Group Not Found**: Ensure roles and groups are created in Keycloak
