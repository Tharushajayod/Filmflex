package model; // Placed inside the core architectural model layer package

import java.io.Serializable;

/**
 * Domain Model representing the Admin entity blueprint.
 * Implements Serializable to support data streaming workflows, and demonstrates
 * strict Encapsulation, default fallback constructors, and custom flat-file serialization logic.
 */
public class Admin implements Serializable {

    // Applying the principle of Encapsulation: All instance variables are marked private
    // to protect structural component states from unintended direct external manipulation.
    private String username;
    private String password;
    private String role;
    private String lastLogin;

    /**
     * Parameterized Constructor blueprint method to initialize the entity instance state.
     * Integrates defensive evaluation logic to automatically apply a fallback role if left blank.
     */
    public Admin(String username, String password, String role) {
        this.username = username;
        this.password = password;
        // Business Rule Integration: Setting a default 'admin' role if the parameter string is null or empty
        this.role = role == null || role.trim().isEmpty() ? "admin" : role.trim();
    }

    // ── Encapsulation Interface Layer Access Methods (Getters & Setters) ──
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getLastLogin() { return lastLogin; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }

    /**
     * CUSTOM SERIALIZATION RULE: Transforms the current data Object's active runtime properties
     * directly into a single unified comma-separated flat-file record string row context.
     */
    public String toFileString() {
        // Enforcing structural comma-delimited layout mapping notation using the private safe data sanitizer helper
        return String.join(",", safe(username), safe(password), safe(role), safe(lastLogin));
    }

    /**
     * FACTORY DESIGN PATTERN: Acts as a custom data deserializer routine mapping a raw flat-file
     * comma-delimited text row entry line directly back into an instanced typed Java Admin object.
     * @param line A single record row string fetched from admins.txt
     * @return Admin object model instance, or null if structural parsing checks fail
     */
    public static Admin fromString(String line) {
        // Safe Guard Clause: Immediate rejection of empty data lines or null pointers to prevent runtime exceptions
        if (line == null || line.trim().isEmpty()) return null;

        // Slicing the row string into indices based on continuous delimiter matching tokens
        // Passing -1 preserves blank trailing spaces variables to avoid out-of-bounds mapping crashes
        String[] parts = line.split(",", -1);

        // Defensive Verification: Asserting row array index density contains at least 3 critical data properties (User, Pw, Role)
        if (parts.length < 3) return null;

        // Instantiating the core structural data model via constructor parameters allocation
        Admin admin = new Admin(parts[0].trim(), parts[1].trim(), parts[2].trim());

        // Dynamic Optional Element Mapping: Evaluating structural index 4 for optional lastLogin timestamp records data entries
        if (parts.length > 3) {
            admin.setLastLogin(parts[3].trim());
        }

        return admin;
    }

    /**
     * Null-Defensive Sanitization Helper: Guards database text record alignment boundaries.
     * Replaces any user-inputted comma symbol with a blank space to prevent catastrophic data alignment shifts.
     */
    private static String safe(String value) {
        // If attribute string is null, output empty string, otherwise trim data and prevent delimiter symbol injections
        return value == null ? "" : value.replace(",", " ").trim();
    }
}