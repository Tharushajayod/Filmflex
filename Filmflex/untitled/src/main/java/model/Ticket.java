package model; // Placed inside the core architectural model layer package

/**
 * Domain Model representing the Ticket entity blueprint.
 * Enforces strict Encapsulation, comma-delimited data tokenization,
 * and fault-tolerant numeric parsing to safely reconstruct object states from flat files.
 */
public class Ticket {

    // Applying the core principle of Encapsulation: Object attributes are marked private
    // to shield the sensitive state parameters from direct unauthorized modification by outer classes.
    private String email;
    private String plan;
    private double price;
    private String timestamp;

    /**
     * Parameterized Constructor blueprint method to allocate instance fields
     * and initialize a brand-new Ticket data entity state model context.
     */
    public Ticket(String email, String plan, double price, String timestamp) {
        this.email = email;
        this.plan = plan;
        this.price = price;
        this.timestamp = timestamp;
    }

    /**
     * FACTORY METHOD PATTERN (File Deserialization): Reconstructs a fully typed Java Ticket object
     * instanced environment by slicing and processing a raw comma-delimited row segment text stream line.
     * Incorporates protective internal try-catch blocks to insulate execution from formatting errors.
     * @param line A single structural tracking line string pulled from tickets.txt
     * @return Ticket model context object reference, or null if validation density constraints fail
     */
    public static Ticket fromFileString(String line) {
        // Safe Guard Clause: Aborting data translation loops early if input stream is blank or null to insulate server stability
        if (line == null || line.trim().isEmpty()) return null;

        // Slicing string metadata values based on explicit comma character token boundaries regulations
        String[] parts = line.split(",");

        // Defensive Structural Validation Check: Enforcing that the record array length contains at least 4 critical properties
        if (parts.length < 4) return null;

        try {
            // Mapping schema layout: parts[0]=email, parts[1]=plan/id, parts[2]=amount/price, parts[3]=date timestamp
            // Invoking explicit floating-point transformations while instantiating the typed object reference safely
            return new Ticket(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]);
        } catch (Exception e) {
            // Fault Tolerance: Catches NumberFormatException if price string holds dirty inputs, returning null to protect the loop
            return null;
        }
    }

    // ── Encapsulation Access Interface Layer (Read-Only Getters) ──
    // Exposing public getter interfaces explicitly to provide safe access metrics while denying structural modifications.
    public String getEmail() { return email; }
    public String getPlan() { return plan; }
    public double getPrice() { return price; }
    public String getTimestamp() { return timestamp; }
}