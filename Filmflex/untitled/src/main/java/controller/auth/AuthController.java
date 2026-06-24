package controller.auth;

import model.Admin;
import model.User;
import service.AdminService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/login", "/register"})
public class AuthController extends HttpServlet {

    // Dependency Injection of Domain Services to isolate request processing from transactional business rules
    private final UserService userService = new UserService();
    private final AdminService adminService = new AdminService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Dynamic Path Routing: Isolating the specific URL trigger token pattern matching context
        String path = request.getServletPath();

        if ("/register".equals(path)) {
            handleRegister(request, response);
        } else if ("/login".equals(path)) {
            handleLogin(request, response);
        } else {
            // Safe fallback response if an unmapped endpoint manages to pass through filters
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Extracting form-encoded string parameters safely from the client request payload fields
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        // Defensive Input Validation: Ensuring non-blank inputs and verification of identical passwords match
        if (isBlank(name) || isBlank(email) || isBlank(password) || !password.equals(confirmPassword)) {
            response.sendRedirect("register.html?error=invalid");
            return;
        }

        // Instantiating a new User Model domain entity safely conforming to the updated 4-parameter constructor rules
        boolean saved = userService.registerUser(new User(name.trim(), email.trim(), password, ""));

        // Applying Conditional Flow Control Redirects based on downstream flat-file persistence file write outcomes
        response.sendRedirect(saved ? "login.html?registered=true" : "register.html?error=exists");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // This unified parameter variable abstracts either a regular User's email string OR an Admin's plain username token
        String usernameOrEmail = request.getParameter("email");
        String password = request.getParameter("password");

        // Configuring corporate web communication structures enforcing standard JSON response criteria
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Safe Guard Clause: Immediate rejection if required payload credentials fields are null or empty
        if (isBlank(usernameOrEmail) || password == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("{\"status\":\"error\",\"message\":\"Missing credentials\"}");
            return;
        }

        // ── STEP 1: ADMINISTRATIVE AUTHENTICATION BARRIER GATEWAY ──
        // Evaluating inputs against the admin metadata records sequence (admins.txt)
        Admin admin = adminService.authenticateAdmin(usernameOrEmail, password);
        if (admin != null) {
            // State Persistence: Binding the validated Admin Model context onto a new secure HTTP Session lifecycle node
            request.getSession(true).setAttribute("admin", admin);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"status\":\"success\",\"role\":\"admin\"}");
            return;
        }

        // ── STEP 2: STANDARD USER AUTHENTICATION BARRIER GATEWAY ──
        // If administrative evaluation yields null, fall down to regular customer user data store mapping verification (users.txt)
        User user = userService.authenticateUser(usernameOrEmail, password);
        if (user != null) {
            // Binding verified customer instance onto active server state session tracker context metrics cache
            request.getSession(true).setAttribute("user", user);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"status\":\"success\",\"role\":\"user\"}");
            return;
        }

        // ── STEP 3: AUTHENTICATION FAILURE CORNER NODE ──
        // Triggered only if both data repository verification evaluations fail to identify valid system operators
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401 Unauthorized parameters injection rule
        out.print("{\"status\":\"error\",\"message\":\"Invalid credentials\"}");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}