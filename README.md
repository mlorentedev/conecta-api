# CONECTA API

This project is a REST API developed with Spring Boot to manage the interaction between educational institutions and companies.

## Requirements

- JDK 17
- Maven
- Docker
- Docker Compose

## Project Structure

```plaintext
conecta-api
├── src
│   ├── main
│   │   ├── java.com.conecta
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── exception
│   │   │   ├── model
│   │   │   ├── repository
│   │   │   ├── service
│   │   │   └── App.java
│   │   └── resources
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       ├── application.properties
│   │       ├── data-dev.sql
│   |       ├── data-prod.sql
│   │       └── schema-dev.sql
|   |       └── schema-prod.sql
├── .gitignore
├── .App-conecta.postman_collection.json
├── LICENSE
|── docker-compose.yml
├── Dockerfile
├── Makefile
├── README.md
└── pom.xml
```

## Running the project

### Running in Development Mode

```bash
make run # Run the project in development mode
```

### Cleaning in Development Mode

```bash
make clean # Clean the project
```

### Running in Production Mode

```bash
make up     # Start the database and the application with Docker Compose
```

### Stopping in Production Mode

```bash
make docker-down    # Stop and remove containers and volumes
```

### Building and Deployment

```bash
make build          # Build the project
make docker-build   # Build the Docker image
make docker-run     # Run the Docker image
make docker-stop    # Stop the Docker container
make docker-rm      # Remove the Docker container
```

## Profiles

`dev`: Development profile with H2 database  
`prod`: Production profile with PostgreSQL database and dockerized

## API Documentation

Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
OpenAPI: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Postman Collection

[App-conecta.postman_collection.json](App-conecta.postman_collection.json)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
