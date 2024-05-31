# Library Management System

The Library Management System is a Java project designed to facilitate the management of books within a simulated library database. Users interact with the system via a command-line interface (CLI), enabling them to perform various operations such as adding, searching, updating, and deleting book records.

## Key Features

1. **Browse Books:**
    - View the complete list of books registered in the library.

2. **Register a New Book:**
    - Add a new book to the library, providing information such as title, author, and publication year.

3. **Search for Books:**
    - Find books based on title, author, publication year, or ID.

4. **Update Book:**
    - Modify information for an existing book, including title, author, and publication year.

5. **Delete Book:**
    - Remove a book from the library based on its ID.

## Project Structure

The project is structured into several classes, each serving a specific purpose:

1. **Book:**
    - Represents a book entity with attributes such as ID, title, author, and publication year.

2. **BookDTO:**
    - Data Transfer Object (DTO) class used to encapsulate book information.

3. **BookRepository:**
    - Manages the persistence of book data, providing methods for CRUD operations.

4. **BookService:**
    - Implements business logic for book manipulation, including querying, adding, searching, updating, and deleting.

5. **BookController:**
    - Provides a command-line interface for users to interact with the system's functionalities.

6. **LibraryPrinter:**
    - Utility class for printing messages and user prompts to the console.

7. **Enums:**
    - Enumeration classes for defining string and numeric fields, as well as update status.

## Getting Started

### Prerequisites
- JDK 17

### Installation

1. **Clone the repository:**
     The current project is available on the _'java'_ branch of this repository.

    ```bash
    git clone https://github.com/Pris-c/library-manager.git --branch java --single-branch
    ```

2. **Compile the Program:**
    - Go into the root directory of the projec:
   `library-manager/library-manager/src`
   
    - Compile the project:
    ```bash
    javac Main.java
    ```
    
2. **Run:**
    - Execute the _'Main'_  class to initiate user interaction.
    ```bash
    java Main
    ```


## Contribution

Contributions are welcome! Feel free to open issues to report bugs or propose enhancements. If you'd like to contribute directly, open a pull request.

## Author

Priscila Campos 👩‍💻

