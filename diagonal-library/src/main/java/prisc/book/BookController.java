package prisc.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prisc.enums.StringField;
import prisc.exceptions.BookAlreadyExistsException;
import prisc.util.LibraryPrinter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BookController {

    BookControllerHelper helper = new BookControllerHelper();
    BookService bookService = new BookService();
    private final Scanner scan = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(BookController.class);


    /**
     * Shows the initial menu, read the user input and calls the appropriated function
     */
    public void mainMenu() {
        String option;
        boolean validOption = false;

        do {
            LibraryPrinter.printMainMenu();
            option = scan.nextLine();

            switch (option) {
                case "1":
                    listAll();
                    break;
                case "2":
                    save();
                    break;
                case "3":
                    if (bookService.libraryIsEmpty()) {
                        LibraryPrinter.printLibraryIsEmpty();
                    } else {
                        find();
                    }
                    break;
                case "4":
                    if (bookService.libraryIsEmpty()) {
                        LibraryPrinter.printMessage("There are no books registered.");
                    } else {
                        update();
                    }
                    break;
                case "5":
                   if (bookService.libraryIsEmpty()) {
                        LibraryPrinter.printMessage("There are no books registered.");
                    } else {
                        delete();
                    }
                    break;
                case "0":
                    validOption = true;
                    break;
                default:
                    LibraryPrinter.printInvalidOption();
            }

        } while (!validOption);
    }



    /**
     * Checks if the database is empty. If not, displays the registered books.
     * If the library is empty, a corresponding message is printed.
     * In case of any exception, an exception message is printed to the user.
     */
    public void listAll() {

        try {
            if (bookService.libraryIsEmpty()) {
                LibraryPrinter.printLibraryIsEmpty();

            } else {
                List<BookDTO> books = bookService.getAll();
                LibraryPrinter.printBookList(books);
            }

        } catch (Exception e){
            LibraryPrinter.printExceptionMessageToUser();
        }
    }


    /**
     * Reads the input from the user to a new book and calls the Service to save it in database.
     * Prints to user if the book was saved or if the operation failed
     */
    public void save() {

        LibraryPrinter.printMessage("Insert the new book's information");

        try {
            String title = helper.readAValidTitleOrAuthor(StringField.TITLE);
            if (title == null){
                return;
            }
            String author = helper.readAValidTitleOrAuthor(StringField.AUTHOR);
            if (author == null){
                return;
            }
            Integer year = helper.readAValidYear();
            if (year == null){
                return;
            }

            BookDTO bookToBeSaved = new BookDTO(title, author, year);
            bookService.save(bookToBeSaved);
            //TODO: Confirm that book was saved
            LibraryPrinter.printSavedSuccessfullyMessage(bookToBeSaved);

        } catch (BookAlreadyExistsException e){
            logger.info(e.getMessage());
            LibraryPrinter.printErrorMessage("The book could not be saved.");
            LibraryPrinter.printMessage(e.getMessage());

        } catch (Exception e){
            LibraryPrinter.printExceptionMessageToUser();
        }
    }


    /**
     * Shows the menu with options to find a book, read the user input
     * and calls the appropriated function
     */
    private void find() {

        String option = helper.findMenu();

        switch (option) {
            case "0":
                return;
                // return to mainMenu
            case "1":
                findByTitle();
                break;
            case "2":
                findByAuthor();
                break;
            case "3":
                findByYear();
                break;
            case "4":
                findById();
                break;

                default:
                    LibraryPrinter.printInvalidOption();
            }
    }




    /**
     * Reads a String input from the user and returns the books that contains it in the title
     */
    private void findByTitle() {
        try {
            String title = helper.readAValidTitleOrAuthor(StringField.TITLE);
            if (title == null){
                return;
            }

            List<BookDTO> books = bookService.findByTitle(title);

            if (!books.isEmpty()) {
                LibraryPrinter.printBookList(books);
            } else {
                LibraryPrinter.printNoBooksFound();
            }
        } catch (Exception e){
            LibraryPrinter.printExceptionMessageToUser();
        }

    }


    /**
     * Reads a String input from the user and returns the books that contains it in the author name
     */
    private void findByAuthor() {
        try {
            String author = helper.readAValidTitleOrAuthor(StringField.AUTHOR);
            if (author == null){
                return;
            }

            List<BookDTO> books = bookService.findByAuthor(author);

            if (!books.isEmpty()) {
                LibraryPrinter.printBookList(books);
            } else {
                LibraryPrinter.printNoBooksFound();
            }
        } catch (Exception e){
            LibraryPrinter.printExceptionMessageToUser();
        }
    }


    /**
     * Reads an int input from the user and returns the books that have it as year
     */
    private void findByYear() {

        try {
            Integer year = helper.readAValidYear();
            if (year == null){
                return;
            }

            List<BookDTO> books = bookService.findByYear(year);

            if (!books.isEmpty()) {
                LibraryPrinter.printBookList(books);
            } else {
                LibraryPrinter.printNoBooksFound();
            }
        } catch (Exception e){
            LibraryPrinter.printExceptionMessageToUser();
        }
    }

    /**
     * Reads an int input from the user and returns the book that have it as id
     */
    private void findById() {

        try {
            Integer id = helper.readAValidId();
            if (id == null){
                return;
            }

            BookDTO book = bookService.findById(id);

            if (book != null) {
                LibraryPrinter.printBook(book);
            } else {
                LibraryPrinter.printNoBooksFound();
            }
        } catch (Exception e){
            LibraryPrinter.printExceptionMessageToUser();
        }
    }


    public void update(){
        //TODO: Check success

        Integer id = helper.readAValidId();
        if (id == null){
            return;
        }


        BookDTO bookInDatabase = bookService.findById(id);
        if (bookInDatabase == null){
            LibraryPrinter.printNoBooksFound();
        } else {

            LibraryPrinter.printBook(bookInDatabase);
            BookDTO bookToUpdate = new BookDTO(bookInDatabase.getId(), bookInDatabase.getTitle(), bookInDatabase.getAuthor(), bookInDatabase.getYear());

            String option = helper.updateMenu();
            try {
                switch (option) {
                    case "1":
                        String title = helper.readAValidTitleOrAuthor(StringField.TITLE);
                        if (title == null) {
                            return;
                        }
                        bookToUpdate.setTitle(title);
                        bookService.update(bookInDatabase, bookToUpdate);
                        LibraryPrinter.printUpdatedSuccessfullyMessage(bookToUpdate);
                        break;

                    case "2":
                        String author = helper.readAValidTitleOrAuthor(StringField.AUTHOR);
                        if (author == null) {
                            return;
                        }
                        bookToUpdate.setAuthor(author);
                        bookService.update(bookInDatabase, bookToUpdate);
                        LibraryPrinter.printUpdatedSuccessfullyMessage(bookToUpdate);
                        break;

                    case "3":
                        Integer year = helper.readAValidYear();
                        if (year == null) {
                            return;
                        }
                        bookToUpdate.setYear(year);
                        bookService.update(bookInDatabase, bookToUpdate);
                        LibraryPrinter.printUpdatedSuccessfullyMessage(bookToUpdate);
                        break;
                    case "0":
                        break;
                    default:
                        LibraryPrinter.printInvalidOption();
                }
            } catch (Exception e){
                LibraryPrinter.printExceptionMessageToUser();
            }
        }
    }


    /**
     * Reads an id from user, check if there is a book with it in database;
     * if there is, delete this book.
     */
    private void delete() {

        try {
            LibraryPrinter.printMessage("Type the book to be deleted");
            Integer id = helper.readAValidId();
            if (id == null) {
                return;
            }

            bookService.delete(id);
            LibraryPrinter.printDeletedSuccessfullyMessage();

        } catch (NoSuchElementException e) {
            LibraryPrinter.printErrorMessage(e.getMessage());

        } catch (Exception e){
            LibraryPrinter.printExceptionMessageToUser();
        }
    }

}
