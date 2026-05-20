package repository;

import model.Ticket;
import util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseTicketRepository {

    public List<Ticket> getAllPurchasedTickets() throws IOException {

        // Operational Monitoring Logs: Printing absolute storage system targets pathing parameters directly onto IDE server console
        System.out.println("Reading tickets from: " + FileUtil.getTicketsFilePath());

        // Reading raw text layout data matrices structures directly from the low-level shared file utilities module
        List<String> lines = FileUtil.readAllTicketLines();

        // Runtime Diagnostic Trace: Outputting structural database line population density limits onto debugging monitors
        System.out.println("Lines found: " + lines.size());

        // Allocating a dynamic array collection in heap memory memory zone to temporarily cache parsed structures models
        List<Ticket> tickets = new ArrayList<>();

        // Data Extraction Pipeline: Streaming through individual delimited string entries rows read from tickets.txt
        for (String line : FileUtil.readAllTicketLines()) {

            // Invoking the Model's Creational Factory method to safely deserialize raw string into typed entity instance
            Ticket t = Ticket.fromFileString(line);

            // Null-Safety Validation Check: Appending the object into collection matrix arrays ONLY if parsing succeeds
            if (t != null) {
                tickets.add(t);
            }
        }

        // Returning the finalized unmarshalled dataset back up into administration dashboard processing threads
        return tickets;
    }
}