package prisc.diagonallibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import prisc.diagonallibrary.model.Author;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.model.googleapi.IndustryIdentifier;
import prisc.diagonallibrary.model.googleapi.VolumeInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper
public abstract class VolumeMapper {

    public static final VolumeMapper INSTANCE = Mappers.getMapper(VolumeMapper.class);

    public static Volume volumeInfoToVolume (VolumeInfo volumeInfo){

        Set<String> authorsList = volumeInfo.getAuthors();
        Set<Author> authors = new HashSet<>(authorsList.stream().map(a -> Author.builder().name(a).build()).toList());
        List<IndustryIdentifier> identifiers = volumeInfo.getIndustryIdentifiers();
        String isbn10, isbn13;
        isbn13 = isbn10 = "0";
        for(IndustryIdentifier i: identifiers){
            if(i.getType().equals("ISBN_10")){
                isbn10 = i.getIdentifier();
            } else {
                isbn13 = i.getIdentifier();
            }
        }
        return Volume.builder()
                .title(volumeInfo.getTitle())
                .authors(authors)
                .isbn10(isbn10)
                .isbn13(isbn13)
                .language(volumeInfo.getLanguage())
                .publishedDate(volumeInfo.getPublishedDate())
                .build();
    }



}
