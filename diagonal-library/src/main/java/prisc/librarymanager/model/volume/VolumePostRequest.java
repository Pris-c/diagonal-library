package prisc.librarymanager.model.volume;

import lombok.Builder;
import lombok.Getter;

/**
 * VolumePostRequest Class
 *
 * This class represents a request to add a new volume to the system. It contains the ISBN of the volume to be added.
 */
@Getter
@Builder
public class VolumePostRequest {
    private String isbn;
}
