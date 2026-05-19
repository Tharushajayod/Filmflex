package controller.user; // Organized under the dedicated user management sub-package structure

import controller.ServletHelper;
import org.json.JSONObject;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller servlet responsible for retrieving the total count of registered customer users.
 * Demonstrates administrative authorization enforcement, service layer data fetching,
 * and standard JSON serialization for dashboard analytics view metrics.
 */
@WebServlet(name = "UserCountServlet", urlPatterns = "/get-user-count")
public class UserCountServlet extends HttpServlet {

    // Dependency Injection: Instantiating the service layer to decouple web routing from core data parsing logic
    private final UserService userService = new UserService();

    /**
     * READ Operation: Intercepts incoming HTTP GET data stream requests to expose metric analytics profiles.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Authorization Barrier Gate: Verifying if the active request session contains legitimate administrative tokens
        // If the checking evaluation fails inside ServletHelper, downstream execution is instantly halted via guard clause
        if (!ServletHelper.requireAdmin(request, response)) return;

        // 1. Calling the Service Layer node to fetch the complete collections list of User domain entities (users.txt)
        // 2. Invoking the .size() collection utility parameter to calculate the total integer count density directly
        // 3. Encapsulating the computed integer metric inside a clean serialized key-value JSONObject dictionary
        String jsonResponse = new JSONObject()
                .put("userCount", userService.getAllUsers().size())
                .toString();

        // Transmitting the structured transaction parameters back to the browser asynchronous AJAX interface script
        ServletHelper.json(response, jsonResponse);
    }
}