# Library Management System

## Overview

Diagonal Library Management System is an Spring Boot Application designed to manage a library's book inventory. It provides functionalities to add, update, delete, and retrieve information about books in the library.
The application uses Google Book API to make the additions easier.


## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Configuration](#configuration)
- [Usage](#usage)
- [Getting Started](#getting-started)
- [Swagger Integration](#Swagger-Integration)
- [Contributing](#contributing)
- [Author](#author)


## Prerequisites

Ensure you have the following installed on your system:

- Java 17
- PostgreSQL Database
- Maven


## Project Structure

The project follows a standard Spring Boot project structure:

- `src/main/java`: Contains the Java source code.
- `src/main/resources`: Contains application properties and configuration files.
- `src/test`: Contains test classes.

## Dependencies

The project utilizes the following major dependencies:

- Spring Boot
- Spring Data JPA
- MapStruct
- Lombok
- H2 Database (for testing)
- PostgreSQL
- Swagger

## Configuration

### Maven Compiler Plugin

The project uses the Maven Compiler Plugin for annotation processing. Ensure you have the required annotation processors for Lombok and MapStruct added to your project's build configuration.

```xml

<plugins>
    <!-- Other plugins -->

    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>${maven-compiler-plugin.version}</version>
        <configuration>
            <annotationProcessorPaths>
                <path>
                    <groupId>org.projectlombok</groupId>
                    <artifactId>lombok</artifactId>
                    <version>${lombok.version}</version>
                </path>
                <path>
                    <groupId>org.mapstruct</groupId>
                    <artifactId>mapstruct-processor</artifactId>
                    <version>${org.mapstruct.version}</version>
                </path>
            </annotationProcessorPaths>
        </configuration>
    </plugin>
</plugins>
```

## Usage

The Diagonal Library Management System provides a set of RESTful APIs to manage a library's book inventory. 

The application uses Google Book API to find and save the book information based on its ISBN values.

You can interact with the APIs using tools like Insomnia, Postman, or integrate them into your own applications.

### Examples:

#### Get all books:

```http
GET http://localhost:8080/volumes
```

#### Add a new book:

```http
POST http://localhost:8080/volumes/{isbn}
```

#### Get a specific book by ID:

```http
GET http://localhost:8080/volumes/{volumeId}
```

#### Find books by ISBN:

```http
GET http://localhost:8080/volumes/isbn/{isbn}
```

#### Find books by title:

```http
GET http://localhost:8080/volumes/title/{title}
```

#### Find books by author:

```http
GET http://localhost:8080/volumes/author/{authorName}
```

#### Find books by category:

```http
GET http://localhost:8080/volumes/category/{categoryName}
```

## Getting Started

1. Clone the repository with the specific branch:
    ```bash
    git clone -b external-api-integration https://github.com/Pris-c/library-manager.git
    ```

2. Set up your PostgreSQL database and update the `credential.properties` file with the appropriate database configurations.


3. Build and run the project:
    - Go into the root directory `library-manager/library-manager`
    - Run the application:

        ```bash
        mvn spring-boot:run
        ```

4. Access the application at [http://localhost:8080](http://localhost:8080).


## Swagger Integration

The Diagonal Library Management System leverages Swagger to provide an interactive interface, enabling direct interaction with the application's APIs. Access the Swagger interface at http://localhost:8080/swagger-ui/index.html to explore and make direct calls to the API endpoints.


## Contributing

Contributions are welcome! Feel free to open issues to report bugs or propose enhancements. If you'd like to contribute directly, open a pull request.

## Author

Priscila Campos




