package prisc.librarymanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;
import prisc.librarymanager.model.googleapi.IndustryIdentifier;
import prisc.librarymanager.model.googleapi.VolumeInfo;
import prisc.librarymanager.model.volume.Author;
import prisc.librarymanager.model.volume.Category;
import prisc.librarymanager.model.volume.Volume;
import prisc.librarymanager.model.volume.VolumeResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(nullValueMappingStrategy =  NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class VolumeMapper {

    public static final VolumeMapper INSTANCE = Mappers.getMapper(VolumeMapper.class);

    abstract Set<Author> mapAuthor(Set<String> authors);
    abstract Set<Category> mapCategory(Set<String> categories);

    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "volumes", ignore = true)
    abstract Author mapAuthor(String name);

    @Mapping(target = "categoryId", ignore = true)
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
    @Mapping(target = "volumeId", ignore = true)
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

    public  abstract List<VolumeResponse> toVolumeResponseList(List<Volume> volumes);

}
