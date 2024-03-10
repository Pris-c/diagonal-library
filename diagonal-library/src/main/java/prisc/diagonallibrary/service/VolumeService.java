package prisc.diagonallibrary.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prisc.diagonallibrary.controller.request.VolumePostRequest;
import prisc.diagonallibrary.controller.response.VolumeResponse;
import prisc.diagonallibrary.exception.volumes.InvalidIsbnException;
import prisc.diagonallibrary.exception.volumes.VolumeIsAlreadyRegisteredException;
import prisc.diagonallibrary.mapper.VolumeMapper;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.repository.VolumeRepository;
import prisc.diagonallibrary.util.GoogleApiConsumer;

/**
 * Service class for managing volume-related operations in the library.
 */
@Service
public class VolumeService {

    @Autowired
    GoogleApiConsumer googleApiConsumer;

    @Autowired
    VolumeRepository volumeRepository;

    @Autowired
    AuthorService authorService;
    @Autowired
    CategoryService categoryService;


    /**
     * Saves a new volume book in database.
     *
     * @param volumePostRequest Request body containing the isbn information.
     * @return Volume response representing the saved volume.
     * @throws InvalidIsbnException If the isbn value is invalid.
     * @throws VolumeIsAlreadyRegisteredException If a volume with the same isbn already exists in the database.
     */
    @Transactional
    public VolumeResponse save(VolumePostRequest volumePostRequest){
        String isbn = volumePostRequest.getIsbn();

        if (isbn.length() != 10 && isbn.length() != 13){
            throw new InvalidIsbnException("Isbn value must have 10 or 13 characters");
        }
        else if (!NumberUtils.isParsable(isbn)) {
            throw new InvalidIsbnException("Isbn value must have only numerical characters");
        }
        else if (checkDatabaseForVolume(isbn)){
            throw new VolumeIsAlreadyRegisteredException("Isbn " + isbn + " is already in library database");
        }

        Volume volumeToSave = googleApiConsumer.get(isbn);
        volumeToSave.setAuthors(authorService.processAuthors(volumeToSave.getAuthors()));
        volumeToSave.setCategories(categoryService.processCategories(volumeToSave.getCategories()));
        Volume savedVolume = volumeRepository.save(volumeToSave);
        return VolumeMapper.INSTANCE.toVolumeResponse(savedVolume);
    }


    /**
     * Check if database already contains the volume.
     *
     * @param isbn String representing the isbn information.
     * @return Boolean value indicating whether there is the volume in database
     */
    private boolean checkDatabaseForVolume(String isbn){
        Volume dbVolume = Volume.builder().build();
        switch (isbn.length()) {
            case 10:
                dbVolume = volumeRepository.findByIsbn10(isbn);
                break;
            case 13:
                dbVolume = volumeRepository.findByIsbn13(isbn);
                break;
            default:
                // TODO: Exception
                break;
        }
        return dbVolume != null;
    }
}
