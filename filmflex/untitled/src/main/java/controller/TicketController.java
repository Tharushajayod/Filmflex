package controller;

import util.FileUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class TicketController {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+\\.\\d{2}$");

    public static void saveTicket(String email, String ticketId, String price) throws IOException {
        validateInput(email, ticketId, price);
        if (hasTicket(email)) throw new IllegalStateException("User already has a ticket");
        List<String> lines = FileUtil.readAllTicketLines();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        lines.add(String.join(",", email.trim(), ticketId.trim(), price.trim(), date));
        FileUtil.writeAllTicketLines(lines);
    }

    public static boolean hasTicket(String email) throws IOException {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email is required");
        for (String line : FileUtil.readAllTicketLines()) {
            if (line.startsWith(email.trim() + ",")) return true;
        }
        return false;
    }

    public static String getTicketDetails(String email) throws IOException {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email is required");
        for (String line : FileUtil.readAllTicketLines()) {
            if (line.startsWith(email.trim() + ",")) return line;
        }
        return null;
    }

    private static void validateInput(String email, String ticketId, String price) {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email is required");
        if (!EMAIL_PATTERN.matcher(email).matches()) throw new IllegalArgumentException("Invalid email format");
        if (ticketId == null || ticketId.trim().isEmpty()) throw new IllegalArgumentException("Ticket ID is required");
        if (price == null || price.trim().isEmpty()) throw new IllegalArgumentException("Price is required");
        if (!PRICE_PATTERN.matcher(price).matches()) throw new IllegalArgumentException("Price must be in format 0000.00");
    }
}