package repository;

import model.Ticket;
import util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseTicketRepository {

    public List<Ticket> getAllPurchasedTickets() throws IOException {

        System.out.println("Reading tickets from: " + FileUtil.getTicketsFilePath());

        List<String> lines = FileUtil.readAllTicketLines();

        System.out.println("Lines found: " + lines.size());

        List<Ticket> tickets = new ArrayList<>();

        for (String line : FileUtil.readAllTicketLines()) {

            Ticket t = Ticket.fromFileString(line);

            if (t != null) {
                tickets.add(t);
            }
        }

        return tickets;
    }
}