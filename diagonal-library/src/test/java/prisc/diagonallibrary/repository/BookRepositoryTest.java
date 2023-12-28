package prisc.diagonallibrary.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import prisc.diagonallibrary.entity.Book;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Test for Book Repository")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private final Book book1 = new Book("One Title", "One person", 1900);
    private final Book book2 = new Book("Other TitLE", "Some author", 2020);
    private final Book book3 = new Book("The Two", "Another AUTHOR", 1900);


    @Test
    @DisplayName("findAll: Returns all books saved in database")
    void findAll_ReturnsAListOfAllBooks_WhenSuccessful(){
        this.createBookDatabase();

        List<Book> bookList = this.bookRepository.findAll();

        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(bookList.size()).isEqualTo(3);
        Assertions.assertThat( bookList.get(0).getBookId()).isNotNull();
    }

    @Test
    @DisplayName("findAll: Returns an empty list if database is empty")
    void findAll_ReturnsAEmptyList_WhenDatabaseIsEmpty(){
        List<Book> bookList = this.bookRepository.findAll();
        Assertions.assertThat(bookList).isEmpty();
    }

    @Test
    @DisplayName("findById: Returns the unique Book that correspond to informed id")
    void findById_ReturnTheUniqueBook_WhenSuccessful(){
        this.createBookDatabase();

        Optional<Book> foundBook = this.bookRepository.findById(book2.getBookId());
        Assertions.assertThat(foundBook).isNotEmpty();
        Assertions.assertThat(foundBook.get()).isEqualTo(book2);
    }

    @Test
    @DisplayName("findById: Returns an Empty Optional when id is not found")
    void findById_ReturnsAnEmptyOptional_WhenIdIsNotFound(){

        Optional<Book> foundBook = this.bookRepository.findById(3L);
        Assertions.assertThat(foundBook).isEmpty();
    }

    @Test
    @DisplayName("findByTitle: Returns a list of books that contains the informed string in its title")
    void findByTitle_ReturnAListOfBooks_WhenSuccessful(){
        this.createBookDatabase();

        List<Book> foundBooksByTitle = this.bookRepository.findByTitleIgnoreCaseContaining("title");
        Assertions.assertThat(foundBooksByTitle.size()).isEqualTo(2);
        Assertions.assertThat(foundBooksByTitle).contains(book1);
        Assertions.assertThat(foundBooksByTitle).contains(book2);
   }

    @Test
    @DisplayName("findByTitle: Returns an empty list when no book is found")
    void findByTitle_ReturnAnEmptyList_WhenNoBookIsFound(){

        List<Book> foundBooksByTitle = this.bookRepository.findByTitleIgnoreCaseContaining("title");
        Assertions.assertThat(foundBooksByTitle).isEmpty();
   }

    @Test
    @DisplayName("findByAuthor: Returns a list of books that contains the informed string in its author name")
    void findByAuthor_ReturnAListOfBooks_WhenSuccessful(){
        this.createBookDatabase();

        List<Book> foundBooksByAuthor = this.bookRepository.findByAuthorIgnoreCaseContaining("author");
        Assertions.assertThat(foundBooksByAuthor.size()).isEqualTo(2);
        Assertions.assertThat(foundBooksByAuthor).contains(book2);
        Assertions.assertThat(foundBooksByAuthor).contains(book3);
   }


    @Test
    @DisplayName("findByAuthor: Returns an empty list when no book is found")
    void findByAuthor_ReturnAnEmptyList_WhenNoBookIsFound(){

        List<Book> foundBooksByAuthor = this.bookRepository.findByAuthorIgnoreCaseContaining("author");
        Assertions.assertThat(foundBooksByAuthor).isEmpty();
    }

    @Test
    @DisplayName("findByYear: Returns a list of books with the informed publication year")
    void findByYear_ReturnAListOfBooks_WhenSuccessful(){
        this.createBookDatabase();

        List<Book> foundBooksByYear = this.bookRepository.findByYear(1900);
        Assertions.assertThat(foundBooksByYear.size()).isEqualTo(2);
        Assertions.assertThat(foundBooksByYear).contains(book1);
        Assertions.assertThat(foundBooksByYear).contains(book3);
   }

    @Test
    @DisplayName("findByYear: Returns an empty list when no book is found")
    void findByYear_ReturnAnEmptyList_WhenNoBookIsFound(){

        List<Book> foundBooksByYear = this.bookRepository.findByYear(1900);
        Assertions.assertThat(foundBooksByYear).isEmpty();
    }

    @Test
    @DisplayName("save: Persists book when successful")
    void save_PersistsBook_WhenSuccessful(){
        Book book = createBook();
        Book savedBook = this.bookRepository.save(book);

        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getBookId()).isNotNull();
        Assertions.assertThat(savedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("update: Replaces book when successful")
    void update_UpdatesBook_WhenSuccessful(){
        Book book = createBook();
        Book savedBook = this.bookRepository.save(book);

        savedBook.setTitle("Updated Title");
        savedBook.setAuthor("Updated Author");
        savedBook.setYear(1813);
        Book updatedBook = this.bookRepository.save(savedBook);

        Assertions.assertThat(updatedBook).isNotNull();
        Assertions.assertThat(this.bookRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(updatedBook.getBookId()).isEqualTo(savedBook.getBookId());
        Assertions.assertThat(updatedBook.getTitle()).isEqualTo("Updated Title");
        Assertions.assertThat(updatedBook.getAuthor()).isEqualTo("Updated Author");
        Assertions.assertThat(updatedBook.getYear()).isEqualTo(1813);
    }


    @Test
    @DisplayName("deleteById: Removes book when successful")
    void deleteById_RemovesBook_WhenSuccessful(){
        Book book = createBook();
        Book savedBook = this.bookRepository.save(book);

        this.bookRepository.deleteById(savedBook.getBookId());
        Optional<Book> optionalBook = this.bookRepository.findById(savedBook.getBookId());
        Assertions.assertThat(optionalBook).isEmpty();
        Assertions.assertThat(this.bookRepository.count()).isEqualTo(0);
    }



    private Book createBook(){
        return Book.builder()
                .title("Book Test")
                .author("Author Test")
                .year(2021)
                .build();
    }

    private void createBookDatabase(){
        this.bookRepository.save(book1);
        this.bookRepository.save(book2);
        this.bookRepository.save(book3);
    }



}