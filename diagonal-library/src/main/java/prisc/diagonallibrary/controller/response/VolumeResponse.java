package prisc.diagonallibrary.controller.response;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VolumeResponse {

    private UUID volume_id;
    private String title;
    private String isbn10;
    private String isbn13;
    private Set<String> authors;
    private Set<String> categories;
    private String publishedDate;
    private String language;

}
