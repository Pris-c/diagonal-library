package prisc.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prisc.enums.StringField;
import prisc.exceptions.BookAlreadyExistsException;
import prisc.util.LibraryPrinter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Controller class for managing book-related operations in the library application.
 * Provides methods for interacting with the library, including listing, saving, finding, updating, and deleting books.
 */
public class BookController {

    BookControllerHelper helper = new BookControllerHelper();
    BookService bookService = new BookService();
    private final Scanner scan = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(BookController.class);


    /**
     * Displays the initial menu, reads user input, and invokes the appropriate function based on the selected option.
     * The menu options include:
     *  - Option 1: Lists all books in the library if it is not empty; otherwise, displays a message indicating an empty library.
     *  - Option 2: Saves a new book to the library.
     *  - Option 3: Searches for a book in the library if it is not empty; otherwise, displays a message indicating an empty library.
     *  - Option 4: Updates information for an existing book in the library if it is not empty; otherwise, displays a message indicating an empty library.
     *  - Option 5: Deletes an existing book from the library if it is not empty; otherwise, displays a message indicating an empty library.
     *  - Option 0: Exits the menu loop.
     * Invalid options prompt a message indicating an invalid choice.
     */
    public void mainMenu() {
        String option;
        boolean validOption = false;

        do {
            try {


                LibraryPrinter.printMainMenu();
                option = scan.nextLine();

                switch (option) {
                    case "1":
                        if (bookService.libraryIsEmpty()) {
                            LibraryPrinter.printLibraryIsEmpty();
                        } else {
                            listAll();
                        }
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
                            LibraryPrinter.printLibraryIsEmpty();
                        } else {
                            update();
                        }
                        break;
                    case "5":
                        if (bookService.libraryIsEmpty()) {
                            LibraryPrinter.printLibraryIsEmpty();
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


            } catch (Exception e){
            logger.error("Error in main menu method " + e + e.getMessage());
            LibraryPrinter.printExceptionMessageToUser();
            }
        } while (!validOption);
    }


    /**
     * Checks if the database is empty. If not, displays the registered books.
     * If the library is empty, a corresponding message is printed.
     * In case of any exception during the process, an exception message is printed to the user.
     */
    public void listAll() {

        try {
            List<BookDTO> books = bookService.getAll();
            LibraryPrinter.printBookList(books);

        } catch (Exception e){
            LibraryPrinter.printExceptionMessageToUser();
        }
    }


    /**
     * Prompts the user to insert the new book's information and attempts to save the book to the library.
     * Displays success or error messages accordingly.
     *
     * The process includes the following steps:
     * 1. Reads and validates the new book's title, author, and publication year.
     * 2. Creates a new BookDTO object with the provided information.
     * 3. Attempts to save the book using the BookService's save method.
     * 4. Displays a success message with the saved book's information if the save is successful.
     * 5. Handles BookAlreadyExistsException by logging the exception and printing an error message to the user.
     * 6. Handles other exceptions by logging detailed error information and printing a general exception message to the user.
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
            logger.error("Something went wrong while saving book" + e + e.getMessage());
            LibraryPrinter.printExceptionMessageToUser();
        }
    }


    /**
     * Invokes various find methods based on user input after presenting the find menu.
     * The find menu options include:
     *  - Option 0: Returns to the mainMenu.
     *  - Option 1: Invokes the findByTitle method to search for books by title.
     *  - Option 2: Invokes the findByAuthor method to search for books by author.
     *  - Option 3: Invokes the findByYear method to search for books by publication year.
     *  - Option 4: Invokes the findById method to search for a book by its unique identifier.
     * Invalid options prompt a message indicating an invalid choice.
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
            logger.error("Something went wrong while fetching book by title " + e + e.getMessage());
            LibraryPrinter.printExceptionMessageToUser();
        }

    }


    /**
     * Prompts the user to enter a valid book title and attempts to find books with matching titles.
     * Displays the list of found books or a message indicating no books were found.
     *
     * The process includes the following steps:
     * 1. Reads and validates the user-inputted title using the helper method.
     * 2. Calls the BookService's findByTitle method to retrieve a list of books with matching titles.
     * 3. Prints the list of found books using LibraryPrinter if the list is not empty.
     * 4. Prints a message indicating no books were found if the list is empty.
     * 5. Handles any exceptions during the process by logging detailed error information and printing a general
     *              exception message to the user.
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
            logger.error("Something went wrong while fetching book by author " + e + e.getMessage());
            LibraryPrinter.printExceptionMessageToUser();
        }
    }


    /**
     * Prompts the user to enter a valid year and attempts to find books with matching publication years.
     * Displays the list of found books or a message indicating no books were found.
     *
     * The process includes the following steps:
     * 1. Reads and validates the user-inputted year using the helper method.
     * 2. Calls the BookService's findByYear method to retrieve a list of books with matching publication years.
     * 3. Prints the list of found books using LibraryPrinter if the list is not empty.
     * 4. Prints a message indicating no books were found if the list is empty.
     * 5. Handles any exceptions during the process by logging detailed error information and printing a general exception message to the user.
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
            logger.error("Something went wrong while fetching book by year " + e + e.getMessage());
            LibraryPrinter.printExceptionMessageToUser();
        }
    }


    /**
     * Prompts the user to enter a valid book ID and attempts to find a book with a matching identifier.
     * Displays the found book or a message indicating no books were found.
     *
     * The process includes the following steps:
     * 1. Reads and validates the user-inputted book ID using the helper method.
     * 2. Calls the BookService's findById method to retrieve a book with the matching identifier.
     * 3. Prints the found book using LibraryPrinter if it is not null.
     * 4. Prints a message indicating no books were found if the book is null.
     * 5. Handles any exceptions during the process by logging detailed error information and printing a general exception message to the user.
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
            logger.error("Something went wrong while fetching book by id " + e + e.getMessage());
            LibraryPrinter.printExceptionMessageToUser();
        }
    }


    /**
     * Prompts the user to enter a valid book ID and updates the corresponding book's information based on user choices.
     * Displays success or error messages accordingly.
     *
     * The process includes the following steps:
     * 1. Reads and validates the user-inputted book ID using the helper method.
     * 2. Retrieves the book information from the database using the BookService's findById method.
     * 3. Displays the current book information using LibraryPrinter or prints a message if no books are found.
     * 4. Creates a copy of the book to update (bookToUpdate) with the same information as the book in the database.
     * 5. Presents an update menu to the user for choosing which information to update (title, author, or year).
     * 6. Reads and validates the new information based on the user's choice and updates the bookToUpdate object.
     * 7. Calls the BookService's update method to update the book in the database with the new information.
     * 8. Displays a success message with the updated book's information if the update is successful.
     * 9. Handles any exceptions during the process by logging detailed error information and printing a general exception message to the user.
     */
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
                logger.error("Something went wrong while updating book " + e + e.getMessage());
                LibraryPrinter.printExceptionMessageToUser();
            }
        }
    }


    /**
     * Prompts the user to enter a valid book ID and deletes the corresponding book from the library.
     * Displays success or error messages accordingly.
     *
     * The process includes the following steps:
     * 1. Prints a message instructing the user to type the book ID to be deleted.
     * 2. Reads and validates the user-inputted book ID using the helper method.
     * 3. Calls the BookService's delete method to delete the book with the specified ID from the library.
     * 4. Displays a success message if the deletion is successful.
     * 5. Handles NoSuchElementException by printing an error message with the exception's message.
     * 6. Handles other exceptions during the process by logging detailed error information and printing a general
     *              exception message to the user.
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
            logger.error("Something went wrong while deleting book " + e + e.getMessage());
            LibraryPrinter.printExceptionMessageToUser();
        }
    }

}
