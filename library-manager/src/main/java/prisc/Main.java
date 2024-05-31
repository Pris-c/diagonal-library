package prisc;

import prisc.book.BookController;

public class Main {
    public static void main(String[] args) {

        BookController controller = new BookController();

        System.out.println("\nWELCOME TO LUMUS LIBRARY");
        controller.mainMenu();

    }

}