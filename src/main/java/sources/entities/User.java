package sources.entities;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String role;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean isPlainUser() {
        return (this.role.equals("PLAIN"));
    }

    public boolean isAccountant() {
        return (this.role.equals("ACCOUNTANT"));
    }

    public boolean isBookingAdministrator() {
        return (this.role.equals("BOOKING_ADMINISTRATOR"));
    }

    public boolean isBusinessAnalytic() {
        return (this.role.equals("ANALYTIC"));
    }

    public boolean isSuperUser() {
        return (this.role.equals("SUPER"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
