package service; // Placed inside the core domain business logic service package layer

import model.Admin;
import util.FileUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service Layer component orchestrating core business logic algorithms for Admin accounts.
 * Acts as the structural intermediary separating HTTP Controller Servlets from direct low-level
 * File I/O operations, enforcing functional transactional rules and authorization checks.
 */
public class AdminService {

    /**
     * Default Constructor initialization node.
     * Enforces explicit bootstrap data generation rules during servlet deployment configurations.
     */
    public AdminService() {
        try {
            // Defensively seeding the file system with default administrative credentials if admins.txt is absent
            FileUtil.initializeAdminData();
        } catch (IOException ignored) {
            // Gracefully suppresses absolute initialization stream exceptions to maintain server booting
        }
    }

    /**
     * BUSINESS RULE (Authentication Matrix): Verifies administrative matching login credentials.
     * Automatically injects a live dynamic operational timestamp record loop parameters upon successful execution.
     * @return Fully populated Admin model reference if credentials match, or null if validation fails
     */
    public Admin authenticateAdmin(String username, String password) {
        // Safe Guard Clause: Immediate rejection of empty input parameters fields to preserve compute cycles
        if (isBlank(username) || password == null) return null;
        try {
            // Linear Data Stream Lookups: Scanning across all registered entity instances arrays matches
            for (Admin admin : getAllAdmins()) {
                // Identity Verification: Evaluating case-insensitive username index matching and exact case password strings
                if (admin.getUsername().equalsIgnoreCase(username.trim()) && admin.getPassword().equals(password)) {

                    // Business State Mutation: Dynamically capturing the precise real-time timestamp signature format
                    admin.setLastLogin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                    // State Overwrites Commit: Calling internal update mechanisms to log execution timestamps back into disk rows
                    updateAdmin(admin.getUsername(), admin);
                    return admin; // Target model mapped successfully
                }
            }
        } catch (IOException e) {
            // Error Trapping: Capturing background file stream failures securely without crashing active web container threads
            System.err.println("Error reading admin database during login: " + e.getMessage());
        }
        return null; // Authentication fail fallback marker
    }

    /**
     * READ Operation: Streams raw file records datasets and compiles them into a structured Java Object collection.
     */
    public List<Admin> getAllAdmins() throws IOException {
        List<Admin> admins = new ArrayList<>();
        // Fetching plain string rows from storage via custom configurations utility paths references
        for (String line : FileUtil.readAllLines(FileUtil.getAdminFilePath())) {
            // Factory Abstraction Mapping: Transforming raw delimited string arrays cleanly into domain instances
            Admin admin = Admin.fromString(line);
            if (admin != null) {
                admins.add(admin);
            }
        }
        return admins; // Yielding unmarshalled business collection array lists
    }

    /**
     * CREATE Operation: Validates uniqueness limits and appends a new Administrative data row entry onto file systems.
     * Enforces domain integrity rules to explicitly deny duplicate account name configurations.
     */
    public boolean addAdmin(Admin admin) throws IOException {
        // Validation Guard Barrier: Denying structural creation loops if mandatory attributes map inputs are absent
        if (admin == null || isBlank(admin.getUsername()) || isBlank(admin.getPassword())) return false;

        // Business Constraints Verification: Scanning lines data profiles to trace pre-existing identities
        for (Admin existing : getAllAdmins()) {
            if (existing.getUsername().equalsIgnoreCase(admin.getUsername())) {
                return false; // Unique identifier conflict dropped early to protect database consistency
            }
        }

        // Appending the newly serialized model string record directly down into permanent tracking text arrays channels
        List<String> lines = FileUtil.readAllLines(FileUtil.getAdminFilePath());
        lines.add(admin.toFileString());
        FileUtil.writeAllLines(FileUtil.getAdminFilePath(), lines);
        return true;
    }

    /**
     * UPDATE Operation: Overwrites matching historical entity records variables with updated state properties.
     * Manages conflict protection barriers and preserves static pre-existing properties values if inputs evaluate blank.
     */
    public boolean updateAdmin(String originalUsername, Admin updatedAdmin) throws IOException {
        if (isBlank(originalUsername) || updatedAdmin == null || isBlank(updatedAdmin.getUsername())) return false;

        List<Admin> admins = getAllAdmins();
        // Conflict Prevention Rule: Reject update if the new target username token is already claimed by a distinct account model
        for (Admin admin : admins) {
            if (!admin.getUsername().equalsIgnoreCase(originalUsername)
                    && admin.getUsername().equalsIgnoreCase(updatedAdmin.getUsername())) return false;
        }

        boolean updated = false;
        List<String> lines = new ArrayList<>();

        // Record Matrix Traversal Mapping Lifecycle
        for (Admin admin : admins) {
            if (admin.getUsername().equalsIgnoreCase(originalUsername)) {

                // Defensive Fallback Injection: Retain historic properties safely if update payload parameters fields are left blank
                if (isBlank(updatedAdmin.getPassword())) updatedAdmin.setPassword(admin.getPassword());
                if (isBlank(updatedAdmin.getLastLogin())) updatedAdmin.setLastLogin(admin.getLastLogin());

                lines.add(updatedAdmin.toFileString()); // Marshalling entity state properties to text format row string
                updated = true;
            } else {
                lines.add(admin.toFileString()); // Keeping adjacent rows unmodified
            }
        }

        if (updated) {
            FileUtil.writeAllLines(FileUtil.getAdminFilePath(), lines);
        }
        return updated;
    }

    /**
     * DELETE Operation: Prunes an individual system operator trace record line mapped using identification names strings.
     */
    public boolean deleteAdmin(String username) throws IOException {
        if (isBlank(username)) return false;
        List<String> lines = new ArrayList<>();
        boolean deleted = false;

        for (Admin admin : getAllAdmins()) {
            if (admin.getUsername().equalsIgnoreCase(username.trim())) {
                deleted = true; // Target found: Skip array preservation stack line to drop record entry row completely
            } else {
                lines.add(admin.toFileString());
            }
        }

        if (deleted) {
            FileUtil.writeAllLines(FileUtil.getAdminFilePath(), lines);
        }
        return deleted;
    }

    /**
     * Null-defensive helper validation block analyzing input parameter density states strings fields.
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}