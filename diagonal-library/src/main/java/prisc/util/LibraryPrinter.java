package prisc.util;

import prisc.book.BookDTO;

public class LibraryPrinter {

    public static void printHibernateException() {
        String message = "Oops! Something went wrong while processing your request. Please try again later. " +
                          "\nIf the problem persists, feel free to contact support.";
        System.out.print(message);
    }

    public static void printSavedSuccessfullyMessage(BookDTO dtoToBeSaved) {
        String message = "Book saved successfully!"
                          + "\n" + dtoToBeSaved.getTitle() + ", " + dtoToBeSaved.getAuthor() + ", " + dtoToBeSaved.getYear();
        System.out.print(message);
    }

    public static void printErrorMessage(String errorMessage) {
        String message = "FAIL: " + errorMessage;
        System.out.print(message);
    }

}
