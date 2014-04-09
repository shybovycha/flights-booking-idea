package beans;

import dao.FlightDAO;
import entities.SoldReportRow;
import org.apache.commons.lang3.StringUtils;
import dao.TicketDAO;
import entities.Flight;
import entities.Ticket;
import managers.FlightManager;
import managers.TicketManager;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean(name="report", eager=true)
@SessionScoped
public class BusinessAnalyticBean {
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BusinessAnalyticBean() {
    }

    public String update() {
        return "report";
    }

    public String getChartData() {
        if (getStartDate() == null || getEndDate() == null) {
            return "[]";
        }

        Vector<String> data = new Vector<String>();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMMM, yyyy");

        List<SoldReportRow> reportRows = TicketDAO.soldReportByDate(getStartDate(), getEndDate());

        for (SoldReportRow row : reportRows) {
            String month = fmt.print(row.getDate());
            String route = String.format("%s - %s", row.getDeparture(), row.getDestination());

            data.add(String.format("{ name: \"%s\", data: [ \"%s\", %d ] }", route, month, row.getTicketsSold()));
        }

        return String.format("[%s]", StringUtils.join(data.toArray(), ","));
    }

    public boolean getCanRender() {
        return ((getStartDate() != null) && (getEndDate() != null));
    }
}
