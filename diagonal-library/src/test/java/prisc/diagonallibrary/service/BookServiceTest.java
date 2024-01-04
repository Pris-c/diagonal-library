package prisc.diagonallibrary.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;
import prisc.diagonallibrary.exception.BookAlreadyExistsException;
import prisc.diagonallibrary.exception.BookIdNotFoundException;
import prisc.diagonallibrary.mapper.BookMapper;
import prisc.diagonallibrary.repository.BookRepository;
import prisc.diagonallibrary.util.BookCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for BookService")
class BookServiceTest {

    private final Book book1 = new Book(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95"), "One Title", "One person", 1900);
    private final Book book2 = new Book(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d96"), "Other TitLE", "Some author", 2020);
    private final Book book3 = new Book(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d97"), "The Two", "Another AUTHOR", 1900);
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepositoryMock;

    @Test
    @DisplayName("getAll: Returns a list of all Books saved in database in BookResponse format")
    void getAll_ReturnsAListOfAllBookResponse_WhenSuccessful() {
        List<Book> books = List.of(book1, book2, book3);
        when(bookRepositoryMock.findAll())
                .thenReturn(books);

        List<BookResponse> bookList = this.bookService.getAll();

        Assertions.assertThat(bookList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .contains(  BookMapper.INSTANCE.toBookResponse(book1),
                            BookMapper.INSTANCE.toBookResponse(book2),
                            BookMapper.INSTANCE.toBookResponse(book3));
    }

    @Test
    @DisplayName("getAll: Returns an empty list when database is empty")
    void getAll_ReturnsAEmpty_WhenDatabaseIsEmpty() {
        when(bookRepositoryMock.findAll())
                .thenReturn(new ArrayList<>());

        List<BookResponse> bookList = this.bookService.getAll();

        Assertions.assertThat(bookList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save: Returns the persisted Book (as BookResponse) when successful")
    void save_ReturnsThePersistedBook_WhenSuccessful() {
        Book book = BookCreator.createValidBook();

        when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(book);

        BookResponse savedBook = this.bookService.save(BookCreator.createBookPostRequesBody());

        Assertions.assertThat(savedBook)
                .isNotNull()
                .isEqualTo(BookMapper.INSTANCE.toBookResponse(book));

        Assertions.assertThat(savedBook.getBookId()).isNotNull();
    }

    @Test
    @DisplayName("save: throws BookAlreadyExistsException when there is a equal book in database")
    void save_ThrowsBookAlreadyExistsException_WhenBookAlreadyExistsInDatabase() {
        when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class)))
                .thenThrow(BookAlreadyExistsException.class);

        Assertions.assertThatExceptionOfType(BookAlreadyExistsException.class)
                .isThrownBy(() -> this.bookService.save(BookCreator.createBookPostRequesBody()));

    }

    @Test
    @DisplayName("findById: Returns the unique book that correspond to id informed when successful")
    void findById_OrThrowBookIdNotFoundException_ReturnsTheUniqueBook_WhenSuccessful() {
        Book validBook = BookCreator.createValidBook();

        when(bookRepositoryMock.findById(any(UUID.class)))
                .thenReturn(Optional.of(validBook));

        BookResponse bookById = this.bookService.findById_OrThrowBookIdNotFoundException(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95"));

        Assertions.assertThat(bookById)
                .isNotNull();
        Assertions.assertThat(bookById.getBookId()).isEqualTo(validBook.getBookId());
    }

    @Test
    @DisplayName("findById: Throws BookIdNotFoundException when there is no book correspondent to informed id")
    void findById_ThrowsBookIdNotFoundException_WhenNoBookIsFound() {

        when(bookRepositoryMock.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BookIdNotFoundException.class)
                .isThrownBy(() -> this.bookService.findById_OrThrowBookIdNotFoundException(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95")));

    }


    @Test
    @DisplayName("findByTitle: Returns a list of Books (as BookResponse) that contains the string informed in title")
    void findByTitle_ReturnsAListOfBooks_WhenSuccessful() {
        List<Book> books = List.of(book1, book2);
        when(bookRepositoryMock.findByTitleIgnoreCaseContaining(any(String.class)))
                .thenReturn(books);

        List<BookResponse> booksByTitle = this.bookService.findByTitle("title");

        Assertions.assertThat(booksByTitle)
                .isNotNull()
                .hasSize(2)
                .contains(BookMapper.INSTANCE.toBookResponse(book1), BookMapper.INSTANCE.toBookResponse(book2));
    }


    @Test
    @DisplayName("findByTitle: Returns an empty list when no book is found")
    void findByTitle_ReturnsAEmptyList_WhenNoBookIsFound() {
        when(bookRepositoryMock.findByTitleIgnoreCaseContaining(any(String.class)))
                .thenReturn(new ArrayList<>());

        List<BookResponse> booksByTitle = this.bookService.findByTitle("title");

        Assertions.assertThat(booksByTitle)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByAuthor: Returns a list of Books (as BookResponse) that contains the string informed in title")
    void findByAuthor_ReturnsAListOfBooks_WhenSuccessful() {
        List<Book> books = List.of(book2, book3);
        when(bookRepositoryMock.findByAuthorIgnoreCaseContaining(any(String.class)))
                .thenReturn(books);

        List<BookResponse> booksByAuthor = this.bookService.findByAuthor("author");

        Assertions.assertThat(booksByAuthor)
                .isNotNull()
                .hasSize(2)
                .contains(BookMapper.INSTANCE.toBookResponse(book2), BookMapper.INSTANCE.toBookResponse(book3));
    }


    @Test
    @DisplayName("findByAuthor: Returns an empty list when no book is found")
    void findByAuthor_ReturnsAEmptyList_WhenNoBookIsFound() {
        when(bookRepositoryMock.findByAuthorIgnoreCaseContaining(any(String.class)))
                .thenReturn(new ArrayList<>());

        List<BookResponse> booksByAuthor = this.bookService.findByAuthor("author");

        Assertions.assertThat(booksByAuthor)
                .isNotNull()
                .isEmpty();
    }




    @Test
    @DisplayName("findByYear: Returns a list of Books (as BookResponse) from publication year informed")
    void findByYear_ReturnsAListOfBooks_WhenSuccessful() {
        List<Book> books = List.of(book1, book3);
        when(bookRepositoryMock.findByYear(any(Integer.class)))
                .thenReturn(books);

        List<BookResponse> booksByYear = this.bookService.findByYear(2000);

        Assertions.assertThat(booksByYear)
                .isNotNull()
                .hasSize(2)
                .contains(BookMapper.INSTANCE.toBookResponse(book1), BookMapper.INSTANCE.toBookResponse(book3));
    }


    @Test
    @DisplayName("findByYear:  Returns an empty list when no book is found")
    void findByYear_ReturnsAnEmptyList_WhenNoBookIsFound() {
        when(bookRepositoryMock.findByYear(any(Integer.class)))
                .thenReturn(new ArrayList<>());

        List<BookResponse> booksByYear = this.bookService.findByYear(2000);

        Assertions.assertThat(booksByYear)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void update() {
    }


    @Test
    @DisplayName("delete: Deletes the book with the id informed if it exists")
    void delete_RemovesBookWithInformedId_WhenSuccessful() {
        Book validBook = BookCreator.createValidBook();
        when(bookRepositoryMock.findById(any(UUID.class)))
                .thenReturn(Optional.of(validBook));

        doNothing().when(bookRepositoryMock).deleteById(any(UUID.class));

        bookService.deleteById(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95"));
        verify(bookRepositoryMock).findById(any(UUID.class));
        verify(bookRepositoryMock).deleteById(any(UUID.class));

        Assertions.assertThatCode(() -> bookService.deleteById(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95")))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete: Throws BookIdNotFoundException if the bookId does not exists in database")
    void deleteById_ThrowsBookIdNotFoundException_IfBookIdDoesNotExistsInDatabase() {
        doThrow(BookIdNotFoundException.class)
                .when(bookRepositoryMock).deleteById(any(UUID.class));

        Assertions.assertThatExceptionOfType(BookIdNotFoundException.class)
                .isThrownBy(() -> this.bookService.deleteById(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95")));

    }

}