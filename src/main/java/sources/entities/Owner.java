package sources.entities;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Date;

import javax.persistence.*;

@Embeddable
@Access(AccessType.FIELD)
public class Owner {
    private String address;
    private String email;
    private String phone;
    private String name;
    private Date ownerFrom;

    public Owner() {}

    public Owner(String name, String phone, String address, String email) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOwnerFrom() {
        return this.ownerFrom;
    }

    public void setOwnerFrom(Date ownerFrom) {
        this.ownerFrom = ownerFrom;
    }
}
