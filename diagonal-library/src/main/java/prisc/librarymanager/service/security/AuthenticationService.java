package prisc.librarymanager.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import prisc.librarymanager.config.security.TokenService;
import prisc.librarymanager.model.user.*;
import prisc.librarymanager.repository.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    /**
     * Authenticates a user and generates a JWT token upon successful authentication.
     *
     * @param authenticationDTO Data Transfer Object containing the user's login credentials.
     * @return LoginResponseDTO containing the generated JWT token.
     */
    public LoginResponseDTO login(AuthenticationDTO authenticationDTO){
        var newUser = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(newUser);

        var token = tokenService.generateToken((LibraryUser) auth.getPrincipal());
        return new LoginResponseDTO(token);
    }

    /**
     * Registers a new user in the system.
     * Checks if a user with the same login already exists; if not, it encrypts the password and saves the user.
     *
     * @param registerDTO Data Transfer Object containing the user's registration information.
     * @return LibraryUser representing the newly registered user, or null if the login is already taken.
     */
    public LibraryUser register(RegisterDTO registerDTO){
        if (this.userRepository.findByLogin(registerDTO.login()) != null){
            return null;
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        LibraryUser newUser = new LibraryUser(registerDTO.name().toLowerCase(), registerDTO.login(), encryptedPassword, UserRole.USER);
        return this.userRepository.save(newUser);
    }

}
