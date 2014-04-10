package sources.managers;

import java.util.List;

import sources.dao.FlightDAO;
import sources.dao.TicketDAO;
import sources.entities.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class TicketManager {
    @Inject
    private TicketDAO ticketDAO;

    @Inject
    private FlightDAO flightDAO;

    public List<Ticket> all() {
        return ticketDAO.all();
    }

    public Ticket find(int id) {
        return ticketDAO.find(id);
    }

    public void destroyAll() {
        ticketDAO.destroyAll();
    }

    public List<Ticket> free(Flight flight) {
        return ticketDAO.free(flight);
    }

    public Ticket create() {
        Ticket t = new Ticket();
        ticketDAO.save(t);
        return t;
    }

    public void addFreeTickets(Flight flight, int amount) {
        for (int i = 0; i < amount; i++) {
            Ticket t = new Ticket();
            t.setFlight(flight);
            ticketDAO.save(t);
            List<Ticket> tickets = flight.getTickets();
            tickets.add(t);
            flight.setTickets(tickets);
            flightDAO.save(flight);
        }
    }

    public void addFreeTickets(int flightId, int amount) {
        Flight flight = flightDAO.find(flightId);

        for (int i = 0; i < amount; i++) {
            Ticket t = new Ticket();
            t.setFlight(flight);
            ticketDAO.save(t);
            List<Ticket> tickets = flight.getTickets();
            tickets.add(t);
            flight.setTickets(tickets);
            flightDAO.save(flight);
        }
    }

    public int bookTickets(int flightId, int count, Owner owner) {
        List<Ticket> tickets = ticketDAO.free(flightId);

        int bound = Math.min(count, tickets.size());

        for (int i = 0; i < bound; i++) {
            Ticket t = tickets.get(i);

            if (t.isAvailable()) {
                t.makeBooked(owner);
                ticketDAO.save(t);
            }
        }

        return bound;
    }

    public int bookTickets(Flight flight, int count, Owner owner) {
        List<Ticket> tickets = ticketDAO.free(flight);

        int bound = Math.min(count, tickets.size());

        for (int i = 0; i < bound; i++) {
            Ticket t = tickets.get(i);

            if (t.isAvailable()) {
                t.makeBooked(owner);
                ticketDAO.save(t);
            }
        }

        return bound;
    }

    public int removeOutdatedOrders() {
        List<Ticket> tickets = ticketDAO.outdated();

        for (Ticket t : tickets) {
            t.makeAvailable();
            ticketDAO.save(t);
        }

        return tickets.size();
    }

    public List<Ticket> orderedTickets() {
        return ticketDAO.ordered();
    }

    public int sell(List<Ticket> tickets) {
        int counter = 0;

        for (Ticket t : tickets) {
            if (t.isBooked()) {
                t.makeSold();
                ticketDAO.save(t);
                counter++;
            }
        }

        return counter;
    }

    public List<SoldReportRow> soldReportByDate(String dateFrom, String dateTo) {
        return ticketDAO.soldReportByDate(dateFrom, dateTo);
    }

    public List<SoldReportRow> soldReportByDestination(String dateFrom, String dateTo) {
        return ticketDAO.soldReportByDestination(dateFrom, dateTo);
    }
}
