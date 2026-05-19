package controller.admin; // Organized under the dedicated administrative sub-package structure

import controller.ServletHelper;
import model.Admin;
import org.json.JSONArray;
import org.json.JSONObject;
import service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller servlet managing the complete CRUD operations pipeline for Admin accounts.
 * Demonstrates advanced multi-url mapping patterns, HTTP method division,
 * data validation handling, and secure RESTful JSON payload data processing.
 */
@WebServlet(urlPatterns = {"/get-all-admins", "/admin-add-admin", "/admin-update-admin", "/admin-delete-admin"})
public class AdminManagementServlet extends HttpServlet {

    // Dependency Injection of the Service Layer to decouple server requests from file-handling processing
    private final AdminService adminService = new AdminService();

    /**
     * READ Operation: Handles incoming HTTP GET requests to retrieve the full list of administrators.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Security Access Guard Barrier Check: Terminate process if request session lacks admin claims
        if (!ServletHelper.requireAdmin(request, response)) return;

        // Initializing a secure JSON Array container to structure collection data records
        JSONArray array = new JSONArray();

        // Mapping the domain Entity objects list directly into standard serialized key-value data models
        for (Admin admin : adminService.getAllAdmins()) {
            array.put(new JSONObject()
                    .put("username", admin.getUsername())
                    .put("role", admin.getRole())
                    // Guarding against potential NullPointerExceptions if lastLogin timestamp fields are empty
                    .put("lastLogin", admin.getLastLogin() == null ? "" : admin.getLastLogin()));
        }

        // Dispatching the populated serialization layout back onto the asynchronous user interface layer
        ServletHelper.json(response, array.toString());
    }

    /**
     * CREATE, UPDATE, DELETE Operations: Handles incoming HTTP POST requests to mutate system data state.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Authorization Barrier Check: Intercepting execution to confirm strict administrative access levels
        if (!ServletHelper.requireAdmin(request, response)) return;

        // Extracting and parsing the raw input payload data directly into a JSON structured dictionary object
        JSONObject json = ServletHelper.readJson(request);

        // Interrogating the servlet path to dynamically identify routing targets across multi-mapped urls patterns
        String path = request.getServletPath();
        boolean ok;

        // ── 1. CREATE OPERATION PATHWAY ──
        if ("/admin-add-admin".equals(path)) {
            // Mapping incoming parameters using safe null-defensive .optString() data access methods
            Admin newAdmin = new Admin(
                    json.optString("username"),
                    json.optString("password"),
                    json.optString("role", "admin") // Applying a default role parameter fallback strategy
            );

            ok = adminService.addAdmin(newAdmin);
            if (!ok) {
                // Handling transaction conflict bounds errors (e.g., entity username string collision in text database records)
                ServletHelper.error(response, HttpServletResponse.SC_CONFLICT, "Username already exists or data is invalid");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "Admin added successfully").toString());

            // ── 2. UPDATE OPERATION PATHWAY ──
        } else if ("/admin-update-admin".equals(path)) {
            Admin updated = new Admin(
                    json.optString("username"),
                    json.optString("password", ""),
                    json.optString("role", "admin")
            );

            // Passing down both unique identifying record markers alongside modified entity structures data blocks
            ok = adminService.updateAdmin(json.optString("originalUsername"), updated);
            if (!ok) {
                ServletHelper.error(response, HttpServletResponse.SC_CONFLICT, "Could not update admin");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "Admin updated successfully").toString());

            // ── 3. DELETE OPERATION PATHWAY ──
        } else {
            // Isolating parameter tokens directly to trigger single element truncation workflows
            ok = adminService.deleteAdmin(json.optString("username"));
            if (!ok) {
                // Return Standard HTTP 404 Status if target identity target element index is absent in permanent flat files
                ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "Admin not found");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "Admin deleted successfully").toString());
        }
    }
}