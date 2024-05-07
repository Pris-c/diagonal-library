package prisc.librarymanager.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import prisc.librarymanager.model.volume.Volume;

import java.io.Serializable;
import java.util.*;


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

    /**
     * User's favorite volumes
     */
    @ManyToMany(mappedBy = "users")
    private Set<Volume> favorites;


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
