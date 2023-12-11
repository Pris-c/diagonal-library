package prisc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prisc.book.BookController;

public class Main {
    public static void main(String[] args) {

        BookController controller = new BookController();

        System.out.println("\nWELCOME TO DIAGONAL LIBRARY");
        controller.mainMenu();

    }

}