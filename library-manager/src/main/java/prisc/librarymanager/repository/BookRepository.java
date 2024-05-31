package prisc.librarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prisc.librarymanager.entity.Book;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for accessing and managing books in the diagonal library.
 * This interface extends the {@link JpaRepository} interface for basic CRUD operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {


    /**
     * Custom query to check if a book with specified attributes exists, ignoring case.
     *
     * @param title  Title of the book.
     * @param author Author of the book.
     * @param year   Year of publication of the book.
     * @return True if a book with the specified attributes exists; false otherwise.
     */
    @Query(value = "SELECT EXISTS (SELECT 1 FROM Book WHERE" +
            " LOWER(title) = LOWER(:title) " +
            "AND LOWER(author) = LOWER(:author) " +
            "AND publication_year = :year)",
            nativeQuery = true)
    boolean existsByAttributesIgnoreCase(@Param("title") String title,
                                         @Param("author") String author,
                                         @Param("year") int year);

    /**
     * Retrieves a list of books whose titles contain the specified substring, ignoring case.
     *
     * @param title Substring to search for in book titles.
     * @return List of books matching the criteria.
     */
    List<Book> findByTitleIgnoreCaseContaining(String title);

    /**
     * Retrieves a list of books whose authors' names contain the specified substring, ignoring case.
     *
     * @param author Substring to search for in book authors' names.
     * @return List of books matching the criteria.
     */
    List<Book> findByAuthorIgnoreCaseContaining(String author);

    /**
     * Retrieves a list of books published in the specified year.
     *
     * @param year Year of publication.
     * @return List of books published in the specified year.
     */
    List<Book> findByYear(int year);
}
