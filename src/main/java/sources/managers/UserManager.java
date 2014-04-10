package sources.managers;

import java.util.List;

import sources.dao.TicketDAO;
import sources.dao.UserDAO;
import sources.entities.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserManager {
    @Inject
    private UserDAO userDAO;
    
    public List<User> all() {
        return userDAO.all();
    }

    public User find(int id) {
        return userDAO.find(id);
    }

    public void destroyAll() {
        userDAO.destroyAll();
    }

    public void destroy(int id) {
        userDAO.destroy(id);
    }

    public User update(int id, String username, String password, String role) {
        User user = userDAO.find(id);

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        userDAO.save(user);

        return user;
    }

    public User create(String username, String password, String role) {
        User user = new User(username, password, role);
        userDAO.save(user);

        return user;
    }

    public User authenticate(String username, String password) {
        User user = userDAO.find(username, password);

        return user;
    }
}
