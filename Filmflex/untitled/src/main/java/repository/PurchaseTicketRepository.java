package repository; // Placed inside the core infrastructure data access repository package layer

import model.Ticket;
import util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Repository component acting as the direct gateway to flat-file ticket storage.
 * Follows the standard Repository Design Pattern to isolate physical file storage mechanics
 * from upstream business logic controllers, facilitating clean architectural abstraction.
 */
public class PurchaseTicketRepository {

    /**
     * READ Operation: Fetches and parses all documented rows inside the text storage into typed domain objects.
     * @return A List collection containing successfully populated Ticket instances blueprints
     * @throws IOException Propagating absolute system I/O errors up into calling controller layers for global catch trapping
     */
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