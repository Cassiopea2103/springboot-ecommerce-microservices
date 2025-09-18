# E-commerce microservices backend built with Spring Boot and Spring Cloud

## Overview of what we will build :

* Core microservices :
    - `Product Service` : Manages product information
    - `Inventory Service` : Manages inventory levels of products
    - `Order Service` : Handles customer orders' processing
    - `Notification Service` : Sends notifications to customers
* Interservice communication using `Feign Clients`
* Service discovery with `Netflix Eureka Server`
* API Gateway using `Spring Cloud Gateway` for routing and load balancing
* Security with `Keycloak` for authentication and authorization
* Resilience and fault tolerance using `Resilience4j`
* `Distibuted tracing` with`Zipkin` for monitoring and debugging
* Event-driven architecture using `Apache Kafka` for asynchronous communication
* Dockerization of services for easy deployment and scalability
* Monitoring of the solution with `Prometheus` and `Grafana`

## System architecture 
<img width="1610" height="1000" alt="spring_cloud_system_architecture" src="https://github.com/user-attachments/assets/7893d443-0d03-4076-b52a-33d7a5550835" />

The system architecture consists of multiple microservices, each responsible for a specific domain within the e-commerce application. 
The services communicate with each other using RESTful APIs and Feign clients.
- The API Gateway serves as the entry point for all client requests, routing them to the appropriate microservice. 
- Service discovery is handled by Netflix Eureka, allowing services to find and communicate with each other dynamically.
- Keycloak is used for securing the services, providing authentication and authorization.
- Resilience4j is implemented to ensure fault tolerance and resilience in case of service failures.
- Zipkin is integrated for distributed tracing, helping to monitor and debug the interactions between services.
- Apache Kafka is used for event-driven communication, enabling asynchronous processing of events.
- Each microservice is dockerized for easy deployment and scalability.
- Prometheus and Grafana are used for monitoring the health and performance of the services.
- The database layer consists of MySQL for relational data and MongoDB for NoSQL data storage.
- The configuration server manages the configuration settings for all microservices.
- Secrets management is handled by HashiCorp Vault, ensuring secure storage and access to sensitive information.
---
