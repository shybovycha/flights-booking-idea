package sources.beans;

import org.springframework.context.annotation.Scope;
import sources.dao.FlightDAO;
import sources.entities.Flight;
import sources.entities.Owner;
import sources.managers.FlightManager;
import sources.managers.TicketManager;
import org.joda.time.DateTime;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

import javax.inject.Inject;
import javax.inject.Named;

import sources.managers.FlightManager;

import sources.entities.Ticket;
import org.springframework.stereotype.Component;

@Named("cart")
@Scope("session")
public class CartBean {
    private int amount;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Flight flight;
    private HashMap<Flight, Integer> tickets = new HashMap<Flight, Integer>();

    @Inject
    private TicketManager ticketManager;

    @Inject
    private FlightManager flightManager;

    public CartBean() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTicketCost() {
        return this.flight.getTicketCost();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDestination() {
        return this.flight.getDestination();
    }

    public String getDeparture() {
        return this.flight.getDeparture();
    }

    public String getDate() {
        String dt = new DateTime(this.flight.getDate()).toString("dd/MM/yyyy");
        return dt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Flight> getFlights() {
        return this.tickets.keySet();
    }

    public Integer getTickets(Flight f) {
        return this.tickets.get(f);
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String bookTickets(int flightId) {
        this.flight = flightManager.find(flightId);
        return "book_tickets";
    }

    public String updateTickets() {
        this.tickets.put(this.flight, this.amount);

        return "show_cart";
    }

    public String book() {
        if (this.tickets.containsKey(this.flight)) {
            Integer newAmount = this.tickets.get(this.flight) + this.amount;
            this.tickets.put(this.flight, newAmount);
        } else {
            this.tickets.put(this.flight, this.amount);
        }

        return "show_cart";
    }

    public String removeTickets(Flight f) {
        if (this.tickets.containsKey(f)) {
            this.tickets.remove(f);
        }

        return "show_cart";
    }

    public String checkoutOverview() {
        return "show_checkout";
    }

    public String editTickets(Flight f) {
        if (this.tickets.containsKey(f)) {
            this.flight = f;
            this.amount = this.tickets.get(f);

            return "edit_cart_tickets";
        }

        return "show_cart";
    }

    public String checkout() {
        Owner owner = new Owner(this.getName(), this.getPhone(), this.getAddress(), this.getEmail());

        for (Map.Entry<Flight, Integer> pair : this.tickets.entrySet()) {
            ticketManager.bookTickets(pair.getKey(), pair.getValue(), owner);
        }

        return "index";
    }

    public float getTotal() {
        float total = 0.f;

        for (Map.Entry<Flight, Integer> pair : this.tickets.entrySet()) {
            Flight f = pair.getKey();
            int ticketAmount = pair.getValue();

            total += f.getTicketCost() * ticketAmount;
        }

        return total;
    }
}
