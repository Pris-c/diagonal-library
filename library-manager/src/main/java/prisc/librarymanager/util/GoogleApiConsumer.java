package prisc.librarymanager.util;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import prisc.librarymanager.exception.EbookTypeException;
import prisc.librarymanager.exception.EmptyApiResponseException;
import prisc.librarymanager.mapper.VolumeMapper;
import prisc.librarymanager.model.Volume;
import prisc.librarymanager.model.googleapi.GoogleApiResponse;

/**
 * GoogleApiConsumer is the component responsible for retrieve information about books using their ISBNs.
 * It utilizes RestTemplate to make HTTP requests to the Google Books API.
 */
@Component
public class GoogleApiConsumer {

    /**
     * Static RestTemplate instance of Spring Web Client used for making HTTP requests to the Google Books API.
     */
    static RestTemplate restTemplate = new RestTemplate();

    /**
     * Retrieves volume information from the Google Books API based on the provided ISBN.
     * @param isbn The ISBN (International Standard Book Number) of the book to retrieve information for.
     *             It accepts also isbn10 or isbn13.
     * @return A Volume object representing the book information retrieved from the Google Books API.
     * @throws EmptyApiResponseException if the API response is empty or does not contain valid information.
     * @throws EbookTypeException if the isbn informed by the user is from an ebook type.
     */
    public Volume get(String isbn){

        String uriGoogleBooks
                  = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&fields=items(volumeInfo(title,authors,publishedDate,industryIdentifiers,categories,language),saleInfo/isEbook)";
         GoogleApiResponse googleResponse = restTemplate
                .getForObject(uriGoogleBooks, GoogleApiResponse.class);

        if( googleResponse == null || googleResponse.getItems() == null || googleResponse.getItems().isEmpty()){
            throw new EmptyApiResponseException("No valid answer to isbn " + isbn);
        } else if( googleResponse.getItems().get(0).getSaleInfo().getIsEbook().equals("true") ){
            throw new EbookTypeException("This library do not work with ebooks");
        }
        return VolumeMapper.INSTANCE
                .toVolume(googleResponse.getItems().get(0).getVolumeInfo());
    }

}
