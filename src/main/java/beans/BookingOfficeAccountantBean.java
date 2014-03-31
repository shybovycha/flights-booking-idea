package beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import managers.TicketManager;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entities.Flight;
import entities.Ticket;

@ManagedBean(name="bookingOfficeAccountant", eager=true)
@SessionScoped
public class BookingOfficeAccountantBean {

    public BookingOfficeAccountantBean() {
    }

    public Vector<Ticket> getTickets() {
        Vector<Ticket> tickets = (Vector<Ticket>) TicketManager.orderedTickets();

        return tickets;
    }
}
