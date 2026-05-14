package controller;

import org.json.JSONObject;
import service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminCountServlet", urlPatterns = "/get-admin-count")
public class AdminCountServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;
        ServletHelper.json(response, new JSONObject().put("adminCount", adminService.getAllAdmins().size()).toString());
    }
}
