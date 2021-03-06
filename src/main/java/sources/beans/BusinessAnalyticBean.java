package sources.beans;

import org.springframework.context.annotation.Scope;
import sources.entities.SoldReportRow;
import org.apache.commons.lang3.StringUtils;
import sources.managers.TicketManager;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

    public String getMonthlySeries() {
        if (getStartDate() == null || getEndDate() == null) {
            return "[]";
        }

        List<SoldReportRow> reportRows = ticketManager.soldReportByMonth(getStartDate(), getEndDate());
        Vector<String> data = new Vector<String>();

        for (SoldReportRow row : reportRows) {
            data.add(String.format("%d", row.getTicketsSold()));
        }

        return String.format("[{ name: \"%s\", data: [ %s ] }]", "by month", StringUtils.join(data.toArray(), ","));
    }

    public String getDateSeries() {
        if (getStartDate() == null || getEndDate() == null) {
            return "[]";
        }

        List<SoldReportRow> reportRows = ticketManager.soldReportByDate(getStartDate(), getEndDate());
        Vector<String> data = new Vector<String>();

        for (SoldReportRow row : reportRows) {
            data.add(String.format("%d", row.getTicketsSold()));
        }

        return String.format("[{ name: \"%s\", data: [ %s ] }]", "by date", StringUtils.join(data.toArray(), ","));
    }

    public String getRouteSeries() {
        if (getStartDate() == null || getEndDate() == null) {
            return "[]";
        }

        List<SoldReportRow> reportRows = ticketManager.soldReportByRoute(getStartDate(), getEndDate());
        Vector<String> data = new Vector<String>();

        for (SoldReportRow row : reportRows) {
            data.add(String.format("%d", row.getTicketsSold()));
        }

        return String.format("[{ name: \"%s\", data: [ %s ] }]", "by route", StringUtils.join(data.toArray(), ","));
    }

    public String getMonthlyCategories() {
        if (getStartDate() == null || getEndDate() == null) {
            return "[]";
        }

        List<SoldReportRow> reportRows = ticketManager.soldReportByMonth(getStartDate(), getEndDate());
        Vector<String> data = new Vector<String>();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMMM");

        for (SoldReportRow row : reportRows) {
            String month = fmt.print(row.getDate());
            data.add(String.format("\"%s\"", month));
        }

        return String.format("[%s]", StringUtils.join(data.toArray(), ","));
    }

    public String getDateCategories() {
        if (getStartDate() == null || getEndDate() == null) {
            return "[]";
        }

        List<SoldReportRow> reportRows = ticketManager.soldReportByDate(getStartDate(), getEndDate());
        Vector<String> data = new Vector<String>();

        for (SoldReportRow row : reportRows) {
            data.add(String.format("\"%s\"", row.getFormattedDate()));
        }

        return String.format("[%s]", StringUtils.join(data.toArray(), ","));
    }

    public String getRouteCategories() {
        if (getStartDate() == null || getEndDate() == null) {
            return "[]";
        }

        List<SoldReportRow> reportRows = ticketManager.soldReportByRoute(getStartDate(), getEndDate());
        Vector<String> data = new Vector<String>();

        for (SoldReportRow row : reportRows) {
            String route = String.format("%s - %s", row.getDeparture(), row.getDestination());
            data.add(String.format("\"%s\"", route));
        }

        return String.format("[%s]", StringUtils.join(data.toArray(), ","));
    }

    public List<SoldReportRow> getMonthlyReport() {
        if (getStartDate() == null || getEndDate() == null) {
            return null;
        }

        return ticketManager.soldReportByMonth(getStartDate(), getEndDate());
    }

    public List<SoldReportRow> getDateReport() {
        if (getStartDate() == null || getEndDate() == null) {
            return null;
        }

        return ticketManager.soldReportByDate(getStartDate(), getEndDate());
    }

    public List<SoldReportRow> getRouteReport() {
        if (getStartDate() == null || getEndDate() == null) {
            return null;
        }

        return ticketManager.soldReportByRoute(getStartDate(), getEndDate());
    }
}
