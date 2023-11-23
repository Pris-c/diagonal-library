package prisc.library;

import prisc.utils.LibraryPrinter;
import prisc.utils.enums.NumericField;
import prisc.utils.enums.StringField;
import prisc.utils.enums.UpdateStatus;

import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class BookController {

    private final Scanner scan = new Scanner(System.in);
    private final BookService bookService = new BookService();



    public void mainMenu(){

        String option;
        boolean validOption = false;

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
                    if (bookService.libraryIsEmpty()){
                        LibraryPrinter.printMessage("There are no books registered.");
                    } else {
                        find();
                    }
                    break;
                case "4":
                    if (bookService.libraryIsEmpty()){
                        LibraryPrinter.printMessage("There are no books registered.");
                    } else {
                        update();
                    }
                    break;
                case "5":
                    if (bookService.libraryIsEmpty()){
                        LibraryPrinter.printMessage("There are no books registered.");
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

        } while(!validOption);
    }




    public void listAll() {

        if (bookService.libraryIsEmpty()){
            LibraryPrinter.printMessage("There are no books registered.");
        } else {
            List<BookDTO> books = bookService.getAll();
            LibraryPrinter.printMessage("Listing books..");
            books.forEach(System.out::println);
        }

    }


    public void save(){

        LibraryPrinter.printMessage("Insert the new book's information");

        String title = readAValidTitleOrAuthor(StringField.TITLE);

        String author = readAValidTitleOrAuthor(StringField.AUTHOR);

        int year = readAValidYear();

        BookDTO bookToBeSaved = new BookDTO(title, author, year);

        BookDTO savedBook = bookService.save( bookToBeSaved );

        int savedBookId = savedBook.getId();

        if (savedBookId > 0){
            LibraryPrinter.printMessage("Book saved successfully");
            System.out.println(savedBook);

        } else if (savedBookId < 0) {
            //TODO: Check books information
            LibraryPrinter.printFailMessage("The Book " + bookToBeSaved.getTitle().toUpperCase() + " by " + bookToBeSaved.getAuthor().toUpperCase() + " already exists.");

        }
        else {
            LibraryPrinter.printFailMessage("The book could not be saved.");
        }

    }


    public void  find(){
        String option;
        boolean validOption = false;

        do {
            LibraryPrinter.printMessage("Chose one option:");
            System.out.println("1 - Find a book by title");
            System.out.println("2 - Find a book by author");
            System.out.println("3 - Find a book by year");
            System.out.println("4 - Find a book by id");
            System.out.println("0 - Back to the main menu");
            LibraryPrinter.printEntryRequest();

            option = scan.nextLine();

            switch (option){
                case "1":
                    validOption = true;
                    findByTitle();
                    break;
                case "2":
                    validOption = true;
                    findByAuthor();
                    break;
                case "3":
                    validOption = true;
                    findByYear();
                    break;
                case "4":
                    validOption = true;
                    findById();
                    break;
                case "0":
                    // returns to method that call it
                    validOption = true;
                    break;
                default:
                    LibraryPrinter.printInvalidOption();
            }

        } while (!validOption);
    }


    private void update(){

        int id = readAValidId();

        BookDTO bookToBeUpdate = bookService.findById(id);
        BookDTO book = bookToBeUpdate;

        int bookId = bookToBeUpdate.getId();

        if (bookId >= 0){

            LibraryPrinter.printMessage("Found book..");
            System.out.println(bookToBeUpdate);
            String option;
            UpdateStatus updateStatus = null;

            do {

                LibraryPrinter.printMessage("Pick an option: ");
                System.out.println("""
                        1 - Update Title
                        2 - Update Author
                        3 - Update Year
                        0 - Back to the main menu""");
                LibraryPrinter.printEntryRequest();

                option = scan.nextLine();

                switch (option){
                    case "1":

                        bookToBeUpdate.setTitle(readAValidTitleOrAuthor(StringField.TITLE));
                        updateStatus = bookService.updateStringFields(bookToBeUpdate);
                        break;

                    case "2":

                        bookToBeUpdate.setAuthor(readAValidTitleOrAuthor(StringField.AUTHOR));
                        updateStatus = bookService.updateStringFields(bookToBeUpdate);
                        break;

                    case "3":

                        bookToBeUpdate.setYear(readAValidYear());
                        updateStatus = bookService.updateYear(bookToBeUpdate);
                        break;

                    case "0":
                        break;
                    default:
                        LibraryPrinter.printInvalidOption();
                        break;

                }

                if (updateStatus != null && !option.equals("0")) {

                    if (updateStatus.equals(UpdateStatus.SUCCESS)) {
                        LibraryPrinter.printMessage("Book updated successfully");
                        System.out.println(bookService.findById(bookToBeUpdate.getId()));

                    } else if (updateStatus.equals(UpdateStatus.SAME_BOOK)) {
                        LibraryPrinter.printFailMessage("The new book information is equals to the previous one.");

                    } else  { // if (updateStatus.equals(UpdateStatus.BOOK_ALREADY_EXISTS))
                        //TODO: Fix books information
                        LibraryPrinter.printFailMessage("One book with Title: " + book.getTitle() +
                                " Author: " + book.getAuthor() + " already exists");
                    }
                }


            } while (!option.equals("0"));

        } else {
            LibraryPrinter.printMessage("No books were found");
        }

    }

    private void delete(){
        LibraryPrinter.printMessage("Type the book to be deleted");
        int id = readAValidId();

        BookDTO bookDTO = bookService.findById(id);

        if (bookDTO.getId() == id){
            boolean deleteSuccessfuly = bookService.delete(id);

            if (deleteSuccessfuly) {
                LibraryPrinter.printMessage("Book deleted successfully");
                System.out.println(bookDTO);
            } else {
                LibraryPrinter.printFailMessage("The book could not be deleted");
            }

        } else {
            LibraryPrinter.printMessage("Book not found");
        }


    }



    /*            ** FIND METHODS **                     */

    private void findByTitle(){

        String title = readAValidTitleOrAuthor(StringField.TITLE);

        List<BookDTO> books = bookService.findByTitle(title);

        if (!books.isEmpty()){
            LibraryPrinter.printMessage("Listing books..");
            books.forEach(System.out::println);
        } else {
            LibraryPrinter.printMessage("No books were found");
        }


    }


    private void findByAuthor(){

        String author = readAValidTitleOrAuthor(StringField.AUTHOR);

        List<BookDTO> books = bookService.findByAuthor(author);

        if (!books.isEmpty()){
            LibraryPrinter.printMessage("Listing books..");
            books.forEach(System.out::println);
        } else {
            LibraryPrinter.printMessage("No books were found");
        }
    }


    private void findById(){

        int id = readAValidId();

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

        int year = readAValidYear();

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

    private String readAValidTitleOrAuthor(StringField field){

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

                LibraryPrinter.printFailMessage("The " + fieldDescription.toLowerCase() + " must have between 1 and " + "100" + " character");

                invalidInput();
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

            validYear = validateYear(stringYear);

            if (validYear != null){
                yearIsValid = true;
            } else {

                LibraryPrinter.printFailMessage("The year must be a number between 0 and " + currentYear);

                invalidInput();
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

            validId = validateId(stringId);

            if (validId != null){
                idIsValid = true;
            } else {
                LibraryPrinter.printFailMessage("The id must be a positive number");
                invalidInput();
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

    private void invalidInput() {
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


}
