package sources.beans;

import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import sources.dao.TicketDAO;
import sources.managers.TicketManager;

import sources.entities.Flight;
import sources.entities.Ticket;
import org.springframework.stereotype.Component;

@Named("bookingOfficeAccountant")
@Scope("session")
public class BookingOfficeAccountantBean {
    @Inject
    private TicketManager ticketManager;

    private Map<Integer, Boolean> selectedTickets = new HashMap<Integer, Boolean>();

    public BookingOfficeAccountantBean() {
    }

    public Vector<Ticket> getTickets() {
        Vector<Ticket> tickets = (Vector<Ticket>) ticketManager.orderedTickets();

        return tickets;
    }

    public Map<Integer, Boolean> getSelectedTickets() {
        return selectedTickets;
    }

    public void setSelectedTickets(Map<Integer, Boolean> selectedTickets) {
        this.selectedTickets = selectedTickets;
    }

    public String markAsSold() {
        Vector<Ticket> tickets = new Vector<Ticket>(); //ticketManager.orderedTickets();

        for (Integer id : selectedTickets.keySet()) {
            if (!selectedTickets.get(id)) {
                continue;
            }

            Ticket t = ticketManager.find(id);

            if (t == null) {
                continue;
            }

            tickets.add(t);
        }

        ticketManager.sell(tickets);

        return "booking_office_accountant";
    }
}
