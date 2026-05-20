package controller.movie;

import controller.ServletHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller servlet responsible for managing the ticket booking pipeline lifecycle endpoints.
 * Intercepts user booking actions, supports dynamic dashboard lookup, and structural admin ledger tracing.
 */
@WebServlet("/purchase-ticket")
public class TicketServlet extends HttpServlet {

    /**
     * CREATE Operation: Intercepts HTTP POST requests to initiate ticket purchasing workflows.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;

        try {
            JSONObject json = ServletHelper.readJson(request);

            // Passing down explicit plan context mapping
            TicketController.saveTicket(
                    json.optString("email"),
                    json.optString("ticketId"),
                    json.optString("plan"),
                    json.optString("price")
            );

            ServletHelper.json(response, new JSONObject().put("message", "Ticket purchased successfully").toString());
        } catch (IllegalArgumentException | IllegalStateException e) {
            ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error purchasing ticket");
        }
    }

    /**
     * READ Operation: Handles individual user ticket dashboard rendering and complete admin logs tracking data matrices.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String targetEmail = request.getParameter("email");

        // ── STEP 1: ADMIN GLOBAL LEDGER HOOK ──
        // If query targets "all", intercept request early to stream complete tickets.txt archive logs array
        if ("all".equalsIgnoreCase(targetEmail)) {
            if (!ServletHelper.requireAdmin(request, response)) return; // Authorization Boundary Protection

            try {
                JSONArray allTickets = new JSONArray();
                // Iterating across raw dataset lines fetched directly from persistence boundaries
                for (String line : util.FileUtil.readAllTicketLines()) {
                    String[] p = line.split(",", -1);
                    // Ensuring row metadata matches the 5 critical positional columns rules mapping layouts
                    if (p.length >= 5) {
                        JSONObject obj = new JSONObject();
                        obj.put("email", p[0]);
                        obj.put("ticketId", p[1]);
                        obj.put("plan", p[2]);
                        obj.put("price", p[3]);
                        obj.put("purchaseDate", p[4]);
                        obj.put("status", "Completed"); // Default fallback status for historic payment logs
                        allTickets.put(obj);
                    }
                }
                ServletHelper.json(response, allTickets.toString());
                return; // Halt process execution workflow hook early
            } catch (Exception e) {
                ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error compiling admin logs data");
                return;
            }
        }

        // ── STEP 2: REGULAR USER SINGLE LOOKUP WORKFLOW ──
        if (!ServletHelper.requireUser(request, response)) return;

        try {
            String details = TicketController.getTicketDetails(targetEmail);

            if (details == null) {
                ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "No ticket found");
                return;
            }

            String[] p = details.split(",", -1);

            // Checking if layout maps the 5 critical positional columns
            if (p.length < 5) {
                ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Corrupted record");
                return;
            }

            // Invoking runtime calculation framework to see if ticket is expired model parameters
            // Mapped arrays indices: p[0]=email, p[1]=ticketId, p[2]=plan, p[3]=price, p[4]=date
            model.Ticket ticketObj = new model.Ticket(p[0], p[2], Double.parseDouble(p[3]), p[4]);
            String status = ticketObj.isExpired() ? "Expired" : "Active";

            JSONObject obj = new JSONObject();
            obj.put("email", p[0]);
            obj.put("ticketId", p[1]);
            obj.put("plan", p[2]);
            obj.put("price", p[3]);
            obj.put("purchaseDate", p[4]);
            obj.put("status", status);

            ServletHelper.json(response, obj.toString());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching data");
        }
    }

    /**
     * UPDATE Operation: Intercepts HTTP PUT requests to safely mutate ticket configurations layers.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;

        try {
            JSONObject json = ServletHelper.readJson(request);

            TicketController.updateTicket(
                    json.optString("email"),
                    json.optString("ticketId"),
                    json.optString("plan"),
                    json.optString("price")
            );

            ServletHelper.json(response, new JSONObject().put("message", "Ticket updated successfully").toString());
        } catch (IllegalArgumentException | IllegalStateException e) {
            ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating data");
        }
    }

    /**
     * DELETE Operation: Intercepts HTTP DELETE request verbs to purge entry metadata records.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;

        try {
            TicketController.deleteTicket(request.getParameter("email"));
            ServletHelper.json(response, new JSONObject().put("message", "Ticket deleted").toString());
        } catch (IllegalArgumentException | IllegalStateException e) {
            ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error dropping ledger row");
        }
    }
}