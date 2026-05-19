package controller;

import model.Admin;
import org.json.JSONArray;
import org.json.JSONObject;
import service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/get-all-admins", "/admin-add-admin", "/admin-update-admin", "/admin-delete-admin"})
public class AdminManagementServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;
        JSONArray array = new JSONArray();
        for (Admin admin : adminService.getAllAdmins()) {
            array.put(new JSONObject()
                    .put("username", admin.getUsername())
                    .put("role", admin.getRole())
                    .put("lastLogin", admin.getLastLogin() == null ? "" : admin.getLastLogin()));
        }
        ServletHelper.json(response, array.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;
        JSONObject json = ServletHelper.readJson(request);
        String path = request.getServletPath();
        boolean ok;
        if ("/admin-add-admin".equals(path)) {
            ok = adminService.addAdmin(new Admin(json.optString("username"), json.optString("password"), json.optString("role", "admin")));
            if (!ok) {
                ServletHelper.error(response, HttpServletResponse.SC_CONFLICT, "Username already exists or data is invalid");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "Admin added successfully").toString());
        } else if ("/admin-update-admin".equals(path)) {
            Admin updated = new Admin(json.optString("username"), json.optString("password", ""), json.optString("role", "admin"));
            ok = adminService.updateAdmin(json.optString("originalUsername"), updated);
            if (!ok) {
                ServletHelper.error(response, HttpServletResponse.SC_CONFLICT, "Could not update admin");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "Admin updated successfully").toString());
        } else {
            ok = adminService.deleteAdmin(json.optString("username"));
            if (!ok) {
                ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "Admin not found");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "Admin deleted successfully").toString());
        }
    }
}
