package controller.admin;

import controller.ServletHelper;
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

@WebServlet(urlPatterns = {
        "/get-all-admins",
        "/admin-add-admin",
        "/admin-update-admin",
        "/admin-delete-admin",
        "/get-current-admin-profile" // FIX: Handled dynamic routing template endpoint here
})
public class AdminManagementServlet extends HttpServlet {

    private final AdminService adminService = new AdminService();

    /**
     * READ Operation: Handles requests to view administrators list or current logged admin session profile attributes.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Security Access Guard Barrier Check: Terminate process if request session lacks admin claims
        if (!ServletHelper.requireAdmin(request, response)) return;

        String path = request.getServletPath();
        Admin currentAdmin = ServletHelper.currentAdmin(request);

        // ── WORKFLOW HOOK 1: GET CURRENT LOGGED IN ADMIN PROFILE DATA ──
        if ("/get-current-admin-profile".equals(path)) {
            if (currentAdmin == null) {
                ServletHelper.error(response, HttpServletResponse.SC_UNAUTHORIZED, "Session expired");
                return;
            }
            // Serializing active operator traits metrics seamlessly to help frontend hide dashboard nodes
            JSONObject profile = new JSONObject()
                    .put("username", currentAdmin.getUsername())
                    .put("role", currentAdmin.getRole());
            ServletHelper.json(response, profile.toString());
            return; // Halt process propagation cleanly
        }

        // ── WORKFLOW HOOK 2: GET ALL REGISTERED ADMINS LIST (SUPER ADMIN ONLY) ──
        // Fail-Safe Interception Barrier Rule: Block normal admin scopes early from reading records grids
        if (currentAdmin == null || !"superadmin".equalsIgnoreCase(currentAdmin.getRole())) {
            ServletHelper.error(response, HttpServletResponse.SC_FORBIDDEN, "Access Denied: Requires Super Admin privileges");
            return;
        }

        JSONArray array = new JSONArray();
        // Mapping the domain Entity objects list directly into standard serialized key-value data models
        for (Admin admin : adminService.getAllAdmins()) {
            array.put(new JSONObject()
                    .put("username", admin.getUsername())
                    .put("role", admin.getRole())
                    .put("lastLogin", admin.getLastLogin() == null ? "" : admin.getLastLogin()));
        }

        ServletHelper.json(response, array.toString());
    }

    /**
     * CREATE, UPDATE, DELETE Operations: Handles state data mutations strictly limiting to Super Admins.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;

        // CRITICAL ROLE ENFORCEMENT: block lower admin ranks programmatically from mutating data rows
        Admin currentAdmin = ServletHelper.currentAdmin(request);
        if (currentAdmin == null || !"superadmin".equalsIgnoreCase(currentAdmin.getRole())) {
            ServletHelper.error(response, HttpServletResponse.SC_FORBIDDEN, "Access Denied: Requires Super Admin privileges");
            return;
        }

        JSONObject json = ServletHelper.readJson(request);
        String path = request.getServletPath();
        boolean ok;

        // ── 1. CREATE OPERATION PATHWAY ──
        if ("/admin-add-admin".equals(path)) {
            Admin newAdmin = new Admin(
                    json.optString("username"),
                    json.optString("password"),
                    json.optString("role", "admin")
            );

            ok = adminService.addAdmin(newAdmin);
            if (!ok) {
                ServletHelper.error(response, HttpServletResponse.SC_CONFLICT, "Username already exists or data is invalid");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "Admin added successfully").toString());

            // ── 2. UPDATE OPERATION PATHWAY ──
        } else if ("/admin-update-admin".equals(path)) {
            Admin updated = new Admin(
                    json.optString("username"),
                    json.optString("password", ""),
                    json.optString("role", "admin")
            );

            ok = adminService.updateAdmin(json.optString("originalUsername"), updated);
            if (!ok) {
                ServletHelper.error(response, HttpServletResponse.SC_CONFLICT, "Could not update admin");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "Admin updated successfully").toString());

            // ── 3. DELETE OPERATION PATHWAY ──
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