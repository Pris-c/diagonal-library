package prisc.librarymanager.model.volume;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * VolumeFavoriteRequest Class
 *
 * This class represents a request to mark a volume as a favorite. It contains the identifier of the volume to be favorited.
 */
@Getter
@Setter
@Builder
public class VolumeFavoriteRequest {
    private String volumeId;
}
