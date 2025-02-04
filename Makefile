.DEFAULT_GOAL := help
PROFILE = dev
SPRING_PORT = 8080
APP_NAME = conecta-api
APP_VERSION = 0.0.1
DOCKER_IMAGE = $(APP_NAME):latest
DOCKER_CONTAINER = $(APP_NAME)-container

.PHONY: help
help: ## Show this help message
	@grep -E '^\S+:.*?## .*$$' $(firstword $(MAKEFILE_LIST)) | awk 'BEGIN {FS = ":.*?## "}; {printf "%-30s %s\n", $$1, $$2}'

.PHONY: build
build: ## Build the application
		@echo "Building the application..."
		mvn clean package -DskipTests

.PHONY: run
run: build ## Run the application
		@echo "Running the application with the profile $(PROFILE) and the port $(SPRING_PORT)..."
		@if [ -z "$(PROFILE)" ]; then echo "PROFILE is not set!"; exit 1; fi
		@if [ -z "$(SPRING_PORT)" ]; then echo "SPRING_PORT is not set!"; exit 1; fi
		java -jar -Dspring.profiles.active=$(PROFILE) -Dserver.port=$(SPRING_PORT) target/conecta-api-$(APP_VERSION).jar

.PHONY: clean
clean: ## Clean the application
		@echo "Cleaning the application..."
		mvn clean

.PHONY: compile
compile: ## Compile the application
		@echo "Compiling the application..."
		mvn compile

.PHONY: db-up
db-up: ## Start the database
		@echo "Starting the database..."
		docker compose up -d db

.PHONY: db-down
db-down: ## Stop the database
		@echo "Stopping the database..."
		docker compose down

.PHONY: docker-build
docker-build: compile ## Build the Docker image
		@echo "Building the Docker image $(DOCKER_IMAGE)..."
		docker build --no-cache -t $(DOCKER_IMAGE) .

.PHONY: docker-run
docker-run: ## Run the Docker container
		@echo "Running the Docker container $(DOCKER_CONTAINER) with the image $(DOCKER_IMAGE)..."
		docker run -d -p $(SPRING_PORT):$(SPRING_PORT) --name $(DOCKER_CONTAINER) $(DOCKER_IMAGE)

.PHONY: docker-stop
docker-stop: ## Stop the Docker container
		@echo "Stopping the Docker container $(DOCKER_CONTAINER)..."
		docker stop $(DOCKER_CONTAINER)

.PHONY: docker-rm
docker-rm: ## Remove the Docker container
		@echo "Removing the Docker container $(DOCKER_CONTAINER)..."
		docker rm $(DOCKER_CONTAINER)

.PHONY: up
up: ## Start the entire infrastructure (API and database)
		@echo "Start the entire infrastructure (API and database)"
		docker compose up -d

.PHONY: down
down: ## Stop the entire infrastructure (API and database)
		@echo "Stop the entire infrastructure (API and database)"
		docker compose down --volumes
