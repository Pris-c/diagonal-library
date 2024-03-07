package prisc.diagonallibrary.controller.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import prisc.diagonallibrary.model.Author;

import java.util.Set;
@Getter
public class VolumePostRequest {

    // TODO: Check to exactly 10 or 13 characters
    //       Check if only numbers
    @Size(min = 10, max = 13, message = "ISBN must have 10 or 13 characters")
    private String isbn;

}
