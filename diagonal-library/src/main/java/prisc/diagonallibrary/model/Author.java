package prisc.diagonallibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID author_id;

    @Size(min = 1, max = 80)
    @NotEmpty
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "authors")
    private Set<Volume> volumes;


}
