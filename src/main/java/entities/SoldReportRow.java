package entities;

import java.sql.Date;

import org.joda.time.DateTime;

public class SoldReportRow {
    public String destination;
    public DateTime date;
    public int ticketsSold;
    public float totalCost;

    public SoldReportRow(Date date, String destination, int ticketsSold, float totalCost) {
        this.destination = destination;
        this.ticketsSold = ticketsSold;
        this.totalCost = totalCost;
        this.date = new DateTime(date);
    }
}
