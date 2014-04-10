package sources.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import sources.entities.*;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FlightDAO extends BaseDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void destroyAll() {
        destroyAll(Flight.class);
    }

    public Flight create(String departure, String destination, String date, double ticketCost) {
        return new Flight(departure, destination, str2date(date), (float) ticketCost);
    }

    public Flight find(int id) {
        return find(Flight.class, id);
    }

    public List<Flight> all() {
        String query = "SELECT f FROM Flight f";
        return query(Flight.class, query);
    }

    public List<Flight> find(String destination, Date date) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String query = String.format("SELECT f FROM Flight f WHERE f.destination LIKE '%%%s%%' AND f.date = '%s'",
                destination, dateStr);

        return query(Flight.class, query);
    }

    public List<Flight> find(String destination, Date dateFrom, Date dateTo) {
        String query = String.format(
                "SELECT f FROM Flight f WHERE f.destination LIKE '%%%s%%' AND (f.date BETWEEN %d AND %d)",
                destination,
                new DateTime(dateFrom).getMillis(),
                new DateTime(dateTo).getMillis()
        );

        return query(Flight.class, query);
    }

    public List<Flight> find(String destination) {
        String query = String.format("SELECT f FROM Flight f WHERE f.destination LIKE '%%%s%%'", destination);

        return query(Flight.class, query);
    }

    public void destroy(int id) {
        destroy(Flight.class, id);
    }
}
