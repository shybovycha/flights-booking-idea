package sources.dao;

import java.sql.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import sources.entities.*;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

    public int count() {
        String query = "SELECT COUNT(f.id) FROM Flight f";
        return getEntityManager().createQuery(query, Long.class).getSingleResult().intValue();
    }

    public Flight create(String departure, String destination, String at, double ticketCost) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
        Timestamp dt = new Timestamp(fmt.parseDateTime(at).toDate().getTime());

        return new Flight(departure, destination, dt, (float) ticketCost);
    }

    public Flight find(int id) {
        return find(Flight.class, id);
    }

    public List<Flight> all() {
        String query = String.format(
                "SELECT f FROM Flight f WHERE f.date >= %d",
                DateTime.now().toLocalDate().toDateTimeAtStartOfDay().getMillis()
        );

        return query(Flight.class, query);
    }

    public List<Flight> all(int limit, int offset) {
        String query = String.format(
                "SELECT f FROM Flight f WHERE f.date >= %d",
                DateTime.now().toLocalDate().toDateTimeAtStartOfDay().getMillis()
        );

        //return query(Flight.class, query, limit, offset);
        List<Flight> results = query(Flight.class, query);

        return results.subList(offset, Math.min(results.size(), offset + limit));
    }

    public List<String> getAvailableDestinations() {
        String query = String.format(
                "SELECT DISTINCT f.destination FROM Flight f WHERE f.date >= %d",
                DateTime.now().toLocalDate().toDateTimeAtStartOfDay().getMillis()
        );

        return query(String.class, query);
    }

    public List<Flight> find(DateTime date) {
        Long dayStart = date.toLocalDate().toDateTimeAtStartOfDay().getMillis(),
                dayEnd = date.plusDays(1).toLocalDate().toDateTimeAtStartOfDay().getMillis();

        String query = String.format("SELECT f FROM Flight f WHERE f.date >= %d AND f.date <= %d", dayStart, dayEnd);

        return query(Flight.class, query);
    }

    public List<Flight> find(Date date) {
        // String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Long dateStr = new DateTime(date).getMillis();
        String query = String.format("SELECT f FROM Flight f WHERE f.date = %d", dateStr);

        return query(Flight.class, query);
    }

    public List<Flight> find(String destination, Date date) {
        //String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Long dateStart = new DateTime(date).toLocalDate().toDateTimeAtStartOfDay().getMillis(),
            dateEnd = new DateTime(date).plusDays(1).toLocalDate().toDateTimeAtStartOfDay().getMillis();

        String query = String.format("SELECT f FROM Flight f WHERE f.destination LIKE '%%%s%%' AND f.date >= %d AND f.date <= %d",
                destination, dateStart, dateEnd);

        return query(Flight.class, query);
    }

    public List<Flight> find(String destination, Date dateFrom, Date dateTo) {
        String query = String.format(
                "SELECT f FROM Flight f WHERE f.destination LIKE '%%%s%%' AND (f.date BETWEEN %d AND %d)",
                destination,
                new DateTime(dateFrom).toLocalDate().toDateTimeAtStartOfDay().getMillis(),
                new DateTime(dateTo).plusDays(1).toLocalDate().toDateTimeAtStartOfDay().getMillis()
        );

        return query(Flight.class, query);
    }

    public List<Flight> find(String destination) {
        String query = String.format("SELECT f FROM Flight f WHERE f.destination LIKE '%%%s%%'", destination);

        return query(Flight.class, query);
    }

    public void destroy(int id) {
        String query = String.format("DELETE FROM Flight f WHERE f.id = %d", id);

        updateQuery(query);
    }
}
