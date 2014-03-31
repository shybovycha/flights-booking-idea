package beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import managers.FlightManager;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entities.Flight;

@ManagedBean(name="bookingOfficeAdministrator", eager=true)
@SessionScoped
public class BookingOfficeAdministratorBean {

    public BookingOfficeAdministratorBean() {
    }

    public Vector<Flight> getFlights() {
        Vector<Flight> flights = (Vector<Flight>) FlightManager.all();

        return flights;
    }
}
