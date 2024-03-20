package prisc.diagonallibrary.util;

import prisc.diagonallibrary.controller.response.VolumeResponse;
import prisc.diagonallibrary.model.Volume;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class VolumeCreator {

    public static Volume createValidVolume(){
        return Volume.builder()
                .title("Harry Potter and the Prisoner of Azkaban")
                .isbn10("0545582938")
                .isbn13("9780545582933")
                .authors(AuthorCreator.createAuthorSetToSave())
                .categories(CategoryCreator.createCategorySetToSave())
                .publishedDate("2013-08-27")
                .language("en")
                .build();
    }
    public static List<VolumeResponse> createValidVolumeResponseList(){
        return List.of(createValidVolumeResponse(),
                VolumeResponse
                        .builder()
                        .volumeId(UUID.randomUUID())
                        .title("The Elements of Style")
                        .isbn10("097522980X")
                        .isbn13("9780975229804")
                        .authors(Set.of("William Strunk"))
                        .categories(Set.of("Language Arts & Disciplines"))
                        .publishedDate("2004")
                        .language("en")
                        .build());
    }

    public static VolumeResponse createValidVolumeResponse(){
        return VolumeResponse.builder()
                .volumeId(UUID.randomUUID())
                .title("Harry Potter and the Prisoner of Azkaban")
                .isbn10("0545582938")
                .isbn13("9780545582933")
                .authors(Set.of("J. K. Rowling", "Kazu Kibuishi"))
                .categories(Set.of("Juvenile Fiction"))
                .publishedDate("2013-08-27")
                .language("en")
                .build();
    }

}
