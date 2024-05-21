package prisc.librarymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prisc.librarymanager.model.user.LibraryUser;
import prisc.librarymanager.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Service class for managing user-related operations in the library.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId The unique ID of the user.
     * @return The LibraryUser object corresponding to the provided ID.
     * @throws NoSuchElementException if no user is found with the provided ID.
     */
    public LibraryUser findById(UUID userId){
        return userRepository.findById(userId).orElseThrow();
    }

    /**
     * Retrieves a user by their login name.
     *
     * @param login The login name of the user.
     * @return The LibraryUser object corresponding to the provided login name.
     */
    public LibraryUser findUserByLogin(String login){
        return userRepository.findLibraryUserByLogin(login);
    }

}
