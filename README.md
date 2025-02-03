# CONECTA API

This project is a REST API developed with Spring Boot to manage the interaction between educational institutions and companies.

## Requirements

- JDK 17
- Maven
- Docker
- Docker Compose

## Running the project

### Running in Development Mode

```bash
make db-up # Start the database with Docker
make run PROFILE=dev # Run the project in development mode
```

### Running in Production Mode

```bash
make docker-up # Start the database and the application with Docker Compose
```

### Stopping and cleaning

```bash
make docker-down # Stop and remove containers and volumes
make clean # Clean the project
```

### Building and Deployment

```bash
make build # Build the project
make docker-build # Build the Docker image
make docker-run # Run the Docker image
make docker-stop # Stop the Docker container
make docker-rm # Remove the Docker container
```

## API Documentation

The API documentation is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
