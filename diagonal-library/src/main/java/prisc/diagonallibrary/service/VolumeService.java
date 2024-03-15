package prisc.diagonallibrary.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prisc.diagonallibrary.controller.response.VolumeResponse;
import prisc.diagonallibrary.exception.InvalidIsbnException;
import prisc.diagonallibrary.exception.VolumeIsAlreadyRegisteredException;
import prisc.diagonallibrary.exception.VolumeNotFoundException;
import prisc.diagonallibrary.mapper.VolumeMapper;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.repository.VolumeRepository;
import prisc.diagonallibrary.util.GoogleApiConsumer;

import java.util.List;
import java.util.UUID;

/**
 * Service class for managing volume-related operations in the library.
 */
@Service
@Log4j2
public class VolumeService {

    @Autowired
    GoogleApiConsumer googleApiConsumer;

    @Autowired
    VolumeRepository volumeRepository;

    @Autowired
    AuthorService authorService;
    @Autowired
    CategoryService categoryService;

   ISBNValidator isbnValidator = ISBNValidator.getInstance();


    /**
     * Saves a new volume book in database.
     *
     * @param isbn String containing the isbn value
     * @return Volume response representing the saved volume.
     * @throws InvalidIsbnException If the isbn value is invalid.
     * @throws VolumeIsAlreadyRegisteredException If a volume with the same isbn already exists in the database.
     */
    @Transactional
    public VolumeResponse save(String isbn){
        if(checkDatabaseForVolume(isbn)){
            throw new VolumeIsAlreadyRegisteredException("Isbn " + isbn + " is already in library database");
        }
        Volume volumeToSave = googleApiConsumer.get(isbn);
        volumeToSave = processIsbns(volumeToSave, isbn);
        volumeToSave.setAuthors(authorService.processAuthors(volumeToSave.getAuthors()));
        volumeToSave.setCategories(categoryService.processCategories(volumeToSave.getCategories()));
        Volume savedVolume = volumeRepository.save(volumeToSave);
        return VolumeMapper.INSTANCE.toVolumeResponse(savedVolume);
    }

    /**
     * Retrieves a list of all volumes.
     *
     * @return List of volumeResponse representing all volumes.
     */
    public List<VolumeResponse> getAll() {
        return VolumeMapper.INSTANCE.toVolumeResponseList(volumeRepository.findAll());
    }

    public VolumeResponse findById(UUID volumeId){
        return VolumeMapper.INSTANCE.toVolumeResponse(
                volumeRepository.findById(volumeId)
                        .orElseThrow(() -> new VolumeNotFoundException(
                                "There ir no book with id = " + volumeId + " in database")
                        ));
    }

    /**
     * Check if database already contains the volume.
     *
     * @param isbn String representing the isbn information.
     * @return Boolean value indicating whether there is the volume in database
     */
    private boolean checkDatabaseForVolume(String isbn){
        Volume dbVolume = switch (isbn.length()) {
            case 10 -> volumeRepository.findByIsbn10(isbn);
            case 13 -> volumeRepository.findByIsbn13(isbn);
            default -> null;
        };
        return dbVolume != null;
    }

    /**
     * Set isbn values if they aren't present
     *
     * @param volumeToSave Object to be saved in database.
     * @param isbn isbn value.
     * @return Volume including both isbn10 and isbn13
     */
    private Volume processIsbns(Volume volumeToSave, String isbn){
        if(volumeToSave.getIsbn10() != null && volumeToSave.getIsbn13() != null){
            return volumeToSave;
        }
        int isbnLength = isbn.length();
        switch (isbnLength){
            case 10:
                if (volumeToSave.getIsbn10() == null){
                    volumeToSave.setIsbn10(isbn);
                }
                if (volumeToSave.getIsbn13() == null){
                    volumeToSave.setIsbn13(isbnValidator.convertToISBN13(isbn));
                }
                break;
            case 13:
                if (volumeToSave.getIsbn13() == null){
                    volumeToSave.setIsbn13(isbn);
                }
                if (volumeToSave.getIsbn10() == null){
                    volumeToSave.setIsbn10(this.convertToISBN10(isbn));
                }
                break;
        }
        return volumeToSave;
    }

    /**
     * Convert ISBN13 to ISBN10
     *
     * @param isbn13 String containing the ISBN13 value.
     * @return String containing the ISBN10 value or null.
     */
    private String convertToISBN10(String isbn13) {
        // Ensure that the input ISBN-13 can be converted to ISBN-10
        if (!isbn13.startsWith("978")) {
            log.warn("isbn13 could not be converted to isbn 10");
            return null;
        }
        // Extract the digits from the input ISBN-13
        String digits = isbn13.substring(3, 12);
        // Calculate the check digit for ISBN-10
        int sum = 0;
        int weight = 10;
        for (int i = 0; i < digits.length(); i++) {
            int digit = Character.getNumericValue(digits.charAt(i));
            sum += digit * weight;
            weight--;
        }
        int checkDigit = (11 - (sum % 11)) % 11;
        // Convert the calculated check digit to a string
        String checkDigitStr = (checkDigit == 10) ? "X" : String.valueOf(checkDigit);
        // Concatenate the ISBN-10 without the check digit and the check digit
        String isbn10 = digits + checkDigitStr;
        if (ISBNValidator.getInstance().isValidISBN10(isbn10)){
            return isbn10;
        } else {
            log.warn("isbn13 could not be converted to isbn 10");
            return null;
        }
    }
}
