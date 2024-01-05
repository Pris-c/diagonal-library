package prisc.diagonallibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prisc.diagonallibrary.controller.request.BookRequest;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.service.BookService;
import prisc.diagonallibrary.validations.groups.PostValidation;
import prisc.diagonallibrary.validations.groups.PutValidation;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for handling HTTP requests related to books in the diagonal library.
 */
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;

    /**
     * Handles HTTP GET request to retrieve a list of all books.
     *
     * @return ResponseEntity with a list of book responses and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAll() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    /**
     * Handles HTTP POST request to save a new book.
     *
     * @param bookRequest Request body containing book information.
     * @return ResponseEntity with the saved book response and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<BookResponse> save(@RequestBody @Validated(PostValidation.class) BookRequest bookRequest) {
        return new ResponseEntity<>(bookService.save(bookRequest), HttpStatus.CREATED);
    }

    /**
     * Handles HTTP GET request to retrieve a specific book by its unique identifier.
     *
     * @param id Unique identifier of the book.
     * @return ResponseEntity with the book response and HTTP status OK.
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(bookService.findById_OrThrowBookIdNotFoundException(id), HttpStatus.OK);
    }

    /**
     * Handles HTTP GET request to retrieve a list of books whose titles contain the specified substring.
     *
     * @param title Substring to search for in book titles.
     * @return ResponseEntity with a list of book responses and HTTP status OK.
     */
    @GetMapping(path = "/title/{title}")
    public ResponseEntity<List<BookResponse>> findByTitle(@PathVariable String title) {
        return new ResponseEntity<>(bookService.findByTitle(title), HttpStatus.OK);
    }

    /**
     * Handles HTTP GET request to retrieve a list of books whose authors' names contain the specified substring.
     *
     * @param author Substring to search for in book authors' names.
     * @return ResponseEntity with a list of book responses and HTTP status OK.
     */
    @GetMapping(path = "/author/{author}")
    public ResponseEntity<List<BookResponse>> findByAuthor(@PathVariable String author) {
        return new ResponseEntity<>(bookService.findByAuthor(author), HttpStatus.OK);
    }

    /**
     * Handles HTTP GET request to retrieve a list of books published in the specified year.
     *
     * @param year Year of publication.
     * @return ResponseEntity with a list of book responses and HTTP status OK.
     */
    @GetMapping(path = "/year/{year}")
    public ResponseEntity<List<BookResponse>> findByYear(@PathVariable int year) {
        return new ResponseEntity<>(bookService.findByYear(year), HttpStatus.OK);
    }

    /**
     * Handles HTTP DELETE request to delete a book by its unique identifier.
     *
     * @param id Unique identifier of the book to be deleted.
     * @return ResponseEntity with HTTP status NO_CONTENT.
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handles HTTP PUT request to update an existing book.
     *
     * @param bookRequest Request body containing updated book information.
     * @return ResponseEntity with the updated book response and HTTP status OK.
     */
    @PutMapping
    public ResponseEntity<BookResponse> update(@RequestBody @Validated(PutValidation.class) BookRequest bookRequest) {
        return new ResponseEntity<>(bookService.update(bookRequest), HttpStatus.OK);
    }

}
