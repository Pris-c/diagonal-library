package prisc.diagonallibrary.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prisc.diagonallibrary.annotation.ValidYear;

import java.time.Year;

@Getter
@Setter
@ToString
public class BookPutRequestBody {

    private int currentYear = Year.now().getValue();


    @NotNull
    @Min(0)
    private Long bookId;

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
