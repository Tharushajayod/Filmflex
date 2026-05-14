package controller;

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
    private final AdminService adminService = new AdminService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/admin-login".equals(request.getServletPath())) handleAdminLogin(request, response);
        else handleAdminLogout(request, response);
    }

    private void handleAdminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("admin-username");
        String password = request.getParameter("admin-password");
        Admin admin = adminService.authenticateAdmin(username, password);
        if (admin == null) {
            response.sendRedirect("adminLogin.html?error=invalid");
            return;
        }
        request.getSession(true).setAttribute("admin", admin);
        request.getSession().setAttribute("isAdmin", Boolean.TRUE);
        response.sendRedirect("adminDashboard.html");
    }

    private void handleAdminLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession(false) != null) request.getSession(false).invalidate();
        response.setStatus(HttpServletResponse.SC_OK);
        ServletHelper.json(response, new org.json.JSONObject().put("message", "Logged out").toString());
    }
}
