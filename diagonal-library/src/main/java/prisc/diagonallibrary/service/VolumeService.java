package prisc.diagonallibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prisc.diagonallibrary.controller.request.VolumePostRequest;
import prisc.diagonallibrary.controller.response.VolumeResponse;
import prisc.diagonallibrary.exception.BookAlreadyExistsException;
import prisc.diagonallibrary.mapper.VolumeMapstructMapper;
import prisc.diagonallibrary.model.Author;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.model.googleapi.GoogleApiResponse;
import prisc.diagonallibrary.model.googleapi.VolumeInfo;
import prisc.diagonallibrary.repository.VolumeRepository;
import prisc.diagonallibrary.util.GoogleApiConsumer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VolumeService {

    @Autowired
    GoogleApiConsumer googleApiConsumer;

    @Autowired
    VolumeRepository volumeRepository;

    @Autowired
    AuthorService authorService;


    @Transactional
    public VolumeResponse save(VolumePostRequest volumePostRequest){
        String isbn = volumePostRequest.getIsbn();
        Volume dbVolume = Volume.builder().build();

        if(isbn.length() == 10){
            dbVolume = volumeRepository.findByIsbn10(isbn);
        } else if (isbn.length() == 13){
            dbVolume = volumeRepository.findByIsbn13(isbn);
        } else {
            // TODO: Exception
        }

        if(dbVolume != null){
         // TODO: Exception
        }

        Volume volumeToSave = googleApiConsumer.get(isbn);
        // TODO: Check if the book was found
        volumeToSave.setAuthors(authorService.processAuthors(volumeToSave.getAuthors()));
        Volume savedVolume = volumeRepository.save(volumeToSave);
        return VolumeMapstructMapper.INSTANCE.toVolumeResponse(savedVolume);
    }
}
