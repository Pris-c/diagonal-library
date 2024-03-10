package prisc.diagonallibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Author Class
 *
 * This class represents an Author entity in the application. It is annotated with JPA annotations
 * to indicate its mapping to a database table.
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {

    /**
     * Automatically generated unique identifier for the author.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID author_id;

    /**
     * Author's name. Should have between 1 and 80 characters.
     */
    @Size(min = 1, max = 80)
    @NotEmpty
    @NotBlank
    private String name;

    /**
     * Volumes written by the author.
     */
    @ManyToMany(mappedBy = "authors")
    private Set<Volume> volumes;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(author_id, author.author_id) && Objects.equals(name, author.name) && Objects.equals(volumes, author.volumes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author_id, name, volumes);
    }
}
