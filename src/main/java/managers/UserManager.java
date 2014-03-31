package managers;

import java.util.List;

import dao.UserDAO;
import entities.*;

public abstract class UserManager {
    public static List<User> all() {
        return UserDAO.all();
    }

    public static void destroyAll() {
        UserDAO.destroyAll();
    }

    public static void destroy(int id) {
        UserDAO.destroy(id);
    }

    public static User update(int id, String username, String password, String role) {
        User user = UserDAO.find(id);

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        UserDAO.save(user);

        return user;
    }

    public static User create(String username, String password, String role) {
        User user = new User(username, password, role);
        UserDAO.save(user);

        return user;
    }

    public static User authenticate(String username, String password) {
        User user = UserDAO.find(username, password);

        return user;
    }
}
