package prisc.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prisc.enums.StringField;
import prisc.exceptions.InvalidValueToIdException;
import prisc.exceptions.InvalidValueToYearException;
import prisc.util.LibraryPrinter;

import java.time.Year;
import java.util.Scanner;

/**
 * Helper class for assisting the BookController in managing user input and interaction during book-related operations.
 * Provides methods for reading valid input for book titles, authors, years, and IDs, as well as displaying update and find menus.
 */
 public class BookControllerHelper {

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
                returnToMainMenu = handleInvalidInput();
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
                returnToMainMenu = handleInvalidInput();
            }
        } while (!yearIsValid && !returnToMainMenu);
        return validYear;
    }


    /**
     * Reads inputs from the user until a valid ID is inserted or the user decides to change the operation.
     *
     * @return A valid integer representing the entered ID or null if the user chooses to return to the main menu.
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
                returnToMainMenu = handleInvalidInput();
            }

        } while (!idIsValid && !returnToMainMenu);
        return validId;
    }


    /**
     * Validates a given string, returning true if it is valid or false if invalid.
     * The string is considered valid if it is not empty, not blank, and its length does not exceed a predefined maximum length.
     *
     * @param inputtedString The string to be validated.
     * @return True if the string is valid; otherwise, false.
     */
    private boolean validateTitleAndAuthorString(String inputtedString) {

        boolean stringIsValid = false;

        //TODO: Define a final value to Strings maximum size ?
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
     * Validates a given string as a year and returns the corresponding integer if it is valid or throws an exception if invalid.
     * The year is considered valid if it can be parsed into an integer, is not negative, and does not exceed the current year.
     *
     * @param inputtedYear The string representing the year to be validated.
     * @return The valid integer representation of the entered year.
     * @throws InvalidValueToYearException If the inputted year is not a valid value for a publication year.
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
     * Validates a given string as an ID and returns the corresponding integer if it is valid or throws an exception
     *                                                                                                  if invalid.
     * The ID is considered valid if it can be parsed into an integer and is not negative.
     *
     * @param inputtedId The string representing the ID to be validated.
     * @return The valid integer representation of the entered ID.
     * @throws InvalidValueToIdException If the inputted ID is not a valid value.
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
     * Offers options to the user when their input is invalid.
     *
     * @return True if the user chooses to return to the main menu; otherwise, false.
     */
    public boolean handleInvalidInput() {

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
     * Displays a menu with options to find a book, reads the user input,
     * and calls the appropriate function based on the selected option.
     *
     * The menu options include:
     *  - Option 1: Find by Title
     *  - Option 2: Find by Author
     *  - Option 3: Find by Year
     *  - Option 4: Find by ID
     *  - Option 0: Return to the main menu
     *
     * @return The user-selected option as a string.
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


    /**
     * Displays a menu with options to update a book, reads the user input,
     * and calls the appropriate function based on the selected option.
     *
     * The menu options include:
     *  - Option 1: Update Title
     *  - Option 2: Update Author
     *  - Option 3: Update Year
     *  - Option 0: Return to the main menu
     *
     * @return The user-selected option as a string.
     */
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
