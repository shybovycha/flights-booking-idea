package sources.filters;

import sources.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {
    private static ArrayList<String> adminPages;
    private static HashMap<String, String> rolePages;

    static {
        adminPages = new ArrayList<String>();

        adminPages.add("/booking_office_accountant.xhtml");
        adminPages.add("/booking_office_administrator.xhtml");
        adminPages.add("/business_analytic.xhtml");
        adminPages.add("/create_flight.xhtml");
        adminPages.add("/create_user.xhtml");
        adminPages.add("/edit_flight.xhtml");
        adminPages.add("/edit_user.xhtml");
        adminPages.add("/root.xhtml");

        rolePages = new HashMap<String, String>();

        rolePages.put("/booking_office_accountant.xhtml", "ACCOUNTANT");
        rolePages.put("/booking_office_administrator.xhtml", "BOOKING_ADMINISTRATOR");
        rolePages.put("/business_analytic.xhtml", "ANALYTIC");
        rolePages.put("/create_flight.xhtml", "BOOKING_ADMINISTRATOR");
        rolePages.put("/create_user.xhtml", "SUPER");
        rolePages.put("/edit_flight.xhtml", "BOOKING_ADMINISTRATOR");
        rolePages.put("/edit_user.xhtml", "SUPER");
        rolePages.put("/root.xhtml", "SUPER");
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String requestedPage = ((HttpServletRequest) req).getServletPath();

        if (adminPages.contains(requestedPage)) {
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login.xhtml");
                return;
            }

            User user = (User) session.getAttribute("user");

            if (!rolePages.get(requestedPage).equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/login.xhtml");
                return;
            }

            chain.doFilter(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }
}