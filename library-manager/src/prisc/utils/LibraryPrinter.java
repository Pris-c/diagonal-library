package prisc.utils;

public class LibraryPrinter {

    public static void printEntryRequest(String field) {
        System.out.print(field + "\n>> ");
    }

    public static void printEntryRequest() {
        System.out.print(">> ");
    }

    public static void printInvalidOption() {
        System.out.println();
        System.out.println("## Invalid option.");
    }

    public static void printFailMessage(String message) {
        System.out.println("\n## FAIL: " + message + "\n");
    }

    public static void printMessage(String message) {
        System.out.println("\n## " + message);
    }


}
