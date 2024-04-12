package prisc.librarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prisc.librarymanager.model.volume.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<List<Category>> findByNameContainingIgnoreCase(String name);
    Optional<Category> findByNameIgnoreCase(String name);

}
