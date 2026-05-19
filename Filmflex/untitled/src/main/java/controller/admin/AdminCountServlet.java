package controller.admin; // Organized under the dedicated administrative sub-package structure

import controller.ServletHelper;
import org.json.JSONObject;
import service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller servlet responsible for retrieving the total count of registered administrators.
 * Demonstrates secure endpoint guard management, service layer integration, and dynamic JSON serialization.
 */
@WebServlet(name = "AdminCountServlet", urlPatterns = "/get-admin-count")
public class AdminCountServlet extends HttpServlet {

    // Dependency Injection of the Service Layer to separate data aggregation from presentation logic
    private final AdminService adminService = new AdminService();

    /**
     * Overridden HTTP doGet handler managing incoming data retrieval request streams.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Authorization Barrier: Validating if the current session context contains an authentic admin token
        // If the verification check fails inside ServletHelper, execution is immediately aborted via guard clause
        if (!ServletHelper.requireAdmin(request, response)) {
            return;
        }

        // 1. Interacting with the Service Layer to gather the complete collection array of administrators
        // 2. Utilizing the .size() collection method to calculate the integer metric total representation
        // 3. Dynamically parsing and encapsulating the integer metric key-value pair inside a new JSONObject framework
        String jsonResponse = new JSONObject()
                .put("adminCount", adminService.getAllAdmins().size())
                .toString();

        // Transmitting the compiled payload text stream string back to the frontend asynchronous caller
        ServletHelper.json(response, jsonResponse);
    }
}