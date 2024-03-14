package prisc.diagonallibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.UUID;

/**
 * Category Class
 * This class represents a Category entity in the application. It is annotated with JPA annotations
 * to indicate its mapping to a database table.
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    /**
     * Automatically generated unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID category_id;

    /**
     * Category's name. Should have between 1 and 30 characters.
     */
    @Size(min = 1, max = 30)
    @NotEmpty
    @NotBlank
    private String name;

    /**
     * Volumes in the Category
     */
    @ManyToMany(mappedBy = "categories")
    private Set<Volume> volumes;
}
