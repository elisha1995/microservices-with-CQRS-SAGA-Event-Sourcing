# Spring Boot Microservices with CQRS & Event Sourcing

This project is a practical implementation of a banking application using a microservices architecture. It demonstrates the principles of **Command Query Responsibility Segregation (CQRS)** and **Event Sourcing (ES)**.

The application is built with Java 25, Spring Boot 3.5.6, Apache Kafka, MongoDB, and MySQL.

## Table of Contents

- [Spring Boot Microservices with CQRS \& Event Sourcing](#spring-boot-microservices-with-cqrs--event-sourcing)
  - [Table of Contents](#table-of-contents)
  - [Core Concepts](#core-concepts)
    - [CQRS (Command Query Responsibility Segregation)](#cqrs-command-query-responsibility-segregation)
    - [Event Sourcing](#event-sourcing)
  - [Project Architecture](#project-architecture)
    - [Modules](#modules)
    - [Architectural Flow](#architectural-flow)
  - [Technologies Used](#technologies-used)
  - [Prerequisites](#prerequisites)

## Core Concepts

### CQRS (Command Query Responsibility Segregation)

CQRS is an architectural pattern that separates the model for writing data (Commands) from the model for reading data (Queries).

-   **Command Side**: Handles create, update, and delete requests. In this project, this is the `account-cmd` service. It focuses on processing commands and ensuring the system's state is consistent.
-   **Query Side**: Handles read requests. In this project, this is the `account-query` service. It provides optimized data views for clients and is updated asynchronously via events from the command side.

### Event Sourcing

Instead of storing the current state of an entity, we store the full sequence of events that have affected that entity. The current state can be derived at any time by replaying these events.

-   **Single Source of Truth**: The event store (MongoDB in this project) becomes the primary source of truth.
-   **Audit Trail**: We have a complete, immutable log of every change that has ever occurred in the system.
-   **State Reconstruction**: We can reconstruct the state of an aggregate at any point in time.

## Project Architecture

The project is divided into four main Maven modules, demonstrating a clear separation of concerns.


### Modules

1.  **`cqrs-core`**: A reusable library containing the core building blocks for the CQRS/ES pattern, including base classes for commands, events, aggregates, and dispatchers.
2.  **`account-common`**: A library containing shared code between the command and query services, primarily the event definitions (`AccountOpenedEvent`, etc.) and common DTOs.
3.  **`account-cmd` (Command Service)**:
    -   Exposes a REST API for state-changing operations (e.g., open account, deposit funds, etc.).
    -   Contains the business logic within the `AccountAggregate`.
    -   Persists events to the **Event Store** (MongoDB).
    -   Publishes events to **Apache Kafka** after successfully persisting them.
4.  **`account-query` (Query Service)**:
    -   Consumes events from Apache Kafka.
    -   Updates a denormalized **Read Database** (MySQL) to maintain a current-state view of the data.
    -   Exposes a REST API for querying bank account data in various ways (e.g., by ID, by holder, by balance).

### Architectural Flow

1.  A client sends a command (e.g., `POST /api/v1/open-bank-account`) to the **Command Service**.
2.  The `CommandDispatcher` routes the command to the appropriate handler.
3.  The handler loads the `AccountAggregate` from the event store or creates a new one.
4.  The aggregate processes the command, validates business rules, and produces one or more events.
5.  The `EventStore` persists these new events to MongoDB and publishes them to a Kafka topic.
6.  The **Query Service**'s `EventConsumer` listens for these events.
7.  The `EventHandler` processes the event and updates the read model in the MySQL database.
8.  Clients can now query the **Query Service** to get the updated state of the bank account.

## Technologies Used

-   **Framework**: Spring Boot 3.5.6
-   **Language**: Java 25
-   **Messaging**: Apache Kafka
-   **Command Database (Event Store)**: MongoDB
-   **Query Database (Read Model)**: MySQL
-   **Build Tool**: Apache Maven
-   **Utilities**: Lombok

## Prerequisites

-   Java 25 (or a compatible JDK)
-   Apache Maven
-   Docker & Docker Compose
-   A REST client like Postman or `curl`

