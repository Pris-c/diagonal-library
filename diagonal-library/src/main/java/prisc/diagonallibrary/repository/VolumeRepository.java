package prisc.diagonallibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prisc.diagonallibrary.model.Volume;

import java.util.UUID;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, UUID> {

    Volume findByIsbn10(String isbn10);
    Volume findByIsbn13(String isbn13);
}
