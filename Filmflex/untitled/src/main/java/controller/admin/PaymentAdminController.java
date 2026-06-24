package controller.admin;

import repository.PurchaseTicketRepository;
import model.Ticket;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


@WebServlet("/api/admin/all-tickets")
public class PaymentAdminController extends HttpServlet {

    // Dependency Injection of the Repository data node to decouple storage extraction from routing mechanics
    private final PurchaseTicketRepository repo = new PurchaseTicketRepository();


    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Interacting with the repository access object layer to fetch the raw array token collection records
            List<Ticket> tickets = repo.getAllPurchasedTickets();

            // Initializing a top-level JSON Array layer container to group serialized transactional rows
            JSONArray array = new JSONArray();

            // Iterating through the model instances collection to dynamically map attributes maps
            for (Ticket t : tickets) {
                JSONObject o = new JSONObject();

                // Employing safe ternary null-defensive checks mapping default placeholder string fallbacks
                o.put("email", t.getEmail() != null ? t.getEmail() : "N/A");
                o.put("plan", t.getPlan() != null ? t.getPlan() : "N/A");
                o.put("amount", t.getPrice()); // Mapping native numeric data type values directly
                o.put("date", t.getTimestamp() != null ? t.getTimestamp() : "N/A");

                array.put(o);
            }

            // Enforcing corporate RESTful response header parameters onto configuration output streams matrix
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");

            // Writing the compiled collection text dataset payload back to the client asynchronous AJAX call script
            res.getWriter().write(array.toString());

        } catch (Exception e) {
            // Operational diagnostics monitoring: Logging the complete failure runtime exception context inside terminal stack trace
            e.printStackTrace();

            // Gracefully terminating the active HTTP response loop utilizing standard Internal Server Error 500 status markers
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}