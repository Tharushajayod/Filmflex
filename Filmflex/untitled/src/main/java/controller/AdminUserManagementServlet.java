package controller;

import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/get-all-users", "/admin-add-user", "/admin-delete-user"})
public class AdminUserManagementServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;
        JSONArray array = new JSONArray();
        for (User user : userService.getAllUsers()) {
            array.put(new JSONObject().put("fullName", user.getFullName()).put("email", user.getEmail()));
        }
        ServletHelper.json(response, array.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;
        JSONObject json = ServletHelper.readJson(request);
        if ("/admin-add-user".equals(request.getServletPath())) {
            User user = new User(json.optString("fullName"), json.optString("email"), json.optString("password"), "");            boolean saved = userService.registerUser(user);
            if (!saved) {
                ServletHelper.error(response, HttpServletResponse.SC_CONFLICT, "Email already exists or data is invalid");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "User added successfully").toString());
        } else {
            boolean deleted = userService.deleteUser(json.optString("email"));
            if (!deleted) {
                ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "User deleted successfully").toString());
        }
    }
}
