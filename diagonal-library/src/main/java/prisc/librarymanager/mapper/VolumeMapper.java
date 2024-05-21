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

/**
 * Mapper interface for converting between Volume-related entities and DTOs.
 */
@Mapper(nullValueMappingStrategy =  NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class VolumeMapper {

    public static final VolumeMapper INSTANCE = Mappers.getMapper(VolumeMapper.class);

    /**
     * Maps a set of author names to a set of Author entities.
     *
     * @param authors set of author names
     * @return set of Author entities
     */
    abstract Set<Author> mapAuthor(Set<String> authors);

    /**
     * Maps a set of category names to a set of Category entities.
     *
     * @param categories set of category names
     * @return set of Category entities
     */
    abstract Set<Category> mapCategory(Set<String> categories);

    /**
     * Maps a single author name to an Author entity.
     * Ignores the authorId and volumes fields in the Author entity.
     *
     * @param name author name
     * @return Author entity
     */
    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "volumes", ignore = true)
    abstract Author mapAuthor(String name);

    /**
     * Maps a single category name to a Category entity.
     * Ignores the categoryId and volumes fields in the Category entity.
     *
     * @param name category name
     * @return Category entity
     */
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "volumes", ignore = true)
    abstract Category mapCategory(String name);

    /**
     * Maps a set of Author entities to a set of author names.
     *
     * @param authors set of Author entities
     * @return set of author names
     */
    Set<String> mapAuthorName(Set<Author> authors){
        return authors.stream().map(Author::getName).collect(Collectors.toSet());
    }

    /**
     * Maps a set of Category entities to a set of category names.
     *
     * @param categories set of Category entities
     * @return set of category names
     */
    Set<String> mapCategoryName(Set<Category> categories){
        return categories.stream().map(Category::getName).collect(Collectors.toSet());
    }

    /**
     * Maps a VolumeInfo entity to a Volume entity.
     * Uses custom mapping expressions for isbn10 and isbn13 fields.
     * Ignores the volumeId field.
     *
     * @param volumeInfo VolumeInfo entity
     * @return Volume entity
     */
    @Mapping(target = "isbn10", expression = "java(mapIsbn(volumeInfo, 10))")
    @Mapping(target = "isbn13", expression = "java(mapIsbn(volumeInfo, 13))")
    @Mapping(target = "volumeId", ignore = true)
    public abstract Volume toVolume(VolumeInfo volumeInfo);

    /**
     * Custom mapping method to extract ISBN based on type (10 or 13) from a VolumeInfo entity.
     *
     * @param volumeInfo VolumeInfo entity
     * @param type ISBN type (10 or 13)
     * @return ISBN string if found, null otherwise
     */
    String mapIsbn(VolumeInfo volumeInfo, int type){
        for (IndustryIdentifier i : volumeInfo.getIndustryIdentifiers()) {
            String isbn = i.getIdentifier();
            if (isbn.length() == type) {
                return isbn;
            }
        }
        return null;
    }

    /**
     * Maps a Volume entity to a VolumeResponse DTO.
     * Uses custom mapping expressions to convert authors and categories.
     *
     * @param volume Volume entity
     * @return VolumeResponse DTO
     */
    @Mapping(target = "authors", expression = "java( mapAuthorName(volume.getAuthors()) )")
    @Mapping(target = "categories", expression = "java( mapCategoryName(volume.getCategories()) )")
    public  abstract VolumeResponse toVolumeResponse(Volume volume);

    /**
     * Maps a list of Volume entities to a list of VolumeResponse DTOs.
     *
     * @param volumes list of Volume entities
     * @return list of VolumeResponse DTOs
     */
    public  abstract List<VolumeResponse> toVolumeResponseList(List<Volume> volumes);

}
