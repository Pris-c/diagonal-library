package prisc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prisc.book.Book;
import prisc.book.BookRepository;

import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);


    public static void main(String[] args) {

        BookRepository bookRepository = new BookRepository();

        List<Book> books = bookRepository.getAll();

        books.forEach(System.out::println);

    }

}