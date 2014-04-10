package sources.beans;

import org.springframework.context.annotation.Scope;
import sources.dao.FlightDAO;
import sources.entities.SoldReportRow;
import org.apache.commons.lang3.StringUtils;
import sources.dao.TicketDAO;
import sources.entities.Flight;
import sources.entities.Ticket;
import sources.managers.FlightManager;
import sources.managers.TicketManager;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("report")
@Scope("session")
public class BusinessAnalyticBean {
    private String startDate;
    private String endDate;

    @Inject
    protected TicketManager ticketManager;

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

        List<SoldReportRow> reportRows = ticketManager.soldReportByDate(getStartDate(), getEndDate());

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
