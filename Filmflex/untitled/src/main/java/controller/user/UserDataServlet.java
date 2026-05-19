package controller.user; // Organized under the dedicated user management sub-package structure

import controller.ServletHelper;
import model.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller servlet responsible for retrieving the profile data of the currently logged-in user.
 * Fetches the user session context securely and serializes core profile attributes into a JSON object mapping.
 * Demonstrates Session tracking, data protection encapsulation, and asynchronous RESTful communication.
 */
@WebServlet("/user-data")
public class UserDataServlet extends HttpServlet {

    /**
     * READ Operation: Intercepts incoming HTTP GET requests to expose the active user's account details.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Security Gatekeeping Barrier: Extracting the authenticated User model context from the current session state
        User user = ServletHelper.currentUser(request);

        // Guard Clause: Terminating the transaction instantly if the caller is unauthenticated or session is stale
        if (user == null) {
            // Returning a standard HTTP 401 Unauthorized status code to signal web safety barriers
            ServletHelper.error(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        // Data Encapsulation & DTO Pattern: Packaging domain model attributes map into a transferrable format
        JSONObject obj = new JSONObject();
        obj.put("fullName", user.getFullName());
        obj.put("email", user.getEmail());
        obj.put("phone", user.getPhone()); // Maps and sends the active phone token parameter directly to the interface

        // Transmitting the compiled serialized serialization string layout back onto the browser client layer
        ServletHelper.json(response, obj.toString());
    }
}