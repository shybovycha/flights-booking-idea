package managers;

import java.util.List;

import dao.*;
import entities.*;

public class FlightManager {
    public static List<Flight> all() {
        return FlightDAO.all();
    }

    public static void destroyAll() {
        FlightDAO.destroyAll();
    }

    public static void deleteEmpty() {
        String query = "DELETE FROM Flight f WHERE SIZE(f.tickets) = 0";
        FlightDAO.updateQuery(query);
    }

    public static List<Flight> findFlights(String to, String when) {
        return FlightDAO.find(to, BaseDAO.str2date(when));
    }

    public static List<Flight> findFlights(String to, String fromDate, String toDate) {
        return FlightDAO.find(to, BaseDAO.str2date(fromDate), BaseDAO.str2date(toDate));
    }

    public static Flight create(String from, String to, String date, double ticketCost) {
        Flight f = FlightDAO.create(from, to, date, ticketCost);
        return FlightDAO.save(f);
    }

    public static Flight update(int id, String from, String to, String date, float ticketCost) {
        Flight f = FlightDAO.find(id);
        f.setDeparture(from);
        f.setDestination(to);
        f.setDate(BaseDAO.str2date(date));
        f.setTicketCost(ticketCost);
        return FlightDAO.save(f);
    }

    public static void destroy(int id) {
        FlightDAO.destroy(id);
    }

    public static void addTickets(int flightId, List<Ticket> tickets) {
        Flight f = FlightDAO.find(flightId);

        for (Ticket t : tickets) {
            t.setFlight(f);
            TicketDAO.save(t);
        }
    }
}
