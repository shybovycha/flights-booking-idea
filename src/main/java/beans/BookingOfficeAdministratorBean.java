package beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.swing.text.DateFormatter;

import dao.FlightDAO;
import managers.FlightManager;

import managers.TicketManager;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entities.Flight;

@ManagedBean(name="administrator", eager=true)
@SessionScoped
public class BookingOfficeAdministratorBean {
    private String destination;
    private String departure;
    private String date;
    private float ticketCost;
    private int ticketsToAdd;
    private Flight flight;

    public BookingOfficeAdministratorBean() {
    }

    public int getTicketsToAdd() {
        return ticketsToAdd;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setTicketsToAdd(int ticketsToAdd) {
        this.ticketsToAdd = ticketsToAdd;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public float getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(float ticketCost) {
        this.ticketCost = ticketCost;
    }

    public String editFlight(int flightId) {
        this.flight = FlightDAO.find(flightId);
        this.departure = this.flight.getDeparture();
        this.destination = this.flight.getDestination();
        this.date = new DateTime(this.flight.getDate()).toString("dd/MM/yyyy");
        this.ticketCost = this.flight.getTicketCost();
        this.ticketsToAdd = 0;

        return "edit_flight";
    }

    public String removeFlight(int flightId) {
        FlightDAO.destroy(flightId);
        return "booking_office_administrator";
    }

    public String updateFlight() {
        DateTimeFormatter df = DateTimeFormat.forPattern("dd/MM/yyyy");

        this.flight.setDate(new Date(df.parseDateTime(this.getDate()).toDate().getTime()));
        this.flight.setDeparture(this.getDeparture());
        this.flight.setDestination(this.getDestination());
        this.flight.setTicketCost(this.getTicketCost());

        if (this.ticketsToAdd > 0) {
            TicketManager.addFreeTickets(this.flight, this.ticketsToAdd);
        }

        return "booking_office_administrator";
    }

    public String createFlight() {
        Flight f = FlightManager.create(departure, destination, date, ticketCost);
        TicketManager.addFreeTickets(f, ticketsToAdd);

        return "booking_office_administrator";
    }

    public Vector<Flight> getFlights() {
        Vector<Flight> flights = (Vector<Flight>) FlightManager.all();

        return flights;
    }
}
