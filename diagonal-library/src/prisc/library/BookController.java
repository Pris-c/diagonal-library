package prisc.library;

import prisc.utils.LibraryPrinter;
import prisc.utils.enums.NumericField;
import prisc.utils.enums.StringField;

import java.time.Year;
import java.util.Scanner;

public class BookController {

    private final Scanner scan = new Scanner(System.in);
    private final BookService bookService = new BookService();















    /*            ** READ VALID INPUTS METHODS **                     */

    /* **
            Receives the StringField which must be validated,
            read inputs from the user until be inserted a valid Title or Author String
            or the user decide change the operation.
    */

    private String readAValidTitleOrAuthor(StringField field){
    //private String readAValidTitleOrAuthor(StringField field, Operation operation){

        String fieldDescription = field.getFieldDescription();

        boolean stringIsValid = false;
        String validString = null;

        do{

            LibraryPrinter.printEntryRequest(fieldDescription);
            String inputtedString = scan.nextLine();
            System.out.println();

            if(validateTitleAndAuthorString(inputtedString).equals(inputtedString) ){
                validString = inputtedString;
                stringIsValid = true;

            } else {
                LibraryPrinter.printFailMessage("The " + fieldDescription.toLowerCase() + " must have between 1 and " + "100" +  " character");

                    // TODO: Implements menus. Maybe use enum Operation to set correct menu
                    // invalidInputToFindMenu(field);
                    // invalidInputToInsertMenu(field);

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
                intYear = -1;
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



}
