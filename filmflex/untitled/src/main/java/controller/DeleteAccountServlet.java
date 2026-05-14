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

@WebServlet("/delete-account")
public class DeleteAccountServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = ServletHelper.currentUser(request);
        if (currentUser == null) {
            ServletHelper.error(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        boolean deleted = userService.deleteUser(currentUser.getEmail());
        if (request.getSession(false) != null) request.getSession(false).invalidate();
        ServletHelper.json(response, new JSONObject().put("message", deleted ? "Account deleted" : "Account not found").toString());
    }
}
