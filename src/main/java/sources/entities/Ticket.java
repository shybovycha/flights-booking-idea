package sources.entities;

import java.sql.Date;
import java.util.Calendar;

import javax.persistence.*;

import org.joda.time.DateTime;
import org.joda.time.Days;

@Entity
@Table(name="tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Embedded
    private Owner owner;

    @ManyToOne
    @JoinColumn(name="flightId")
    private Flight flight;

    /* TODO: change from String to enum TICKET_STATUS { AVAILABLE, BOOKED, SOLD } */
    private String status;

    public Ticket() {
        this.status = "AVAILABLE";
    }

    public boolean isBooked() {
        return this.status.equals("ORDERED");
    }

    public boolean isAvailable() {
        return this.status.equals("AVAILABLE");
    }

    public boolean isSold() {
        return this.status.equals("SOLD");
    }

    public void makeAvailable() {
        this.owner = null;
        this.status = "AVAILABLE";
    }

    public void makeBooked(Owner owner) {
        this.owner = owner;
        this.owner.setOwnerFrom(new Date(Calendar.getInstance().getTimeInMillis()));
        this.status = "ORDERED";
    }

    public DateTime bookedAt() {
        if (this.isBooked() && this.owner != null) {
            return new DateTime(this.owner.getOwnerFrom());
        } else {
            return null;
        }
    }

    public boolean isOutdated() {
        return (Days.daysBetween(DateTime.now(), bookedAt()).getDays() > 3);
    }

    public void makeSold() {
        this.status = "SOLD";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
