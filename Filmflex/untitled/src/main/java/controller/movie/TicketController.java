package controller.movie; // Organized under the movie package container structure

import util.FileUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility Controller class responsible for managing core business logic for Movie Tickets.
 * Handles input verification using Regular Expressions (Regex), duplication checks,
 * and persistent storage write routines over flat files (tickets.txt).
 */
public class TicketController {

    // Enforcing strict structural validation boundaries utilizing compiled Pattern Regex constants
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+\\.\\d{2}$");

    /**
     * CREATE Operation: Commits a newly purchased ticket record row onto disk storage (tickets.txt).
     * Validates inputs, checks for active premium limits, appends data formatting tags, and flushes states.
     */
    public static void saveTicket(String email, String ticketId, String price) throws IOException {
        // Step 1: Triggering strict validation constraints check routines over structural raw inputs
        validateInput(email, ticketId, price);

        // State Business Constraint: Guarding platform boundaries against double purchases loops
        if (hasTicket(email)) {
            // Throwing IllegalStateException if system runtime rules are violated by current operation intent
            throw new IllegalStateException("User already has a ticket");
        }

        // Extracting historical dataset context blocks from flat storage system boundaries
        List<String> lines = FileUtil.readAllTicketLines();

        // Generating automated real-time transaction timestamp strings conforming to corporate formats
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Serializing fields into a single delimited row segment string layout array token
        lines.add(String.join(",", email.trim(), ticketId.trim(), price.trim(), date));

        // Committing updated arrays streams back onto absolute hard drive partition coordinates
        FileUtil.writeAllTicketLines(lines);
    }

    /**
     * READ Operation (Verification): Checks if a unique user identity already possesses an active ticket entry.
     * @return boolean true if matching email pattern exists at the index boundary header rows
     */
    public static boolean hasTicket(String email) throws IOException {
        // Defensive Null-Check guard rule block evaluating string argument presence state density
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        // Optimization check: Parsing each row entry iteratively to trace structural matching key tokens
        for (String line : FileUtil.readAllTicketLines()) {
            // Employs quick index prefix matching boundaries check layout to scale string comparison speeds
            if (line.startsWith(email.trim() + ",")) return true;
        }
        return false;
    }

    /**
     * READ Operation (Retrieval): Extracts complete row metadata associated with a verified email string index.
     * @return Raw delimited line string record from tickets.txt, or null if query targets are absent
     */
    public static String getTicketDetails(String email) throws IOException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        for (String line : FileUtil.readAllTicketLines()) {
            if (line.startsWith(email.trim() + ",")) return line;
        }
        return null;
    }

    /**
     * Strict Defensive Validation Routine: Evaluates raw arguments strings against strict system constraints.
     * Intercepts formatting errors before they reach the persistent database flat layer file.
     */
    private static void validateInput(String email, String ticketId, String price) {
        // Null and Empty space string density verification guard clauses blocks
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email is required");

        // Regex format matching validation barrier to assert syntactical accuracy standards
        if (!EMAIL_PATTERN.matcher(email).matches()) throw new IllegalArgumentException("Invalid email format");

        if (ticketId == null || ticketId.trim().isEmpty()) throw new IllegalArgumentException("Ticket ID is required");
        if (price == null || price.trim().isEmpty()) throw new IllegalArgumentException("Price is required");

        // Strict Numeric Constraint: Confirming double values enforce explicit 2-digit decimal precision layout configurations (e.g., "450.00")
        if (!PRICE_PATTERN.matcher(price).matches()) {
            throw new IllegalArgumentException("Price must be in format 0000.00");
        }
    }
}