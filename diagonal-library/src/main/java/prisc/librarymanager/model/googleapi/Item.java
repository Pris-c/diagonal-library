package prisc.librarymanager.model.googleapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an item from the Google Books API response.
 * This item contains information about a book, including its volume and sales information.
 */
@Getter
@Setter
@NoArgsConstructor
public class Item {
    VolumeInfo volumeInfo;
    SaleInfo saleInfo;
}
