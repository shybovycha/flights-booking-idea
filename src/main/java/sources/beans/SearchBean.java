package sources.beans;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sources.entities.Flight;
import sources.managers.FlightManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Vector;

@Named("search")
@Scope("session")
public class SearchBean {
    private String date;
    private String destination;
    private Vector<Flight> flights;

    @Inject
    private FlightManager flightManager;

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

        this.flights = (Vector<Flight>) flightManager.findFlights(destination, now, date);
        // this.flights = (Vector<Flight>) FlightManager.all();

        return "search_results";
    }
}
