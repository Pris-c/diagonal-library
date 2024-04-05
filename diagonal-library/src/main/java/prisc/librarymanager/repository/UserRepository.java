package prisc.librarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import prisc.librarymanager.model.user.LibraryUser;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<LibraryUser, UUID> {
    UserDetails findByLogin(String login);
}
