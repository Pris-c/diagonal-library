package prisc.diagonallibrary.controller.request;

import lombok.Getter;
import prisc.diagonallibrary.annotation.ValidIsbn;

@Getter
public class VolumePostRequest {

    @ValidIsbn(message = "Invalid isbn value")
    private String isbn;
}
