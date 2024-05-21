package prisc.librarymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prisc.librarymanager.model.volume.Author;
import prisc.librarymanager.repository.AuthorRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing author-related operations in the library.
 */
@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    /**
     * Processes a set of authors, retrieving their information from the database.
     * If an author is not found, it is saved first.
     *
     * @param authors Set of Author objects containing the authors' names.
     * @return Set of Author objects containing both the names and IDs of the authors.
     */
    public Set<Author> processAuthors(Set<Author> authors){
        return  authors.stream().map(this::processAuthor).collect(Collectors.toSet());
    }


    /**
     * Retrieves author's information from the database.
     * If the author is not found, it saves it first.
     *
     * @param author The Author object containing the author's name.
     * @return An Author object containing both the name and ID of the author.
     */
    private Author processAuthor(Author author){
        return authorRepository.findByNameIgnoreCase(author.getName())
                .orElseGet(() -> authorRepository.save(author));
    }

    /**
     * Retrieves the name of the author from the database based on the author's unique ID.
     *
     * @param authorID The unique ID of the author.
     * @return A String containing the name of the author.
     */
    private String getName(UUID authorID){
        Author author = authorRepository.findById(authorID).orElseThrow();
        return author.getName();
    }

    /**
     * Retrieves a list of authors whose name contains the provided substring.
     *
     * @param name String representing a substring of the author's name.
     * @return List of Author objects containing the corresponding authors, or null if not found.
     */
    public List<Author> findByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name).orElse(null);
    }

}
