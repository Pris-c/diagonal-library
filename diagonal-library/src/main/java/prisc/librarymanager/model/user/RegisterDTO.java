package prisc.librarymanager.model.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
