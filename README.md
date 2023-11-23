# Diagonal Library

This Java project is a Library Management System, providing basic functionalities for managing books within a simulated library database. Users can view, add, search, update, and delete book records through a command-line interface.

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

The project is organized into several classes, including:

1. **Book:**
    - Represents a book with attributes such as ID, title, author, and publication year.

2. **BookDTO:**
    - A Data Transfer Object (DTO) class that protects the database object, representing the book information.

3. **BookRepository:**
    - Manages the persistence of book data, including methods for saving, querying, updating, and deleting books. Ensures a single book list (database) for the entire application.
4. **BookService:**
    - Provides services for book manipulation, including querying, adding, searching, updating, and deleting. This class is responsible for implementing the business rules.

5. **BookController:**
    - Offers a command-line interface for users to interact with the system's functionalities.

6. **LibraryPrinter:**
    - A utility class for printing messages and user prompts to the console.

7. **Enums:**
    - Enumeration classes for defining string and numeric fields, as well as update status.

## How to Use

1. **Run the Program:**
    - Execute the `mainMenu` method in the `BookController` class to initiate user interaction.

2. **Menu Options:**
    - Choose an option from the main menu by entering the corresponding number.
    - Follow the on-screen instructions to perform the desired operations.

## Requirements for Developers

Developers wishing to utilize or contribute to this project should have the following:

- **Java Development Kit (JDK):**
    - Ensure you have Java installed. The project is developed using Java 17, and having the JDK installed is essential.

- **Integrated Development Environment (IDE):**
    - Use a Java-friendly IDE such as IntelliJ IDEA or Eclipse for a seamless development experience.

## Contribution

Contributions are welcome! Feel free to open issues to report bugs or propose enhancements. If you'd like to contribute directly, open a pull request.

## Author

Priscila Campos

