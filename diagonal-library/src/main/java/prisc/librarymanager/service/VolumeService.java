package prisc.librarymanager.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prisc.librarymanager.exception.VolumeIsAlreadyRegisteredException;
import prisc.librarymanager.mapper.VolumeMapper;
import prisc.librarymanager.model.volume.*;
import prisc.librarymanager.repository.VolumeRepository;
import prisc.librarymanager.util.GoogleApiConsumer;

import java.util.*;

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

    @Autowired
    UserService userService;

   private final ISBNValidator isbnValidator = ISBNValidator.getInstance();

    /**
     * Saves a new volume book in the database.
     *      At first, it is verified if the book is already in the database.
     *      If not, the Google API is consulted for the provided ISBN, and the information is handled.
     *      Finally, the book is saved in the database.
     *
     * @param isbn String containing the ISBN value.
     * @return VolumeResponse representing the saved volume.
     * @throws VolumeIsAlreadyRegisteredException If a volume with the same ISBN already exists in the database.
     */
    @Transactional
    public VolumeResponse save(String isbn){
        Volume dbVolume = internalFindByIsbn(isbn);
        if (dbVolume != null){
            throw new VolumeIsAlreadyRegisteredException("This volume is already registered in database: volumeId = " + dbVolume.getVolumeId());
        }

        Volume volumeToSave = googleApiConsumer.get(isbn); // throw exception if volume is not found or type is invalid

        volumeToSave = processIsbns(volumeToSave, isbn);
        volumeToSave.setAuthors(authorService.processAuthors(volumeToSave.getAuthors()));
        volumeToSave.setCategories(categoryService.processCategories(volumeToSave.getCategories()));
        volumeToSave.setPublishedDate(volumeToSave.getPublishedDate().substring(0, 4));

        Volume savedVolume = volumeRepository.save(volumeToSave);
        return VolumeMapper.INSTANCE.toVolumeResponse(savedVolume);
    }

    /**
     * Adds a volume to a user's favorites.
     *
     * @param volumeFavoriteRequest Request object containing the volume ID.
     */
    public void addFavorite(VolumeFavoriteRequest volumeFavoriteRequest){
        UUID userId = userService.getUserIdFromContext();
        Volume favoriteVolume = volumeRepository.findById(UUID.fromString(volumeFavoriteRequest.getVolumeId())).get();
        favoriteVolume.getUsers().add(userService.findById(userId));
        volumeRepository.save(favoriteVolume);
    }

    /**
     * Removes a volume from a user's favorites.
     *
     * @param volumeFavoriteRequest Request object containing the volume ID.
     */
    public void removeFavorite(VolumeFavoriteRequest volumeFavoriteRequest){
        UUID userId = userService.getUserIdFromContext();
        Volume favoriteVolume = volumeRepository.findById(UUID.fromString(volumeFavoriteRequest.getVolumeId())).get();
        favoriteVolume.getUsers().removeIf(user -> user.getUserID().equals(userId));
        volumeRepository.save(favoriteVolume);
    }

    /**
     * Retrieves a list of volumes that are favorites of the current user.
     *
     * @return List of VolumeResponse representing the user's favorite volumes.
     */
    public List<VolumeResponse> getUserFavorites(){
        UUID userId = userService.getUserIdFromContext();
        List<VolumeResponse> volumes = VolumeMapper.INSTANCE.toVolumeResponseList(volumeRepository.findFavoriteByUserId(userId).orElse(null));
        return volumes;
    }

    /**
     * Retrieves a list of the top 5 favorite volumes in the library.
     *
     * @return List of VolumeResponse representing the top 5 favorite volumes.
     */
    public List<VolumeResponse> findTop5Favorites(){
        return VolumeMapper.INSTANCE.toVolumeResponseList(volumeRepository.findTop5Favorites().orElse(null));
    }


    /**
     * Retrieves a list of all volumes.
     *
     * @return List of volumeResponse representing all volumes.
     */
    public List<VolumeResponse> getAll() {
        return VolumeMapper.INSTANCE.toVolumeResponseList(volumeRepository.findAll());
    }

    /**
     * Retrieves a volume by its unique ID.
     *
     * @param volumeId The unique ID of the volume.
     * @return VolumeResponse representing the volume corresponding to the provided ID.
     */
    public VolumeResponse findById(UUID volumeId){
        return VolumeMapper.INSTANCE.toVolumeResponse(volumeRepository.findById(volumeId).orElse(null));
    }

    /**
     * Retrieves a volume by its ISBN.
     *
     * @param isbn String containing the ISBN information.
     * @return VolumeResponse representing the volume corresponding to the provided ISBN, or null if not found.
     */
    public VolumeResponse findByIsbn(String isbn){
        Volume dbVolume = internalFindByIsbn(isbn);
        if (dbVolume != null){
            return VolumeMapper.INSTANCE.toVolumeResponse(dbVolume);
        } else {
            return null;
        }
    }

    /**
     * Retrieves a list of volumes whose title contains the provided substring.
     *
     * @param title String representing a substring of the volume title.
     * @return List of VolumeResponse representing the volumes with titles containing the substring.
     */
    public List<VolumeResponse> findByTitle(String title) {
        return VolumeMapper.INSTANCE.toVolumeResponseList(volumeRepository
                .findByTitleContainingIgnoreCase(title).orElse(null));
    }

    /**
     * Retrieves a list of volumes written by authors whose name contains the provided substring.
     *
     * @param authorName String representing a substring of the author's name.
     * @return List of VolumeResponse representing the volumes written by authors with names containing the substring.
     */
    public List<VolumeResponse> findByAuthor(String authorName) {
        List<Author> authors = authorService.findByName(authorName);
        if(authors == null){
            return null;
        } else {
            Set<Volume> volumes = new HashSet<>();
            for (Author author: authors){
                Optional<List<Volume>> authorVolumes = volumeRepository.findByAuthorsAuthorId(author.getAuthorId());
                authorVolumes.ifPresent(volumes::addAll);
            }
            if (volumes.isEmpty()){
                return null;
            }
            return VolumeMapper.INSTANCE.toVolumeResponseList(new ArrayList<>(volumes));
        }
    }

    /**
     * Retrieves a list of volumes in the specified category.
     *
     * @param categoryName String representing a substring of the category name.
     * @return List of VolumeResponse representing the volumes in the specified category.
     */
    public List<VolumeResponse> findByCategory(String categoryName) {
        List<Category> categories = categoryService.findByName(categoryName);
        if(categories == null){
            return null;
        } else {
            Set<Volume> volumes = new HashSet<>();
            for (Category category: categories){
                Optional<List<Volume>> categoryVolumes = volumeRepository.findByCategoriesCategoryId(category.getCategoryId());
                categoryVolumes.ifPresent(volumes::addAll);
            }
            if (volumes.isEmpty()){
                return null;
            }
            return VolumeMapper.INSTANCE.toVolumeResponseList(new ArrayList<>(volumes));
        }
    }

    /**
     * Deletes a volume from the database.
     *
     * @param id String containing the ID of the volume to delete.
     */
    public void delete(String id){
        volumeRepository.deleteById(UUID.fromString(id));
    }

    /**
     * Checks if a volume with the provided ISBN already exists in the database.
     *
     * @param isbn String containing the ISBN information.
     * @return Boolean value indicating whether the volume exists in the database.
     */
    private boolean checkDatabaseForVolume(String isbn){
        return findByIsbn(isbn) != null;
    }

    /**
     * Sets ISBN values if they aren't present in the volume object.
     *
     * @param volumeToSave Volume object to be saved in the database.
     * @param isbn ISBN value.
     * @return Volume object including both ISBN-10 and ISBN-13.
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
     * Converts ISBN-13 to ISBN-10.
     *
     * @param isbn13 String containing the ISBN-13 value.
     * @return String containing the ISBN-10 value, or null if conversion is not possible.
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

    /**
     * Internal method to find a volume by its ISBN.
     *
     * @param isbn String containing the ISBN information.
     * @return Volume object corresponding to the provided ISBN, or null if not found.
     */
    private Volume internalFindByIsbn(String isbn){
       return switch (isbn.length()) {
            case 10 -> volumeRepository.findByIsbn10(isbn).orElse(null);
            case 13 -> volumeRepository.findByIsbn13(isbn).orElse(null);
            default -> null;
        };
    }

}
