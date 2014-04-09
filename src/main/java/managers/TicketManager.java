package managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.FlightDAO;
import dao.TicketDAO;
import entities.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TicketManager {
    public static List<Ticket> all() {
        return TicketDAO.all();
    }

    public static void destroyAll() {
        TicketDAO.destroyAll();
    }

    public static List<Ticket> free(Flight flight) {
        return TicketDAO.free(flight);
    }

    public static Ticket create() {
        Ticket t = new Ticket();
        TicketDAO.save(t);
        return t;
    }

    public static void addFreeTickets(Flight flight, int amount) {
        for (int i = 0; i < amount; i++) {
            Ticket t = new Ticket();
            t.setFlight(flight);
            TicketDAO.save(t);
            List<Ticket> tickets = flight.getTickets();
            tickets.add(t);
            flight.setTickets(tickets);
            FlightDAO.save(flight);
        }
    }

    public static void addFreeTickets(int flightId, int amount) {
        Flight flight = FlightDAO.find(flightId);

        for (int i = 0; i < amount; i++) {
            Ticket t = new Ticket();
            t.setFlight(flight);
            TicketDAO.save(t);
            List<Ticket> tickets = flight.getTickets();
            tickets.add(t);
            flight.setTickets(tickets);
            FlightDAO.save(flight);
        }
    }

    public static int bookTickets(int flightId, int count, Owner owner) {
        List<Ticket> tickets = TicketDAO.free(flightId);

        int bound = Math.min(count, tickets.size());

        for (int i = 0; i < bound; i++) {
            Ticket t = tickets.get(i);

            if (t.isAvailable()) {
                t.makeBooked(owner);
                TicketDAO.save(t);
            }
        }

        return bound;
    }

    public static int bookTickets(Flight flight, int count, Owner owner) {
        List<Ticket> tickets = TicketDAO.free(flight);

        int bound = Math.min(count, tickets.size());

        for (int i = 0; i < bound; i++) {
            Ticket t = tickets.get(i);

            if (t.isAvailable()) {
                t.makeBooked(owner);
                TicketDAO.save(t);
            }
        }

        return bound;
    }

    public static int removeOutdatedOrders() {
        List<Ticket> tickets = TicketDAO.outdated();

        for (Ticket t : tickets) {
            t.makeAvailable();
            TicketDAO.save(t);
        }

        return tickets.size();
    }

    public static List<Ticket> orderedTickets() {
        return TicketDAO.ordered();
    }

    public static int sell(List<Ticket> tickets) {
        int counter = 0;

        for (Ticket t : tickets) {
            if (t.isBooked()) {
                t.makeSold();
                TicketDAO.save(t);
                counter++;
            }
        }

        return counter;
    }

    public static List<SoldReportRow> soldReportByDate(String dateFrom, String dateTo) {
        return TicketDAO.soldReportByDate(dateFrom, dateTo);
    }

    public static List<SoldReportRow> soldReportByDestination(String dateFrom, String dateTo) {
        return TicketDAO.soldReportByDestination(dateFrom, dateTo);
    }
}
