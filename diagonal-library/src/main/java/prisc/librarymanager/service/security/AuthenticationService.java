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

    public LoginResponseDTO login(AuthenticationDTO authenticationDTO){
        var newUser = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(newUser);

        var token = tokenService.generateToken((LibraryUser) auth.getPrincipal());
        return new LoginResponseDTO(token);
    }

    public LibraryUser register(RegisterDTO registerDTO){
        if (this.userRepository.findByLogin(registerDTO.login()) != null){
            return null;
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        LibraryUser newUser = new LibraryUser(registerDTO.name().toLowerCase(), registerDTO.login(), encryptedPassword, UserRole.USER);
        return this.userRepository.save(newUser);
    }


}
