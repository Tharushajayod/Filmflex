package controller;

import model.User;
import org.json.JSONObject;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update-user")
public class UpdateUserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = ServletHelper.currentUser(request);
        if (currentUser == null) {
            ServletHelper.error(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        try {
            JSONObject json = ServletHelper.readJson(request);
            String fullName = json.optString("fullName", currentUser.getFullName());
            String currentPassword = json.optString("currentPassword", "");
            String newPassword = json.optString("newPassword", "");

            if (fullName.trim().isEmpty()) {
                ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "Full name is required");
                return;
            }
            if (!newPassword.trim().isEmpty() && !currentUser.getPassword().equals(currentPassword)) {
                ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "Current password is incorrect");
                return;
            }
            boolean updated = userService.updateUser(currentUser.getEmail(), fullName, newPassword);
            if (!updated) {
                ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Update failed");
                return;
            }
            User refreshed = userService.findByEmail(currentUser.getEmail());
            request.getSession(true).setAttribute("user", refreshed);
            ServletHelper.json(response, new JSONObject().put("message", "Profile updated successfully").toString());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
