package sources.managers;

import java.sql.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import sources.dao.*;
import sources.entities.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;

@Service
public class FlightManager {
    @Inject
    private FlightDAO flightDAO;

    @Inject
    private TicketDAO ticketDAO;

    public List<Flight> all() {
        return flightDAO.all();
    }

    public List<Flight> all(int limit, int offset) {
        return flightDAO.all(limit, offset);
    }

    public Flight find(int id) {
        return flightDAO.find(id);
    }

    public int count() {
        return flightDAO.count();
    }

    @Transactional
    public void destroyAll() {
        flightDAO.destroyAll();
    }

    @Transactional
    public void deleteEmpty() {
        String query = "DELETE FROM Flight f WHERE SIZE(f.tickets) = 0";
        flightDAO.updateQuery(query);
    }

    public List<Flight> findFlights(String to, String when) {
        return flightDAO.find(to, BaseDAO.str2date(when));
    }

    public List<Flight> findFlights(String to, String fromDate, String toDate) {
        return flightDAO.find(to, BaseDAO.str2date(fromDate), BaseDAO.str2date(toDate));
    }

    public List<String> getAvailableDestinations() {
        return flightDAO.getAvailableDestinations();
    }

    @Transactional
    public Flight create(String from, String to, String date, double ticketCost) {
        Flight f = flightDAO.create(from, to, date, ticketCost);
        return flightDAO.save(f);
    }

    @Transactional
    public Flight update(int id, String from, String to, String date, float ticketCost) {
        Flight f = flightDAO.find(id);
        f.setDeparture(from);
        f.setDestination(to);
        f.setDate(BaseDAO.str2date(date));
        f.setTicketCost(ticketCost);
        return flightDAO.save(f);
    }

    @Transactional
    public Flight update(int id, String from, String to, Date date, float ticketCost) {
        Flight f = flightDAO.find(id);
        f.setDeparture(from);
        f.setDestination(to);
        f.setDate(date);
        f.setTicketCost(ticketCost);
        return flightDAO.save(f);
    }

    @Transactional
    public void destroy(int id) {
        flightDAO.destroy(id);
    }

    @Transactional
    public void addTickets(int flightId, List<Ticket> tickets) {
        Flight f = flightDAO.find(flightId);

        for (Ticket t : tickets) {
            t.setFlight(f);
            ticketDAO.save(t);
        }
    }
}
