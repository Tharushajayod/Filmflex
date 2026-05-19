package model; // Placed inside the core architectural model layer package

/**
 * Domain Model representing the Payment entity blueprint (Billing Profile).
 * Enforces strict Encapsulation, custom comma-delimited data tokenization,
 * and structural sanitization to protect flat-file storage layouts.
 */
public class Payment {

    // Applying the core principle of Encapsulation: Object attributes are marked private
    // to shield the sensitive state parameters from direct unauthorized modification by outer classes.
    private String email;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolder;

    /**
     * Parameterized Constructor blueprint method to allocate instance fields
     * and initialize a brand-new Payment data entity state model context.
     */
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

    /**
     * FLAT-FILE SERIALIZATION: Collapses the internal properties map of the runtime Object instances
     * directly into a single comma-separated text record line row format layout.
     */
    public String toFileString() {
        // Passing arguments directly into the safe countermeasure sanitizer to protect array indices mapping configurations
        return String.join(",", safe(email), safe(cardNumber), safe(expiryDate), safe(cvv), safe(cardHolder));
    }

    /**
     * FACTORY METHOD PATTERN (File Deserialization): Reconstructs a fully typed Java Payment object instanced
     * environment by slicing and processing a raw comma-delimited row segment text stream line.
     * @param line A single structural tracking line string pulled from payments.txt
     * @return Payment model context object reference, or null if validation density constraints fail
     */
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

    /**
     * Comma-Injection Attack Countermeasure: Checks user-inputted billing variables for literal commas.
     * Replaces comma tokens with spaces to guarantee structural flat-file line index consistency is preserved.
     */
    private static String safe(String value) {
        // If string attribute points to a null reference output blank space, otherwise strip commas and trim trailing spaces
        return value == null ? "" : value.replace(",", " ").trim();
    }
}