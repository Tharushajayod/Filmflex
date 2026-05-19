package controller;

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
    private final PurchaseTicketRepository repo = new PurchaseTicketRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            List<Ticket> tickets = repo.getAllPurchasedTickets();
            JSONArray array = new JSONArray();
            for (Ticket t : tickets) {
                JSONObject o = new JSONObject();
                o.put("email", t.getEmail());
                o.put("plan", t.getPlan());
                o.put("amount", t.getPrice());
                o.put("date", t.getTimestamp());
                array.put(o);
            }
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(array.toString());
        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}