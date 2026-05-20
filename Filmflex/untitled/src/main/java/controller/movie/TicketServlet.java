package controller.movie;

import controller.ServletHelper;
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

            // Delegating input string tokens mapping and persistent file save executions to the static logic utility controller
            TicketController.saveTicket(
                    json.optString("email"),
                    json.optString("ticketId"),
                    json.optString("price")
            );

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
            // Fetching raw comma-delimited record matching query user email parameters from flat data files boundaries
            String details = TicketController.getTicketDetails(request.getParameter("email"));

            if (details == null) {
                // Handling missing entities references indexing lookups safely (HTTP 404 Not Found status)
                ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "No ticket found for this user");
                return;
            }
            String[] p = details.split(",", -1);

            // Building data mapping definitions transforming positional array elements directly into REST attributes
            JSONObject obj = new JSONObject();
            obj.put("email", p.length > 0 ? p[0] : "");
            obj.put("ticketId", p.length > 1 ? p[1] : "");
            obj.put("price", p.length > 2 ? p[2] : "");
            obj.put("purchaseDate", p.length > 3 ? p[3] : "");

            // Writing the compiled structural object back to frontend client dashboard modules
            ServletHelper.json(response, obj.toString());

        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching ticket details");
        }
    }
}