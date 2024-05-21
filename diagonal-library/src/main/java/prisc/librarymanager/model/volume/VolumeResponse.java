package prisc.librarymanager.model.volume;

import lombok.*;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * VolumeResponse Class
 *
 * This class represents a response containing information about a volume.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VolumeResponse {

    private UUID volumeId;
    private Integer units;
    private String title;
    private String isbn10;
    private String isbn13;
    private Set<String> authors;
    private Set<String> categories;
    private String publishedDate;
    private String language;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolumeResponse that = (VolumeResponse) o;
        return Objects.equals(title, that.title) && Objects.equals(isbn10, that.isbn10) && Objects.equals(isbn13, that.isbn13) && Objects.equals(authors, that.authors) && Objects.equals(categories, that.categories) && Objects.equals(publishedDate, that.publishedDate) && Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isbn10, isbn13, authors, categories, publishedDate, language);
    }
}
