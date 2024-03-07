package prisc.diagonallibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
public class Volume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID volume_id;

    @NotEmpty
    @NotBlank
    @Size(min=1, max=80)
    private String title;

    @ManyToMany
    @JoinTable(name = "volume_author",
            joinColumns = @JoinColumn(name = "volume_id"),
            inverseJoinColumns =  @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    private String publishedDate;

    @Size(min = 10, max = 10)
    private String isbn10;

    @Size(min = 13, max = 13)
    private String isbn13;

    @Size(min = 5, max = 5)
    private String language;








}
