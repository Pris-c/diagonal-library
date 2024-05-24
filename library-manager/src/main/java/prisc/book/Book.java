package prisc.book;

import jakarta.persistence.*;

/** Book Class
*
* This class represents a Book entity in the application. It is annotated with JPA annotations
* to indicate its mapping to a database table.
*
* Annotations:
* - @Entity: Specifies that the class is an entity and is mapped to a database table.
* - @Id: Indicates the primary key field of the entity.
* - @GeneratedValue: Specifies the strategy used to generate values for the primary key.
*                   In this case, GenerationType.AUTO allows the persistence provider to choose the strategy.
* - @Override: Indicates that a method overrides a method declared in a superclass.
*              Here, it is used for the toString() method to provide a custom string representation of the object.
*
* Fields:
* - private int bookId: The unique identifier for the book.
* - private String title: The title of the book.
* - private String author: The author of the book.
* - private int year: The publication year of the book.
*
* Constructors:
* - public Book(): Default constructor.
* - public Book(int bookId, String title, String author, int year): Parameterized constructor to initialize all fields.
* - public Book(String title, String author, int year): Parameterized constructor without bookId.
*
* Methods:
* - Getters and setters for all fields to access and modify the state of the object.
* - @Override
*   public String toString(): Custom toString method to provide a formatted string representation of the object.
*
* Usage:
* This class is used as an entity in Hibernate for database operations related to books. It allows
* creating, retrieving, updating, and deleting Book records in the database.
*/

@Entity
//@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;
    private String title;
    private String author;
    private int year;

    public Book() {
    }

    public Book(int bookId, String title, String author, int year) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return " Book " + bookId + ", Title: " + title + ", Author: " + author + ", Year: " + year;
    }
}
