package prisc.diagonallibrary.controller;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.request.BookPutRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    //TODO: Validate Path Variables
    private static final Logger logger = LogManager.getLogger(BookController.class);

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
    public ResponseEntity<BookResponse> findById(@Valid @PathVariable Long id){
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }
    @GetMapping(path = "/title/{title}")
    public ResponseEntity<List<BookResponse>> findByTitle(@Valid @PathVariable String title){
        return new ResponseEntity<>(bookService.findByTitle(title), HttpStatus.OK);
    }
    @GetMapping(path = "/author/{author}")
    public ResponseEntity<List<BookResponse>> findByAuthor(@Valid @PathVariable String author){
        return new ResponseEntity<>(bookService.findByAuthor(author), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id){
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<BookResponse> update(@Valid @RequestBody BookPutRequestBody bookPutRequestBody){
        return new ResponseEntity<>(bookService.update(bookPutRequestBody), HttpStatus.OK);
    }


}
