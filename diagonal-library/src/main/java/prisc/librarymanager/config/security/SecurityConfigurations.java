package prisc.librarymanager.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    /**
     * Configures security filter chain.
     *
     * @param httpSecurity HttpSecurity object used to configure security settings.
     * @return SecurityFilterChain object representing the configured security filter chain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.GET, "/volumes").permitAll()
                                .requestMatchers(HttpMethod.GET, "/volumes/top-favorites").permitAll()
                                .requestMatchers(HttpMethod.POST, "/volumes").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,  "/volumes").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/volumes/favorite").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, "/volumes/favorite").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/volumes/favorite").hasRole("USER")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Configures custom AuthenticationManager.
     *
     * @param authenticationConfiguration AuthenticationConfiguration object used to configure authentication manager.
     * @return AuthenticationManager object representing the configured authentication manager.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Provides a PasswordEncoder bean for encoding passwords.
     * @return PasswordEncoder object representing the configured password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
