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
    * @param isbn String as PathVariable
    * @return ResponseEntity with the saved book volume response and HTTP status CREATED.
    */
    @PostMapping
    public ResponseEntity<VolumeResponse> save(@RequestBody VolumePostRequest volumePostRequest){
        String isbn = volumePostRequest.getIsbn();
        checkForValidIsbn(isbn);    // throw exception if isbn is not valid

        return new ResponseEntity<>(volumeService.save(isbn), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id){
        log.info("DELETE CALLED");
        log.info("STRING ID: " + id);
        volumeService.delete(id);

        if(volumeService.findById(UUID.fromString(id)) == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/favorite")
    public ResponseEntity addFavorite(@RequestBody VolumeFavoriteRequest volumeFavoriteRequest){
        log.info("ADD FAVORITE CALLED");
        volumeService.addFavorite(volumeFavoriteRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/favorite")
    public ResponseEntity removeFavorite(@RequestBody VolumeFavoriteRequest volumeFavoriteRequest){
        log.info("REMOVE FAVORITE CALLED");
        volumeService.removeFavorite(volumeFavoriteRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/favorite")
    public ResponseEntity<List<VolumeResponse>> getUserFavorites(){
        log.info("GET FAVORITES CALLED");
        return new ResponseEntity<>(volumeService.getUserFavorites(), HttpStatus.OK);
    }


    /**
     * Handles HTTP GET request to retrieve a list of all volumes.
     *
     * @return ResponseEntity with a list of VolumeResponse and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<VolumeResponse>> getAll(){
        return new ResponseEntity<>(volumeService.getAll(), HttpStatus.OK);
    }

    /**
     * Handles HTTP GET request to retrieve the book witch matches with the informed id.
     * @param volumeId UUID as PathVariable
     * @return ResponseEntity with VolumeResponse of the found Volume and HTTP status OK.
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
     * Handles HTTP GET request to retrieve the book witch matches with the informed isbn value.
     * @param isbn String as PathVariable
     * @return ResponseEntity with VolumeResponse of the found Volume and HTTP status OK if it is found
     *         or status NOT_FOUND if it is not found
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
     * Handles HTTP GET request to retrieve the book witch title contains the informed string.
     * @param title String as PathVariable
     * @return ResponseEntity with VolumeResponse of the found Volume and HTTP status OK if it is found
     *         or status NOT_FOUND if it is not found
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
     * Handles HTTP GET request to retrieve the book witch authors name contains the informed string.
     * @param authorName String as PathVariable
     * @return ResponseEntity with VolumeResponse of the found Volume and HTTP status OK if it is found
     *         or status NOT_FOUND if it is not found
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
     * Handles HTTP GET request to retrieve the book within a category
     * @param categoryName String as PathVariable
     * @return ResponseEntity with VolumeResponse of the found Volume and HTTP status OK if it is found
     *         or status NOT_FOUND if it is not found
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
     * Checks if the isbn has a valid size
     * @param isbn String containing isbn value
     * @throws InvalidIsbnException if the lenght is invalid
     *          or isbn String not have only numerical characters
     */
    private void checkForValidIsbn(String isbn){
        int isbnLength = isbn.length();
        log.error("ISBN length: " + isbnLength);
        log.error("ISBN: " + isbn);
        if (isbnLength != 10 && isbnLength !=13){
            throw new InvalidIsbnException("The isbn must have 10 or 13 characters");
        }
    }

    /**
     * Checks if the string informed by the user is valid
     * @param userInput String
     * @throws InvalidUserInputException if the string is empty or bigger than 100 characters
     *
     */
    private void checkForValidString(String userInput){
        if (userInput.isBlank() || userInput.length() > 100){
            throw new InvalidUserInputException("The input must have between 1 and 100 characters");
        }
    }

}