# Library Management System

## Project Overview

The Library Management System project is a comprehensive book management system designed to cater to the needs of a library. It has undergone several versions, introducing new features and incorporating modern technologies such as Maven, SQL databases, and the Spring Boot framework.

This application enables essential operations within a library context, including the ability to save new books, make alterations to existing records, delete works from the collection, and search for specific books in the database.

With different versions, ranging from a basic implementation in pure Java to more advanced iterations utilizing Maven, Spring Boot, and other enhancements, the project aims to provide a flexible and scalable solution for efficient library book management. Each version builds upon the previous one, bringing improvements in project structure, dependency management, documentation, and unit testing, aligning with industry best practices and technological advancements.

## Upcoming Versions

Despite the initial goal being achieved in the "spring-boot-migration" branch, I will implement two additional steps:

- Integration with an external API;
- Migration of the database to Docker.



## Branching Strategy

Different versions of the project are organized into specific branches:

1.  [**java**](https://github.com/Pris-c/diagonal-library/tree/java/diagonal-library/src]branch) ✔ :
   - The initial version of the Library Management System is implemented solely in Java, featuring a simulated List-based database. User interaction in this version occurs through a command-line prompt.
    
2.  [**maven-sql-integration**](https://github.com/Pris-c/library-manager/tree/maven-sql-integration)  ✔ :
   - This branch focuses on integrating Maven to enhance project structure and dependency management. Additionally, it includes the integration of an SQL database for establishing persistent and efficient data storage.

3. [**spring-boot-migration**](https://github.com/Pris-c/library-manager/tree/spring-boot-migration) ✔  :
- The project underwent a transition to the Spring Boot framework to leverage advanced features and realized architectural enhancements. The replacement entailed the adoption of a RESTful API paradigm.
  
4. external-api-integration
- To simplify the addition of books to the database, an external API integration will be implemented. This integration will allow for searching and retrieving several information about the books.
  
5. docker-database-migration
- To streamline the deployment and management of the database, this branch will focus on migrating the project's database to Docker. This migration aims to enhance scalability, portability, and ease of maintenance by containerizing the database within a Docker environment.
