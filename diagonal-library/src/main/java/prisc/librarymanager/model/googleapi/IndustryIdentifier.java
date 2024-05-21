package prisc.librarymanager.model.googleapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an industry identifier for a book, such as ISBN.
 */
@Getter
@Setter
@NoArgsConstructor
public class IndustryIdentifier {

    String type;
    String identifier;

}
