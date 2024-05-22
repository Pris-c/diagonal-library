package prisc.librarymanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prisc.librarymanager.exception.InvalidIsbnException;
import prisc.librarymanager.exception.InvalidUserInputException;
import prisc.librarymanager.model.volume.VolumeFavoriteRequest;
import prisc.librarymanager.model.volume.VolumePostRequest;
import prisc.librarymanager.model.volume.VolumeResponse;
import prisc.librarymanager.service.VolumeService;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for handling HTTP requests related to book volumes in the library-manager.
 */

@RestController
@CrossOrigin
@Log4j2
@RequestMapping("/volumes")
public class VolumeController {

    @Autowired
    VolumeService volumeService;

    /**
    * Handles HTTP POST request to save a new book volume.
    *
    * @param volumePostRequest containing the isbn value
    * @return ResponseEntity with the saved book volume response and HTTP status CREATED.
    */
    @PostMapping
    public ResponseEntity<VolumeResponse> save(@RequestBody VolumePostRequest volumePostRequest){
        String isbn = volumePostRequest.getIsbn();
        checkForValidIsbn(isbn);    // throw exception if isbn is not valid
        return new ResponseEntity<>(volumeService.save(isbn), HttpStatus.CREATED);
    }

    /**
     * Handles HTTP DELETE request to remove a book volume from the database.
     *
     * @param id String representing the volume ID.
     * @return ResponseEntity with HTTP status OK if the book is deleted, or HTTP status NOT FOUND if the book is not found in the database.
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        if(volumeService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        volumeService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles HTTP POST request to add a book volume to the user's favorites.
     *
     * @param volumeFavoriteRequest Request body containing the id of the volume to be added to favorites.
     * @return ResponseEntity with HTTP status OK if the volume is successfully added to favorites.
     */
    @PostMapping(path = "/favorite")
    public ResponseEntity addFavorite(@RequestBody VolumeFavoriteRequest volumeFavoriteRequest){
        volumeService.addFavorite(volumeFavoriteRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles HTTP DELETE request to remove a book volume from the user's favorites.
     *
     * @param volumeFavoriteRequest Request body containing the id of the volume to be removed from favorites.
     * @return ResponseEntity with HTTP status OK if the volume is successfully removed from favorites.
     */
    @DeleteMapping(path = "/favorite")
    public ResponseEntity removeFavorite(@RequestBody VolumeFavoriteRequest volumeFavoriteRequest){
        volumeService.removeFavorite(volumeFavoriteRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles HTTP GET request to retrieve the user's favorite book volumes.
     *
     * @return ResponseEntity with a list of VolumeResponse containing the user's favorite volumes and HTTP status OK.
     */
    @GetMapping(path = "/favorite")
    public ResponseEntity<List<VolumeResponse>> getUserFavorites(){
        return new ResponseEntity<>(volumeService.getUserFavorites(), HttpStatus.OK);
    }

    /**
     * Handles HTTP GET request to retrieve the top 5 favorite book volumes.
     *
     * @return ResponseEntity with a list of VolumeResponse containing the top 5 favorite volumes and HTTP status OK.
     */
    @GetMapping(path = "/top-favorites")
    public ResponseEntity<List<VolumeResponse>> findTop5Favorites(){
        return new ResponseEntity<>(volumeService.findTop5Favorites(), HttpStatus.OK);
    }

    /**
     * Handles HTTP GET request to retrieve a list of all book volumes.
     *
     * @return ResponseEntity with a list of VolumeResponse containing all book volumes and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<VolumeResponse>> getAll(){
        return new ResponseEntity<>(volumeService.getAll(), HttpStatus.OK);
    }

    /**
     * Handles HTTP GET request to retrieve the book volume that matches the provided ID.
     *
     * @param volumeId The UUID of the volume to retrieve, specified as a PathVariable.
     * @return ResponseEntity with the VolumeResponse of the found volume and HTTP status OK if found,
     *         or HTTP status NOT_FOUND if not found.
     */
    @GetMapping(path = "/{volumeId}")
    public ResponseEntity<VolumeResponse> findById(@PathVariable UUID volumeId){
        VolumeResponse volumeResponse = volumeService.findById(volumeId);
        if (volumeResponse != null){
            return new ResponseEntity<>(volumeResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles HTTP GET request to retrieve the book volume that matches the provided ISBN.
     *
     * @param isbn The ISBN of the volume to retrieve, specified as a PathVariable.
     * @return ResponseEntity with the VolumeResponse of the found volume and HTTP status OK if found,
     *         or HTTP status NOT_FOUND if not found.
     */
    @GetMapping(path = "/isbn/{isbn}")
    public ResponseEntity<VolumeResponse> findByIsbn(@PathVariable String isbn){
        checkForValidIsbn(isbn);
        VolumeResponse volumeResponse = volumeService.findByIsbn(isbn);
        if (volumeResponse != null){
            return new ResponseEntity<>(volumeResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles HTTP GET request to retrieve the book volumes whose titles contain the provided string.
     *
     * @param title The string to search for in the titles of book volumes, specified as a PathVariable.
     * @return ResponseEntity with a list of VolumeResponse of the found volumes and HTTP status OK if found,
     *         or HTTP status NOT_FOUND if not found.
     */
    @GetMapping(path = "/title/{title}")
    public ResponseEntity<List<VolumeResponse>> findByTitle(@PathVariable String title){
        checkForValidString(title);
        List<VolumeResponse> volumeResponse = volumeService.findByTitle(title);
        if (volumeResponse == null || volumeResponse.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(volumeResponse, HttpStatus.OK);
        }
    }

    /**
     * Handles an HTTP GET request to retrieve the book volumes whose authors' names contain the provided string.
     *
     * @param authorName The string to search for in the names of book authors, specified as a PathVariable.
     * @return ResponseEntity with a list of VolumeResponse of the found volumes and HTTP status OK if found,
     *         or HTTP status NOT_FOUND if not found.
     */
    @GetMapping(path = "/author/{authorName}")
    public ResponseEntity<List<VolumeResponse>> findByAuthor(@PathVariable String authorName) {
        checkForValidString(authorName);
        List<VolumeResponse> volumeResponse = volumeService.findByAuthor(authorName);
        if (volumeResponse == null || volumeResponse.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(volumeResponse, HttpStatus.OK);
        }
    }

    /**
     * Handles an HTTP GET request to retrieve the book volumes within a specified category.
     *
     * @param categoryName The name of the category to search for, specified as a PathVariable.
     * @return ResponseEntity with a list of VolumeResponse of the found volumes and HTTP status OK if found,
     *         or HTTP status NOT_FOUND if not found.
     */
    @GetMapping(path = "/category/{categoryName}")
    public ResponseEntity<List<VolumeResponse>> findByCategory(@PathVariable String categoryName){
        checkForValidString(categoryName);
        List<VolumeResponse> volumeResponse = volumeService.findByCategory(categoryName);
        if (volumeResponse == null || volumeResponse.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(volumeResponse, HttpStatus.OK);
        }
    }

    /**
     * Checks if the ISBN has a valid length and consists only of numerical characters.
     *
     * @param isbn A String containing the ISBN value to be checked.
     * @throws InvalidIsbnException If the length of the ISBN is invalid, or it contains non-numerical characters.
     */
    private void checkForValidIsbn(String isbn){
        int isbnLength = isbn.length();
        if (isbnLength != 10 && isbnLength !=13){
            throw new InvalidIsbnException("The isbn must have 10 or 13 characters");
        }
    }

    /**
     * Checks if the provided string is valid.
     *
     * @param userInput The string to be checked for validity.
     * @throws InvalidUserInputException If the string is empty or exceeds 100 characters.
     */
    private void checkForValidString(String userInput){
        if (userInput.isBlank() || userInput.length() > 100){
            throw new InvalidUserInputException("The input must have between 1 and 100 characters");
        }
    }
}
