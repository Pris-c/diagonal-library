package prisc.diagonallibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import prisc.diagonallibrary.controller.response.VolumeResponse;
import prisc.diagonallibrary.model.Author;
import prisc.diagonallibrary.model.Category;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.model.googleapi.IndustryIdentifier;
import prisc.diagonallibrary.model.googleapi.VolumeInfo;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public abstract class VolumeMapper {

    public static final VolumeMapper INSTANCE = Mappers.getMapper(VolumeMapper.class);

    abstract Set<Author> mapAuthor(Set<String> authors);
    abstract Set<Category> mapCategory(Set<String> categories);

    @Mapping(target = "author_id", ignore = true)
    @Mapping(target = "volumes", ignore = true)
    abstract Author mapAuthor(String name);

    @Mapping(target = "category_id", ignore = true)
    @Mapping(target = "volumes", ignore = true)
    abstract Category mapCategory(String name);

    Set<String> mapAuthorName(Set<Author> authors){
        return authors.stream().map(Author::getName).collect(Collectors.toSet());
    }
    Set<String> mapCategoryName(Set<Category> categories){
        return categories.stream().map(Category::getName).collect(Collectors.toSet());
    }

    @Mapping(target = "isbn10", expression = "java(mapIsbn(volumeInfo, 10))")
    @Mapping(target = "isbn13", expression = "java(mapIsbn(volumeInfo, 13))")
    @Mapping(target = "volume_id", ignore = true)
    public abstract Volume toVolume(VolumeInfo volumeInfo);

    String mapIsbn(VolumeInfo volumeInfo, int type){
        for (IndustryIdentifier i : volumeInfo.getIndustryIdentifiers()) {
            String isbn = i.getIdentifier();
            if (isbn.length() == type) {
                return isbn;
            }
        }
        return null;
    }

    @Mapping(target = "authors", expression = "java( mapAuthorName(volume.getAuthors()) )")
    @Mapping(target = "categories", expression = "java( mapCategoryName(volume.getCategories()) )")
    public  abstract VolumeResponse toVolumeResponse(Volume volume);

}
