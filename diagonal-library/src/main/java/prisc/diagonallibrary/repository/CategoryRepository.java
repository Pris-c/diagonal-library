package prisc.diagonallibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prisc.diagonallibrary.model.Category;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    /**
     * Retrieves the category whose name is equals to the specified string, ignoring case.
     *
     * @param name Substring to search for.
     * @return The Category that matching the criteria.
     */
    Optional<Category> findByNameIgnoreCase(String name);

}
