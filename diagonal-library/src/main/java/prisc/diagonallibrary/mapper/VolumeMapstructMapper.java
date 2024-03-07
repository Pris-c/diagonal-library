package prisc.diagonallibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import prisc.diagonallibrary.controller.response.VolumeResponse;
import prisc.diagonallibrary.model.Author;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.model.googleapi.VolumeInfo;

import java.util.Set;

@Mapper
public interface VolumeMapstructMapper {

    public static final VolumeMapstructMapper INSTANCE = Mappers.getMapper(VolumeMapstructMapper.class);
    VolumeResponse toVolumeResponse(Volume volume);

    Volume toVolume(VolumeInfo volumeInfo);

    Set<Author> map(Set<String> authors);

    Author map(String name);

}
