package beans;

import java.util.ArrayList;
import java.util.Arrays;
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

    public UserManagementBean() {
    }

    public Vector<User> getUsers() {
        Vector<User> users = (Vector<User>) UserManager.all();

        return users;
    }
}
