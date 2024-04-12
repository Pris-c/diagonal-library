package prisc.librarymanager.model.volume;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

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
public class Author {

    /**
     * Automatically generated unique identifier for the author.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id")
    private UUID authorId;

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

}
