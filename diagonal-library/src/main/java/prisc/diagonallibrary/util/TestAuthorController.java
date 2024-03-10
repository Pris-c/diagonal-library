package prisc.diagonallibrary.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prisc.diagonallibrary.mapper.VolumeMapper;
import prisc.diagonallibrary.model.Author;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.model.googleapi.VolumeInfo;
import prisc.diagonallibrary.service.AuthorService;

import java.util.Set;

@RestController
@RequestMapping("/test")
public class TestAuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping
    public ResponseEntity<Author> get(){

        Set<String> set = Set.of("Author 1", "Auhtor 2", "Author 3");
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setAuthors(set);
        Volume volume = VolumeMapper.INSTANCE.toVolume(volumeInfo);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
