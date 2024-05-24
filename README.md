# Library Management System

## Overview

The Library Management System is a Java-based console application designed to manage a library's book inventory. This
project uses PostgreSQL as the database, integrates with Hibernate for database interaction, utilizes Maven for
dependency management and project build, and employs Log4j for logging, ensuring logs are recorded in a file for better
user experience.

## Features

- **Main Menu:** The main menu provides an intuitive interface for users to perform various actions, including listing all books, adding new books, searching for books, updating book information, and deleting books.

- **Listing Books:** Users can view a list of all books in the library. If the library is empty, a corresponding message  is displayed.

- **Adding Books:** Enrich the library by adding new books, providing details such as title, author, and publication
  year.

- **Searching for Books:** The application supports searching for books based on title, author, publication year, and
  ID.

- **Updating Book Information:** Modify the information of existing books, including the title, author, and publication
  year.

- **Deleting Books:** Remove books from the library by providing their unique ID.

## Getting Started

### Prerequisites
- JDK 17
- Maven
- PostgreSQL


## Usage

To set up and use the Library Management System, follow these steps:

1. **Clone the Repository:**
    The current project is available on the _'maven-sql-integration'_ branch of this repository

    ```bash
   git clone https://github.com/yourusername/library-manager.git
   cd library-manager
   git checkout maven-sql-integration
   ```

2. **Configuration:**
    - Ensure you have a PostgreSQL database set up.
    - Update the `hibernate.cfg.xml` file with your database credentials:
      ```xml
      <property name="hibernate.connection.url">jdbc:postgresql://<your-database-url>:<port>/<database-name></property>
      <property name="hibernate.connection.username">your-username</property>
      <property name="hibernate.connection.password">your-password</property>
      ```
    - Customize the `log4j2.xml` file to suit your logging preferences, ensuring logs are recorded as required.


3. **Run the Application:**
    - Go into the root directory of the project and run the application:
      ```bash
      mvn spring-boot:run
      ```

## Contribution

Contributions are welcome! Feel free to open issues to report bugs or propose enhancements. If you'd like to contribute directly, open a pull request.

## Author

Priscila Campos 👩‍💻
