package prisc.librarymanager.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prisc.librarymanager.controller.request.BookPutRequest;
import prisc.librarymanager.controller.request.BookPostRequest;
import prisc.librarymanager.controller.response.BookResponse;
import prisc.librarymanager.service.BookService;

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
     * @param bookPostRequest Request body containing book information.
     * @return ResponseEntity with the saved book response and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<BookResponse> save(@Valid @RequestBody BookPostRequest bookPostRequest) {
        return new ResponseEntity<>(bookService.save(bookPostRequest), HttpStatus.CREATED);
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
     * Handles an HTTP GET request to retrieve a list of books with titles containing the specified substring.
     *
     * @param title Substring to search for in book titles.
     * @return ResponseEntity with a list of book responses and HTTP status OK.
     */
    @GetMapping(path = "/title")
    public ResponseEntity<List<BookResponse>> findByTitle(@RequestParam String title) {
        return new ResponseEntity<>(bookService.findByTitle(title), HttpStatus.OK);
    }


    /**
     * Handles an HTTP GET request to retrieve a list of books with authors' names containing the specified substring.
     *
     * @param author Substring to search for in book authors' names.
     * @return ResponseEntity with a list of book responses and HTTP status OK.
     */
    @GetMapping(path = "/author")
    public ResponseEntity<List<BookResponse>> findByAuthor(@RequestParam String author) {
        return new ResponseEntity<>(bookService.findByAuthor(author), HttpStatus.OK);
    }

    /**
     * Handles an HTTP GET request to retrieve a list of books published in the specified year.
     *
     * @param year Year of publication.
     * @return ResponseEntity with a list of book responses and HTTP status OK.
     */
    @GetMapping(path = "/year")
    public ResponseEntity<List<BookResponse>> findByYear(@RequestParam int year) {
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
    public ResponseEntity<BookResponse> update(@RequestBody @Valid BookPutRequest bookRequest) {
        return new ResponseEntity<>(bookService.update(bookRequest), HttpStatus.OK);
    }

}
