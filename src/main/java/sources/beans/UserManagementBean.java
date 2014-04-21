package sources.beans;

import java.util.HashMap;
import java.util.Vector;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import sources.managers.FlightManager;
import sources.managers.UserManager;

import sources.entities.Flight;
import sources.entities.User;
import org.springframework.stereotype.Component;

@Named("root")
@Scope("session")
public class UserManagementBean {
    private int userId;
    private String username;
    private String password;
    private String role;

    @Inject
    private UserManager userManager;

    public UserManagementBean() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String editUser(int id) {
        loadUser(id);

        return "edit_user";
    }

    public String newUser() {
        clearUser();

        return "create_user";
    }

    public String removeUser(int id) {
        userManager.destroy(id);
        clearUser();

        return "root";
    }

    public void loadUser(int id) {
        User u = userManager.find(id);

        this.userId = id;
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.role = u.getRole();
    }

    public void clearUser() {
        this.userId = 0;
        this.username = "";
        this.password = "";
        this.role = "";
    }

    public String updateUser() {
        userManager.update(userId, username, password, role, true);

        return "root";
    }

    public String createUser() {
        userManager.create(username, password, role);

        return "root";
    }

    public HashMap<String, String> getUserRoles() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("Administrator", "BOOKING_ADMINISTRATOR");
        values.put("Accountant", "ACCOUNTANT");
        values.put("Analytic", "ANALYTIC");
        values.put("Super user", "SUPER");

        return values;
    }

    public Vector<User> getUsers() {
        Vector<User> users = (Vector<User>) userManager.all();

        return users;
    }
}
