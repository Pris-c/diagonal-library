package prisc.diagonallibrary.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;
import prisc.diagonallibrary.exception.BookAlreadyExistsException;
import prisc.diagonallibrary.mapper.BookMapper;
import prisc.diagonallibrary.repository.BookRepository;

import java.util.List;


@Service
public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class);

    @Autowired
    BookRepository bookRepository;


    public List<BookResponse> getAll(){
        return BookMapper.toBookResponseList(bookRepository.findAll());
    }

    public BookResponse save(BookPostRequestBody bookPostRequestBody) throws BookAlreadyExistsException{
        Book bookToSave = BookMapper.toBook(bookPostRequestBody);

        if (bookRepository.existsByAttributes(bookToSave.getTitle(), bookToSave.getAuthor(), bookToSave.getYear())){
            logger.error("The book " + bookPostRequestBody + " is already in database");
            throw new BookAlreadyExistsException("The book " + bookPostRequestBody + " is already in database");
        }

        return BookMapper.toBookResponse(bookRepository.save(bookToSave));
    }





}
