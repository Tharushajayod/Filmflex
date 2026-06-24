package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Ticket {

    private String email;
    private String plan;
    private double price;
    private String timestamp;

    public Ticket(String email, String plan, double price, String timestamp) {
        this.email = email;
        this.plan = plan;
        this.price = price;
        this.timestamp = timestamp;
    }

    public static Ticket fromFileString(String line) {
        if (line == null || line.trim().isEmpty())
            return null;

        String[] parts = line.split(",");

        // CORRECTION CHECK: Enforcing that the flat record row contains all 5 columns properly
        if (parts.length < 5)
            return null;

        try {
            // Index Mapping: parts[0]=email, parts[2]=plan, parts[3]=price, parts[4]=timestamp
            return new Ticket(parts[0], parts[2], Double.parseDouble(parts[3]), parts[4]);
        } catch (Exception e) {
            // Fault Tolerance fallback trap protecting structural parsing failure drops
            return null;
        }
    }

    public boolean isExpired() {
        // Lifetime packages will never compute expiration algorithms loops
        if (this.plan != null && this.plan.toLowerCase().contains("lifetime")) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date purchaseDate = sdf.parse(this.timestamp);

            Calendar cal = Calendar.getInstance();
            cal.setTime(purchaseDate);

            // Enforcing 30 days corporate validation time windows rules states
            cal.add(Calendar.DAY_OF_MONTH, 30);
            Date expiryThresholdDate = cal.getTime();

            return new Date().after(expiryThresholdDate);
        } catch (Exception e) {
            return true; // Default fallback to expired rule to insulate system boundaries securely
        }
    }

    // ── Encapsulation Access Interface Layer (Read-Only Getters) ──
    public String getEmail() { return email; }
    public String getPlan() { return plan; }
    public double getPrice() { return price; }
    public String getTimestamp() { return timestamp; }
}