package prisc.librarymanager.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "library_users")
public class LibraryUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID userID;
    @Column(unique = true)
    private String login;
    private String name;
    private String password;
    private UserRole role;

    public LibraryUser(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return Collection of user authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Check if the user is an administrator
        if (this.role == UserRole.ADMIN) {
            // If yes, grant the authorities ROLE_ADMIN and ROLE_USER
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
        // If not, grant only the authority ROLE_USER
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }


    /*
    isAccountNonExpired(),
    isAccountNonLocked(),
    isCredentialsNonExpired(),
    isEnabled():
                These methods are overridden from the UserDetails interface and always return true,
                indicating that the user account is always valid and enabled.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
