package prisc.diagonallibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prisc.diagonallibrary.controller.request.AuthorPostRequest;
import prisc.diagonallibrary.model.Author;
import prisc.diagonallibrary.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {



    /**
     * Retrieves the author whose name is equals to the specified string, ignoring case.
     *
     * @param name Substring to search for.
     * @return The Author that matching the criteria.
     */
    Optional<Author> findByNameIgnoreCase(String name);



}
