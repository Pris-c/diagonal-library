package prisc.librarymanager.model.googleapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the sale information of a book from the Google Books API response.
 * This information includes whether the book is an ebook.
 */
@Getter
@Setter
@NoArgsConstructor
public class SaleInfo {
     String isEbook;
}
