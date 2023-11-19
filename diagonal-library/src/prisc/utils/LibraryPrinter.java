package prisc.utils;

import prisc.library.Book;

import java.util.List;

public class LibraryPrinter {


//    public static void printOutput(String output){
//        System.out.println(" ## " + output);
//        System.out.println(" #######################\n");
//    }

//    public static void printBookListOrNotFoundMessage(List<Book> books){
//
//        if (!books.isEmpty()){
//            System.out.println(" ## Listing books... ");
//            books.forEach(System.out::println);
//            System.out.println();
//        } else {
//            System.out.println(" No books were found.");
//        }
//
//        System.out.println(" #######################\n");
//    }



    public static void printBookOrNotFoundMessage(Book book) {

        if (book.getBookId() != 0){

            System.out.println("## Found book: ");
            System.out.println(book);
            System.out.println();
        } else {
            System.out.println("No book was found.");
        }

        System.out.println("#######################\n");
    }

    public static void printEntryRequest(String field){
        System.out.print(field + "\n>> ");
    }

    public static void printEntryRequest(){
        System.out.print(">> ");
    }

    public static void printInvalidOption(){
        System.out.println();
        System.out.println("## Invalid option.");
    }

    public static void printFailMessage(String message){
        System.out.println("\n## FAIL: " + message +"\n");
    }
    public static void printMessage(String message){
        System.out.println("\n## " + message);
    }




}
