package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.joda.time.DateTime;
import org.joda.time.format.*;

import entities.Flight;

@ManagedBean(name="search", eager=true)
@SessionScoped
public class SearchBean {
    private String date;
    private String destination;

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

    public Flight[] getFlights() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

        Flight[] flights = new Flight[] {
            new Flight("here", this.getDestination(),
                    new java.sql.Date(fmt.parseDateTime(this.getDate()).toDate().getTime()),
                    (float) 14.99)
        };

        return flights;
    }

    public String perform() {
        return "search_results";
    }
}
