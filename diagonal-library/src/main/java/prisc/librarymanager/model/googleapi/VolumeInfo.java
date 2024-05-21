package prisc.librarymanager.model.googleapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * Represents the volume information of a book from the Google Books API response.
 * This information includes the title, authors, published date, industry identifiers,
 * categories, and language of the book.
 */
@Getter
@Setter
@NoArgsConstructor
public class VolumeInfo {

    String title;
    Set<String> authors;
    String publishedDate;
    List<IndustryIdentifier> industryIdentifiers;
    List<String> categories;
    String language;

}
