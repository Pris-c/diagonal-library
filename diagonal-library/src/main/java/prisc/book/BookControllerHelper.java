package prisc.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prisc.enums.StringField;
import prisc.exceptions.InvalidValueToIdException;
import prisc.exceptions.InvalidValueToYearException;
import prisc.util.LibraryPrinter;

import java.time.Year;
import java.util.Scanner;

public class BookControllerHelper {
    //TODO: Check for possible exceptions

    private final Scanner scan = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(BookControllerHelper.class);

    /**
     * Reads inputs from the user until a valid Title or Author String is inserted or the user decides
     * to change the operation.
     *
     * @param field The StringField enum indicating whether the input is for a title or an author.
     * @return A valid String representing the entered title or author or null, if the user wants to ga back to main menu;
     */
    public String readAValidTitleOrAuthor(StringField field) {
        String fieldDescription = field.getFieldDescription();

        boolean stringIsValid;
        boolean returnToMainMenu = false;
        String validString = null;

        do {
            LibraryPrinter.printEntryRequest(fieldDescription);
            String inputtedString = scan.nextLine();

            stringIsValid = validateTitleAndAuthorString(inputtedString);

            if (stringIsValid) {
                validString = inputtedString;

            } else {
                LibraryPrinter.printInvalidInputtedString(fieldDescription);
                returnToMainMenu = invalidInputMenu();

            }

        } while (!stringIsValid  && !returnToMainMenu);

        return validString;
    }


    /**
     * Reads inputs from the user until a valid Year is inserted or the user decides to change the operation.
     *
     * @return A valid int representing the entered year.
     */
    public Integer readAValidYear() {

        String stringYear;
        Integer validYear = null;
        boolean returnToMainMenu = false;
        boolean yearIsValid;

        do {
            LibraryPrinter.printYearRequest();
            stringYear = scan.nextLine();

            try {
                validYear = validateYear(stringYear);
                yearIsValid = true;

            } catch (InvalidValueToYearException e){
                yearIsValid = false;
                LibraryPrinter.printInvalidInputtedYear();
                returnToMainMenu = invalidInputMenu();
            }

        } while (!yearIsValid && !returnToMainMenu);

        return validYear;
    }


    /**
     * Reads inputs from the user until be inserted a valid id
     * or the user decide change the operation.
     */
    public Integer readAValidId() {

        String stringId;
        Integer validId = null;
        boolean returnToMainMenu = false;
        boolean idIsValid;

        do {
            LibraryPrinter.printIdRequest();
            stringId = scan.nextLine();

            try {
                validId = validateId(stringId);
                idIsValid = true;

            } catch (InvalidValueToIdException e) {
                idIsValid = false;
                LibraryPrinter.printInvalidInputtedId();
                returnToMainMenu = invalidInputMenu();
            }

        } while (!idIsValid && !returnToMainMenu);

        return validId;
    }



    /**
     * Receives a String and returns it if is valid or null if invalid
     */
    private boolean validateTitleAndAuthorString(String inputtedString) {

        boolean stringIsValid = false;

        int maxLength = 100;

        boolean stringLengthIsValid = inputtedString.length() <= maxLength;
        boolean stringIsNotEmpty = !inputtedString.isEmpty();
        boolean stringIsNotBlank = !inputtedString.isBlank();

        if (stringIsNotEmpty && stringIsNotBlank && stringLengthIsValid) {
            stringIsValid = true;
        }

        return stringIsValid;
    }

    /**
     * Receives a String and returns the Integer that represents the year
     * if is valid, or null if is invalid
     */
    private int validateYear(String inputtedYear) {

        int currentYear = Year.now().getValue();
        int validYear;

        try {
            validYear = Integer.parseInt(inputtedYear);

            if ((validYear < 0) || (validYear > currentYear)) {
                throw new InvalidValueToYearException( inputtedYear + " is not a valid value to publication year.");
            }

        } catch (InvalidValueToYearException exception) {
            logger.info("Error to validate year: " + exception.getMessage());
            throw exception;

        } catch (Exception exception) {
            logger.info("Error to validate year: " + exception.getMessage());
            throw new InvalidValueToYearException( inputtedYear + " is not a valid value to publication year.");
        }

        return validYear;
    }


    /**
     * Receives a String and returns the Integer that represents the id
     * if is valid, or null if is invalid
     */
    private int validateId(String inputtedId) {

        int validId;

        try {
            validId = Integer.parseInt(inputtedId);

            if (validId < 0) {
                throw new InvalidValueToIdException( inputtedId + " is not a valid id value.");
            }

        } catch (InvalidValueToIdException exception) {
            logger.info("Error to validate id: " + exception.getMessage());
            throw exception;

        } catch (Exception exception) {
            logger.info("Error to validate id: " + exception.getMessage());
            throw new InvalidValueToIdException( inputtedId + " is not a valid id value.");
        }
        return validId;
    }




    /**
     * Offers options to user when his input is invalid
     */
    public boolean invalidInputMenu() {

        boolean returnToMainMenu = false;

        boolean validOption = false;
        do {

            LibraryPrinter.printInvalidInputMenu();
            String option = scan.nextLine();

            switch (option) {
                case "1":
                    validOption = true;
                    // Returns to method that calls it
                    break;

                case "0":
                    validOption = true;
                    returnToMainMenu = true;
                    break;
                default:
                    LibraryPrinter.printInvalidOption();
                    break;
            }
        } while (!validOption);

        return returnToMainMenu;
    }


    /**
     * Shows the menu with options to find a book, read the user input
     * and calls the appropriated function
     */
    public String findMenu() {
        String option;
        boolean validOption = false;

        do {

            LibraryPrinter.printFindMenu();
            option = scan.nextLine();

            switch (option) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "0":
                    validOption = true;
                    break;
                default:
                    LibraryPrinter.printInvalidOption();
            }

        } while (!validOption);

        return option;
    }


    public String updateMenu() {

        String option;
        boolean validOption = false;

        do {
            LibraryPrinter.printUpdateMenu();
            option = scan.nextLine();

            switch (option) {
                case "1":
                case "2":
                case "3":
                case "0":
                    validOption = true;
                    break;
                default:
                    LibraryPrinter.printInvalidOption();
            }

        } while (!validOption);

        return option;




    }
}
