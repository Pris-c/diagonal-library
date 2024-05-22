package prisc.librarymanager.util;

import prisc.librarymanager.model.user.LibraryUser;
import prisc.librarymanager.model.volume.VolumeFavoriteRequest;
import prisc.librarymanager.model.volume.VolumePostRequest;
import prisc.librarymanager.model.volume.VolumeResponse;
import prisc.librarymanager.model.volume.Volume;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class VolumeCreator {

    public static Volume createValidVolumeToSave(){
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
    public static Volume createValidVolumeToFavorite(){
        Set<LibraryUser> usersSet = new HashSet<>();
        usersSet.add(LibraryUser.builder().userID(UUID.randomUUID()).name("Name1").build());
        return Volume.builder()
                .title("Harry Potter and the Prisoner of Azkaban")
                .isbn10("0545582938")
                .isbn13("9780545582933")
                .authors(AuthorCreator.createAuthorSetToSave())
                .categories(CategoryCreator.createCategorySetToSave())
                .publishedDate("2013-08-27")
                .users(usersSet)
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

    public static List<Volume> createValidVolumeList(){
        return List.of(createValidVolume(),
                Volume
                    .builder()
                    .volumeId(UUID.randomUUID())
                    .title("The Elements of Style")
                    .isbn10("097522980X")
                    .isbn13("9780975229804")
                    .authors(AuthorCreator.createAuthorSet())
                    .categories(CategoryCreator.createCategorySet())
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

    public static Volume createValidVolume(){
        return Volume.builder()
                .volumeId(UUID.randomUUID())
                .title("Harry Potter and the Prisoner of Azkaban")
                .isbn10("0545582938")
                .isbn13("9780545582933")
                .authors(AuthorCreator.createAuthorSet())
                .categories(CategoryCreator.createCategorySet())
                .publishedDate("2013-08-27")
                .language("en")
                .build();
    }

    public static VolumePostRequest createValidVolumePostRequest(){
        return VolumePostRequest.builder()
                .isbn("0439554934")
                .build();
    }

    public static VolumeFavoriteRequest createValidVolumeFavoriteRequest(){
        VolumeFavoriteRequest volumeFavoriteRequest = new VolumeFavoriteRequest();
        volumeFavoriteRequest.setVolumeId(UUID.randomUUID().toString());
        return volumeFavoriteRequest;
    }


}
