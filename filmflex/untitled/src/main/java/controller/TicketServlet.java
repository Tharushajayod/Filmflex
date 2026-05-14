package controller;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/purchase-ticket")
public class TicketServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;
        try {
            JSONObject json = ServletHelper.readJson(request);
            TicketController.saveTicket(json.optString("email"), json.optString("ticketId"), json.optString("price"));
            ServletHelper.json(response, new JSONObject().put("message", "Ticket purchased successfully").toString());
        } catch (IllegalArgumentException | IllegalStateException e) {
            ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while purchasing ticket");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;
        try {
            String details = TicketController.getTicketDetails(request.getParameter("email"));
            if (details == null) {
                ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "No ticket found for this user");
                return;
            }
            String[] p = details.split(",", -1);
            JSONObject obj = new JSONObject();
            obj.put("email", p.length > 0 ? p[0] : "");
            obj.put("ticketId", p.length > 1 ? p[1] : "");
            obj.put("price", p.length > 2 ? p[2] : "");
            obj.put("purchaseDate", p.length > 3 ? p[3] : "");
            ServletHelper.json(response, obj.toString());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching ticket details");
        }
    }
}
