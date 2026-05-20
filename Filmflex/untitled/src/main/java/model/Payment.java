package model;

public class Payment {

    // Applying the core principle of Encapsulation: Object attributes are marked private
    // to shield the sensitive state parameters from direct unauthorized modification by outer classes.
    private String email;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolder;

    public Payment(String email, String cardNumber, String expiryDate, String cvv, String cardHolder) {
        this.email = email;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
    }

    // ── Encapsulation Access Interface Layer (Read-Only Getters) ──
    // Exposing public getter interfaces explicitly to provide safe access metrics while denying write mutations.
    public String getEmail() { return email; }
    public String getCardNumber() { return cardNumber; }
    public String getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
    public String getCardHolder() { return cardHolder; }

    public String toFileString() {
        // Passing arguments directly into the safe countermeasure sanitizer to protect array indices mapping configurations
        return String.join(",", safe(email), safe(cardNumber), safe(expiryDate), safe(cvv), safe(cardHolder));
    }

    public static Payment fromFileString(String line) {
        // Safe Guard Clause: Aborting data translation loops early if input stream is blank or null to insulate server stability
        if (line == null || line.trim().isEmpty()) return null;

        // Slicing string metadata values based on explicit comma character token boundaries rules
        // Passing -1 limit configuration ensures trailing spaces columns map properly without throwing array exceptions
        String[] parts = line.split(",", -1);

        // Defensive Structural Validation Check: Enforcing that the record array length contains exactly 5 critical billing properties
        if (parts.length < 5) return null;

        // Instantiating the domain entity context by mapping positional array index values safely onto constructor positions
        return new Payment(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim());
    }

    private static String safe(String value) {
        // If string attribute points to a null reference output blank space, otherwise strip commas and trim trailing spaces
        return value == null ? "" : value.replace(",", " ").trim();
    }
}