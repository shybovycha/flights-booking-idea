package sources.dao;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import sources.entities.*;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDAO extends BaseDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Ticket find(int id) {
        return find(Ticket.class, id);
    }

    public void destroyAll() {
        destroyAll(Ticket.class);
    }

    public List<Ticket> all() {
        return all(Ticket.class);
    }

    public Owner createOwner(String name, String phone, String address, String email) {
        return new Owner(name, phone, address, email);
    }

    public List<Ticket> outdated() {
        String query = String.format(
                "SELECT t FROM Ticket t JOIN Flight f WHERE t.owner.ownerFrom < %d AND f.date >= %d AND t.status = 'BOOKED'",
                DateTime.now().toLocalDate().toDateTimeAtStartOfDay().minusDays(3).getMillis(),
                DateTime.now().toLocalDate().toDateTimeAtStartOfDay().getMillis()
        );

        return query(Ticket.class, query);
    }

    public List<Ticket> ordered() {
        String query = "SELECT t FROM Ticket t WHERE t.status = 'ORDERED' AND t.owner IS NOT NULL";

        return query(Ticket.class, query);
    }

    public List<Ticket> ordered(int flightId) {
        String query = String.format(
                "SELECT t FROM Ticket t JOIN Flight f WHERE t.status = 'ORDERED' AND t.owner IS NOT NULL AND f.id = %d",
                flightId);

        return query(Ticket.class, query);
    }

    public List<Ticket> free() {
        String query = "SELECT t FROM Ticket t WHERE t.status = 'ORDERED' AND t.owner IS NULL";

        return query(Ticket.class, query);
    }

    public List<Ticket> free(int flightId) {
        String query = String.format(
                "SELECT t FROM Ticket t JOIN t.flight f WHERE t.status = 'AVAILABLE' AND t.owner IS NULL AND f.id = %d",
                flightId);

        return query(Ticket.class, query);
    }

    public List<Ticket> free(Flight flight) {
        return free(flight.getId());
    }

    public List<Ticket> sold() {
        String query = "SELECT t FROM Ticket t WHERE t.status = 'SOLD' AND t.owner IS NOT NULL";

        return query(Ticket.class, query);
    }

    public List<SoldReportRow> soldReportByMonth(String from, String to) {
        DateTimeFormatter dfTxt = DateTimeFormat.forPattern("dd/MM/yyyy");

        String query = String.format(
                "SELECT t.ownerfrom, f.departure, f.destination, COUNT(t.id), SUM(f.ticketCost) FROM " +
                "tickets t JOIN flights f ON t.flightid = f.id " +
                "WHERE t.status = 'SOLD' AND t.ownerfrom IS NOT NULL AND (t.ownerfrom BETWEEN %d and %d) " +
                "GROUP BY strftime('%%m', t.ownerfrom / 1000, 'unixepoch')",
                dfTxt.parseDateTime(from).getMillis(),
                dfTxt.parseDateTime(to).getMillis()
        );

        EntityManager entityManager = getEntityManager();
        Query q = entityManager.createNativeQuery(query);

        List<Object[]> rows = q.getResultList();
        entityManager.close();

        List<SoldReportRow> entities = new Vector<SoldReportRow>();

        for (Object[] row : rows) {
            SoldReportRow entity = new SoldReportRow(
                    Long.parseLong((String) row[0]),
                    (String) row[1],
                    (String) row[2],
                    (Integer) row[3],
                    (Double) row[4]
            );

            entities.add(entity);
        }

        return entities;
    }

    public List<SoldReportRow> soldReportByDate(String from, String to) {
        DateTimeFormatter dfTxt = DateTimeFormat.forPattern("dd/MM/yyyy");

        String query = String.format(
                "SELECT t.ownerfrom, f.departure, f.destination, COUNT(t.id), SUM(f.ticketCost) FROM " +
                        "tickets t JOIN flights f ON t.flightid = f.id " +
                        "WHERE t.status = 'SOLD' AND t.ownerfrom IS NOT NULL AND (t.ownerfrom BETWEEN %d and %d) " +
                        "GROUP BY strftime('%%d-%%m-%%Y', t.ownerfrom / 1000, 'unixepoch')",
                dfTxt.parseDateTime(from).getMillis(),
                dfTxt.parseDateTime(to).getMillis()
        );

        EntityManager entityManager = getEntityManager();
        Query q = entityManager.createNativeQuery(query);

        List<Object[]> rows = q.getResultList();
        entityManager.close();

        List<SoldReportRow> entities = new Vector<SoldReportRow>();

        for (Object[] row : rows) {
            SoldReportRow entity = new SoldReportRow(
                    Long.parseLong((String) row[0]),
                    (String) row[1],
                    (String) row[2],
                    (Integer) row[3],
                    (Double) row[4]
            );

            entities.add(entity);
        }

        return entities;
    }

    public List<SoldReportRow> soldReportByRoute(String from, String to) {
        DateTimeFormatter dfTxt = DateTimeFormat.forPattern("dd/MM/yyyy");

        String query = String.format(
            /*"SELECT " +
                "NEW bionic_e9.coursework.entities.SoldReportLine(date, destination, SUM(cost), COUNT(id)) " +
            "FROM (" +
                "SELECT t.id AS id, t.owner.ownerFrom AS date, f.destination AS destination, f.ticketCost AS cost " +
                "FROM Ticket t JOIN t.flight f" +
                "WHERE t.status = 'SOLD' AND t.owner IS NOT NULL AND t.owner.ownerFrom BETWEEN %s and %s " +
                "GROUP BY departure, destination" +
            ")",*/
            "SELECT NEW sources.entities.SoldReportRow(f.date, f.departure, f.destination, COUNT(t.id), SUM(f.ticketCost)) FROM " +
                    "Ticket t JOIN t.flight f " +
                    "WHERE t.status = 'SOLD' AND t.owner IS NOT NULL AND (f.date BETWEEN %d and %d) " +
                    "GROUP BY f.departure, f.destination",
            dfTxt.parseDateTime(from).getMillis(),
            dfTxt.parseDateTime(to).getMillis()
        );

        return query(SoldReportRow.class, query);
    }
}
