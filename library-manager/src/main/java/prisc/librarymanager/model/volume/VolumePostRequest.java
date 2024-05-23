package prisc.librarymanager.model.volume;

/**
 * VolumePostRequest Class
 *
 * This class represents a request to add a new volume to the system. It contains the ISBN of the volume to be added.
 */
public class VolumePostRequest {
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
