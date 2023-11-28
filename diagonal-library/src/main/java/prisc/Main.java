package prisc;

import java.util.List;


import prisc.entity.Book;
import prisc.entity.BookRepository;


public class Main {
    public static void main(String[] args) {

        BookRepository bookRepository = new BookRepository();

/*

        Book book1 = new Book("Book Repository persist", "Priscila", 2020);

        bookRepository.save(book);
        bookRepository.save(book1);


        List<Book> books = bookRepository.findByTitle("persist");
        List<Book> books = bookRepository.findByAuthor("jakarta");
        List<Book> books = bookRepository.findByYear(2010);
        Book book = bookRepository.findById(3);
          bookRepository.delete(153);

          if (book != null) {
            System.out.println("Found: ");
            System.out.println(book);
        } else {
            System.out.println("Book not found");
        }




*/
        //Book book = new Book(1, "Livro atualizado", "merge method", 2023);

        //bookRepository.update(book);

        System.out.println("LISTING: ");
        List<Book> books = bookRepository.getAll();
        System.out.println("\n\nPRINTING LIST");
        books.forEach(b -> {
            System.out.println("Print book: " + b);
        });


    }

}