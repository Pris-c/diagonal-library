package prisc.librarymanager.model.volume;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import prisc.librarymanager.model.user.LibraryUser;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Volume Class
 *
 * This class represents a Volume entity in the application. It is annotated with JPA annotations
 * to indicate its mapping to a database table.
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Volume  implements Serializable {

    /**
     * Automatically generated unique identifier for the volume.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "volume_id")
    private UUID volumeId;

    /**
     * Quantity of volumes.
     */
    private Integer units;

    /**
     * Title of the volume. Should have between 1 and 80 characters.
     */
    @NotEmpty
    @NotBlank
    private String title;

    /**
     * Authors of the volume.
     */
    @ManyToMany
    @JoinTable(name = "volume_author",
            joinColumns = @JoinColumn(name = "volume_id"),
            inverseJoinColumns =  @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;


    /**
     * Published Date of the volume.
     * Accepts the format sending by Google API, it could have only year or complete date information
     */
    private String publishedDate;

    /**
     * Categories of the volume.
     */
    @ManyToMany
    @JoinTable(name = "volume_category",
            joinColumns = @JoinColumn(name = "volume_id"),
            inverseJoinColumns =  @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    /**
     * Users who has favored the volume.
     */
    @ManyToMany
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "volume_id"),
            inverseJoinColumns =  @JoinColumn(name = "user_id")
    )
    private Set<LibraryUser> users;

    /**
     * Unique Isbn_10 identifier value of the volume.
     */
    @NotEmpty
    @NotBlank
    @Size(min = 10, max = 10)
    private String isbn10;

    /**
     * Unique Isbn_13 identifier value of the volume.
     */
    @NotEmpty
    @NotBlank
    @Size(min = 13, max = 13)
    private String isbn13;

    /**
     * Language of the volume.
     * Accepts the format sending by Google API, it could have only language information, or both language and country.
     */
    private String language;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volume volume = (Volume) o;
        return Objects.equals(volumeId, volume.volumeId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(volumeId);
    }
}
