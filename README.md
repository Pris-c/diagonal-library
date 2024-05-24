# Library Manager

## Overview

Library Manager is a Spring Boot application designed to manage a library's book collection, including volumes, authors, categories, and user interactions such as adding favorite books. The application uses Google Books API to retrieve book information and integrates with a PostgreSQL database for storage.

This branch was built with the mission to integrate this backend application with the frontend in [Lumus Library](https://github.com/Pris-c/lumus-library-angular).


## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)



## Features

- **Volume Management:** Add, update, delete, and search for books by ISBN, title, author, or category.
- **User Management:** User registration, authentication, and favorite book management.
- **Category and Author Management:** Handle book categories and authors, ensuring they are properly stored and retrieved from the database.
- **Google Books API Integration:** Fetch book details using ISBN from Google Books API.
- **Data Initialization:** Load initial data from a JSON file if the database is empty.


## Technologies Used

- **Spring Boot:** For building the application.
- **Spring Data JPA:** For database operations.
- **Spring Security:** For authentication and authorization.
- **Hibernate:** For ORM (Object-Relational Mapping).
- **PostgreSQL:** For the relational database.
- **Mockito:** For unit testing.
- **JUnit:** For running tests.
- **AssertJ:** For fluent assertions in tests.
- **Google Books API:** For fetching book information.
- **Log4j2:** For logging.
- **Jackson:** For JSON processing.

## Getting Started

### Prerequisites

- Java 11
- Maven 3.6.3
- PostgreSQL 12

### Installation

1. **Clone the repository:**
     The current project is available on the _'adapt-for-frontend-integration'_ branch of this repository.

    ```bash
   git clone https://github.com/yourusername/library-manager.git
   cd library-manager
   git checkout adapt-for-frontend-integration
    ```

2. **Configure the database:**

Create a PostgreSQL database and update the application.yml file with your database credentials.
    
        spring:
        datasource:
            url: jdbc:postgresql:<your-database-path>
            username: <databse username>
            password: <database password>
            driver-class-name: org.postgresql.Driver
        jpa:
            hibernate:
            ddl-auto: update
            show-sql: true
            format_sql: true
    
3. **Configure token secret:**
Create a .properties file to keep your private key, which will be used by Spring Security to create and authenticate users' tokens.
This secret key is essential for ensuring the security of user authentication and authorization.

     ```bash
    api.security.token.secret = ${JWT_SECRET: <your-secret-key>}
     ```


   
4. **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

5. **Access the application:**
Open your browser and go to http://localhost:8080.



## Project Structure

- `src/main/java/prisc/librarymanager`: Contains the main application class and configuration.
- `src/main/java/prisc/librarymanager/controller`: Contains REST controllers for handling HTTP requests.
- `src/main/java/prisc/librarymanager/service`: Contains service classes for business logic.
- `src/main/java/prisc/librarymanager/repository`: Contains repository interfaces for database operations.
- `src/main/java/prisc/librarymanager/model`: Contains entity classes representing the database tables.
- `src/main/java/prisc/librarymanager/util`: Contains utility classes such as the Google API consumer and DataLoader
- `src/main/resources`: Contains application properties and initial data files.
- `src/test`: Contains unit and integration tests.

## API Endpoints

### Volume Endpoints

- `GET /volumes`: Retrieve all volumes.
- `GET /volumes/{id}`: Retrieve a volume by ID.
- `POST /volumes`: Add a new volume by ISBN.
- `DELETE /volumes/{id}`: Delete a volume by ID.

### User Endpoints

- `POST /users/register`: Register a new user.
- `POST /users/login`: Authenticate a user and return a JWT token.
- `GET /users/favorites`: Get the authenticated user's favorite volumes.
- `POST /users/favorites`: Add a volume to the user's favorites.
- `DELETE /users/favorites`: Remove a volume from the user's favorites.

### Author Endpoints

- `GET /authors/{name}`: Retrieve authors by name.

### Category Endpoints

- `GET /categories/{name}`: Retrieve categories by name.

## Testing

The project includes unit tests for service and controller layers. To run the tests, use the following command:

 ```bash
   mvn test
```


## Contributing

Contributions are welcome! Feel free to open issues to report bugs or propose enhancements. If you'd like to contribute
directly, open a pull request.

## Author

Priscila Campos 👩‍💻


