package prisc.diagonallibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prisc.diagonallibrary.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
