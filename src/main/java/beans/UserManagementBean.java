package beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import managers.FlightManager;
import managers.UserManager;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entities.Flight;
import entities.User;

@ManagedBean(name="root", eager=true)
@SessionScoped
public class UserManagementBean {
    private String userId;
    private String username;
    private String password;
    private String role;

    public UserManagementBean() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

        return "edit_user.xhtml";
    }

    public String removeUser(int id) {
        UserManager.destroy(id);

        return "root";
    }

    public void loadUser(int id) {
        User u = UserManager.find(id);

        this.username = u.getUsername();
        this.password = u.getPassword();
        this.role = u.getRole();
    }

    public void updateUser(int id) {
        UserManager.update(id, username, password, role);
    }

    public void createUser() {
        UserManager.create(username, password, role);
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
        Vector<User> users = (Vector<User>) UserManager.all();

        return users;
    }
}
