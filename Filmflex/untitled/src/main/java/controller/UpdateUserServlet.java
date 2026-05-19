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

// Fixed: Matched mapping context directly to clear 404 connection dropped errors completely
@WebServlet("/update-profile")
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

            // Checking if password route invocation block triggered
            if (json.has("currentPw") || json.has("newPw")) {
                String currentPw = json.optString("currentPw", "");
                String newPw = json.optString("newPw", "");

                if (!currentUser.getPassword().equals(currentPw)) {
                    ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "Current password verification failed!");
                    return;
                }

                userService.updateUser(currentUser.getEmail(), currentUser.getFullName(), newPw, currentUser.getPhone());
                User refreshed = userService.findByEmail(currentUser.getEmail());
                request.getSession(true).setAttribute("user", refreshed);
                ServletHelper.json(response, new JSONObject().put("message", "Password updated successfully").toString());
                return;
            }

            // Otherwise, route into personal profile metadata update blocks
            String fullName = json.optString("fullName", currentUser.getFullName());
            String phone = json.optString("phone", "");

            if (fullName.trim().isEmpty()) {
                ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "Full name is required");
                return;
            }

            boolean updated = userService.updateUser(currentUser.getEmail(), fullName, "", phone);
            if (!updated) {
                ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Update failed");
                return;
            }

            User refreshed = userService.findByEmail(currentUser.getEmail());
            request.getSession(true).setAttribute("user", refreshed);
            ServletHelper.json(response, new JSONObject().put("message", "Profile details updated successfully").toString());

        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}