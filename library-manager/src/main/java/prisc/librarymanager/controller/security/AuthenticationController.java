package prisc.librarymanager.controller.security;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prisc.librarymanager.model.user.AuthenticationDTO;
import prisc.librarymanager.model.user.LibraryUser;
import prisc.librarymanager.model.user.RegisterDTO;
import prisc.librarymanager.service.security.AuthenticationService;

/**
 * Controller class for handling authentication-related requests.
 */
@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    /**
     * Endpoint for user login.
     *
     * @param authenticationDTO AuthenticationDTO object containing login credentials.
     * @return ResponseEntity representing the HTTP response with the generated authentication token.
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        return ResponseEntity.ok(authenticationService.login(authenticationDTO));
    }

    /**
     * Endpoint for user registration.
     *
     * @param registerDTO RegisterDTO object containing user registration information.
     * @return ResponseEntity representing the HTTP response indicating the success or failure of the registration.
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){
        LibraryUser newUser = authenticationService.register(registerDTO);
        if (newUser == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

}
