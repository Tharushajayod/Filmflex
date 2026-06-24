package controller.movie;

import util.FileUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class TicketController {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9_.]+@(.+)$");
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+\\.\\d{2}$");


    public static void saveTicket(String email, String ticketId, String plan, String price) throws IOException {
        validateInput(email, ticketId, price);

        if (hasTicket(email)) {
            throw new IllegalStateException("User already has a ticket");
        }

        List<String> lines = FileUtil.readAllTicketLines();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // FIX: Included plan variables inside serialization block layout
        lines.add(String.join(",", email.trim(), ticketId.trim(), plan.trim(), price.trim(), date));
        FileUtil.writeAllTicketLines(lines);
    }

    public static boolean hasTicket(String email) throws IOException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        for (String line : FileUtil.readAllTicketLines()) {
            if (line.startsWith(email.trim() + ",")) return true;
        }
        return false;
    }

    public static String getTicketDetails(String email) throws IOException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        for (String line : FileUtil.readAllTicketLines()) {
            if (line.startsWith(email.trim() + ",")) return line;
        }
        return null;
    }

    public static void updateTicket(String email, String newTicketId, String newPlan, String newPrice) throws IOException {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email is required");
        if (!PRICE_PATTERN.matcher(newPrice).matches()) throw new IllegalArgumentException("Invalid price format");

        List<String> lines = FileUtil.readAllTicketLines();
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        String cleanEmail = email.trim();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        for (String line : lines) {
            if (line.startsWith(cleanEmail + ",")) {
                updatedLines.add(String.join(",", cleanEmail, newTicketId.trim(), newPlan.trim(), newPrice.trim(), date));
                found = true;
            } else {
                updatedLines.add(line);
            }
        }
        if (!found) throw new IllegalStateException("No active ticket found to update");
        FileUtil.writeAllTicketLines(updatedLines);
    }

    public static void deleteTicket(String email) throws IOException {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email is required");
        List<String> lines = FileUtil.readAllTicketLines();
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        String cleanEmail = email.trim();

        for (String line : lines) {
            if (line.startsWith(cleanEmail + ",")) {
                found = true;
            } else {
                updatedLines.add(line);
            }
        }
        if (!found) throw new IllegalStateException("No ticket records exist under this target email");
        FileUtil.writeAllTicketLines(updatedLines);
    }

    private static void validateInput(String email, String ticketId, String price) {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email is required");
        if (!EMAIL_PATTERN.matcher(email).matches()) throw new IllegalArgumentException("Invalid email format");
        if (ticketId == null || ticketId.trim().isEmpty()) throw new IllegalArgumentException("Ticket ID is required");
        if (price == null || price.trim().isEmpty()) throw new IllegalArgumentException("Price is required");
        if (!PRICE_PATTERN.matcher(price).matches()) {
            throw new IllegalArgumentException("Price must be in format 0000.00");
        }
    }
}