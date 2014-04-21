package sources.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import sources.dao.FlightDAO;
import sources.managers.FlightManager;

import sources.managers.TicketManager;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import sources.entities.Flight;
import org.springframework.stereotype.Component;

@Named("administrator")
@Scope("session")
public class BookingOfficeAdministratorBean {
    private String destination;
    private String departure;
    private String date;
    private String filterDate;
    private float ticketCost;
    private int ticketsToAdd;
    private Flight flight;
    private final int pageSize = 10;
    private int currentPage = 0;

    @Inject
    private TicketManager ticketManager;

    @Inject
    private FlightManager flightManager;

    public BookingOfficeAdministratorBean() {
    }

    public String getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(String filterDate) {
        this.filterDate = filterDate;
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

    public int getCurrentPage() {
        return this.currentPage + 1;
    }

    public void setCurrentPage(int value) {
        this.currentPage = value;
    }

    public String editFlight(int flightId) {
        this.flight = flightManager.find(flightId);
        this.departure = this.flight.getDeparture();
        this.destination = this.flight.getDestination();
        this.date = new DateTime(this.flight.getDate()).toString("dd/MM/yyyy");
        this.ticketCost = this.flight.getTicketCost();
        this.ticketsToAdd = 0;

        return "edit_flight";
    }

    public String removeFlight(int flightId) {
        flightManager.destroy(flightId);
        return "booking_office_administrator";
    }

    public String updateFlight() {
        DateTimeFormatter df = DateTimeFormat.forPattern("dd/MM/yyyy");

        this.flightManager.update(
                flight.getId(),
                departure,
                destination,
                new Date(df.parseDateTime(this.getDate()).toDate().getTime()),
                ticketCost
        );

        if (this.ticketsToAdd > 0) {
            ticketManager.addFreeTickets(this.flight, this.ticketsToAdd);
        }

        return "booking_office_administrator";
    }

    public String createFlight() {
        Flight f = flightManager.create(departure, destination, date, ticketCost);
        ticketManager.addFreeTickets(f, ticketsToAdd);

        return "booking_office_administrator";
    }

    public int getOutdatedTicketsAmount() {
        return ticketManager.getOutdatedTicketsAmount();
    }

    public String removeOutdatedTickets() {
        ticketManager.removeOutdatedTickets();

        return "booking_office_administrator";
    }

    public boolean getHasOutdatedTickets() {
        return (ticketManager.getOutdatedTicketsAmount() > 0);
    }

    public ArrayList<Flight> getFlights() {
        if (filterDate == null || filterDate.isEmpty()) {
            return new ArrayList<Flight>(flightManager.all(pageSize, currentPage * pageSize));
        } else {
            return new ArrayList<Flight>(flightManager.filter(filterDate, pageSize, currentPage * pageSize));
        }
    }

    public void gotoPage(int page) {
        if (page > 0 && page <= getPagesCount()) {
            this.currentPage = page - 1;
        }
    }

    public int getPagesCount() {
        int flightsCount = flightManager.count();

        if (flightsCount < pageSize) {
            return 0;
        }

        return (flightsCount / pageSize);
    }

    public String filter() {
        return "booking_office_administrator";
    }
}
