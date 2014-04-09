package beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.User;
import managers.UserManager;

import java.util.HashMap;

@ManagedBean(name="login", eager=true)
@SessionScoped
public class LoginBean {
    private String username;
    private String password;
    private String message;

    public LoginBean() {
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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public String perform() {
        User user = UserManager.authenticate(this.getUsername(), this.getPassword());

        if (user != null) {
            if (user.isSuperUser()) {
                return "root";
            } else if (user.isAccountant()) {
                return "booking_office_accountant";
            } else if (user.isBookingAdministrator()) {
                return "booking_office_administrator";
            } else if (user.isBusinessAnalytic()) {
                return "report";
            }
        }

        this.setMessage("Could not find such user");

        return "login";
    }
}
