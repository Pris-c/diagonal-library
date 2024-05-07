package prisc.librarymanager.model.volume;

import lombok.Getter;

import java.util.UUID;

@Getter
public class VolumeFavoriteRequest {

    private UUID volumeId;
    private UUID userId;

}
