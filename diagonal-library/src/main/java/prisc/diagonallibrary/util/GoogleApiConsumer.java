package prisc.diagonallibrary.util;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import prisc.diagonallibrary.mapper.VolumeMapper;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.model.googleapi.GoogleApiResponse;

@Component
public class GoogleApiConsumer {

    static RestTemplate restTemplate = new RestTemplate();

    public Volume get(String isbn) {

        String uriGoogleBooks
                = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&fields=items(volumeInfo(title,authors,publishedDate,industryIdentifiers,language))";
        GoogleApiResponse googleResponse = restTemplate
                .getForObject(uriGoogleBooks, GoogleApiResponse.class);

        // TODO: Check if exists a volume
        return VolumeMapper.INSTANCE.volumeInfoToVolume(googleResponse.getItems().get(0).getVolumeInfo());
    }

}
