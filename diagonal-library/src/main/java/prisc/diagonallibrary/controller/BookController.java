package prisc.diagonallibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prisc.diagonallibrary.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;
}
