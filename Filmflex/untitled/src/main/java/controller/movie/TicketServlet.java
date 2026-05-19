package controller.movie; // Organized under the movie package container structure

import controller.ServletHelper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller servlet responsible for managing the ticket booking pipeline lifecycle endpoints.
 * Handles HTTP POST requests to purchase new tickets and HTTP GET requests to retrieve transaction receipts metadata.
 * Demonstrates advanced multi-catch error tracking patterns and serialized JSON mapping matrices.
 */
@WebServlet("/purchase-ticket")
public class TicketServlet extends HttpServlet {

    /**
     * CREATE Operation: Intercepts HTTP POST requests to initiate ticket purchasing workflows.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Access Authorization Guard: Terminate process instantly if current session lacks regular user claims
        if (!ServletHelper.requireUser(request, response)) return;

        try {
            // Ingesting incoming raw stream request buffer data and parsing it into a structured JSONObject dictionary
            JSONObject json = ServletHelper.readJson(request);

            // Delegating input string tokens mapping and persistent file save executions to the static logic utility controller
            TicketController.saveTicket(
                    json.optString("email"),
                    json.optString("ticketId"),
                    json.optString("price")
            );

            // Dispatching a serialized standard transaction completion message back to client AJAX fetch routines
            ServletHelper.json(response, new JSONObject().put("message", "Ticket purchased successfully").toString());

        } catch (IllegalArgumentException | IllegalStateException e) {
            // Java 7 Multi-Catch Feature: Trapping expected client-side business rule violations parameters gracefully
            // Returns an HTTP 400 Bad Request error code injecting specific failure trace text strings (e.g., "Duplicate purchase")
            ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());

        } catch (Exception e) {
            // Global Exception Fallback: Capturing unexpected disk operations crashes safely (HTTP 500)
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while purchasing ticket");
        }
    }

    /**
     * READ Operation: Intercepts HTTP GET requests to render transactional historical metadata rows.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Security Gatekeeping: Enforcing authentication token verification routines early in execution flow
        if (!ServletHelper.requireUser(request, response)) return;

        try {
            // Fetching raw comma-delimited record matching query user email parameters from flat data files boundaries
            String details = TicketController.getTicketDetails(request.getParameter("email"));

            if (details == null) {
                // Handling missing entities references indexing lookups safely (HTTP 404 Not Found status)
                ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "No ticket found for this user");
                return;
            }

            // Split Array Processing: Passing negative limit value splits columns safely preserving null trailing arrays density
            String[] p = details.split(",", -1);

            // Building data mapping definitions transforming positional array elements directly into REST attributes
            JSONObject obj = new JSONObject();
            obj.put("email", p.length > 0 ? p[0] : "");
            obj.put("ticketId", p.length > 1 ? p[1] : "");
            obj.put("price", p.length > 2 ? p[2] : "");
            obj.put("purchaseDate", p.length > 3 ? p[3] : ""); // Extracting generated timestamp record

            // Writing the compiled structural object back to frontend client dashboard modules
            ServletHelper.json(response, obj.toString());

        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching ticket details");
        }
    }
}