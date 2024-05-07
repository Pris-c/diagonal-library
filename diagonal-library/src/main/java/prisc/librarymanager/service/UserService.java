package prisc.librarymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prisc.librarymanager.model.user.LibraryUser;
import prisc.librarymanager.repository.UserRepository;

import java.util.UUID;

/**
 * Service class for managing user-related operations in the library.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public LibraryUser findById(UUID userId){
        //TODO: exception
        return userRepository.findById(userId).get();
    }



}
