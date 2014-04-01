package beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import managers.FlightManager;

import managers.TicketManager;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entities.Flight;

@ManagedBean(name="bookingOfficeAdministrator", eager=true)
@SessionScoped
public class BookingOfficeAdministratorBean {
    private String destination;
    private String departure;
    private String date;
    private float ticketCost;
    private int ticketsToAdd;

    public BookingOfficeAdministratorBean() {
    }

    public int getTicketsToAdd() {
        return ticketsToAdd;
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
        return "booking_office_administrator.xhtml";
    }

    public String updateFlight(int flightId) {
        return "booking_office_administrator.xhtml";
    }

    public String createFlight() {
        Flight f = FlightManager.create(departure, destination, date, ticketCost);
        TicketManager.addFreeTickets(f, ticketsToAdd);

        return "booking_office_administrator.xhtml";
    }

    public Vector<Flight> getFlights() {
        Vector<Flight> flights = (Vector<Flight>) FlightManager.all();

        return flights;
    }
}
