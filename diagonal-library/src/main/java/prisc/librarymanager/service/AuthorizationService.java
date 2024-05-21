package prisc.librarymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import prisc.librarymanager.repository.UserRepository;

/**
 * AuthorizationService Class
 *
 * This class implements the UserDetailsService interface to provide user authentication and authorization functionality.
 * It retrieves user details from the UserRepository based on the provided username.
 */
@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Loads user details by username.
     *
     * @param username The username of the user whose details are being loaded.
     * @return UserDetails containing the details of the user with the specified username.
     * @throws UsernameNotFoundException if no user with the specified username is found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }
}
