package sources.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import sources.entities.User;
import sources.managers.UserManager;
import org.springframework.stereotype.Component;

@Named("login")
@Scope("session")
public class LoginBean {
    private String username;
    private String password;
    private String passwordConfirmation;
    private String message;
    private User user;

    @Inject
    private UserManager userManager;

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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String password) {
        this.passwordConfirmation = password;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public String updateUserPassword() {
        if (!password.equals(passwordConfirmation)) {
            this.message = "Passwords do not match";

            return "edit_user_password";
        }

        userManager.update(user.getId(), user.getUsername(), this.getPassword(), user.getRole(), false);

        return getUserPage();
    }

    public String getUserPage() {
        if (user.isSuperUser()) {
            return "root";
        } else if (user.isAccountant()) {
            return "booking_office_accountant";
        } else if (user.isBookingAdministrator()) {
            return "booking_office_administrator";
        } else if (user.isBusinessAnalytic()) {
            return "business_analytic";
        } else {
            return "login";
        }
    }

    public String perform() {
        user = userManager.authenticate(this.getUsername(), this.getPassword());

        if (user != null) {
            if (user.needsToChangePassword()) {
                return "edit_user_password";
            }

            return getUserPage();
        }

        this.setMessage("Could not find such user and password combination");

        return "login";
    }
}
