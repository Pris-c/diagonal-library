# Library Management System

## Overview

The Library Management System is a Java-based console application designed to manage a library's book inventory. This
project uses PostgreSQL as the database, integrates with Hibernate for database interaction, utilizes Maven for
dependency management and project build, and employs Log4j for logging, ensuring logs are recorded in a file for better
user experience.

## Features

- **Main Menu:** The main menu provides an intuitive interface for users to perform various actions, including listing
  all books, adding new books, searching for books, updating book information, and deleting books.

- **Listing Books:** Users can view a list of all books in the library. If the library is empty, a corresponding message
  is displayed.

- **Adding Books:** Enrich the library by adding new books, providing details such as title, author, and publication
  year.

- **Searching for Books:** The application supports searching for books based on title, author, publication year, and
  ID.

- **Updating Book Information:** Modify the information of existing books, including the title, author, and publication
  year.

- **Deleting Books:** Remove books from the library by providing their unique ID.

## Usage

To utilize the Library Management System, follow these steps:

1. **Clone the Repository:**
    - Clone the project repository using your preferred IDE or the following command:
      ```bash
      git clone https://github.com/Pris-c/library-manager.git
      ```

2. **Import into IDE:**
    - Open the project in your favorite Java IDE (e.g., IntelliJ, Eclipse).
    <br><br>
3. **Configure PostgreSQL:**
    - Ensure you have a PostgreSQL database set up.
    - Update the database configuration in the Hibernate configuration file (`hibernate.cfg.xml`) with your database credentials.
      <br><br>
4. **Run the Application:**
    - Run the `Main` class within your IDE.
      <br><br>
5. **Interact with the System:**
    - Follow the on-screen instructions to navigate through the main menu and perform various library management actions.

## Dependencies

The Library Management System has the following dependencies:

- PostgreSQL
- Hibernate
- Log4j

## Configuration

Before running the application, make sure to configure the following:

- **PostgreSQL Database:**
    - Set up a PostgreSQL database.
    - Update the Hibernate configuration file (`hibernate.cfg.xml`) with your database credentials.

- **Log4j Configuration:**
    - Configure Log4j to suit your logging preferences in the `log4j2.xml` file.

## Contribution

Contributions are welcome! Feel free to open issues to report bugs or propose enhancements. If you'd like to contribute directly, open a pull request.

## Author

Priscila Campos
