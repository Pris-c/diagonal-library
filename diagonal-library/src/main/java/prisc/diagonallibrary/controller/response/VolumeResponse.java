package prisc.diagonallibrary.controller.response;

import lombok.*;
import prisc.diagonallibrary.model.Author;

import java.util.Objects;
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
    private String publishedDate;
    private String language;

}
