package controller;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/register"})
public class AuthController extends HttpServlet {
    private final UserService userService = new UserService();

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
        boolean saved = userService.registerUser(new User(name.trim(), email.trim(), password));
        response.sendRedirect(saved ? "login.html?registered=true" : "register.html?error=exists");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = userService.authenticateUser(request.getParameter("email"), request.getParameter("password"));
        if (user == null) {
            response.sendRedirect("login.html?error=invalid");
            return;
        }
        request.getSession(true).setAttribute("user", user);
        response.sendRedirect("UserDashboard.html");
    }

    private boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
}
