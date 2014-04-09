package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import managers.FlightManager;
import org.joda.time.DateTime;
import org.joda.time.format.*;

import entities.Flight;

import java.util.Vector;

@ManagedBean(name="search", eager=true)
@SessionScoped
public class SearchBean {
    private String date;
    private String destination;
    private Vector<Flight> flights;

    public SearchBean() {
    }

    public void setDate(String value) {
        this.date = value;
    }

    public String getDate() {
        return this.date;
    }

    public void setDestination(String value) {
        this.destination = value;
    }

    public String getDestination() {
        return this.destination;
    }

    public Vector<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Vector<Flight> value) {
        this.flights = value;
    }

    public String perform() {
        String now = DateTime.now().toString("dd/MM/yyyy");

        this.flights = (Vector<Flight>) FlightManager.findFlights(destination, now, date);
        // this.flights = (Vector<Flight>) FlightManager.all();

        return "search_results";
    }
}
