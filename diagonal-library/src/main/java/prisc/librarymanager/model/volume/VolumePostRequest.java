package prisc.librarymanager.model.volume;

import lombok.Getter;

/**
 * VolumePostRequest Class
 *
 * This class represents a request to add a new volume to the system. It contains the ISBN of the volume to be added.
 */
@Getter
public class VolumePostRequest {
    private String isbn;
}
