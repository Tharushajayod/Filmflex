package service; // Placed inside the core domain business logic service package layer

import model.User;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Layer component orchestrating core business workflows for standard User profiles.
 * Manages identity registration constraints, session credential verification mappings,
 * profile field mutations, and coordinates data persistence streams directly via FileUtil wrappers.
 */
public class UserService {

    /**
     * CREATE Operation: Validates raw input entries and handles new customer account registration rules.
     * Enforces explicit business restrictions to deny duplicate email configurations inside users.txt.
     */
    public boolean registerUser(User user) throws IOException {
        // Defensive Input Validation Rule: Reject creation early if critical core properties evaluate blank
        if (user == null || isBlank(user.getFullName()) || isBlank(user.getEmail()) || isBlank(user.getPassword())) return false;

        List<User> users = getAllUsers();
        // Domain Constraint Verification: Scanning current lines storage to trace pre-existing identities profiles
        for (User existing : users) {
            if (existing.getEmail().equalsIgnoreCase(user.getEmail())) {
                return false; // Unique identifier identity conflict dropped to preserve database integrity
            }
        }

        // Fetching historical text logs lines array data bundle lists safely from storage bounds
        List<String> lines = FileUtil.readAllLines();

        // Invoking overridden toString() pattern to marshaling typed domain entity back to comma-delimited row string
        lines.add(user.toString());

        // Committing the updated collection lists arrays directly back into the flat file database structures block
        FileUtil.writeAllLines(lines);
        return true;
    }

    /**
     * BUSINESS RULE (Authentication Gateway): Validates incoming user login credentials profiles.
     * Maps across case-insensitive email indexes and exact case string matches configurations.
     * @return Fully populated User object reference if credentials pass boundaries parameters, or null if dropped
     */
    public User authenticateUser(String email, String password) throws IOException {
        // Safe Guard Clause: Immediate rejection of empty query tokens variables to preserve operational speed
        if (isBlank(email) || password == null) return null;

        // Linear Scan Execution Loop: Iterating across initialized domain entity models
        for (User user : getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email.trim()) && user.getPassword().equals(password)) {
                return user; // Safe verification matched: passing instance metrics context back to servlet session handler
            }
        }
        return null; // Authentication tracking failure fallback marker
    }

    /**
     * UPDATE Operation (Profile Details & Security Change): Selectively modifies editable properties variables.
     * Preserves pre-existing static records variables if specific fields parameters drop empty streams.
     */
    public boolean updateUser(String email, String fullName, String newPassword, String phone) throws IOException {
        if (isBlank(email) || isBlank(fullName)) return false;

        List<User> users = getAllUsers();
        boolean updated = false;
        List<String> lines = new ArrayList<>();

        // Record Matrix Traversal Mapping Loop
        for (User user : users) {
            // Locating targeted identity string record row sequence intersection
            if (user.getEmail().equalsIgnoreCase(email.trim())) {
                user.setFullName(fullName.trim());
                user.setPhone(phone == null ? "" : phone.trim()); // Formatting safety parameter tags

                // Selective Mutation Rule: Overwrite password ONLY if the new input string isn't blank
                if (!isBlank(newPassword)) {
                    user.setPassword(newPassword);
                }
                updated = true;
            }
            lines.add(user.toString()); // Re-serializing runtime state maps back into safe tracking array list layers
        }

        if (updated) {
            FileUtil.writeAllLines(lines);
        }
        return updated;
    }

    /**
     * DELETE Operation: Truncates an individual target user identity data row trace mapped via email pointer string.
     * Assured exact signature mapping matching the standalone core user controller layer logic cleanly.
     */
    public boolean deleteUser(String email) throws IOException {
        if (isBlank(email)) return false;

        List<User> users = getAllUsers();
        List<String> lines = new ArrayList<>();
        boolean deleted = false;

        // Extract-Filter-Rewrite Paradigm Loop execution
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email.trim())) {
                deleted = true; // Target matched: skip array preservation stack line to drop record entry row completely
            } else {
                // Keeping unmatched adjacent customer datasets completely unmodified inside compilation writes stream bundle
                lines.add(user.toString());
            }
        }

        if (deleted) {
            FileUtil.writeAllLines(lines);
        }
        return deleted;
    }

    /**
     * READ Operation (Global Extraction Collection): Fetches all logged lines text entries and translates them into typed arrays list.
     */
    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        for (String line : FileUtil.readAllLines()) {
            // Factory Abstraction Parser Pattern: Transforming individual text line streams back into typed User model entities
            User user = User.fromString(line);
            if (user != null) {
                users.add(user);
            }
        }
        return users; // Yielding unmarshalled business collection array lists cache data nodes
    }

    /**
     * CONDITIONAL READ (Query Single Lookup): Searches the active collections metrics maps to extract individual users by email.
     */
    public User findByEmail(String email) throws IOException {
        if (isBlank(email)) return null;
        for (User user : getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email.trim())) return user;
        }
        return null;
    }

    /**
     * Null-defensive helper validation block analyzing input parameter density states strings fields.
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}