package controller.admin;

import controller.ServletHelper;
import model.Admin;
import service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "AdminAuthController", urlPatterns = {"/admin-login", "/admin-logout"})
public class AdminAuthController extends HttpServlet {

    // Dependency Injection of the Service Layer to abstract underlying core verification algorithms
    private final AdminService adminService = new AdminService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Dynamic path checking: Deciding whether to process an authentication entry or session termination
        if ("/admin-login".equals(request.getServletPath())) {
            handleAdminLogin(request, response);
        } else {
            handleAdminLogout(request, response);
        }
    }

    private void handleAdminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Extracting string parameters safely encapsulated from the incoming form submission payload fields
        String username = request.getParameter("admin-username");
        String password = request.getParameter("admin-password");

        // Loose Coupling: Delegating strict authentication logic rules execution to the Service Layer entity
        Admin admin = adminService.authenticateAdmin(username, password);

        // Conditional Guard Clauses: Halting downstream operations if validation against permanent files fails
        if (admin == null) {
            // Redirecting backend application flow state back onto user interface with an error parameter flag
            response.sendRedirect("adminLogin.html?error=invalid");
            return;
        }

        // State Persistence Management: Initializing a new secure HTTP Session to persist user context state
        request.getSession(true).setAttribute("admin", admin); // Binding the authenticated Model object instance
        request.getSession().setAttribute("isAdmin", Boolean.TRUE); // Explicit state flag for security authorization barriers

        // Forwarding execution loop control onto the secure administrative management interface dashboard view layer
        response.sendRedirect("adminDashboard.html");
    }

    private void handleAdminLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Checking for pre-existing active memory session states without initializing blank instances
        if (request.getSession(false) != null) {
            // Invalidating the current session context to permanently flush session authorization variables cached
            request.getSession(false).invalidate();
        }

        // Configuring HTTP 200 OK standard validation status response parameters onto headers matrix
        response.setStatus(HttpServletResponse.SC_OK);

        // Utilizing a helper component to respond with a clean, standardized JSON transaction confirmation message
        ServletHelper.json(response, new org.json.JSONObject().put("message", "Logged out").toString());
    }
}