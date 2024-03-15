package prisc.diagonallibrary.controller;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prisc.diagonallibrary.controller.response.VolumeResponse;
import prisc.diagonallibrary.exception.InvalidIsbnException;
import prisc.diagonallibrary.service.VolumeService;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for handling HTTP requests related to book volumes in the library-manager.
 */
@RestController
@RequestMapping("/volumes")
public class VolumeController {

    @Autowired
    VolumeService volumeService;

    /**
    * Handles HTTP POST request to save a new book volume.
    *
    * @param isbn String containing book isbn.
    * @return ResponseEntity with the saved book volume response and HTTP status CREATED.
    */
    @PostMapping(path = "/{isbn}")
    public ResponseEntity<VolumeResponse> save(@PathVariable String isbn){
        int isbnLength = isbn.length();
        if (isbnLength != 10 && isbnLength !=13){
            throw new InvalidIsbnException("The isbn must have 10 or 13 characters");
        } else if (!NumberUtils.isDigits(isbn)) {
            throw new InvalidIsbnException("The isbn must have only numerical characters");
        }
        return new ResponseEntity<>(volumeService.save(isbn), HttpStatus.CREATED);
    }

    /**
     * Handles HTTP GET request to retrieve a list of all volumes.
     *
     * @return ResponseEntity with a list of volumes responses and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<VolumeResponse>> getAll(){
        return new ResponseEntity<>(volumeService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{volumeId}")
    public ResponseEntity<VolumeResponse> findById(@PathVariable UUID volumeId){
        return new ResponseEntity<>(volumeService.findById(volumeId), HttpStatus.OK);
    }
}
