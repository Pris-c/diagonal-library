package prisc.librarymanager.model.user;

/**
 * Enum representing the roles of users in the library system.
 */
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
