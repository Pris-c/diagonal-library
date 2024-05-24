package prisc.util;

import prisc.book.BookDTO;

import java.time.Year;
import java.util.List;

public class LibraryPrinter {


    public static void printBookList(List<BookDTO> books){
        System.out.println("\nListing books..");
        books.forEach(System.out::println);
    }

    public static void printBook(BookDTO book) {
        System.out.println("\nFound book.." + "\n" + book);
    }

    public static void printSavedSuccessfullyMessage(BookDTO dtoToBeSaved) {
        String message = "\nBook saved successfully!"
                + "\n" + dtoToBeSaved.getTitle() + ", " + dtoToBeSaved.getAuthor() + ", " + dtoToBeSaved.getYear();
        System.out.println(message);
    }
    public static void printDeletedSuccessfullyMessage() {
        String message = "\nBook deleted successfully!";
        System.out.println(message);
    }
    public static void printUpdatedSuccessfullyMessage(BookDTO bookUpdated) {
        String message = "\nBook updated successfully! \n" + bookUpdated ;
        System.out.println(message);
    }

    public static void printExceptionMessageToUser() {
        String message = "\nOops! Something went wrong while processing your request. Please try again later. " +
                          "\nIf the problem persists, feel free to contact support.";
        System.out.println(message);
    }


    public static void printErrorMessage(String errorMessage) {
        String message = "\nFAIL: " + errorMessage;
        System.out.println(message);
    }

    public static void printLibraryIsEmpty() {
        System.out.println("\nThere are no books registered.");
    }

    public static void printMessage(String message) {
        System.out.println("\n" + message);
    }

    public static void printNoBooksFound(){
        System.out.println("\nNo books were found");
    }

    public static void printEntryRequest(String field){
        System.out.print("\n" + field + ": \n>> " );
    }
    public static void printYearRequest(){
        System.out.print("\nYear: \n>> ");
    }

    public static void printIdRequest() {
        System.out.print("\nId: \n>> ");
    }

    public static void printInvalidOption() {
        System.out.println("\n## Invalid option.");
    }


    public static void printInvalidInputtedString(String field){
        System.out.println("\nFAIL: The " + field.toLowerCase() + " must have between 1 and 100 characters");
    }

    public static void printInvalidInputtedYear() {
        System.out.println("\nThe year must be a number between 0 and " + Year.now().getValue());
    }

    public static void printInvalidInputtedId() {
        System.out.println("\nThe id must be a positive number.");
    }

    public static void printMainMenu(){
        System.out.print("""
                    
                    What do you want to do?
                    1 - Consult available books
                    2 - Register a new book
                    3 - Find a book
                    4 - Update a book
                    5 - Delete a book
                    0 - Finish the application
                    >>\s""");
    }
    public static void printFindMenu(){
        System.out.print("""
                    
                    Choose one option:
                    1 - Find a book by title
                    2 - Find a book by author
                    3 - Find a book by year
                    4 - Find a book by id
                    0 - Back to the main menu
                    >>\s""");
    }


    public static void printInvalidInputMenu(){
        System.out.print("""
                    
                    1 - Try it again
                    0 - Return to the main menu
                    >>\s""");

    }


    public static void printUpdateMenu() {
        System.out.print("""
                    Choose one option:
                    1 - Update title
                    2 - Update author
                    3 - Update year
                    0 - Back to the main menu
                    >>\s""");
    }
}
