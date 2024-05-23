package prisc.librarymanager.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import prisc.librarymanager.model.volume.Volume;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a user in the library system.
 * Implements UserDetails interface for Spring Security integration.
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "library_users")
public class LibraryUser implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID userID;
    @Column(unique = true)
    private String login;
    private String name;
    private String password;
    private UserRole role;

    @ManyToMany(mappedBy = "users")
    private Set<Volume> favorites;

    /**
     * Constructs a LibraryUser with the specified attributes.
     *
     * @param name     The name of the user.
     * @param login    The login username of the user.
     * @param password The password of the user.
     * @param role     The role of the user.
     */
    public LibraryUser(String name, String login, String password, UserRole role){
        this.name = name;
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

    /**
     * Returns the username of the user.
     *
     * @return The username.
     */
    @Override
    public String getUsername() {
        return login;
    }

    /**
     * Indicates whether the user account is non-expired.
     *
     * @return True if the user account is non-expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user account is non-locked.
     *
     * @return True if the user account is non-locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user credentials are non-expired.
     *
     * @return True if the user credentials are non-expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user account is enabled.
     *
     * @return True if the user account is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryUser that = (LibraryUser) o;
        return Objects.equals(userID, that.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userID);
    }
}
