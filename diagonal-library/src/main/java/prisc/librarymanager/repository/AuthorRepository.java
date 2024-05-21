package prisc.librarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prisc.librarymanager.model.volume.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<Author> findByNameIgnoreCase(String name);
    Optional<List<Author>> findByNameContainingIgnoreCase(String name);
}
