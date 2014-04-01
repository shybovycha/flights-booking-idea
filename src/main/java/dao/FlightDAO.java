package dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class FlightDAO extends BaseDAO {
    public static void destroyAll() {
        destroyAll(Flight.class);
    }

    public static Flight create(String departure, String destination, String date, double ticketCost) {
        return new Flight(departure, destination, str2date(date), (float) ticketCost);
    }

    public static Flight find(int id) {
        return find(Flight.class, id);
    }

    public static List<Flight> all() {
        String query = "SELECT f FROM Flight f";
        return query(Flight.class, query);
    }

    public static List<Flight> find(String destination, Date date) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String query = String.format("SELECT f FROM Flight f WHERE f.destination LIKE '%%%s%%' AND f.date = '%s'",
                destination, dateStr);

        return query(Flight.class, query);
    }

    public static List<Flight> find(String destination, Date dateFrom, Date dateTo) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Flight> q = entityManager.createQuery("SELECT f FROM Flight f WHERE f.destination LIKE :destination AND (f.date BETWEEN :dateFrom AND :dateTo)", Flight.class);
        q.setParameter("destination", String.format("%%%s%%", destination));
        q.setParameter("dateFrom", dateFrom);
        q.setParameter("dateTo", dateTo);
        List<Flight> entities = null;

        try {
            entities = q.getResultList();
        } finally {
            entityManager.close();
        }

        return entities;
    }

    public static List<Flight> find(String destination) {
        String query = String.format("SELECT f FROM Flight f WHERE f.destination LIKE '%%%s%%'", destination);

        return query(Flight.class, query);
    }

    public static void destroy(int id) {
        BaseDAO.destroy(Flight.class, id);
    }
}
