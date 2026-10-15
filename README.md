# User Service

A REST API for managing users and their payment cards, built with Java and Spring Boot.

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Liquibase
- Redis
- MapStruct and Lombok
- Maven
- JUnit, Mockito and Testcontainers
- Docker and Docker Compose
- GitHub Actions and SonarQube Cloud

## Environment Configuration

The project uses environment variables to configure database and Redis connections.

An example configuration is provided in `.env.example`.

Before running the application with Docker Compose, create a `.env` file in the project root by copying `.env.example`:

Update the values in `.env` if necessary. You can use your own database name, username, and password, provided they match your PostgreSQL configuration.

- `DB_NAME` — PostgreSQL database name.
- `DB_USERNAME` — PostgreSQL username.
- `DB_PASSWORD` — PostgreSQL password.
- `DB_URL` — JDBC connection URL used by the application.
- `REDIS_HOST` — Redis hostname.
- `REDIS_PORT` — Redis port.


## Running Locally

1. Make sure PostgreSQL and Redis are running.
2. Open `CourseProjectApplication` in IntelliJ IDEA.
3. In the run configuration, set **Active profiles** to `local`.
4. Run the application.

Make sure the database connection settings and credentials are configured correctly.

The API is available at:

`http://localhost:8080`

Health check:

`http://localhost:8080/actuator/health`

## Running with Docker

Make sure Docker Desktop or Docker Engine is running.

### 1. Build the application

Open PowerShell in the project root and execute:

```powershell
.\mvnw.cmd clean package -DskipTests
```

Wait for `BUILD SUCCESS`.

### 2. Build and start the containers

```powershell
docker compose up --build -d
```

Check the container status:

```powershell
docker compose ps
```

Check application logs:

```powershell
docker compose logs --tail=100 app
```

Check application health:

`http://localhost:8080/actuator/health`

To stop the containers:

```powershell
docker compose down
```

The database volume is preserved when using `docker compose down`. To remove it as well, run `docker compose down -v`. This deletes persisted database data.

## Running Tests

Run the tests using the Maven Wrapper.

On Windows:

```powershell
.\mvnw.cmd clean verify
```

On Linux or macOS:

```bash
./mvnw clean verify
```

The project includes unit tests and integration tests. Integration tests using Testcontainers require Docker to be available.

## API Endpoints

Base URL: `http://localhost:8080`

### Users

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users` | Create a user |
| GET | `/api/users` | Get users with optional filters and pagination |
| GET | `/api/users/{id}` | Get a user by ID |
| PUT | `/api/users/{id}` | Update a user |
| PATCH | `/api/users/{id}/activate` | Activate a user |
| PATCH | `/api/users/{id}/deactivate` | Deactivate a user |
| DELETE | `/api/users/{id}` | Delete a user |
| GET | `/api/users/active?name=Anna` | Find active users by name |
| GET | `/api/users/without-cards` | Find users without cards |
| GET | `/api/users/by-email?email=anna@example.com` | Find a user by email |

### Payment Cards

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/cards` | Create a payment card |
| GET | `/api/cards` | Get cards with pagination |
| GET | `/api/cards/{id}` | Get a card by ID |
| GET | `/api/cards/user/{userId}` | Get cards by user ID |
| PUT | `/api/cards/{id}` | Update a card |
| PATCH | `/api/cards/{id}/activate` | Activate a card |
| PATCH | `/api/cards/{id}/deactivate` | Deactivate a card |
| DELETE | `/api/cards/{id}` | Delete a card |

## CI Pipeline

GitHub Actions is configured to build and test the project, run code analysis with SonarQube Cloud, and build the Docker image.

The SonarQube token is configured as a GitHub Actions repository secret named `SONAR_TOKEN`.

## Configuration and Security

- Use the `local` Spring profile when running the application directly from IntelliJ IDEA.
- Use the Docker-specific configuration when running through Docker Compose.
- Do not commit passwords, tokens, or other secrets to the repository.
