package prisc.librarymanager.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import prisc.librarymanager.exception.InvalidCredentialsException;
import prisc.librarymanager.model.user.LibraryUser;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Log4j2
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Generates a JWT token for the given user.
     *
     * @param user LibraryUser object representing the user for whom the token is generated.
     * @return String representing the generated JWT token.
     */
    public String generateToken(LibraryUser user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("library-auth")
                    .withSubject(user.getLogin()+"_"+user.getRole())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception){
            throw new InvalidCredentialsException("Error while generate token");
        }
    }

    /**
     * Validates a JWT token and retrieves the subject (user information) if valid.
     *
     * @param token String representing the JWT token to validate.
     * @return String representing the subject (user information) retrieved from the token.
     */
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("library-auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }


    /**
     * Generates the expiration date for JWT tokens (2 hours from the current time).
     *
     * @return Instant representing the expiration date/time of the token.
     */
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("Z"));
    }

}
