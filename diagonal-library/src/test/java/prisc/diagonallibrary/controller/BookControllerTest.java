//package prisc.diagonallibrary.controller;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import prisc.diagonallibrary.controller.request.BookPostRequest;
//import prisc.diagonallibrary.controller.request.BookPutRequest;
//import prisc.diagonallibrary.controller.response.BookResponse;
//import prisc.diagonallibrary.exception.BookAlreadyExistsException;
//import prisc.diagonallibrary.exception.BookIdNotFoundException;
//import prisc.diagonallibrary.service.BookService;
//import prisc.diagonallibrary.util.BookCreator;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
///**
// * Test class for validating the functionality of the {@link BookController}.
// */
//@ExtendWith(SpringExtension.class)  // Uses Spring with JUnit
//@DisplayName("Test for BookController")
//class BookControllerTest {
//
//    /*
//    private final BookResponse book1 =
//            new BookResponse(UUID.fromString(
//                    "a7669e4c-4420-43c8-9b90-81e149d37d95"),
//                    "One Title",
//                    "One person",
//                    1900);
//    private final BookResponse book2 = new BookResponse(UUID.fromString(
//            "a7669e4c-4420-43c8-9b90-81e149d37d96"),
//            "Other TitLE",
//            "Some author",
//            2020);
//    private final BookResponse book3 = new BookResponse(UUID.fromString(
//            "a7669e4c-4420-43c8-9b90-81e149d37d97"),
//            "The Two",
//            "Another AUTHOR",
//            1900);
//
//    @InjectMocks
//    private BookController bookController;
//    @Mock
//    private BookService bookServiceMock;
//
//    @Test
//    @DisplayName("getAll: Returns a list of all Books saved in database in BookResponse format")
//    void getAll_ReturnsAListOfAllBookResponse_WhenSuccessful() {
//        List<BookResponse> books = List.of(book1, book2, book3);
//        when(bookServiceMock.getAll())
//                .thenReturn(books);
//
//        List<BookResponse> bookList = this.bookController.getAll().getBody();
//
//        Assertions.assertThat(bookList)
//                .isNotNull()
//                .isNotEmpty()
//                .contains(book1, book2, book3)
//                .hasSize(3);
//    }
//
//    @Test
//    @DisplayName("getAll: Returns an empty list when database is empty")
//    void getAll_ReturnsAEmpty_WhenDatabaseIsEmpty() {
//        when(bookServiceMock.getAll())
//                .thenReturn(new ArrayList<>());
//
//        List<BookResponse> bookList = this.bookController.getAll().getBody();
//
//        Assertions.assertThat(bookList)
//                .isNotNull()
//                .isEmpty();
//    }
//
//    @Test
//    @DisplayName("save: Returns the persisted Book (in BookResponse format) when successful")
//    void save_ReturnsThePersistedBook_WhenSuccessful() {
//        BookResponse bookResponse = BookCreator.createBookResponse();
//
//        when(bookServiceMock.save(any(BookPostRequest.class)))
//                .thenReturn(bookResponse);
//
//        BookResponse savedBook = this.bookController.save(BookCreator.createBookRequestToSave()).getBody();
//
//        Assertions.assertThat(savedBook)
//                .isNotNull()
//                .isEqualTo(bookResponse);
//
//        Assertions.assertThat(savedBook.getBookId()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("save: throws BookAlreadyExistsException when there is a equal book in database")
//    void save_ThrowsBookAlreadyExistsException_WhenBookAlreadyExistsInDatabase() {
//        when(bookServiceMock.save(any(BookPostRequest.class)))
//                .thenThrow(BookAlreadyExistsException.class);
//
//        Assertions.assertThatExceptionOfType(BookAlreadyExistsException.class)
//                .isThrownBy(() -> this.bookController.save(BookCreator.createBookRequestToSave()));
//    }
//
//    @Test
//    @DisplayName("findById: Returns the unique book that correspond to id informed when successful")
//    void findById_ReturnsTheUniqueBook_WhenSuccessful() {
//        BookResponse bookResponse = BookCreator.createBookResponse();
//        when(bookServiceMock.findById_OrThrowBookIdNotFoundException(any(UUID.class)))
//                .thenReturn(bookResponse);
//
//        BookResponse bookById = this.bookController
//                .findById(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95")).getBody();
//
//        Assertions.assertThat(bookById)
//                .isNotNull()
//                .isEqualTo(bookResponse);
//    }
//
//    @Test
//    @DisplayName("findById: Throws BookIdNotFoundException when there is no book correspondent to informed id")
//    void findById_ThrowsBookIdNotFoundException_WhenNoBookIsFound() {
//        doThrow(BookIdNotFoundException.class)
//                .when(bookServiceMock).findById_OrThrowBookIdNotFoundException(any(UUID.class));
//
//        Assertions.assertThatExceptionOfType(BookIdNotFoundException.class)
//                .isThrownBy(() -> this.bookController
//                        .findById(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95")));
//    }
//
//    @Test
//    @DisplayName("findByTitle: Returns a list of Books (as BookResponse) that contains the string informed in title")
//    void findByTitle_ReturnsAListOfBooks_WhenSuccessful() {
//        List<BookResponse> books = List.of(book1, book2);
//        when(bookServiceMock.findByTitle(any(String.class)))
//                .thenReturn(books);
//
//        List<BookResponse> booksByTitle = this.bookController.findByTitle("title").getBody();
//
//        Assertions.assertThat(booksByTitle)
//                .isNotNull()
//                .hasSize(2)
//                .contains(book1, book2);
//    }
//
//    @Test
//    @DisplayName("findByTitle: Returns an empty list when no book is found")
//    void findByTitle_ReturnsAEmptyList_WhenNoBookIsFound() {
//        when(bookServiceMock.findByTitle(any(String.class)))
//                .thenReturn(new ArrayList<>());
//
//        List<BookResponse> booksByTitle = this.bookController.findByTitle("NoTitle").getBody();
//
//        Assertions.assertThat(booksByTitle)
//                .isNotNull()
//                .isEmpty();
//    }
//
//    @Test
//    @DisplayName("findByAuthor: Returns a list of Books (as BookResponse) that " +
//            "contains the string informed in its author name")
//    void findByAuthor_ReturnsAListOfBooks_WhenSuccessful() {
//        List<BookResponse> books = List.of(book2, book3);
//        when(bookServiceMock.findByAuthor(any(String.class)))
//                .thenReturn(books);
//
//        List<BookResponse> booksByAuthor = this.bookController.findByAuthor("author").getBody();
//
//        Assertions.assertThat(booksByAuthor)
//                .isNotNull()
//                .hasSize(2)
//                .contains(book2, book3);
//    }
//
//    @Test
//    @DisplayName("findByAuthor: Returns an empty list when no book is found")
//    void findByAuthor_ReturnsAnEmptyList_WhenNoBookIsFound() {
//
//        when(bookServiceMock.findByAuthor(any(String.class)))
//                .thenReturn(new ArrayList<>());
//
//        List<BookResponse> booksByAuthor = this.bookController.findByAuthor("NoAuthor").getBody();
//
//        Assertions.assertThat(booksByAuthor)
//                .isNotNull()
//                .isEmpty();
//    }
//
//    @Test
//    @DisplayName("findByYear: Returns a list of Books (as BookResponse) from publication year informed")
//    void findByYear_ReturnsAListOfBooks_WhenSuccessful() {
//        List<BookResponse> books = List.of(book1, book3);
//        when(bookServiceMock.findByYear(any(Integer.class)))
//                .thenReturn(books);
//
//        List<BookResponse> booksByYear = this.bookController.findByYear(2000).getBody();
//
//        Assertions.assertThat(booksByYear)
//                .isNotNull()
//                .hasSize(2)
//                .contains(book1, book3);
//    }
//
//    @Test
//    @DisplayName("findByYear:  Returns an empty list when no book is found")
//    void findByYear_ReturnsAnEmptyList_WhenNoBookIsFound() {
//        when(bookServiceMock.findByYear(any(Integer.class)))
//                .thenReturn(new ArrayList<>());
//
//        List<BookResponse> booksByYear = this.bookController.findByYear(20).getBody();
//
//        Assertions.assertThat(booksByYear)
//                .isNotNull()
//                .isEmpty();
//    }
//
//    @Test
//    @DisplayName("delete: Deletes the book with the id informed if it exists")
//    void deleteById_RemovesBookWithInformedId_WhenSuccessful() {
//        doNothing()
//                .when(bookServiceMock).deleteById(any(UUID.class));
//
//        ResponseEntity<Void> response = this.bookController
//                .delete(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95"));
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }
//
//    @Test
//    @DisplayName("delete: Throws BookIdNotFoundException if the bookId does not exists in database")
//    void deleteById_ThrowsBookIdNotFoundException_IfBookIdDoesNotExistsInDatabase() {
//        doThrow(BookIdNotFoundException.class)
//                .when(bookServiceMock).deleteById(any(UUID.class));
//
//        Assertions.assertThatExceptionOfType(BookIdNotFoundException.class)
//                .isThrownBy(() -> this.bookController
//                        .delete(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95")));
//    }
//
//    @Test
//    @DisplayName("update: Replaces book's information when successful")
//    void update_ReplaceBookInformation_WhenSuccesful() {
//        BookResponse response = BookCreator.createBookResponse();
//        when(bookServiceMock.update(any(BookPutRequest.class)))
//                .thenReturn(response);
//
//        BookResponse bookUpdated = this.bookController.update(BookCreator.createBookRequestToUpdate()).getBody();
//        Assertions.assertThat(bookUpdated).isNotNull();
//        Assertions.assertThat(bookUpdated.getBookId()).isEqualTo(response.getBookId());
//    }
//
//    @Test
//    @DisplayName("update: Throws BookIdNotFoundException if the bookId does not exists in database")
//    void update_ThrowsBookIdNotFoundException_IfBookIdDoesNotExistsInDatabase() {
//        doThrow(BookIdNotFoundException.class)
//                .when(bookServiceMock).update(any(BookPutRequest.class));
//
//        Assertions.assertThatExceptionOfType(BookIdNotFoundException.class)
//                .isThrownBy(() -> this.bookController.update(BookCreator.createBookRequestToUpdate()));
//
//    }
//    @Test
//    @DisplayName("update: Throws BookAlreadyExistsException if book with equals attributes already exists in database")
//    void update_ThrowsBookAlreadyExistsException_IfEqualBookAlreadyExistsInDatabase() {
//        doThrow(BookAlreadyExistsException.class)
//                .when(bookServiceMock).update(any(BookPutRequest.class));
//
//        Assertions.assertThatExceptionOfType(BookAlreadyExistsException.class)
//                .isThrownBy(() -> this.bookController.update(BookCreator.createBookRequestToUpdate()));
//    }*/
//}