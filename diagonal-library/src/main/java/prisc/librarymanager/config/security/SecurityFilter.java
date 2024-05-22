package prisc.librarymanager.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import prisc.librarymanager.repository.UserRepository;

import java.io.IOException;

@Component
@Log4j2
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    /**
     * Method to process incoming requests and apply security measures.
     *
     * @param request     HttpServletRequest representing the incoming request.
     * @param response    HttpServletResponse representing the response to be sent.
     * @param filterChain FilterChain for invoking the next filter in the chain.
     * @throws ServletException If a servlet error occurs during processing.
     * @throws IOException      If an I/O error occurs during processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null){
            var login = tokenService.validateToken(token).split("_")[0];
            UserDetails user = userRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Method to extract token from the request.
     *
     * @param request HttpServletRequest representing the incoming request.
     * @return String representing the extracted token, or null if not found.
     */
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null){
            return null;
        } else {
            return authHeader.replace("Bearer ", "");
        }
    }

}
