# E-commerce microservices backend built with Spring Boot and Spring Cloud

<img width="410" height="210" alt="spring_boot_microservices" src="https://github.com/user-attachments/assets/e8d8a365-04c9-42f3-983f-0f2b75a65596" />

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

The system architecture consists of multiple microservices, each responsible for a specific domain within the e-commerce
application.
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
Each microservie, at its core, will have the following architecture :
<table>
  <tr>
    <td>
        <img width="600" height="430" alt="spring_cloud_microservice" src="https://github.com/user-attachments/assets/b2572140-a0e3-4ec3-b6fb-cf8941d4c2cc" />
    </td>
    <td valign="top">
        <ul>
            <li>The controller handles incoming HTTP requests from clients</li>
            <li>It then calls a service which manages core business logic </li>
            <li>The service uses a repository to communicate with the database </li>
            <li>On microservices requiring asynchronous processing, the service can call a message queue too ( eg. Kafka ).</li>
        </ul>
    </td>
  </tr>
</table>

---

## Inter-service communication

<img width="460" height="309" alt="inter-service-communication" src="https://github.com/user-attachments/assets/a75e53e8-78e9-40b5-9e04-1d748fec4858" />

Each microservice in our architecture is decoupled from others. In other terms, one microservice cannot directly access
to another one's data.
In order to maintain a certain logic, we establish inter-service communication.  
For that purpose, there are many options ( WebClient , RestTemplate ... ).  
For our implementation, we use `WebClient`. As an explicit example, before placing an order inside the order service, we
do checks with the inventory service to see if quantity in cart are well available.
---

## Discovery Server

So far, so good. Our microservices can communicate via WebClient and make RESTful calls.   
But all of this is on localhost. Our design is suited for cloud-native solutions.  
On the cloud :

1. We can have multiple deployments (each with its own address).
   The requesting service won't know which one is available.
2. IP address change often between instances restarts, crashes ...

This doesn't fit with our previous approach. To solve the problem , we implement a discovery server.
A `discovery server` is like a registry containing a mapping between service names and their current valid addresses.
With that, a service can be identified via it's defined name and its data can be accessed accordingly without hardcoding
addresses.
<img width="1331" height="442" alt="discovery-service" src="https://github.com/user-attachments/assets/2b54c9a0-932b-445a-93c8-309eae74e78c" />

With this setup, communication between microservices transit by the discovery server as described by the following
schema :
<img width="720" height="381" alt="communication-discovery-server" src="https://github.com/user-attachments/assets/740d9a3e-26be-40fc-9368-cd9054d31a44" />
_Additionally , the discovery server will send a copy of its registry to each microservices so that they can directly
query other microservices without requesting it._

## API Gateway

The API Gateway is a server that acts as an entry point for all client requests to the microservices. It handles
routing, load balancing and much more.
With the current setup, client would have to know the exact address of each microservice to make requests. This is not
ideal for a production environment where microservices addresses can change frequently.  
<img width="520" height="340" alt="API_GATEWAY" src="https://github.com/user-attachments/assets/05b7bf56-e34e-41d0-92c3-1fd86739a713" />   
An API Gateway solves this problem by providing routing capabilities. Clients can make requests to the API Gateway,
which then forwards the requests to the appropriate microservice based on predefined routing rules.

Additionally, the gateway can also handle cross-cutting concerns such as :

- authentication and authorization
- rate limiting
- load balancing
- logging
- caching
- request and response transformations
- more ...

## Keycloak security

To secure our microservices, we use Keycloak.