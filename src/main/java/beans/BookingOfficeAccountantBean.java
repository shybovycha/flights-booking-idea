package beans;

import java.util.*;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.TicketDAO;
import managers.TicketManager;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entities.Flight;
import entities.Ticket;

@ManagedBean(name="bookingOfficeAccountant", eager=true)
@SessionScoped
public class BookingOfficeAccountantBean {
    private Map<Integer, Boolean> selectedTickets = new HashMap<Integer, Boolean>();

    public BookingOfficeAccountantBean() {
    }

    public Vector<Ticket> getTickets() {
        Vector<Ticket> tickets = (Vector<Ticket>) TicketManager.orderedTickets();

        return tickets;
    }

    public Map<Integer, Boolean> getSelectedTickets() {
        return selectedTickets;
    }

    public void setSelectedTickets(Map<Integer, Boolean> selectedTickets) {
        this.selectedTickets = selectedTickets;
    }

    public String markAsSold() {
        Vector<Ticket> tickets = new Vector<Ticket>(); //TicketManager.orderedTickets();

        for (Integer id : selectedTickets.keySet()) {
            if (!selectedTickets.get(id)) {
                continue;
            }

            Ticket t = TicketDAO.find(id);

            if (t == null) {
                continue;
            }

            tickets.add(t);
        }

        TicketManager.sell(tickets);

        return "booking_office_accountant";
    }
}
