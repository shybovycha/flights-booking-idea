package sources.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import sources.entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDAO extends BaseDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public User find(int id) {
        return find(User.class, id);
    }

    public void destroyAll() {
        destroyAll(Ticket.class);
    }

    public List<User> all() {
        String query = "SELECT u FROM User u";
        Vector<User> users = (Vector<User>) query(User.class, query);

        return users;
    }

    public void destroy(int id) {
        destroy(User.class, id);
    }

    public User find(String username) {
        String query = String.format("SELECT u FROM User u WHERE u.username = '%s'",
                username);

        ArrayList<User> users = (ArrayList<User>) query(User.class, query);

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User find(String username, String password) {
        String query = String.format("SELECT u FROM User u WHERE u.username = '%s' AND u.password = '%s'",
                username, password);

        Vector<User> users = (Vector<User>) query(User.class, query);

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
