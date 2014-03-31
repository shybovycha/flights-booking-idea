package dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import entities.*;

public class FlightDAO extends BaseDAO {
    protected static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

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
        String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String query = String.format("SELECT f FROM Flight f WHERE destination LIKE '%%%s%%' AND date = %s",
                destination, dateStr);

        return query(Flight.class, query);
    }

    public static List<Flight> find(String destination, Date dateFrom, Date dateTo) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateFromStr = df.format(dateFrom);
        String dateToStr = df.format(dateTo);
        String query = String.format("SELECT f FROM Flight f WHERE destination LIKE '%%%s%%' AND date BETWEEN '%s' AND '%s'",
                destination, dateFromStr, dateToStr);

        return query(Flight.class, query);
    }

    public static void destroy(int id) {
        BaseDAO.destroy(Flight.class, id);
    }
}
