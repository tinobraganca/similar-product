
![Diagram](./assets/diagram.jpg "Diagram")

# Similar Products API - Spring Boot

##  Overview

This project is a **Spring Boot REST API** that finds similar products based on a given product ID. The implementation follows:
 
- **Spring Boot with OpenAPI documentation**
- **Feign Client to connect with external services**
- **Metrics collection using Prometheus & Grafana**
- **Automated tests**

##  Features

- **Get similar products** using REST API (`GET /product/{productId}/similar`)
- **Handles errors** for missing products and server issues
- **OpenAPI documentation** with Swagger UI
- **Uses Feign Client** to fetch product data
- **Includes unit tests** for API and service
- **Collects metrics with** Prometheus and sends them to Grafana

## Automated Tests

This project follows TDD principles and includes automated tests to ensure reliability and stability:

- **Unit Tests**: Validates individual components using JUnit & Mockito.

- **Controller Tests**: Uses MockMvc to simulate API requests and validate responses.

- **Service Tests**: Ensures business logic correctness by mocking external dependencies.

##  How to Run

### 1️⃣ Run with Docker

```sh
docker-compose up --build
```

### 2️⃣ API Documentation

Swagger UI: [http://localhost:5000/swagger-ui.html](http://localhost:5000/swagger-ui.html)

##  API Endpoints

### **1️⃣ Get Similar Products**

**Endpoint:** `GET /product/{productId}/similar`

| Response Code | Description              |
| ------------- | ------------------------ |
| 200           | Returns similar products |
| 404           | Product not found        |
| 500           | Internal server error    |

### Metrics Monitoring with Prometheus & Grafana

This project includes metrics collection using Micrometer & Prometheus, sending data to Grafana for visualization.

- **Prometheus scrapes metrics from** http://localhost:5000/actuator/prometheus

- **Grafana visualizes metrics from Prometheus**

- **Dashboards include HTTP requests, CPU, memory, and JVM metrics**

## ️Technologies Used
- **Java 21**
- **Spring Boot** (REST API)
- **Feign Client** (External API calls)
- **JUnit5 & Mockito** (Testing)
- **OpenAPI/Swagger** (API Documentation)
- **Docker & Docker Compose** (Containerization)
- **Gradle** (Dependency Management)

## 🔍 Important

This project follows the same folder structure and Docker Compose setup as the base challenge. It is designed to run inside the same Docker network as the other services.
❗ I don't know why, but on my PC, the configuration to use an external Docker network to merge with the existing Docker Compose setup was not working well. Because of this, I placed this service inside the same folder structure as the base Compose setup to ensure it runs within the correct network.
