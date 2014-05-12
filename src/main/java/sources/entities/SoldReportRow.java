package sources.entities;

import java.sql.Date;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SoldReportRow {
    private String departure;
    private String destination;
    private DateTime date;
    private int ticketsSold;
    private float totalCost;

    private static DateTimeFormatter monthFormatter;
    private static DateTimeFormatter dateFormatter;

    static {
        monthFormatter = DateTimeFormat.forPattern("MMMM");
        dateFormatter = DateTimeFormat.forPattern("dd-MM-YYYY");
    }

    public SoldReportRow(Timestamp date, String departure, String destination, Long ticketsSold, Double totalCost) {
        this.departure = departure;
        this.destination = destination;
        this.ticketsSold = ticketsSold.intValue();
        this.totalCost = totalCost.floatValue();
        this.date = new DateTime(date.getTime());
    }

    public SoldReportRow(Long date, String departure, String destination, Integer ticketsSold, Double totalCost) {
        this.departure = departure;
        this.destination = destination;
        this.ticketsSold = ticketsSold;
        this.totalCost = totalCost.floatValue();
        this.date = new DateTime(date);
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getMonth() {
        return monthFormatter.print(getDate());
    }

    public String getRoute() {
        return String.format("%s - %s", getDeparture(), getDestination());
    }

    public String getFormattedDate() {
        return dateFormatter.print(getDate());
    }
}
