package prisc.library;

import prisc.utils.LibraryPrinter;
import prisc.utils.enums.NumericField;
import prisc.utils.enums.Operation;
import prisc.utils.enums.StringField;

import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class BookController {

    private final Scanner scan = new Scanner(System.in);
    private final BookService bookService = new BookService();



    public void mainMenu(){

        String option;

        do {

            LibraryPrinter.printMessage("What do you want to do?");
            System.out.println("""
                                    1 - Consult available books
                                    2 - Register a new book
                                    3 - Find a book
                                    4 - Update a book
                                    5 - Delete a book
                                    0 - Finish the application""");
            LibraryPrinter.printEntryRequest();

            option = scan.nextLine();

            switch (option){
                case "1":
                    listAll();
                    break;
                case "2":
                    save();
                    break;
                case "3":
                    find();
                    break;
                case "4":
                    //updateById();
                    break;
                case "5":
                    //deleteById();
                    break;
                case "0":
                    break;
                default:
                    LibraryPrinter.printInvalidOption();
            }

        } while(!option.equals("0"));
    }





    public void listAll() {
        List<BookDTO> books = bookService.getAll();

        if (!books.isEmpty()){
            LibraryPrinter.printMessage("Listing books..");
            books.forEach(System.out::println);
        } else {
            LibraryPrinter.printMessage("No books were found");
        }
    }


    public void save(){

        Operation operation = Operation.SAVE;

        LibraryPrinter.printMessage("Insert the new book's infos");

        String title = readAValidTitleOrAuthor(StringField.TITLE, operation);

        String author = readAValidTitleOrAuthor(StringField.AUTHOR, operation);

        int year = readAValidYear(operation);

        BookDTO bookToBeSaved = new BookDTO(title, author, year);

        BookDTO savedBook = bookService.save( bookToBeSaved );

        int savedBookId = savedBook.getId();

        if (savedBookId > 0){
            LibraryPrinter.printMessage("Book saved successfully");
            System.out.println(savedBook);

        } else if (savedBookId < 0) {
            LibraryPrinter.printFailMessage("This book is already registered.");
        }
        else {
            LibraryPrinter.printFailMessage("The book could not be saved.");
        }

    }


    public void  find(){
        String option = "-1";

        do {
            LibraryPrinter.printMessage("Chose one option:");
            System.out.println("1 - Find a book by title");
            System.out.println("2 - Find a book by author");
            System.out.println("3 - Find a book by year");
            System.out.println("4 - Find a book by id");
            System.out.println("0 - Back to the Main menu");
            LibraryPrinter.printEntryRequest();

            option = scan.nextLine();

            switch (option){
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
                case "0":
                    // returns to method that call it
                    option = "0";
                    break;
                default:
                    LibraryPrinter.printInvalidOption();
            }

        } while (!option.equals("0"));
    }



    /*            ** FIND METHODS **                     */

    private void findByTitle(){

        Operation operation = Operation.FIND;

        String title = readAValidTitleOrAuthor(StringField.TITLE, operation);

        List<BookDTO> books = bookService.findByTitle(title);

        if (!books.isEmpty()){
            LibraryPrinter.printMessage("Listing books..");
            books.forEach(System.out::println);
        } else {
            LibraryPrinter.printMessage("No books were found");
        }


    }


    private void findByAuthor(){

        Operation operation = Operation.FIND;

        String author = readAValidTitleOrAuthor(StringField.AUTHOR, operation);

        List<BookDTO> books = bookService.findByAuthor(author);

        if (!books.isEmpty()){
            LibraryPrinter.printMessage("Listing books..");
            books.forEach(System.out::println);
        } else {
            LibraryPrinter.printMessage("No books were found");
        }


    }


    private void findById(){

        Operation operation = Operation.FIND;

        int id = readAValidId(operation);

        BookDTO book = bookService.findById(id);
        int bookId = book.getId();

        if (bookId > 0){
            LibraryPrinter.printMessage("Found book..");
            System.out.println(book);
        } else {
            LibraryPrinter.printMessage("No books were found");
        }


    }



    private void findByYear(){


        Operation operation = Operation.FIND;

        int year = readAValidYear(operation);

        List<BookDTO> books = bookService.findByYear(year);

        if (!books.isEmpty()){
            LibraryPrinter.printMessage("Listing books..");
            books.forEach(System.out::println);
        } else {
            LibraryPrinter.printMessage("No books were found");
        }


    }













    /*            ** READ VALID INPUTS METHODS **                     */

    /* **
            Receives the StringField which must be validated,
            read inputs from the user until be inserted a valid Title or Author String
            or the user decide change the operation.
    */

    //private String readAValidTitleOrAuthor(StringField field){
    private String readAValidTitleOrAuthor(StringField field, Operation operation){

        String fieldDescription = field.getFieldDescription();

        boolean stringIsValid = false;
        String validString = null;

        do{

            LibraryPrinter.printEntryRequest(fieldDescription);
            String inputtedString = scan.nextLine();

            if( validateTitleAndAuthorString(inputtedString)!=null && validateTitleAndAuthorString(inputtedString).equals(inputtedString) ){
                validString = inputtedString;
                stringIsValid = true;

            } else {

                LibraryPrinter.printFailMessage("The " + fieldDescription.toLowerCase() + " must have between 1 and " + "100" +  " character");

                if (operation.equals(Operation.SAVE)) {
                    invalidInputToSave();
                } else if (operation.equals(Operation.FIND)) {
                    invalidInputToFind();
                }
                // else if (OTHER)

            }

        } while(!stringIsValid);

        return validString;
    }



    /* **
            Read inputs from the user until be inserted a valid year
            or the user decide change the operation.
    */

    private int readAValidYear(Operation operation) {

        String stringYear;
        Integer validYear;
        boolean yearIsValid = false;
        int currentYear = Year.now().getValue();
        String fieldDescription = NumericField.YEAR.getFieldDescription();

        do {

            LibraryPrinter.printEntryRequest(fieldDescription);
            stringYear = scan.nextLine();

            validYear = validateYear(stringYear);

            if (validYear != null){
                yearIsValid = true;
            } else {

                LibraryPrinter.printFailMessage("The year must be a number between 0 and " + currentYear);

                if (operation.equals(Operation.SAVE)) {
                    invalidInputToSave();
                } else if (operation.equals(Operation.FIND)){
                    invalidInputToFind();
                }

            }

        } while (!yearIsValid);

        return validYear;
    }

    /* **
           Read inputs from the user until be inserted a valid id
           or the user decide change the operation.
   */
    private int readAValidId(Operation operation) {

        String stringId;
        Integer validId;
        boolean idIsValid = false;

        String fieldDescription = NumericField.ID.getFieldDescription();

        do {

            LibraryPrinter.printEntryRequest(fieldDescription);
            stringId = scan.nextLine();

            validId = validateId(stringId);

            if (validId != null){
                idIsValid = true;
            } else {
                LibraryPrinter.printFailMessage("The id must be a positive number");
               if (operation.equals(Operation.SAVE)) {
                    invalidInputToSave();
                } else if (operation.equals(Operation.FIND)){
                    invalidInputToFind();
                }
            }

        } while (!idIsValid);

        return validId;
    }









    /*            ** VALIDATE INPUTS METHODS **                     */

    /* **
            Receives a String and returns it if is valid
            or null if invalid
    */
    private String validateTitleAndAuthorString(String inputtedString){

        String validString = null;

        int maxLength = 100;

        boolean stringLengthIsValid = inputtedString.length() < maxLength;
        boolean stringIsNotEmpty = !inputtedString.isEmpty();
        boolean stringIsNotBlank = !inputtedString.isBlank();

        if(stringLengthIsValid && stringIsNotBlank && stringIsNotEmpty){
            validString = inputtedString;
        }

        return validString;

    }


    /* **
          Receives a String and returns the Integer that represents the year
          if is valid, or null if is invalid
    */
    private Integer validateYear(String inputtedYear){

        int currentYear = Year.now().getValue();
        Integer intYear = null;

        try{

            intYear = Integer.parseInt(inputtedYear);

            if ( (intYear < 0) || (intYear > currentYear) ){
                intYear = null;
            }

        } catch (Exception ignored){}

        return intYear;

    }

    /* **
         Receives a String and returns the Integer that represents the id
         if is valid, or null if is invalid
   */
    private Integer validateId(String inputtedId){

        Integer validId = null;

        try{

            validId = Integer.parseInt(inputtedId);

            if (validId < 0){
                validId = null;
            }

        } catch (Exception ignored){}

        return validId;

    }


    /*            ** INVALID INPUTS MENUS  **                     */

    private void invalidInputToSave() {
        boolean validOption = false;

        do {

            System.out.println("""
                                1 - Try it again
                                0 - Return to the main menu""");


            LibraryPrinter.printEntryRequest();
            String option = scan.nextLine();

            switch (option) {
                case "1":
                    validOption = true;
                    // Returns to method that calls it
                    break;

                case "0":
                    validOption = true;
                    mainMenu();
                    break;
                default:
                    LibraryPrinter.printInvalidOption();
                    break;
            }
        } while (!validOption);

    }

    private void invalidInputToFind(){

        boolean validOption = false;

        do {

            System.out.println("""
                    1 - Try it again
                    2 - Select another search option
                    0 - Return to the main menu""");


            LibraryPrinter.printEntryRequest();
            String option = scan.nextLine();

            switch (option) {
                case "1":
                    validOption = true;
                    // Returns to method that calls it
                    break;

                case "2":
                    validOption = true;
                    find();
                    break;

                case "0":
                    validOption = true;
                    mainMenu();
                    break;
                default:
                    LibraryPrinter.printInvalidOption();
                    break;
            }
        } while (!validOption);


    }



}
