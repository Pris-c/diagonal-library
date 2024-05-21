package prisc.librarymanager.model.googleapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the response from the Google Books API.
 */
@Getter
@Setter
@NoArgsConstructor
public class GoogleApiResponse implements Serializable {

    List<Item> items;

}
