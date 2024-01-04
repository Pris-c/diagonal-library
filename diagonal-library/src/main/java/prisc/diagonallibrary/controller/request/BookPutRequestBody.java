package prisc.diagonallibrary.controller.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prisc.diagonallibrary.annotation.ValidYear;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class BookPutRequestBody {

    private UUID bookId;

    @Size(min=1, max=50)
    @NotEmpty
    @NotBlank
    private String title;

    @Size(min=1, max=50)
    @NotEmpty
    @NotBlank
    private String author;

    @ValidYear
    private int year;

}
