package controller.admin; // Organized under the dedicated administrative sub-package structure

import controller.ServletHelper;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller servlet responsible for administrative User Management workflows.
 * Handles fetching all users, adding new user accounts manually, and administrative deletion.
 * Demonstrates HTTP method division (GET/POST) and safe JSON parsing constraints.
 */
@WebServlet(urlPatterns = {"/get-all-users", "/admin-add-user", "/admin-delete-user"})
public class AdminUserManagementServlet extends HttpServlet {

    // Dependency Injection of the Service Layer to separate business rule evaluation from request handling
    private final UserService userService = new UserService();

    /**
     * READ Operation: Intercepts HTTP GET requests to retrieve the full list of registered users.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Security Gatekeeping Barrier: Confirming if the active session possesses valid administrative claims
        if (!ServletHelper.requireAdmin(request, response)) return;

        // Initializing a top-level JSON Array container to parse the collected records data structure safely
        JSONArray array = new JSONArray();

        // Iterating through the domain User model entities retrieved from the persistence layer (users.txt)
        for (User user : userService.getAllUsers()) {
            // Serializing target properties into structured JSON dictionary nodes map definitions
            array.put(new JSONObject()
                    .put("fullName", user.getFullName())
                    .put("email", user.getEmail()));
        }

        // Transmitting the compiled serialized payload stream string back to the user interface layer
        ServletHelper.json(response, array.toString());
    }

    /**
     * CREATE & DELETE Operations: Intercepts HTTP POST requests to mutate user datasets context blocks.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Authorization Guard Clause: Restricting downstream modifications exclusively to verified administrators
        if (!ServletHelper.requireAdmin(request, response)) return;

        // Ingesting the incoming request reader buffer data stream and converting it into a JSONObject dictionary
        JSONObject json = ServletHelper.readJson(request);

        // Identifying the operational intent dynamically by interrogating the matching servlet mapping path
        if ("/admin-add-user".equals(request.getServletPath())) {

            // ── 1. CREATE OPERATION PATHWAY (MANUAL REGISTRATION) ──
            // Instantiating a new User data object utilizing null-defensive .optString() input mapping methods
            User user = new User(
                    json.optString("fullName"),
                    json.optString("email"),
                    json.optString("password"),
                    "" // Passing an empty string placeholder for optional phone parameter values
            );

            // Delegating core data write verification tasks down to the Service Layer layer node
            boolean saved = userService.registerUser(user);
            if (!saved) {
                // Violating database unique constraints (e.g., trying to write a duplicate user email row marker)
                ServletHelper.error(response, HttpServletResponse.SC_CONFLICT, "Email already exists or data is invalid");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "User added successfully").toString());

        } else {

            // ── 2. DELETE OPERATION PATHWAY ──
            // Extracting unique key identifiers strings tokens to execute data record deletion mechanics
            boolean deleted = userService.deleteUser(json.optString("email"));
            if (!deleted) {
                // Gracefully responding with an HTTP 404 error if the specified identity index record is missing
                ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "User deleted successfully").toString());
        }
    }
}