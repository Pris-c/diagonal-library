package prisc.diagonallibrary.service;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.request.BookPutRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;
import prisc.diagonallibrary.exception.BookAlreadyExistsException;
import prisc.diagonallibrary.exception.BookIdNotFoundException;
import prisc.diagonallibrary.mapper.BookMapper;
import prisc.diagonallibrary.repository.BookRepository;

import java.util.List;


@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;


    public List<BookResponse> getAll(){
        return BookMapper.INSTANCE.toBookResponseList(bookRepository.findAll());
    }

    @Transactional
    public BookResponse save(BookPostRequestBody bookPostRequestBody) throws BookAlreadyExistsException{
        Book bookToSave = BookMapper.INSTANCE.toBook(bookPostRequestBody);
        if (bookRepository.existsByAttributes(bookToSave.getTitle(), bookToSave.getAuthor(), bookToSave.getYear())){
            throw new BookAlreadyExistsException("The book " + bookPostRequestBody + " is already in database");
        }

        return BookMapper.INSTANCE.toBookResponse(bookRepository.save(bookToSave));
    }


    public BookResponse findById(Long id){
        return BookMapper.INSTANCE.toBookResponse(
                bookRepository.findById(id)
                .orElseThrow( () -> new BookIdNotFoundException("Id " + id + " not found."))
                );
    }

    public List<BookResponse> findByTitle(String title){
        return BookMapper.INSTANCE.toBookResponseList(bookRepository.findByTitleIgnoreCaseContaining(title));
    }
    public List<BookResponse> findByAuthor(String author){
        return BookMapper.INSTANCE.toBookResponseList(bookRepository.findByAuthorIgnoreCaseContaining(author));
    }
    public List<BookResponse> findByYear(int year){
        return BookMapper.INSTANCE.toBookResponseList(bookRepository.findByYear(year));
    }


    @Transactional
    public BookResponse update(BookPutRequestBody bookPutRequestBody){
        //Verify if the id exists or throw exception in method findById
        Book savedBook = BookMapper.INSTANCE.toBook(findById(bookPutRequestBody.getBookId()));

        //Check if there is an identical book in the database
        if (bookRepository.existsByAttributes(bookPutRequestBody.getTitle(), bookPutRequestBody.getAuthor(), bookPutRequestBody.getYear())){
            throw new BookAlreadyExistsException("The book " + bookPutRequestBody + " is already in database");
        }

        bookPutRequestBody.setBookId(savedBook.getBookId());
        Book updatedBook = bookRepository.save(BookMapper.INSTANCE.toBook(bookPutRequestBody));

        return BookMapper.INSTANCE.toBookResponse(updatedBook);
    }

    @Transactional
    public void delete(Long id){
        bookRepository.delete(BookMapper.INSTANCE.toBook(findById(id)));
    }





}
