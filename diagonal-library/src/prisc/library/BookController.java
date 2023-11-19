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

            System.out.println("\n## What do you want to do?");
            System.out.println("1 - Consult available books");
            System.out.println("2 - Register a new book");
            System.out.println("3 - Find a book");
            System.out.println("4 - Update a book");
            System.out.println("5 - Delete a book");
            System.out.println("0 - Finish the application");
            System.out.print(">> ");

            option = scan.nextLine();

            switch (option){
                case "1":
                    listAll();
                    break;
                case "2":
                    save();
                    break;
                case "3":
                    //findBook();
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

        int year = readAValidYear();

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
            System.out.println();
            // TODO: Improve printer

            if( validateTitleAndAuthorString(inputtedString)!=null && validateTitleAndAuthorString(inputtedString).equals(inputtedString) ){
                validString = inputtedString;
                stringIsValid = true;

            } else {

                LibraryPrinter.printFailMessage("The " + fieldDescription.toLowerCase() + " must have between 1 and " + "100" +  " character");

                if (operation.equals(Operation.SAVE)) {
                    invalidInputToSave();
                }
                // else if (find)

            }

        } while(!stringIsValid);

        return validString;
    }



    /* **
            Read inputs from the user until be inserted a valid year
            or the user decide change the operation.
    */

    private int readAValidYear() {

        String stringYear;
        Integer validYear;
        boolean yearIsValid = false;
        int currentYear = Year.now().getValue();
        String fieldDescription = NumericField.YEAR.getFieldDescription();

        do {

            LibraryPrinter.printEntryRequest(fieldDescription);
            stringYear = scan.nextLine();
            System.out.println();

            validYear = validateYear(stringYear);

            if (validYear != null){
                yearIsValid = true;
            } else {
                LibraryPrinter.printFailMessage("The year must be a number between 0 and " + currentYear);
                invalidInputToSave();

                // TODO: Implements menu.
                // invalidInputToFindMenu(field);
                // invalidInputToInsertMenu(field);
            }

        } while (!yearIsValid);

        return validYear;
    }

    /* **
           Read inputs from the user until be inserted a valid id
           or the user decide change the operation.
   */
    private int readAValidId() {

        String stringId;
        Integer validId;
        boolean idIsValid = false;

        String fieldDescription = NumericField.ID.getFieldDescription();

        do {

            LibraryPrinter.printEntryRequest(fieldDescription);
            stringId = scan.nextLine();
            System.out.println();

            validId = validateId(stringId);

            if (validId != null){
                idIsValid = true;
            } else {
                LibraryPrinter.printFailMessage("The Id must be a positive number");
                invalidInputToSave();
                // TODO: Implements menu.
                // invalidInputToFindMenu(field);
                // invalidInputToInsertMenu(field)
                // invalidUserInputMenu(fieldDescription);
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


    private void invalidInputToSave() {
        boolean validOption = false;

        do {

            System.out.println("1 - Try it again\n" +
                                "0 - Return to the main menu");


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



}
