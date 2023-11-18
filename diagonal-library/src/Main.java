import prisc.library.LibraryService;

public class Main {
    public static void main(String[] args) {

        LibraryService libraryHelper = new LibraryService();

        libraryHelper.fillBooksList();

        System.out.println("Welcome to the Diagonal Library");

        libraryHelper.mainMenu();

    }
}