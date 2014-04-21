package sources.beans;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

    public boolean getHasResults() {
        return !(this.flights == null || this.flights.isEmpty());
    }

    public String getAvailableDestinations() {
        Vector<String> destinations = (Vector<String>) flightManager.getAvailableDestinations();
        String result = "";

        for (int i = 0; i < destinations.size(); i++) {
            result += String.format("'%s'", destinations.get(i));

            if (i < destinations.size() - 1) {
                result += ",";
            }
        }

        return String.format("[%s]", result);
    }

    public String perform() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

        DateTime targetDate = formatter.parseDateTime(date),
                now = DateTime.now(),
                lowerBound,
                upperBound;

        if (targetDate.equals(now) || (targetDate.isAfter(now.minusDays(3)) && targetDate.isBefore(now.plusDays(3)))) {
            lowerBound = now;
            upperBound = now.plusDays(3);
        } else {
            lowerBound = targetDate.minusDays(3);
            upperBound = targetDate.plusDays(3);
        }

        this.flights = (Vector<Flight>) flightManager.findFlights(destination,
            lowerBound.toString("dd/MM/yyyy"),
            upperBound.toString("dd/MM/yyyy")
        );

        return "search_results";
    }
}
