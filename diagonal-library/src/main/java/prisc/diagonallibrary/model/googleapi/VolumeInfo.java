package prisc.diagonallibrary.model.googleapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VolumeInfo {

    String title;
    List<String> authors;
    String publishedDate;
    List<IndustryIdentifier> industryIdentifiers;
    String language;

}
