package prisc.librarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prisc.librarymanager.model.volume.Volume;

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

    @Query(value = "SELECT * FROM volume WHERE volume_id IN (SELECT volume_id FROM favorites WHERE user_id = :userId)", nativeQuery = true)
    Optional<List<Volume>> findFavoriteByUserId(@Param("userId") UUID userId);

    @Query(value = "SELECT volume.* FROM " +
            "(SELECT  volume_id, COUNT(volume_id) AS volume_count FROM favorites GROUP BY volume_id LIMIT 5) " +
            "AS tabela JOIN volume ON volume.volume_id = tabela.volume_id " +
            "ORDER BY volume_count DESC", nativeQuery = true)
    Optional<List<Volume>>findTop5Favorites();

}
