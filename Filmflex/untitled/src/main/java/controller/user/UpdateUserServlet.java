package controller.user; // Organized under the dedicated user management sub-package structure

import controller.ServletHelper;
import model.User;
import org.json.JSONObject;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller servlet handling account profile modifications and security password changes.
 * Demonstrates state validation metrics, loose coupling with the service layer,
 * and dynamic session data refresh mechanics upon data mutation workflows.
 */
@WebServlet("/update-profile")
public class UpdateUserServlet extends HttpServlet {

    // Dependency Injection of the Service Layer to decouple routing logic from file write transactions
    private final UserService userService = new UserService();

    /**
     * Intercepts HTTP POST request streams to update user profile fields or account security configurations.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Security Authentication Barrier: Extracting the active customer context model from current thread state
        User currentUser = ServletHelper.currentUser(request);
        if (currentUser == null) {
            // Guard clause termination utilizing standard HTTP 401 Unauthorized status if token is missing
            ServletHelper.error(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        try {
            // Parsing the incoming request reader buffer stream data directly into a JSON structural layout
            JSONObject json = ServletHelper.readJson(request);

            // ── ROUTING PATHWAY A: SECURITY PASSWORD MODIFICATION WORKFLOW ──
            // Conditional Routing: Checking if payload dictionary maps contain safety password fields indicators
            if (json.has("currentPw") || json.has("newPw")) {
                String currentPw = json.optString("currentPw", "");
                String newPw = json.optString("newPw", "");

                // Cryptographic validation rule verification against stored object state credentials
                if (!currentUser.getPassword().equals(currentPw)) {
                    // Instantly fail transaction if raw input does not match historical metadata matches (HTTP 400)
                    ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "Current password verification failed!");
                    return;
                }

                // Invoking service layer algorithm logic to rewrite user credential lines inside users.txt database records
                userService.updateUser(currentUser.getEmail(), currentUser.getFullName(), newPw, currentUser.getPhone());

                // State Sync: Fetching freshly committed text file mutations back to synchronize current runtime context
                User refreshed = userService.findByEmail(currentUser.getEmail());
                request.getSession(true).setAttribute("user", refreshed); // Overwriting active session instance attribute mappings

                ServletHelper.json(response, new JSONObject().put("message", "Password updated successfully").toString());
                return;
            }

            // ── ROUTING PATHWAY B: PERSONAL PROFILE METADATA MODIFICATION WORKFLOW ──
            // Triggered fallback option if payload contains regular descriptive non-security traits attributes fields
            String fullName = json.optString("fullName", currentUser.getFullName());
            String phone = json.optString("phone", "");

            // Enforcing business rule data integrity boundaries by rejecting blank names spaces
            if (fullName.trim().isEmpty()) {
                ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "Full name is required");
                return;
            }

            // Passing modifications down onto the service layer persistent record mutation channels
            boolean updated = userService.updateUser(currentUser.getEmail(), fullName, "", phone);
            if (!updated) {
                // Return standard HTTP 500 Internal Server error status if data write processing routines fail
                ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Update failed");
                return;
            }

            // State Cache Invalidation and Dynamic Session Sync Strategy
            User refreshed = userService.findByEmail(currentUser.getEmail());
            request.getSession(true).setAttribute("user", refreshed); // Overwriting old session reference object container

            ServletHelper.json(response, new JSONObject().put("message", "Profile details updated successfully").toString());

        } catch (Exception e) {
            // Error trapping block catching unexpected structural formatting drops parameters anomalies
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}