package prisc.librarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prisc.librarymanager.model.Volume;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, UUID> {

    Optional<Volume> findByIsbn10(String isbn10);
    Optional<Volume> findByIsbn13(String isbn13);
    Optional<List<Volume>> findByTitleContainingIgnoreCase(String title);
    Optional<List<Volume>> findByAuthorsAuthorId(UUID authorId);
    Optional<List<Volume>> findByCategoriesCategoryId(UUID authorId);


}
