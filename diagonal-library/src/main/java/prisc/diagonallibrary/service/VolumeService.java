package prisc.diagonallibrary.service;

import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prisc.diagonallibrary.controller.request.VolumePostRequest;
import prisc.diagonallibrary.controller.response.VolumeResponse;
import prisc.diagonallibrary.exception.InvalidIsbnException;
import prisc.diagonallibrary.exception.VolumeIsAlreadyRegisteredException;
import prisc.diagonallibrary.mapper.VolumeMapper;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.repository.VolumeRepository;
import prisc.diagonallibrary.util.GoogleApiConsumer;

import java.util.List;

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

   ISBNValidator isbnValidator = ISBNValidator.getInstance();


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
            default -> Volume.builder().build();
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
     * @return String containing the ISBN10 value.
     */
    private String convertToISBN10(String isbn13)
    {
        int k,j = 0;
        int count = 10;
        String st = isbn13.trim().substring(3,12);
        for(int i = 0; i < st.length(); i++)
        {
            k = Integer.parseInt(st.charAt(i)+"");
            j = j + k * count;
            count--;
        }
        j = (11 - (j % 11)) % 11;
        return st+j;
    }


}
