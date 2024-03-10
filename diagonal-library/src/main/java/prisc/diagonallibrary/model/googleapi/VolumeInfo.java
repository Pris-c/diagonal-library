package prisc.diagonallibrary.model.googleapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class VolumeInfo {

    String title;
    Set<String> authors;
    String publishedDate;
    List<IndustryIdentifier> industryIdentifiers;
    List<String> categories;
    String language;

}
