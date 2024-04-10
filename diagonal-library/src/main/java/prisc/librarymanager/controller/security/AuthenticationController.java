package prisc.librarymanager.controller.security;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prisc.librarymanager.config.security.TokenService;
import prisc.librarymanager.model.user.AuthenticationDTO;
import prisc.librarymanager.model.user.LibraryUser;
import prisc.librarymanager.model.user.LoginResponseDTO;
import prisc.librarymanager.model.user.RegisterDTO;
import prisc.librarymanager.repository.UserRepository;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        var newUser = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(newUser);

        var token = tokenService.generateToken((LibraryUser) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){
        if (this.userRepository.findByLogin(registerDTO.login()) != null){
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        LibraryUser newUser = new LibraryUser(registerDTO.login(), encryptedPassword, registerDTO.role());
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }



}
