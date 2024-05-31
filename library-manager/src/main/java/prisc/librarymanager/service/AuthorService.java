package prisc.librarymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prisc.librarymanager.model.Author;
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
     * Calls processAuthor for each Author on the Set<>
     *
     * @param authors Set<Author> containing volume's authors' names.
     * @return Set<Author> containing both names and ids of the authors.
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
     * Retrieves a List of Authors witch name contains the informed substring
     *
     * @param name String representing a substring of author's name
     * @return List<Author> containing the correspondent Authors
     *         or null
     */
    public List<Author> findByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name).orElse(null);
    }


}
