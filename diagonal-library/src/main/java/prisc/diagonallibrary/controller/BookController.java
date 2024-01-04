package prisc.diagonallibrary.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.request.BookPutRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.service.BookService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAll(){
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponse> save(@Valid @RequestBody BookPostRequestBody bookPostRequestBody){
        return new ResponseEntity<>(bookService.save(bookPostRequestBody), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable UUID id){
        return new ResponseEntity<>(bookService.findById_OrThrowBookIdNotFoundException(id), HttpStatus.OK);
    }
    @GetMapping(path = "/title/{title}")
    public ResponseEntity<List<BookResponse>> findByTitle(@PathVariable String title){
        return new ResponseEntity<>(bookService.findByTitle(title), HttpStatus.OK);
    }
    @GetMapping(path = "/author/{author}")
    public ResponseEntity<List<BookResponse>> findByAuthor(@PathVariable String author){
        return new ResponseEntity<>(bookService.findByAuthor(author), HttpStatus.OK);
    }
    @GetMapping(path = "/year/{year}")
    public ResponseEntity<List<BookResponse>> findByYear(@PathVariable int year){
        return new ResponseEntity<>(bookService.findByYear(year), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<BookResponse> update(@Valid @RequestBody BookPutRequestBody bookPutRequestBody){
        return new ResponseEntity<>(bookService.update(bookPutRequestBody), HttpStatus.OK);
    }



}
