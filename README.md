# Book Loan Management Service

A spring boot application for managing book loan data.

**NB! This application is meant to demonstrate the developers' capabilities and might have technical issues that had not been solved.**

### Functionality
Since this is a demo app, no separate secure storage of user data is implemented. To succeed in login within the application, passwords for the users are provided below:
- **admin** - password: adminPass
  - Adding new books to the system
  - Editing book data within the system
  - Deleting the books from the system
  - Removing reservations set by the user
  - Mark books as loaned out to user
  - Marking books as returned by the user
- **user** - password: userPass
  - Searching for books in the system
  - Reserving books within the system
  - Removing prior reservations within the system
  - Marking the books as received within the system
  - Marking the books as returned


## Technologies used
- **Java 22**: Programming language used in the project.
- **Spring Boot 3.x**: Framework used to build the application.
- **Spring Data JPA**: Data persistence and repository management.
- **PostgreSQL**: Relational database used for data storage.
- **Spring Security**: Provides authentication and authorization.
- **AngularJS**: Frontend framework for dynamic web apps.
- **JUnit 5**: Framework used to write and run tests.
- **Mockito**: Library for mocking dependencies in tests.
- **Lombok**: To reduce boilerplate code.

## Installation

### Prerequisites

- Java 22
- AngularJS 18.2.4
- Gradle 7.x or later

## Cloning the repository

```bash
git clone https://github.com/languidpie/smit-homework.git
cd smit-homework
```

## Build and Run the Application

### Build database with Docker
```bash
docker-compose up -d
```

### Build the project
```bash
./gradlew build
```

### Run the Back-end Application
```bash
./gradlew bootRun
```

### Run the Front-end Application
```bash
cd client/
ng serve --proxy-config proxy.conf.json
```

Back-End application will start running on http://localhost:8080.

Front-End application will start running on http://localhost:4200.

## Access to OpenAPI/Swagger

### Path to Open API doc
```
http://localhost:8080/v3/api-docs
```

### Swagger UI URL
```
http://localhost:8080/swagger-ui/index.html#/
```

## Local PostgreSQL Database

```
spring.datasource.url=jdbc:postgresql://localhost:6432/book_management
spring.datasource.username=admin
spring.datasource.password=secret
```

### Known issues
TBD