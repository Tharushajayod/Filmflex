package repository;

import model.Ticket;
import util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseTicketRepository {
    public List<Ticket> getAllPurchasedTickets() throws IOException {
        List<Ticket> tickets = new ArrayList<>();
        // FileUtil මගින් tickets.txt එක කියවන්න
        for (String line : FileUtil.readAllTicketLines()) {
            Ticket t = Ticket.fromFileString(line);
            if (t != null) tickets.add(t);
        }
        return tickets;
    }
}