package model; // Placed inside the core architectural model layer package

import java.io.Serializable;

/**
 * Domain Model representing the standard User entity blueprint.
 * Implements Serializable to support object stream serialization states.
 * Demonstrates strict Encapsulation, comma-separated flat-file formatting,
 * and backward-compatible data parsing to support older schema logs layers safely.
 */
public class User implements Serializable {

    // Applying the core principle of Encapsulation: Object data attributes are marked private
    // to shield the sensitive state variables from direct unauthorized modification by outer classes.
    private String fullName;
    private String email;
    private String password;
    private String phone; // Added the phone field structure to support active communication tracking

    /**
     * Fully Parameterized Constructor blueprint method to allocate instance fields
     * and initialize a brand-new User domain entity state model context.
     * Integrates defensive null evaluation logic to automatically apply a blank string fallback if phone is omitted.
     */
    public User(String fullName, String email, String password, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone == null ? "" : phone.trim();
    }

    // ── Encapsulation Access & Mutation Interface Layer (Getters & Setters) ──
    // Providing public wrapper methods to safely expose and mutate private field-level values
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * OBJECT STRING REPRESENTATION: Overrides the standard Object.toString() blueprint
     * to flatten the active instance properties directly into a single comma-delimited data row.
     * Appends the phone sequence safely and dynamically into text stream lines for flat file persistence.
     */
    @Override
    public String toString() {
        // Enforcing structural comma-delimited layout mapping notation using the private safe data sanitizer helper
        return String.join(",", safe(fullName), safe(email), safe(password), safe(phone));
    }

    /**
     * FACTORY METHOD PATTERN (File Deserialization): Reconstructs a fully typed Java User object instance
     * environment by slicing and processing a raw comma-delimited text line string from files database.
     * Features schema evolutionary safety checks to ensure backward compatibility with historical data assets.
     * @param line A single structural data row pulled from users.txt
     * @return User object model context reference, or null if validation density checks fail
     */
    public static User fromString(String line) {
        // Safe Guard Clause: Immediate rejection of empty data lines or null pointers to insulate server loops stability
        if (line == null || line.trim().isEmpty()) return null;

        // Slicing database row matrix strings using the comma character token delimiter boundary rules
        // Passing -1 limit configuration ensures trailing empty spaces columns map properly without dropping indices
        String[] parts = line.split(",", -1);

        // Defensive Structural Validation Check: Enforcing that the record array length contains at least 3 critical fields (Name, Email, Pw)
        if (parts.length < 3) return null;

        // Backward Compatibility Validation Strategy: Evaluating structural index 4 density array layers.
        // If phone chunk column parameter doesn't exist on older legacy user data rows inside users.txt,
        // fallback to an empty string safely to prevent throwing an ArrayIndexOutOfBoundsException crash.
        String phoneChunk = parts.length >= 4 ? parts[3].trim() : "";

        // Instantiating the domain entity context by mapping positional array index values safely onto constructor positions
        return new User(parts[0].trim(), parts[1].trim(), parts[2].trim(), phoneChunk);
    }

    /**
     * Comma-Injection Attack Countermeasure Sanitizer: Evaluates user-inputted variable streams for literal commas.
     * Replaces comma tokens with spaces to guarantee structural flat-file line index consistency is preserved.
     */
    private static String safe(String value) {
        // If string attribute points to a null reference output blank space, otherwise strip commas and trim trailing spaces
        return value == null ? "" : value.replace(",", " ").trim();
    }
}