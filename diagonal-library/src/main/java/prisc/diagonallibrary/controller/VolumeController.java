package prisc.diagonallibrary.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prisc.diagonallibrary.controller.request.VolumePostRequest;
import prisc.diagonallibrary.controller.response.VolumeResponse;
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
    * @param volumePostRequest Request body containing book isbn.
    * @return ResponseEntity with the saved book volume response and HTTP status CREATED.
    */
    @PostMapping
    public ResponseEntity<VolumeResponse> save(@Valid @RequestBody VolumePostRequest volumePostRequest){
        return new ResponseEntity<>(volumeService.save(volumePostRequest), HttpStatus.CREATED);
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
