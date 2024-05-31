package prisc.librarymanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import prisc.librarymanager.model.googleapi.GoogleApiResponse;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for Consumer Google API")
public class ConsumerTest {

     RestTemplate restTemplate = new RestTemplate();

    @Test
    void get() {
        String fooResourceUrl
                = "https://www.googleapis.com/books/v1/volumes?q=isbn:9789726081890&fields=items(volumeInfo(title,authors,publishedDate,industryIdentifiers,language))";
        GoogleApiResponse gResponse = restTemplate
                .getForObject(fooResourceUrl, GoogleApiResponse.class);

        //VolumeMapper mapper = VolumeMapper.INSTANCE;

        //System.out.println(mapper.volumeInfoToVolume(gResponse.getItems().get(0).getVolumeInfo()));
        Assertions.assertNotNull(gResponse);
    }




 /*       String fooResourceUrl
                = "https://www.googleapis.com/books/v1/volumes?q=isbn:9789726081890&fields=items(volumeInfo(title,authors,publishedDate,industryIdentifiers,language))";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }*/

}
