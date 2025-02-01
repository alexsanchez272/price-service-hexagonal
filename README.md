# Price Service

## Overview
This Spring Boot application provides a REST API for retrieving pricing information from an e-commerce database. 
It implements hexagonal architecture for clean code organization, follows an API-first approach,
and uses Swagger for API documentation.

## Features
- REST endpoint for price consultation
- Hexagonal architecture
- API-first development with Swagger/OpenAPI
- In-memory H2 database
- Comprehensive test suite (unit, integration, end-to-end)
- Spring Boot Actuator for monitoring

## Technologies
- Java 17
- Spring Boot 3.4.2
- H2 Database
- SpringDoc OpenAPI (Swagger)
- RestAssured
- Cucumber
- Spring Boot Actuator

## Project Structure

```plaintext
price-service/
├── src/
│   ├── main/
│   │   ├── java/com/masc/price_service/
│   │   │   ├── domain/
│   │   │   │   ├── models/
│   │   │   │   ├── ports/
│   │   │   │   │   ├── in/
│   │   │   │   │   ├── out/
│   │   │   │   ├── exceptions/
│   │   │   ├── application/
│   │   │   │   ├── services/
│   │   │   │   ├── usecases/
│   │   │   ├── repository/
│   │   │   │   ├── persistence/
│   │   │   │   │   ├── adapters/
│   │   │   │   │   ├── entities/
│   │   │   │   │   ├── repositories/
│   │   │   │   ├── web/
│   │   │   ├── config/
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── schema.sql
│   │   │   ├── data.sql
│   ├── test/
│   │   ├── java/com/masc/price_service/
│   │   │   ├── e2e/
│   │   │   ├── unit/
│   │   │   ├── integration/
│   │   ├── resources/features/
└── pom.xml
```

## Getting Started

### Prerequisites
- Java 17
- Maven

### Running the Application
1. Clone the repository:

   ```bash
   git clone https://github.com/alexsanchez272/price-service-hexagonal.git
   ```

2. Navigate to the project directory:

   ```bash
   cd price-service
   ```

3. Build the project:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`.

### H2 Console Access
1. Open `http://localhost:8080/h2-console`
2. Use these settings:
   ```plaintext
   JDBC URL: jdbc:h2:mem:testdb
   User Name: sa
   Password: (leave empty)
   ```

### API Documentation
Swagger UI: `http://localhost:8080/swagger-ui.html`

## Testing

### Running All Tests

```bash
mvn test
```

### Running E2E Tests

```bash
mvn test -Dtest=CucumberTest
```

## Code Coverage

This project uses JaCoCo for code coverage analysis. To generate the coverage report:

1. Run the tests with coverage:
   ```bash
   mvn clean test
   ```

2. Generate the JaCoCo report:
   ```bash
   mvn jacoco:report
   ```

3. The coverage report will be generated in the `target/site/jacoco` directory. Open the `index.html` file in a web browser to view the report.

## API Usage

**Endpoint:**

```http
GET /api/prices?startDate={date}&productId={id}&brandId={id}
```

**Example:**

```http
GET /api/prices?startDate=2020-06-14T10:00:00&productId=35455&brandId=1
```

## Actuator Endpoints
- Health check: `http://localhost:8080/actuator/health`
- All endpoints: `http://localhost:8080/actuator`

## OpenAPI Specification
- Generated `openapi.json` 
- File location: `target/openapi.json` (after build)

---

### Note
This configuration uses an in-memory database, which means data will be lost when the application stops. For persistent storage, you can change the URL to:

```plaintext
jdbc:h2:file:./data/testdb
```

to save the database to a file.

