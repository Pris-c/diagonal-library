package prisc.diagonallibrary.controller;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.exception.BookAlreadyExistsException;
import prisc.diagonallibrary.mapper.BookMapper;
import prisc.diagonallibrary.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
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


}
