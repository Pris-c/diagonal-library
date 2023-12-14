package prisc.diagonallibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prisc.diagonallibrary.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;



}
