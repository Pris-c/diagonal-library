# Library Manager

## Overview

Library Manager is a Spring Boot application designed to manage a library's book collection, including volumes, authors, categories, and user interactions, such as handle their favorite books. The application uses Google Books API to retrieve book information and integrates with a PostgreSQL database for storage.

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

- Java 17
- Maven
- PostgreSQL

### Installation

1. **Clone the repository:**
     The current project is available on the _'adapt-for-frontend-integration'_ branch of this repository.

    ```bash
     git clone -b adapt-for-frontend-integration https://github.com/Pris-c/library-manager.git
    ```

2. **Configurations**

    2.1 **Database:**

    Set up your PostgreSQL database and update the `credential.properties` file with the appropriate database configurations.

    2.2 **Token secret:**
    Set your private key at `credential.properties`. This secret key is essential for ensuring the security of user authentication and authorization.

    2.3 **Define initial ADMIN data**
    In this first execution, an ADMIN user will be created as defined in `credential.properties`. Set the ADMIN informations in this file.

   
3. **Run the application:**
Go into the root directory `library-manager/library-manager`, and run the application:
    ```bash
    mvn spring-boot:run
    ```

**Note:**

1. The application is configured to create all the tables on the database in runtime.
In this first execution, an initial list of books will be persisted, built from `library-manager/src/main/resources/volumes-starter.json.`
2. For consecutive runs, to keep your data, consider changing the property spring.jpa.hibernate.ddl-auto to 'update' in `library-manager/src/main/resources/application.yml`.

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

##### Retrieve all volumes:

```http
GET http://localhost:8080/volumes
```

##### Add a new volume:

```http
POST http://localhost:8080/volumes
Content-Type: application/json

{
  "isbn": "9781234567890"
}
```

##### Retrieve a volume by ID:

```http
GET http://localhost:8080/volumes/{volumeId}
```

##### Delete a volume by ID:

```http
DELETE http://localhost:8080/volumes/{id}
```

##### Retrieve a volume by ISBN:

```http
GET http://localhost:8080/volumes/isbn/{isbn}
```

##### Retrieve volumes by title:

```http
GET http://localhost:8080/volumes/title/{title}
```

##### Retrieve volumes by author name:

```http
GET http://localhost:8080/volumes/author/{authorName}
```

##### Retrieve volumes by category:

```http
GET http://localhost:8080/volumes/category/{categoryName}
```

##### Retrieve the top 5 favorite volumes:

```http
GET http://localhost:8080/volumes/top-favorites
```
##### Get the authenticated user's favorite volumes:

```http
GET http://localhost:8080/volumes/favorite
```

##### Add a volume to the user's favorites:

```http
POST http://localhost:8080/volumes/favorite
Content-Type: application/json

{
  "volumeId": "uuid-of-the-volume"
}
```

##### Remove a volume from the user's favorites:

```http
DELETE http://localhost:8080/volumes/favorite
Content-Type: application/json

{
  "volumeId": "uuid-of-the-volume"
}
```


### User Endpoints

##### Register a new user:

```http
POST http://localhost:8080/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123",
  "email": "newuser@example.com"
}
```

##### Authenticate a user and return a JWT token:

```http
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "username": "existinguser",
  "password": "password123"
}
```

## Testing

The project includes unit tests for service and controller layers. To run the tests, use the following command from the root:

 ```bash
   mvn test
```


## Contributing

Contributions are welcome! Feel free to open issues to report bugs or propose enhancements. If you'd like to contribute
directly, open a pull request.

## Author

Priscila Campos 👩‍💻


