package controller;

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
    private final UserService userService = new UserService();
    private final AdminService adminService = new AdminService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/register".equals(path)) {
            handleRegister(request, response);
        } else if ("/login".equals(path)) {
            handleLogin(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        if (isBlank(name) || isBlank(email) || isBlank(password) || !password.equals(confirmPassword)) {
            response.sendRedirect("register.html?error=invalid");
            return;
        }

        // FIXED: Added the 4th parameter "" (empty string) to match the new User constructor signature safely
        boolean saved = userService.registerUser(new User(name.trim(), email.trim(), password, ""));
        response.sendRedirect(saved ? "login.html?registered=true" : "register.html?error=exists");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // This parameter holds either the User's email OR the Admin's plain username
        String usernameOrEmail = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (isBlank(usernameOrEmail) || password == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("{\"status\":\"error\",\"message\":\"Missing credentials\"}");
            return;
        }

        // 1. Try to authenticate as Admin using the plain username/email
        Admin admin = adminService.authenticateAdmin(usernameOrEmail, password);
        if (admin != null) {
            request.getSession(true).setAttribute("admin", admin);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"status\":\"success\",\"role\":\"admin\"}");
            return;
        }

        // 2. If it's not an admin, try to authenticate as regular User
        User user = userService.authenticateUser(usernameOrEmail, password);
        if (user != null) {
            request.getSession(true).setAttribute("user", user);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"status\":\"success\",\"role\":\"user\"}");
            return;
        }

        // 3. Both failed
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        out.print("{\"status\":\"error\",\"message\":\"Invalid credentials\"}");
    }

    private boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
}